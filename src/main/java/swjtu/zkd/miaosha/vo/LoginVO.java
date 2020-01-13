package swjtu.zkd.miaosha.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import swjtu.zkd.miaosha.validator.IsMobile;

import javax.validation.constraints.NotNull;

@Data
public class LoginVO {

    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min = 10)
    private String password;
}
