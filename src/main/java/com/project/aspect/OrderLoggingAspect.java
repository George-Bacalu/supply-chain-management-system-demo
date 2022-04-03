package com.project.aspect;

import com.project.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class OrderLoggingAspect {

   @Around("within(com.project.service.impl.OrderServiceImpl)")
   public Object logMethodAroundExecution(ProceedingJoinPoint joinPoint) throws Throwable {
      log.info("entering method {} with args: {}", joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));
      Instant before = Instant.now();

      Order order = (Order) joinPoint.proceed();

      Instant after = Instant.now();
      long duration = Duration.between(before, after).toMillis();
      log.info("method execution finished in {}", duration);
      log.info("exiting method {} with args: {}", joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));
      return order;
   }

   @After("within(com.project.service.impl.OrderServiceImpl)")
   public void logMethodAfterExecution(JoinPoint joinPoint) {
      log.info("after method {}", joinPoint.getSignature());
   }

   @AfterReturning(value = "within(com.project.service.impl.OrderServiceImpl)", returning = "order")
   public void logMethodAfterReturningExecution(JoinPoint joinPoint, Order order) {
      log.info("after returning method {} with order: {}", joinPoint.getSignature(), order);
   }

   @AfterThrowing(value = "within(com.project.service.impl.OrderServiceImpl)", throwing = "exception")
   public void logMethodAfterThrowingExecution(JoinPoint joinPoint, Throwable exception) {
      log.info("after throwing method {} with message: \"{}\"", joinPoint.getSignature(), exception.getMessage());
   }
}
