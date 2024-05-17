package org.fwx.d01;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Consumer
 * @Description TODO
 * @Author Fwx
 * @Date 2024/5/15 10:43
 * @Version 1.0
 */
public class Consumer {
    /**
     * 主函数：创建一个连接到RabbitMQ服务器的程序，并从队列中消费消息。
     *
     * @param args 命令行参数（未使用）
     * @throws IOException 如果在与RabbitMQ服务器的通信中发生IO错误
     * @throws TimeoutException 如果建立连接时超时
     */
    public static void main(String[] args) throws IOException, TimeoutException {
        // 定义要发送消息的主题
        String topic = "topic1";

        // 创建ConnectionFactory并设置RabbitMQ服务器的主机名、端口、用户名和密码
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.2.100");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("123");

        // 使用ConnectionFactory创建一个到RabbitMQ服务器的新连接
        Connection connection = factory.newConnection();

        // 在连接上创建一个Channel
        Channel channel = connection.createChannel();

        // 定义消息接收回调函数
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("接收到的消息：" + new String(message.getBody()));
        };

        // 定义取消消费的回调函数
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息消费被中断");
        };

        //
        /**
         * 开始消费队列"hello"中的消息，采用自动确认模式
         *
         * @param queueName 要订阅的队列名称，此处为"hello"。
         * @param autoAck 是否自动确认消息，true为自动确认。
         * @param deliverCallback 消息到达时的回调处理函数。
         * @param cancelCallback 取消订阅时的回调处理函数。
         */
        channel.basicConsume(topic,true,deliverCallback,cancelCallback);
    }
}
