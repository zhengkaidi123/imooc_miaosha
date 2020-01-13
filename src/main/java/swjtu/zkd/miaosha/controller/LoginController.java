package swjtu.zkd.miaosha.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import swjtu.zkd.miaosha.result.Result;
import swjtu.zkd.miaosha.service.MiaoshaUserService;
import swjtu.zkd.miaosha.vo.LoginVO;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private MiaoshaUserService userService;

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(@Valid LoginVO loginVO, HttpServletResponse response) {
//        log.info(loginVO.toString());
//        if (loginVO == null) {
//            return Result.error(CodeMsg.MOBILE_EMPTY);
//        }
//        String password = loginVO.getPassword();
//        String mobile = loginVO.getMobile();
//        if (StringUtils.isEmpty(password)) {
//            return Result.error(CodeMsg.PASSWORD_EMPTY);
//        }
//        if (StringUtils.isEmpty(mobile)) {
//            return Result.error(CodeMsg.MOBILE_EMPTY);
//        }
//        if (!ValidatorUtil.isMobile(mobile)) {
//            return Result.error(CodeMsg.MOBILE_ERROR);
//        }
        userService.login(loginVO, response);
        return Result.success(true);
    }
}
