package com.rabbit.mq.practice.mqtask;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author zhugp
 * @description TODO
 * @date 2019/12/13 16:17
 */
public class Worker1 {

    public static final String QUEUE_NAME = "mq_task_queue";


    public static void main(String args[]) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.94");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        boolean autoAck = false;
        channel.basicQos(1);
        channel.basicConsume(QUEUE_NAME, autoAck, "", new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                long deliveryTag = envelope.getDeliveryTag();
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println("worker2 收到消息:"+message);
                work(message);
                System.out.println("worker2 done");
                channel.basicAck(deliveryTag, false);
            }
        });

    }

    public static void work(String str) {
        char[] strarr = str.toCharArray();
        for (char c : strarr) {
            if (c == '.') {
                try {
                    Thread.sleep(2_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
