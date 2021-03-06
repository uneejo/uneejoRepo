package com.unicompany.hr.attendance.applicationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unicompany.common.exception.ProcedureException;
import com.unicompany.hr.attendance.dao.AttdReportDAO;
import com.unicompany.hr.attendance.to.ConditionBean;
import com.unicompany.hr.attendance.to.DailyAttdReportBean;
import com.unicompany.hr.attendance.to.MonthAttdReportBean;
import com.unicompany.hr.attendance.to.OverNightReportBean;

@Component
public class AttdReportAppServiceImpl implements AttdReportAppService {
	/* AttdReportDAO setting */
	
	@Autowired
	private AttdReportDAO attdReportDAO;
	

	//연장 심야 신청 리스트 
	public List<OverNightReportBean> findOverNightReport(HashMap<String,String> map){
		return attdReportDAO.selectOverNightReport(map);
	}
	
	//연장 심야 신청 일괄 신청
	public void updateRequestStatus(List<OverNightReportBean> overNightReportList){
		for(OverNightReportBean overNightReportBean:overNightReportList){
			attdReportDAO.updateRequestStatus(overNightReportBean);
		}
	}
	
	// 일근태관리테이블에서 기준일에 해당하는 승인된 데이터를 조회하는 메서드 
	@Override
	public List<DailyAttdReportBean> findApprovalDailyAttdReport(String baseDay){
		 return attdReportDAO.selectApprovalDailyAttdReport(baseDay);
	}
	
	// 해당날짜의 일근태를 마감하는 메서드 (Y-> N / N-> Y ) 
	@Override
	public void updateDailyAttdCloseYN(List<DailyAttdReportBean> dailyAttdReportList) {
		 for(DailyAttdReportBean dailyAttdReportBean : dailyAttdReportList) {
			 attdReportDAO.updateDailyAttdCloseYN(dailyAttdReportBean);
	     }
	}
	
	// 일근태관리테이블에서 기준일에 해당하는 데이터를 생성,조회하는 메서드  프로시저
		@Override
	public List<DailyAttdReportBean> findDailyAttdReport(String baseDay) throws ProcedureException{
		 
			Map<String,String> map=new HashMap<>();
		map.put("baseDay",baseDay);
		attdReportDAO.createDailyAttdReport(map); //생성 
		 
		 int errorCode=Integer.parseInt((String) map.get("errorCode"));
		
		 if(errorCode < 0){
	            throw new ProcedureException((String) map.get("errorMsg"));
		 }
		 
		return attdReportDAO.selectDailyAttdReportByDate(baseDay); // 조회 
	}
		
	//연장 심야 승인 목록조회	
	public List<OverNightReportBean> findOverNightReportByCondition(ConditionBean condition){
		return attdReportDAO.selectOverNightReportByCondition(condition);
	}	
	
	
	//연장 심야 신청등록, 신청삭제, 승인업데이트
	public void batchOverNight(List<OverNightReportBean> overNightReportList){
		           		
		for(OverNightReportBean overNightReportBean:overNightReportList){
			
			switch(overNightReportBean.getStatus()) {
			
			case "insert" : attdReportDAO.insertOverNightReport(overNightReportBean); break;
			case "update" : attdReportDAO.updateApprovalStatus(overNightReportBean); break;
			case "delete" : attdReportDAO.deleteOverNightReport(overNightReportBean); break;
		
			}
	    }
	}
	
	
	// 해당년월의 일근태관리테이블의 데이터중 미마감인 day관련 정보를 조회하는 항목 
	@Override
	public List<DailyAttdReportBean> findUnClosedDailyAttdReport(String baseYearMonth) {
		return attdReportDAO.selectUnClosedDailyAttdReport(baseYearMonth);
	}
	
	//월근태 미마감항목 조회에서 일근태/근태외를 미마감한 경우 일괄적으로 처리하는 메서드 
	@Override
	public List<DailyAttdReportBean> batchDailyAttdCloseYN(List<DailyAttdReportBean> dailyAttdReportList) {
		 String baseYearMonth=null;
		 for(DailyAttdReportBean dailyAttdReportBean : dailyAttdReportList) {
			
			 attdReportDAO.updateDailyAttdCloseYN(dailyAttdReportBean);
			 if(baseYearMonth==null){
				 baseYearMonth = dailyAttdReportBean.getBasicDay(); // 날짜 얻어 옴 
			 }
	     }
		 baseYearMonth = baseYearMonth.substring(0, 6);
		 return findUnClosedDailyAttdReport(baseYearMonth); // 수정 후 일근태 미마감 목록 다시 조회 !!
	}
	

	// 월근태관리테이블에서 기준일에 해당하는 데이터를 생성,조회하는 메서드 
	@Override
	public List<MonthAttdReportBean> findMonthAttdReport(String baseYearMonth) throws ProcedureException {
		
		Map<String, String> map=new HashMap<>();
		map.put("baseYearMonth", baseYearMonth);
		attdReportDAO.createMonthAttdReport(map);
		 int errorCode=Integer.parseInt((String) map.get("errorCode"));
		 //System.out.println("프로시저에서 에러발생 ::"+map.get("errorMsg"));

		 if(errorCode < 0){
	            throw new ProcedureException((String) map.get("errorMsg"));
		 }
		return attdReportDAO.selectDailyAttdReportByYearMonth(baseYearMonth);
	}
	
	
	// 기준년월의 데이터를 마감하는 메서드 
	@Override
	public void updateMonthAttdCloseYN(List<MonthAttdReportBean> monthAttdReportList) {
		String basicYearMonth = monthAttdReportList.get(0).getBasicYearMonth();
		String closeYn = monthAttdReportList.get(0).getCloseYn();
		Map<String,String> map = new HashMap<String,String>();
		map.put("basicYearMonth", basicYearMonth);
		map.put("closeYn", closeYn);  // map에 담아서 넘김.. 
		attdReportDAO.updateMonthAttdCloseYN(map);
	}

	//월근태 마감 내역 조회 
	@Override
	public List<MonthAttdReportBean> findClosedMonthAttdReport(String baseYearMonth){

		return attdReportDAO.selectClosedMonthAttdReport(baseYearMonth);
	}
}