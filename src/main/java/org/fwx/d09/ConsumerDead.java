package org.fwx.d09;

import com.rabbitmq.client.Channel;
import org.fwx.utils.RabbitMQUtils;

/**
 * @ClassName ConsumerDead
 * @Description TODO
 * @Author Fwx
 * @Date 2024/5/15 20:28
 * @Version 1.0
 */
public class ConsumerDead {
    /**
     * 主函数入口，用于启动RabbitMQ消息消费者的死信队列处理。
     * @param args 命令行参数，此示例中未使用。
     * @throws Exception 抛出异常，处理过程中的任何异常都可能被抛出。
     */
    public static void main(String[] args) throws Exception {
        // 获取与RabbitMQ服务器的连接通道
        Channel channel = RabbitMQUtils.getChannel();

        // 订阅死信队列的消息，采用自动确认模式
        channel.basicConsume(Envionment.DEAD_QUEUE,true,
                // 消息处理回调函数，接收到消息后进行处理
                (s, message) -> {
                    // 打印接收到的消息内容
                    System.out.println("ConsumerDead 接收到的消息是：" + new String(message.getBody()));
                },
                // 消费者启动完成后的回调函数，此示例中未做任何操作
                s ->{});
    }

}
