package org.fwx.d03;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.fwx.utils.RabbitMQUtils;

/**
 * @ClassName Consumer2
 * @Description 防止数据丢失
 *              需要对消费者应答设置为false，并在消费者中设置消息应答模式为手动应答
 * @Author Fwx
 * @Date 2024/5/15 13:45
 * @Version 1.0
 */
public class Consumer2 {
    private static final String QUEUE_NAME = "topic3";

    /**
     * 主函数：创建一个RabbitMQ的消费者，用于接收并处理队列中的消息。
     * @param args 命令行参数（未使用）
     * @throws Exception 抛出异常的条件不明确，因为取决于RabbitMQ操作和线程操作的结果
     */
    public static void main(String[] args) throws Exception {
        // 获取与RabbitMQ服务器的连接通道
        Channel channel = RabbitMQUtils.getChannel();

        // 定义消息接收回调函数
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            // 打印接收到的消息内容
            System.out.println("Consumer2接收到的消息为：" + new String(message.getBody()));
            try {
                // 模拟消息处理过程，暂停5秒
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // 将线程中断异常转换为运行时异常
                throw new RuntimeException(e);
            }
            // 确认消息处理成功
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };

        // 设置自动确认消息为false
        boolean autoAck = false;
        // 订阅队列消息，开始消费
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, (CancelCallback) -> {
            // 处理消费取消的回调操作
            System.out.println("消费者取消消费的回调");
        });
    }

}
