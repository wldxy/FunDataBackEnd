package fundata.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by huang on 17-3-10.
 */
@Component
@Aspect
public class ExceptionAdvice {
    @AfterThrowing(value = "execution(* fundata.control..*(..))",
            throwing = "npException")
    public void NullPointerException(JoinPoint joinPoint, NullPointerException npException) {
        System.out.println("NullPointerException:" + joinPoint);
    }

}
