package org.fwx.rabbitmqboot.mqconsumer;

import lombok.extern.slf4j.Slf4j;
import org.fwx.rabbitmqboot.config.ConfirmExchangeConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName ConfirmExchangeConsumer
 * @Description TODO
 * @Author Fwx
 * @Date 2024/5/16 9:55
 * @Version 1.0
 */
@Slf4j
@Component
public class ConfirmExchangeConsumer {

    @RabbitListener(queues = ConfirmExchangeConfig.CONFIRM_QUEUE_NAME)
    public void receiveConfirmExchange(Message message) {
        log.info("接收到消息：{}", new String(message.getBody()));
    }
}
