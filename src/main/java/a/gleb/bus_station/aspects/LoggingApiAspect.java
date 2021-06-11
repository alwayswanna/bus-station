package a.gleb.bus_station.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingApiAspect {

    @Pointcut("execution(* a.gleb.bus_station.api.*.*(..))")
    private void allMethodInApiPackage(){}

    @Before("allMethodInApiPackage()")
    public void beforeApiMethodsAdvice(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        log.info("User work with API method: " + signature.getMethod().getName());
    }

    @AfterThrowing(pointcut = "allMethodInApiPackage()", throwing = "exception")
    public void afterApiMethodThrowException(RuntimeException exception) throws Throwable{
        log.warn("User work with API method. Throws exception: "
            + exception.getMessage());

    }
}
