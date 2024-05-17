package org.fwx.d09;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.fwx.utils.RabbitMQUtils;

/**
 * @ClassName Producer
 * @Description 死信队列生产者
 *              三种死信情况：消息 TTL 过期
 *                          队列达到最大长度(队列满了，无法再添加数据到 mq 中)
 *                          消息被拒绝(basic.reject 或 basic.nack)并且 requeue=false.
 * @Author Fwx
 * @Date 2024/5/15 20:01
 * @Version 1.0
 */
public class Producer {

    /**
     * 主函数：用于演示如何向RabbitMQ发送消息。
     *
     * @param args 命令行参数（未使用）
     * @throws Exception 如果初始化环境或发送消息过程中发生错误，则抛出异常
     */
    public static void main(String[] args) throws Exception {
        // 测试前记得删除队列和交换器
        // 1.初始化交换器和队列的环境
        Envionment.init();
        // 2.初始化交换机和队列，并设置队列长度为6,超出长度的消息将被认定为死信。
        //Envionment.initWithNormalQueueLength();

        // 获取与RabbitMQ服务器的通信通道
        Channel channel = RabbitMQUtils.getChannel();

        // 循环发送10条消息
        for (int i = 0; i < 10; i++) {
            // 消息内容为 "info" 加上当前循环次数
            String message = "info" + i;

            /**
             * 1.测试数据时间超时后进入死信队列
             */
            // 创建消息属性，设置消息过期时间
            /*AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().expiration("10000").build();
            // 向指定交换器发送消息，消息路由键为预定义的关键字
            channel.basicPublish(Envionment.NORMAL_EXCHANGE, Envionment.NORMAL_ROUTING_KEY, basicProperties, message.getBytes());*/

            /**
             * 2.测试消息超过队列长度或被拒绝后进入死信队列
             */
            // 向指定交换器发送消息，消息路由键为预定义的关键字
            channel.basicPublish(Envionment.NORMAL_EXCHANGE, Envionment.NORMAL_ROUTING_KEY, null, message.getBytes());

            // 打印消息发送完成的提示
            System.out.println("发送消息完成：" + message);
        }
    }

}
