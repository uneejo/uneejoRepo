package com.project.uniproduct.common.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.dao.DataAccessException;

public class CommonAdvice {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
		
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		if (logger.isDebugEnabled()) {
			logger.debug(className + "." + methodName + "() 시작");
		}
		Object[] args = joinPoint.getArgs();
		if ((args != null) && (args.length > 0)) {
			for (int i = 0; i < args.length; i++) {
				logger.debug("매개변수 [" + i + "] : " + args[i]);
			}
		}

		Object retVal = joinPoint.proceed();
		if (logger.isDebugEnabled()) {
			logger.debug(className + "." + methodName + "() 종료");
		}
		return retVal;
		
	}

	public void afterThrowing(DataAccessException ex) throws Throwable {
		
		if (logger.isDebugEnabled()) {
			logger.debug("DataAccessException afterThrowing 시작 : " + ex.getClass().getName() + " 예외 잡힘");
		}
		if (logger.isErrorEnabled()) {
			logger.error(ex.getMessage());
		}

		if (logger.isDebugEnabled()) {
			logger.debug("DataAccessException afterThrowing 종료");
		}

		throw ex;
	}
}
