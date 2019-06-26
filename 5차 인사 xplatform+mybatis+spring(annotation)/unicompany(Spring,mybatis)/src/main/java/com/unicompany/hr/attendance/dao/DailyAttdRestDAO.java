package com.unicompany.hr.attendance.dao;

import java.util.List;

import com.unicompany.hr.attendance.to.ConditionBean;
import com.unicompany.hr.attendance.to.DailyAttdRestBean;

public interface DailyAttdRestDAO {
	public List<DailyAttdRestBean> selectAttdRestList(String empCode);
	
	public void insertDailyAttdRest(DailyAttdRestBean dailyAttdRestBean);
	
	public List<DailyAttdRestBean> selectAttdRestListByCondition(ConditionBean conditionBean);
	
	public List<DailyAttdRestBean> selectUnApprovedDailyAttdRestList(String basicDay);

	public void updateDailyAttdRest(DailyAttdRestBean dailyAttdRestBean);
	public void deleteDailyAttdRest(DailyAttdRestBean dailyAttdRestBean);

}
