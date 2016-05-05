package com.cd.test.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by chendong on 15-11-24.
 */
@Aspect
public class AspectAsp {

    @Around("@annotation(aspectAnno)")
    public Object around(ProceedingJoinPoint joinPoint, AspectAnno aspectAnno) throws Throwable {
        System.out.println("abd" + this.getClass());
        return joinPoint.proceed();
    }
}
