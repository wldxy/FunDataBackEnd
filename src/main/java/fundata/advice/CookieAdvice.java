package fundata.advice;

import fundata.configure.Constants;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by huang on 17-3-10.
 */
@Component
@Aspect
public class CookieAdvice {
    @AfterReturning(value = "execution(* fundata.control.AuthorizeController.sign*(..))",
            returning = "result")
    public void addCookie(Map<String, String> result){
        String token = result.get("token");
        if("".equals(token) == false) {
            ServletRequestAttributes servletContainer = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletResponse response = servletContainer.getResponse();
            Cookie cookie = new Cookie(Constants.AUTHORIZATION, result.get("userId") + "_" + token);
            cookie.setDomain(Constants.FRONT_DOMAIN);
            cookie.setPath(Constants.COOKIE_PATH);
            cookie.setMaxAge(Constants.TOKEN_EXPIRES_HOUR*60*60);
            cookie.setHttpOnly(false);
            response.addCookie(cookie);
            result.clear();
            result.put("code", "200");
        }
        else {
            result.clear();
            result.put("code", "0");
        }
    }

    @AfterReturning(value = "execution(* fundata.control.AuthorizeController.logOut(..))",
            returning = "result")
    public void deleteCookie(Map<String, String> result){
        String token = result.get("token");
        if("".equals(token) == false) {
            ServletRequestAttributes servletContainer = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletResponse response = servletContainer.getResponse();
            Cookie cookie = new Cookie(Constants.AUTHORIZATION, null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            result.clear();
            result.put("code", "200");
        }
        else {
            result.clear();
            result.put("code", "0");
        }
    }
}

