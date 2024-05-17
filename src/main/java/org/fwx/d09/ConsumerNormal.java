package org.fwx.d09;

import com.rabbitmq.client.Channel;
import org.fwx.utils.RabbitMQUtils;

/**
 * @ClassName ConsumerNormal
 * @Description TODO
 * @Author Fwx
 * @Date 2024/5/15 20:19
 * @Version 1.0
 */
public class ConsumerNormal {

    /**
     * 主函数入口，用于启动RabbitMQ的消费者监听指定队列。
     *
     * @param args 命令行参数，此程序未使用该参数。
     * @throws Exception 如果在获取RabbitMQ通道或消费消息过程中发生错误，则抛出异常。
     */
    public static void main(String[] args) throws Exception {
        // 获取与RabbitMQ服务器的连接通道
        Channel channel = RabbitMQUtils.getChannel();

        // 启动对正常队列的消息消费
        channel.basicConsume(Envionment.NORMAL_QUEUE,true,
                // 消息处理回调函数
                (s, message) -> {
                    // 打印接收到的消息内容
                    System.out.println("ConsumerNormal 接收到的消息是：" + new String(message.getBody()));
                },
                // 消费者启动完成后的回调函数，此示例中未做任何操作
                s ->{});
    }

}
