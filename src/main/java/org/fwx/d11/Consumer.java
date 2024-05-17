package org.fwx.d11;

import com.rabbitmq.client.Channel;
import org.fwx.utils.RabbitMQUtils;

/**
 * @ClassName Consumer
 * @Description TODO
 * @Author Fwx
 * @Date 2024/5/16 23:35
 * @Version 1.0
 */
public class Consumer {
    private static final String QUEUE_NAME = "priority_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtils.getChannel();

        channel.basicConsume(QUEUE_NAME, true, (consumerTag, message) -> {
            System.out.println("接收到的消息：" + new String(message.getBody()));
        }, consumerTag -> {
        });

    }
}
