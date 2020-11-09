package edu.mum.chatserver.server;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;


@Component
public class MQController {
    @Value("${spring.rabbitmq.host}")
    private String host = "rabbitmq-service-rabbitmq-ha";

    @Value("${spring.rabbitmq.port}")
    private Integer port = 5672;

    @Value("${spring.rabbitmq.username}")
    private String username = "guest";

    @Value("${spring.rabbitmq.password}")
    private String password = "StZfT4AM18Id0S9wDEGCiG28";
    @Autowired
    private MsgController msgController;
    ConnectionFactory connectionFactory = new ConnectionFactory();

    private Channel channelSend;
    private Channel channelRcv;
    private static final String EXCHANGE_NAME = "chat_msg";
    private String queueName;


    public MQController() throws IOException, TimeoutException {
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        System.out.println(connectionFactory.getHost());
        Connection connection = (Connection) connectionFactory.newConnection();
        channelSend = connection.createChannel();
        channelSend.exchangeDeclare(EXCHANGE_NAME, "topic");
        channelRcv = connection.createChannel();

        channelRcv.exchangeDeclare(EXCHANGE_NAME, "topic");
        queueName = channelRcv.queueDeclare().getQueue();

    }

    public synchronized void  publish(String routingKey, String message) throws IOException {
        channelSend.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
    }

    public synchronized void bindKey(String bindingKey) throws IOException {
        channelRcv.queueBind(queueName, EXCHANGE_NAME, bindingKey);
    }

    public synchronized void unbindKey(String bindingKey) throws IOException {
        channelRcv.queueUnbind(queueName, EXCHANGE_NAME, bindingKey);
    }

    public void startRcv(){
        new Thread() {
            public void run() {
                System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), "UTF-8");
                    System.out.println(" [x] Received '" +
                            delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
                    msgController.logic(message,null);
                };
                try {
                    channelRcv.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }





}
