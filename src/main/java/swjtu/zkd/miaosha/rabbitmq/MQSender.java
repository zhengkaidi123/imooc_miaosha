package swjtu.zkd.miaosha.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swjtu.zkd.miaosha.redis.RedisService;

@Service
public class MQSender {

    private static Logger logger = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

//    public void send(Object message) {
//        String msg = RedisService.beanToString(message);
//        logger.info("send message: " + msg);
//        amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
//    }

    public void sendMiaoshaMessage(MiaoshaMessage miaoshaMessage) {
        String msg = RedisService.beanToString(miaoshaMessage);
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE, msg);
    }

//    public void sendTopic(Object message) {
//        String msg = RedisService.beanToString(message);
//        logger.info("send topic message: " + msg);
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key1", msg + "1");
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key2", msg + "2");
//    }
//
//    public void sendFanout(Object message) {
//        String msg = RedisService.beanToString(message);
//        logger.info("send fanout message: " + msg);
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "", msg + "1");
//    }
//
//    public void sendHeaders(Object message) {
//        String msg = RedisService.beanToString(message);
//        logger.info("send headers message: " + msg);
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setHeader("header1", "value1");
//        messageProperties.setHeader("header2", "value2");
//        Message message1 = new Message(msg.getBytes(), messageProperties);
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "", message1);
//    }
}
