#!/usr/bin/env python3
import json

import pika
import os

mq_host = os.environ.get('MQ_HOST') or 'localhost'
connection = pika.BlockingConnection(
    pika.ConnectionParameters(host=mq_host))
channel = connection.channel()

channel.exchange_declare(exchange='mail-topic', exchange_type='topic', durable=True)

routing_key = 'eapx.mail'
message = {
    "email": "bruce1988cn@gmail.com",
    "content": "Test"
}
channel.basic_publish(
    exchange='mail-topic', routing_key=routing_key, body=json.dumps(message))
print(" [x] Sent %r:%r" % (routing_key, message))
connection.close()