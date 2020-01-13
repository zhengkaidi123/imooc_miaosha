package swjtu.zkd.miaosha.exception;

import lombok.Data;
import swjtu.zkd.miaosha.result.CodeMsg;

@Data
public class GlobalException extends RuntimeException {

    private CodeMsg codeMsg;

    public GlobalException() {

    }

    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }
}
