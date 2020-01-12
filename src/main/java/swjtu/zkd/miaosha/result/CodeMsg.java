package swjtu.zkd.miaosha.result;

import lombok.Getter;

@Getter
public class CodeMsg {

    //通用异常
    public static final CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static final CodeMsg SERVER_ERROR = new CodeMsg(50010, "server error");
    //登陆模块  5002XX

    //商品模块  5003XX

    //订单模块  5004XX

    //秒杀模块  5005XX

    private int code;

    private String msg;

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
