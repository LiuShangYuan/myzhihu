package cn.scut.zhihu.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * User: yinkai
 * Date: 2019-11-09
 * Time: 11:27
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);


    @Before("execution(* cn.scut.zhihu.controller.*Controller.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();
        for (Object arg : joinPoint.getArgs()) {
            if (arg != null) {
                sb.append("arg:" + arg.toString() + "|");
            }
        }

        logger.info("before method:" + sb.toString());
    }

    @After("execution(* cn.scut.zhihu.controller.IndexController.*(..))")
    public void afterMethod() {
        logger.info("after method" + new Date());
    }

}
