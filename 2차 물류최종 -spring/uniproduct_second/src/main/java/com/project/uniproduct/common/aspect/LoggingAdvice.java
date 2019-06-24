package com.project.uniproduct.common.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class LoggingAdvice implements MethodInterceptor {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public Object invoke(MethodInvocation invocation) throws Throwable {

		if (logger.isDebugEnabled()) {
			logger.debug(
					invocation.getThis().getClass().getSimpleName() + " : " + invocation.getMethod().getName() + " 시작");

			Object[] args = invocation.getArguments();

			if ((args != null) && (args.length > 0)) {
				for (int i = 0; i < args.length; i++) {
					logger.debug("매개변수 [ " + i + " ]: " + args[i]);
				}
			}

		}

		Object returnValue = invocation.proceed();

		if (logger.isDebugEnabled()) {
			logger.debug(
					invocation.getThis().getClass().getSimpleName() + " : " + invocation.getMethod().getName() + " 종료");
		}
		return returnValue;

	}

}
