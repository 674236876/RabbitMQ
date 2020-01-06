package com.rabbit.mq.demo.customer;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

/**
 * @author zhugp
 * @description TODO
 * @date 2019/12/11 9:52
 */
public class Recv {
    private static String QUEUE_NAME = "hello";
    private static int i = 0;

    public static void main(String args[]) throws Exception{
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.94");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("recv 接受信息啦");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("[x] Received ' " + message + "'（已处理"+(i++)+"）");
        };

        String consumeTag = channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });

        System.out.println("consume标识符：" + consumeTag + "(被用来取消消费者的)");



    }

}
