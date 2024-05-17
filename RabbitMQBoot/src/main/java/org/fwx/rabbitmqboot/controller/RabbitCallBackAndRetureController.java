package org.fwx.rabbitmqboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.fwx.rabbitmqboot.config.ConfirmExchangeConfig;
import org.fwx.rabbitmqboot.config.RabbitCallBackAndRetureConfig;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RabbitCallBackAndRetureController
 * @Description
 *              测试：http://localhost:8080/callback/send/你好！
 * @Author Fwx
 * @Date 2024/5/16 20:54
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/callback")
public class RabbitCallBackAndRetureController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/send/{msg}")
    public void send(@PathVariable("msg") String msg){
        // 1.成功接收消息
//        CorrelationData correlationData1 = new CorrelationData("1");
//        rabbitTemplate.convertAndSend(ConfirmExchangeConfig.CONFIRM_EXCHANGE_NAME,ConfirmExchangeConfig.CONFIRM_ROUTING_KEY,msg+"@1",correlationData1);
//        log.info("消息发送成功:" + msg+"@1");

        // 2.交换机有问题时
//        CorrelationData correlationData2 = new CorrelationData("2");
//        rabbitTemplate.convertAndSend(ConfirmExchangeConfig.CONFIRM_EXCHANGE_NAME+"@2",ConfirmExchangeConfig.CONFIRM_ROUTING_KEY,msg+"@2",correlationData2);
//        log.info("消息发送成功:" + msg+"@2");

        // 3.队列有问题时
        // 正常队列有问题时，首先会转发到备份交换机，如果没有备份交换机，则会调用退回消息接口
        CorrelationData correlationData3 = new CorrelationData("3");
        rabbitTemplate.convertAndSend(ConfirmExchangeConfig.CONFIRM_EXCHANGE_NAME,ConfirmExchangeConfig.CONFIRM_ROUTING_KEY+"@3",msg+"@3",correlationData3);
        log.info("消息发送成功:" + msg+"@3");
    }
}
