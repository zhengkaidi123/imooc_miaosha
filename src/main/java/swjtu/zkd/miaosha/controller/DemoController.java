package swjtu.zkd.miaosha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import swjtu.zkd.miaosha.result.CodeMsg;
import swjtu.zkd.miaosha.result.Result;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello() {
        return Result.success("hello, imooc");
    }

    @RequestMapping("/error")
    @ResponseBody
    public Result<String> error() {
        return Result.error(CodeMsg.SERVER_ERROR);
    }
}
