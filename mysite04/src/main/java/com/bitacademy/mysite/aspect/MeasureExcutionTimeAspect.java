package com.bitacademy.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class MeasureExcutionTimeAspect {

	@Around("execution(* *..*.repository.*.*(..)) || execution(* *..*.service.*.*(..)) || execution(* *..*.controller.*.*(..))")
	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
		// before advice
		StopWatch sw = new StopWatch();
		sw.start();

		Object result = pjp.proceed();

		// after advice
		sw.stop();

		Long totaltime = sw.getTotalTimeMillis();

		String className = pjp.getTarget().getClass().getName();
		String methodname = pjp.getSignature().getName();
		String taskName = className + "." + methodname;

		System.out.println("[Execution Time][" + taskName + "]" + totaltime + "millis");

		return result;
	}
}
