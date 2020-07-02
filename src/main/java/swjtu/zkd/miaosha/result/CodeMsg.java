package swjtu.zkd.miaosha.result;

import lombok.Getter;

@Getter
public class CodeMsg {

    //通用异常
    public static final CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static final CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static final CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");
    public static final CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102, "非法请求");
    public static final CodeMsg ACCESS_LIMIT_REACHED = new CodeMsg(500103, "访问太频繁");
    //登陆模块  5002XX
    public static final CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或已失效");
    public static final CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "密码不能为空");
    public static final CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号不能为空");
    public static final CodeMsg MOBILE_ERROR = new CodeMsg(500213, "手机号码格式错误");
    public static final CodeMsg USER_NOT_EXIST = new CodeMsg(500214, "用户不存在");
    public static final CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密码错误");

    //商品模块  5003XX

    //订单模块  5004XX
    public static final CodeMsg ORDER_NOT_EXISTS = new CodeMsg(500400, "订单不存在");

    //秒杀模块  5005XX
    public static final CodeMsg MIAOSHA_OVER = new CodeMsg(500500, "商品已秒杀完毕");
    public static final CodeMsg REPEATED_MIAOSHA = new CodeMsg(500501, "不能重复秒杀");
    public static final CodeMsg MIAOSHA_FAIL = new CodeMsg(500502, "秒杀失败");

    private int code;

    private String msg;

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }
}
