package com.unicompany.hr.attendance.applicationService;

import java.util.HashMap;
import java.util.List;

import com.unicompany.hr.attendance.to.ConditionBean;
import com.unicompany.hr.attendance.to.DayAnnualBean;

public interface DayAnnualAppService {
	public List<DayAnnualBean> findAnnualMgt(HashMap<String,String> map);
	
	public List<DayAnnualBean> addDayAnnual(DayAnnualBean dayAnnulBean);
	
	public List<DayAnnualBean> findDayAnnualListByCondition(ConditionBean conditionBean);

	public void batchDayAnnual(List<DayAnnualBean> dayAnnualList);
	
}
