package com.rabbit.mq.practice.mqlogs;

import com.rabbitmq.client.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * @author zhugp
 * @description TODO
 * @date 2019/12/13 17:48
 */
public class LogSaveFileConsumer {
    private static final String EXCHAGE_NAME = "log_exchage_fanout";

    public static void main(String args[]) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("192.168.1.94");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHAGE_NAME, "fanout");
        System.out.println("[日志保存者启动]");
        channel.basicConsume(queueName,false, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                long deliveryTag = envelope.getDeliveryTag();
                String message = new String(body, StandardCharsets.UTF_8);
                save2LocalFile(message);
                channel.basicAck(deliveryTag, false);
            }
        });
    }

    private static void save2LocalFile(String log) {
        try
        {
            String dir = "D://";
            String logFileName = new SimpleDateFormat("yyyy-MM-dd")
                    .format(new Date());
            File file = new File(dir, logFileName+".txt");
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write((log + "\r\n").getBytes());
            fos.flush();
            fos.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
