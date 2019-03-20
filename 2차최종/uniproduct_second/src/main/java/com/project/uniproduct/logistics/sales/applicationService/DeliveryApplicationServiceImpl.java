package com.project.uniproduct.logistics.sales.applicationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeliveryApplicationServiceImpl implements DeliveryApplicationService {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(EstimateApplicationServiceImpl.class);
	
	// 싱글톤
	private static DeliveryApplicationService instance = new DeliveryApplicationServiceImpl();

	private DeliveryApplicationServiceImpl() {
	}

	public static DeliveryApplicationService getInstance() {
		
		if (logger.isDebugEnabled()) {
			logger.debug("@ DeliveryApplicationService 객체접근");
		}
		
		return instance;
	}
	
	
	
}
