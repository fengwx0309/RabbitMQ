d01:简单的生产、消费
d02:生产者产生多条数据，多个消费者消费

d03:多个消费者消费数据，如果某个消费者接收到数据，但是程序出现异常退出，则该条消息会重新发送给其他消费者
    需要对消费者应答设置为false，并在消费者中设置消息应答模式为手动应答

d04:设置队列持久化，消息持久化
     消息不公平分配策略:
        默认轮询分发消息
        设置值为1时，谁消费的快，消费完成就分配消息给谁
        预取值：值需要大于1，消费者按设置的值来获取对应的条数，比如预取值为5，则消费者每次获取5条消息

d05:消息发布确认
        1、单挑数据发布确认
        2、批量发布确认
        3、异步发布确认

d06:交换机 fanout 扇出/广播模式，发布消息到交换器，交换器将消息广播给所有绑定的队列。
d07:交换机 direct 直接模式，根据路由键进行消息投递
d08:交换机 topic 模式
d09:三种死信情况：消息 TTL 过期
                队列达到最大长度(队列满了，无法再添加数据到 mq 中)
                消息被拒绝(basic.reject 或 basic.nack)并且 requeue=false

d10:RabbitMQBoot Springboot 整合 RabbitMQ，添加了swagger2(暂时没用，有配置类swagger-ui.html可访问)
    1.交换器topic模式：org.fwx.rabbitmqboot.controller.RabbitMQTopicController
    2.延迟队列：org.fwx.rabbitmqboot.controller.RabbitMQDelayedController

    3.确认发布高级：org.fwx.rabbitmqboot.controller.ConfirmExchangeController
        1) yml:publisher-confirm-type: correlated # 开启消息确认，消息发送到交换机后，成功或失败都会返回一个确认信息
            publisher-returns: true            # 开启消息返回，消息发送到队列失败会返回一个消息

        2) 配回调接口，交换机确认，队列消息回退：org.fwx.rabbitmqboot.config.RabbitCallBackAndRetureConfig

        3) 备份交换机、备份队列、报警队列：org.fwx.rabbitmqboot.config.ConfirmExchangeConfig
           正常队列有问题时，首先会转发到备份交换机，如果没有备份交换机，则会调用退回消息接口

d11:优先级队列，参数设置：x-max-priority。需要先把数据全部发完，再启动消费者

