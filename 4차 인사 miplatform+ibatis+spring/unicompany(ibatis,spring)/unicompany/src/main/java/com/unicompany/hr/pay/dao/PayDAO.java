package com.unicompany.hr.pay.dao;

import java.util.List;
import java.util.Map;

import com.unicompany.hr.pay.to.SalaryInputBean;

public interface PayDAO {
	public Map<String, Object> payCalculate(String paymentsDate, String  standardDate);
	public List<SalaryInputBean> selectSalaryInputList(String paymentsDate);
}
