package com.rabbit.mq.practice.mqtask;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhugp
 * @description TODO
 * @date 2019/12/13 16:17
 */
public class NewTask {

    public static final String QUEUE_NAME = "mq_task_queue";


    public static void main(String args[]) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.94");
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            for (int i = 0; i < 10; i++) {
                String dots = "";
                for (int j = 0; j <= i; j++) {
                    dots += ".";
                }
                String message = "helloworld" + dots + dots.length();
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
//                持久化队列信息，当队列中保存有信息的情况下，服务器关闭，下次也会保留。 默认是不会保留的
//                channel.basicPublish("", QUEUE_NAME,MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

                System.out.println(" [x] Sent '" + message + "'");
            }

        }
    }
}
