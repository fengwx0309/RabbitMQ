package org.fwx.rabbitmqboot.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ComfirmExchangeConfig
 * @Description
 * @Author Fwx
 * @Date 2024/5/16 9:39
 * @Version 1.0
 */
@Configuration
public class ConfirmExchangeConfig {

    // 确认交换机的名称
    public static final String CONFIRM_EXCHANGE_NAME = "confirm_exchange";
    // 确认队列的名称
    public static final String CONFIRM_QUEUE_NAME = "confirm_queue";
    // 确认路由键的名称
    public static final String CONFIRM_ROUTING_KEY = "confirm_routingkey";
    // 备份交换机的名称
    public static final String BACKUP_EXCHANGE_NAME = "backup_exchange";
    // 备份队列的名称
    public static final String BACKUP_QUEUE_NAME = "backup_queue";
    // 警告队列的名称
    public static final String WARNING_QUEUE_NAME = "warning_queue";




    // ----------------------------- start 普通交换机(跳转备份交换机)，普通队列，绑定 -----------------------------
    // 发布确认交换机
    @Bean(name = "confirmExchange")
    public DirectExchange confirmExchange() {
        //return new DirectExchange(CONFIRM_EXCHANGE_NAME);
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME)
                // 配置备份交换机
                .withArgument("alternate-exchange", BACKUP_EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    // 发布确认队列
    @Bean(name = "confirmQueue")
    public Queue confirmQueue() {
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }

    // 绑定
    @Bean(name = "confirmExchangeBindingComfirmQueue")
    public Binding confirmExchangeBindingComfirmQueue(@Qualifier("confirmQueue") Queue queue,
                                                      @Qualifier("confirmExchange") DirectExchange confirmExchange){
        return BindingBuilder.bind(queue).to(confirmExchange).with(CONFIRM_ROUTING_KEY);
    }
    // ----------------------------- end 普通交换机(跳转备份交换机)，普通队列，绑定 -----------------------------

    // ----------------------------- start 备份交换机，备份队列，报警队列，绑定 -----------------------------
    @Bean(name = "backupExchange")
    public FanoutExchange backupExchange() {
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }

    @Bean(name = "backupQueue")
    public Queue backupQueue() {
        return QueueBuilder.durable(BACKUP_QUEUE_NAME).build();
    }

    @Bean(name = "warningQueue")
    public Queue warningQueue() {
        return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
    }

    @Bean(name = "backupExchangeBindingBackupQueue")
    public Binding backupExchangeBindingBackupQueue(@Qualifier("backupQueue") Queue backupQueue,
                                                    @Qualifier("backupExchange") FanoutExchange backupExchange){
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }

    @Bean(name = "backupExchangeBindingWarningQueue")
    public Binding backupExchangeBindingWarningQueue(@Qualifier("warningQueue") Queue warningQueue,
                                                     @Qualifier("backupExchange") FanoutExchange backupExchange){
        return BindingBuilder.bind(warningQueue).to(backupExchange);
    }
    // ----------------------------- end 备份交换机，备份队列，报警队列，绑定 -----------------------------
}
