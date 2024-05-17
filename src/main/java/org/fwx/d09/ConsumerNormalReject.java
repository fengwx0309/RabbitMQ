package org.fwx.d09;

import com.rabbitmq.client.Channel;
import org.fwx.utils.RabbitMQUtils;

/**
 * @ClassName ConsumerNormalReject
 * @Description TODO
 * @Author Fwx
 * @Date 2024/5/15 20:19
 * @Version 1.0
 */
public class ConsumerNormalReject {

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
        // 设置为false，表示消费完成后不发送确认消息
        channel.basicConsume(Envionment.NORMAL_QUEUE,false,
                // 消息处理回调函数
                (s, message) -> {
                    String data = new String(message.getBody());
                    if ("info5".equals(data)){
                        System.out.println("ConsumerNormalReject 消费者拒绝消息：" + data);
                        // 拒绝消息，并设置requeue为false，表示将消息不放回队列
                        channel.basicReject(message.getEnvelope().getDeliveryTag(),false);
                    } else {
                        System.out.println("ConsumerNormalReject 接收到的消息是：" + data);
                        // 确认消息，表示消息已被处理
                        channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
                    }
                },
                // 消费者启动完成后的回调函数，此示例中未做任何操作
                s ->{});
    }

}
