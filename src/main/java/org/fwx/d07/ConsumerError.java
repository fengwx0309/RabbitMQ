package org.fwx.d07;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import org.fwx.utils.RabbitMQUtils;

/**
 * @ClassName ConsumerError
 * @Description TODO
 * @Author Fwx
 * @Date 2024/5/15 16:36
 * @Version 1.0
 */
public class ConsumerError {
    private static final String EXCHANGE_NAME = "direct_exchange";

    /**
     * 主函数：创建RabbitMQ的通道，声明交换器，声明队列并将其绑定到交换器上，然后开始消费队列中的消息。
     * @param args 命令行参数（未使用）
     * @throws Exception 抛出异常处理通用错误
     */
    public static void main(String[] args) throws Exception {
        // 获取RabbitMQ的通道
        Channel channel = RabbitMQUtils.getChannel();

        // 声明一个直接类型的交换器
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        // 声明一个非持久、非排他、非自动删除的队列
        channel.queueDeclare("disk",false,false,false,null);

        // 将队列绑定到交换器上，指定绑定键为"disk"
        channel.queueBind("disk",EXCHANGE_NAME,"error");

        // 开始消费队列中的消息，消费时打印消息内容
        channel.basicConsume("disk",true,
                (consumerTag, message) -> {
                    // 当消费者接收到消息时，打印消息内容
                    System.out.println("disk-error 接收到的消息为：" + new String(message.getBody()));
                },
                consumerTag -> {});
    }

}
