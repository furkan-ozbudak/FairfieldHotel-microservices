#!/usr/bin/env python3
import json

import pika
import sys
import os
from mail import MessageService

ms = MessageService()
ms = MessageService()
mq_host = os.environ.get('MQ_HOST') or 'localhost'
mq_port = os.environ.get('MQ_PORT') or '5672'
mq_user = os.environ.get('MQ_USER') or 'guest'
mq_pswd = os.environ.get('MQ_PSWD') or 'guest'
mq_credentials = pika.PlainCredentials(mq_user, mq_pswd)

connection = pika.BlockingConnection(
    pika.ConnectionParameters(host=mq_host,port=mq_port,credentials=mq_credentials))
channel = connection.channel()

channel.exchange_declare(exchange='mail-topic', exchange_type='topic',durable=True)

result = channel.queue_declare(queue='', durable=True)
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
    ms.sendEmailMessage(data['email'],data['content'])


channel.basic_consume(
    queue=queue_name, on_message_callback=callback, auto_ack=True)

channel.start_consuming()