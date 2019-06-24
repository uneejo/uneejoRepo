package com.unicompany.hr.attendance.applicationService;

import java.util.List;

import com.unicompany.hr.attendance.to.ConditionBean;
import com.unicompany.hr.attendance.to.DailyAttdRestBean;

public interface DailyAttdRestAppService {
	public List<DailyAttdRestBean> findAttdRestList(String empCode);
	
	public List<DailyAttdRestBean> addDailyAttdRest(DailyAttdRestBean dailyAttdRestBean);
	
	public List<DailyAttdRestBean> findAttdRestListByCondition(ConditionBean conditionBean);	
	
	public List<DailyAttdRestBean> findUnApprovedDailyAttdRestList(String basicDay);
	
	public void batchDailyAttdRest(List<DailyAttdRestBean> dailyAttdRestList);
	
	
	
}
