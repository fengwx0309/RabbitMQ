package org.fwx.rabbitmqboot.mqconsumer;

import lombok.extern.slf4j.Slf4j;
import org.fwx.rabbitmqboot.config.ConfirmExchangeConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName BackupExchangeConsumer
 * @Description TODO
 * @Author Fwx
 * @Date 2024/5/16 22:43
 * @Version 1.0
 */
@Slf4j
@Component
public class BackupExchangeConsumer {

    @RabbitListener(queues = ConfirmExchangeConfig.BACKUP_QUEUE_NAME)
    public void receiveBackupExchange(String msg) {
        log.info("接收到备份交换机备份队列消息：{}", msg);
    }

    @RabbitListener(queues = ConfirmExchangeConfig.WARNING_QUEUE_NAME)
    public void receiveWarningExchange(String msg) {
        log.info("接收到备份交换机报警队列消息：{}", msg);
    }
}
