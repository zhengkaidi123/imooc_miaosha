package swjtu.zkd.miaosha.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swjtu.zkd.miaosha.dao.MiaoshaUserDAO;
import swjtu.zkd.miaosha.domain.MiaoshaUser;
import swjtu.zkd.miaosha.exception.GlobalException;
import swjtu.zkd.miaosha.redis.RedisService;
import swjtu.zkd.miaosha.redis.UserKey;
import swjtu.zkd.miaosha.result.CodeMsg;
import swjtu.zkd.miaosha.util.MD5Util;
import swjtu.zkd.miaosha.util.UUIDUtil;
import swjtu.zkd.miaosha.vo.LoginVO;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    private MiaoshaUserDAO miaoshaUserDAO;

    @Autowired
    private RedisService redisService;

    public MiaoshaUser getById(long userId) {
        return miaoshaUserDAO.getById(userId);
    }

    public boolean login(LoginVO loginVO, HttpServletResponse response) {
        if (loginVO == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVO.getMobile();
        MiaoshaUser user = getById(Long.parseLong(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }
        String formPassword = loginVO.getPassword();
        String dbPassword = user.getPassword();
        String dbSalt = user.getSalt();
        String calcPassword = MD5Util.formPassToDBPass(formPassword, dbSalt);
        if (!dbPassword.equals(calcPassword)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        String token = UUIDUtil.uuid();
        addCookie(response, user, token);
        return true;
    }

    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser user = redisService.get(UserKey.tokenPrefix, token, MiaoshaUser.class);
        if (user != null) {
            addCookie(response, user, token);
        }
        return user;
    }

    private void addCookie(HttpServletResponse response, MiaoshaUser user, String token) {
        // 生成cookie
        redisService.set(UserKey.tokenPrefix, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.tokenPrefix.getExpireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
