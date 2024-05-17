package org.fwx.rabbitmqboot.mqconsumer;

import lombok.extern.slf4j.Slf4j;
import org.fwx.rabbitmqboot.config.DelayedQueueConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName RabbitMQDelayedConsumer
 * @Description RabbitMQDelayedConsumer 类用于消费延时队列的消息。
 * @Author Fwx
 * @Date 2024/5/16 1:19
 * @Version 1.0
 */
@Slf4j
@Component
public class RabbitMQDelayedConsumer {

    /**
     * 监听延时队列，接收并处理消息。
     *
     * @param message 接收到的消息对象，包含消息体。
     */
    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE_NAME)
    public void receiveDelayedQueue(Message message){
        // 将消息体转换为字符串
        String msg = new String(message.getBody());
        // 记录接收到消息的时间和内容
        log.info("当前时间：{},收到延时队列的消息：{}", new Date().toString(), msg);
    }
}

