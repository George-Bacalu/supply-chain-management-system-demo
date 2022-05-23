package com.project.aspect;

import com.project.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class UserLoggingAspect {

   @Around("execution(com.project.entity.User || void com.project.service.*.*(..))")
   public Object logMethodAroundExecution(ProceedingJoinPoint joinPoint) throws Throwable {
      log.info("entering user method {} with args: {}", joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));
      Instant before = Instant.now();

      User user = (User) joinPoint.proceed();

      Instant after = Instant.now();
      long duration = Duration.between(before, after).toMillis();
      log.info("user method execution finished in {}", duration);
      log.info("exiting user method {} with args: {}", joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));
      return user;
   }

   @Before("execution(com.project.entity.User || void com.project.service.*.*(..))")
   public void logMethodBeforeExecution(JoinPoint joinPoint) {
      log.info("before user method {}", joinPoint.getSignature());
   }

   @After("execution(com.project.entity.User || void com.project.service.*.*(..))")
   public void logMethodAfterExecution(JoinPoint joinPoint) {
      log.info("after user method {}", joinPoint.getSignature());
   }

   @AfterReturning(value = ("execution(com.project.entity.User || void com.project.service.*.*(..))"), returning = "user")
   public void logMethodAfterReturningExecution(JoinPoint joinPoint, User user) {
      log.info("after returning method {} with user: {}", joinPoint.getSignature(), user);
   }

   @AfterThrowing(value = ("execution(com.project.entity.User || void com.project.service.*.*(..))"), throwing = "exception")
   public void logMethodAfterThrowingExecution(JoinPoint joinPoint, Throwable exception) {
      log.info("after throwing method {} with message: \"{}\"", joinPoint.getSignature(), exception.getMessage());
   }
}
