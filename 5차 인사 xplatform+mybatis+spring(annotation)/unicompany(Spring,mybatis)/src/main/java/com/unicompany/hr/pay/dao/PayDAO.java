package com.unicompany.hr.pay.dao;

import java.util.List;
import java.util.Map;

import com.unicompany.hr.pay.to.SalaryInputBean;

public interface PayDAO {
	public Map<String, Object> payCalculate(Map<String, String> map);
	public List<SalaryInputBean> selectSalaryInputList(String paymentsDate);
}
