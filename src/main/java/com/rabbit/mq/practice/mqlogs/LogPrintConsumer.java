package com.rabbit.mq.practice.mqlogs;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author zhugp
 * @description TODO
 * @date 2019/12/13 17:48
 */
public class LogPrintConsumer {
    private static final String EXCHAGE_NAME = "log_exchage_fanout";

    public static void main(String args[]) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("192.168.1.94");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHAGE_NAME, "fanout");
        System.out.println("[日志打印者启动]");
        channel.basicConsume(queueName,false, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                long deliveryTag = envelope.getDeliveryTag();
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println(message);
                channel.basicAck(deliveryTag, false);
            }
        });
    }

}
