package swjtu.zkd.miaosha.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import swjtu.zkd.miaosha.domain.MiaoshaUser;
import swjtu.zkd.miaosha.exception.GlobalException;
import swjtu.zkd.miaosha.result.CodeMsg;
import swjtu.zkd.miaosha.service.MiaoshaUserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private MiaoshaUserService userService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return MiaoshaUser.class == clazz;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        String paramToken = request.getParameter(MiaoshaUserService.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request, MiaoshaUserService.COOKIE_NAME_TOKEN);
        if (!StringUtils.isEmpty(cookieToken) || !StringUtils.isEmpty(paramToken)) {
            String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
            MiaoshaUser user = userService.getByToken(response, token);
            if (user != null) {
                return user;
            }
        }
        throw new GlobalException(CodeMsg.SESSION_ERROR);
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length <= 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
