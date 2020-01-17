package swjtu.zkd.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import swjtu.zkd.miaosha.rabbitmq.MQSender;
import swjtu.zkd.miaosha.result.Result;

//@Controller
public class SampleController {

//    @Autowired
//    private MQSender mqSender;

//    @RequestMapping("/mq")
//    @ResponseBody
//    public Result<String> mq() {
//        mqSender.send("hello, imooc");
//        return Result.success("hello world");
//    }
//
//    @RequestMapping("/mq/topic")
//    @ResponseBody
//    public Result<String> topic() {
//        mqSender.sendTopic("hello, imooc");
//        return Result.success("hello world");
//    }
//
//    @RequestMapping("/mq/fanout")
//    @ResponseBody
//    public Result<String> fanout() {
//        mqSender.sendFanout("hello");
//        return Result.success("hello world");
//    }
//
//    @RequestMapping("/mq/headers")
//    @ResponseBody
//    public Result<String> headers() {
//        mqSender.sendHeaders("hello");
//        return Result.success("hello world");
//    }
}
