package org.fwx.d06;

import com.rabbitmq.client.Channel;
import org.fwx.utils.RabbitMQUtils;

/**
 * @ClassName Consumer01
 * @Description TODO
 * @Author Fwx
 * @Date 2024/5/15 16:15
 * @Version 1.0
 */
public class Consumer01 {

    private static final String EXCHANGE_NAME = "fanout_exchange";

    /**
     * 主函数，用于演示RabbitMQ的简单消费者模式。
     * 该函数不返回任何结果，但会监听指定的队列，一旦有消息到来，就会打印出来。
     *
     * @param args 命令行参数（未使用）
     * @throws Exception 如果与RabbitMQ的交互过程中出现错误，则会抛出异常
     */
    public static void main(String[] args) throws Exception {
        // 获取与RabbitMQ服务器的连接通道
        Channel channel = RabbitMQUtils.getChannel();

        // 声明一个扇形交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        // 声明一个队列，并返回队列名
        String queueName = channel.queueDeclare().getQueue();

        // 将队列绑定到交换器上，绑定键为空字符串
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        // 消费者开始消费队列中的消息
        channel.basicConsume(queueName, true,
                (consumerTag, message) -> {
                    // 当消费者接收到消息时，打印消息内容
                    System.out.println("消费者01接收到的消息为：" + new String(message.getBody()));
                },
                consumerTag -> {});

    }

}
