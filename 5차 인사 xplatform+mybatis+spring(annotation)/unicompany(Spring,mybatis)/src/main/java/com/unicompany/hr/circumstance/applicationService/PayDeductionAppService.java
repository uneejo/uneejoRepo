package com.unicompany.hr.circumstance.applicationService;

import java.util.List;

import com.unicompany.hr.circumstance.to.PayDeductionBean;

public interface PayDeductionAppService {
	public List<PayDeductionBean> findPayDeductionList();
	public void batchPayDeduction(List<PayDeductionBean> payDeductionList);
}
