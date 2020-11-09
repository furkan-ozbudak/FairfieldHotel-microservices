#!/usr/bin/env python
import json

import pika
import sys
from py import mail
import os

ms = mail.MessageService()
mq_host = os.environ.get('MQ_HOST') or 'localhost'

connection = pika.BlockingConnection(
    pika.ConnectionParameters(host=mq_host))
channel = connection.channel()

channel.exchange_declare(exchange='mail-topic', exchange_type='topic')

result = channel.queue_declare(queue='', exclusive=True)
queue_name = result.method.queue

binding_keys = ['eapx.mail']
if not binding_keys:
    sys.stderr.write("Usage: %s [binding_key]...\n" % sys.argv[0])
    sys.exit(1)

for binding_key in binding_keys:
    channel.queue_bind(
        exchange='mail-topic', queue=queue_name, routing_key=binding_key)

print(' [*] Waiting for logs. To exit press CTRL+C')


def callback(ch, method, properties, body):
    data = json.loads(str(body,'utf-8'))
    ms.sendEmailMessage(data['mail'],data['content'])


channel.basic_consume(
    queue=queue_name, on_message_callback=callback, auto_ack=True)

channel.start_consuming()