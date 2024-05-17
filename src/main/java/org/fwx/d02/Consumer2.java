package org.fwx.d02;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import org.fwx.utils.RabbitMQUtils;

/**
 * @ClassName Consumer
 * @Description TODO
 * @Author Fwx
 * @Date 2024/5/15 13:02
 * @Version 1.0
 */
public class Consumer2 {
    private static final String QUEUE_NAME = "topic2";

    public static void main(String[] args) throws Exception {
        // 1. 创建连接
        Connection connection = RabbitMQUtils.getConnection();

        // 2. 创建channel
        Channel channel = connection.createChannel();

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("消费者2接收到的消息：" + new String(message.getBody()));
        };

        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消费者2取消消费回调逻辑");
        };

        // 5. 创建消费者
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);

    }
}
