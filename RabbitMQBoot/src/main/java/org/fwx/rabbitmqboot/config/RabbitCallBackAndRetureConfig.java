package org.fwx.rabbitmqboot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @ClassName RabbitCallBackAndRetureConfig
 * @Description 消息发送失败回调
 * @Author Fwx
 * @Date 2024/5/16 17:59
 * @Version 1.0
 */
@Slf4j
@Component
public class RabbitCallBackAndRetureConfig implements RabbitTemplate.ReturnCallback,RabbitTemplate.ConfirmCallback {

    /**
     * 自动注入RabbitMQ的模板类，用于发送消息及处理回调。
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 初始化方法，设置消息发送的确认回调和返回回调。
     */
    @PostConstruct
    public void init(){
        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.setConfirmCallback(this);
    }

    /**
     * 确认回调方法，当消息被服务器成功消费或拒绝时被调用。
     *
     * @param correlationData 与消息发送相关联的数据，用于追踪消息。
     * @param ack 消息是否被成功确认。
     * @param cause 如果消息未被确认，原因字符串。
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (ack){
            log.info("交换机收到消息，id:{}",id);
        }else {
            log.info("交换机没有收到消息，id:{},原因:{}",id,cause);
        }
    }

    /**
     * 返回回调方法，当消息无法被正确投递到队列时被调用。
     *
     * @param message 被返回的消息内容。
     * @param replyCode AMQP返回码。
     * @param replyText 返回的文本信息。
     * @param exchange 消息尝试投递的交换机。
     * @param routingKey 尝试使用的路由键。
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息发送失败，消息内容:{},交换机:{},路由键:{}",message,exchange,routingKey);
    }

}
