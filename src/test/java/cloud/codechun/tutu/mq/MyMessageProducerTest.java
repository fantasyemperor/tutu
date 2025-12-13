package cloud.codechun.tutu.mq;

import cloud.codechun.tutu.mq.MyMessageProducer;
import com.rabbitmq.client.Channel;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.handler.annotation.Header;


// 使用 @SpringBootTest 注解标记该类为一个 Spring Boot 的测试类
@SpringBootTest
class MyMessageProducerTest {

    // 使用 @Resource 注解注入一个消息生产者的实例
    @Resource
    private MyMessageProducer myMessageProducer;
    @Autowired
    private MyMessageConsumer myMessageConsumer;

    // 使用 @Test 注解标记一个测试方法，命名为 sendMessage
    @Test
    /**
     * 在测试方法中，调用消息生产者的 sendMessage 方法，发送一条消息
     *
     * 参数1：交换机名称为 "code_exchange"，表示将消息发送到该交换机
     * 参数2：路由键为 "my_routingKey"，表示消息将通过该路由键进行路由
     * 参数3：消息内容为 "你好呀"，即要发送的具体消息内容
     */
    void sendMessage() {
        // 调用消息生产者的 sendMessage 方法发送消息
        myMessageProducer.sendMessage("code_exchange", "my_routingKey", "你好呀");
    }


}

