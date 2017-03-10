package fundata.advice;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
            response.addCookie(new Cookie("token", result.get("userId") + "_" + token));
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
            Cookie cookie = new Cookie("token", null);
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

