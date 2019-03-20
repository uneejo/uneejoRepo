package com.project.uniproduct.common.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.project.uniproduct.common.transaction.DataSourceTransactionManager;

public class TransactionAdvice implements MethodInterceptor {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	 	
	private DataSourceTransactionManager dataSourceTransactionManager;

	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		if (logger.isDebugEnabled()) {
			logger.debug(
					invocation.getThis().getClass().getSimpleName() + " : " + invocation.getMethod().getName() + " 시작");
		}

		Object returnValue = null;

		try {

			dataSourceTransactionManager.beginTransaction();
			returnValue = invocation.proceed();
			dataSourceTransactionManager.commitTransaction();

			return returnValue;

		} catch (Exception e) {

			dataSourceTransactionManager.rollbackTransaction();
			logger.error(e.getMessage());
			throw e;

		} finally {

			if (logger.isDebugEnabled()) {
				logger.debug(invocation.getThis().getClass().getSimpleName() + " : " + invocation.getMethod().getName()
						+ " 종료");
			}

		}

	}

}
