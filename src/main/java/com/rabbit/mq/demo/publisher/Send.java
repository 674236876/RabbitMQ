package com.rabbit.mq.demo.publisher;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author zhugp
 * @description TODO
 * @date 2019/12/10 17:59
 */
public class Send {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.94");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "我丢了一条信息过去云端了";
            for (int i = 0; i < 1000; i++) {
//                第一个参数是转发器。默认标识符是“ ”，第二个参数决定发送到那个队列
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            }
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
