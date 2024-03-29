package com.unicompany.hr.circumstance.service;

import java.util.List;
import java.util.Map;

import com.unicompany.common.exception.ProcedureException;
import com.unicompany.hr.circumstance.applicationService.AnnualAppService;
import com.unicompany.hr.circumstance.applicationService.BaseWorkTimeAppService;
import com.unicompany.hr.circumstance.applicationService.DeductionInsurAppService;
import com.unicompany.hr.circumstance.applicationService.HobongAppService;
import com.unicompany.hr.circumstance.applicationService.HolidayAppService;
import com.unicompany.hr.circumstance.applicationService.PayDeductionAppService;
import com.unicompany.hr.circumstance.applicationService.SalPaymentDateAppService;
import com.unicompany.hr.circumstance.to.AnnualBean;
import com.unicompany.hr.circumstance.to.BaseWorkTimeBean;
import com.unicompany.hr.circumstance.to.DeductionTaxBean;
import com.unicompany.hr.circumstance.to.HobongBean;
import com.unicompany.hr.circumstance.to.HolidayBean;
import com.unicompany.hr.circumstance.to.IncomeTaxBean;
import com.unicompany.hr.circumstance.to.PayDeductionBean;
import com.unicompany.hr.circumstance.to.SalPaymentDateBean;

public class CircumstanceServiceFacadeImpl implements CircumstanceServiceFacade{

	private HobongAppService hobongAppService;	
	public void setHobongAppService(HobongAppService hobongAppService) {
		this.hobongAppService = hobongAppService;
	}
	
	
	private BaseWorkTimeAppService baseWorkTimeAppService;
	public void setBaseWorkTimeAppService(BaseWorkTimeAppService baseWorkTimeAppService) {
		this.baseWorkTimeAppService = baseWorkTimeAppService;
	}
	
	
	private HolidayAppService holidayAppService;	
	public void setHolidayAppService(HolidayAppService holidayAppService) {
		this.holidayAppService = holidayAppService;
	}
	
	
	private AnnualAppService annualAppService;
	public void setAnnualAppService(AnnualAppService annualAppService) {
		this.annualAppService = annualAppService;
	}
	
	private DeductionInsurAppService deductionsInsurAppService;	
	public void setDeductionInsurAppService(DeductionInsurAppService deductionsInsurAppService) {
		this.deductionsInsurAppService = deductionsInsurAppService;
	}
	
	private PayDeductionAppService payDeductionAppService;
	public void setPayDeductionAppService(PayDeductionAppService payDeductionAppService) {
		this.payDeductionAppService = payDeductionAppService;
	}
	
	
	private SalPaymentDateAppService salPaymentDateAppService;
	
	public void setSalPaymentDateAppService(SalPaymentDateAppService salPaymentDateAppService) {
		this.salPaymentDateAppService = salPaymentDateAppService;
	}
	

	/* 호봉목록을 가지고 오는 메서드 */
	@Override
	public List<HobongBean> findHobongList() {
		return hobongAppService.findHobongList();
	}
	
	/* 호봉관련처리를 일괄적으로 하는 메서드 */
	@Override
	public void batchHobong(List<HobongBean> hobongList) {
		hobongAppService.batchHobong(hobongList);
	}
	
	/* 기본근무시간 목록을 다 가지고 오는 메서드 */
	@Override
	public List<BaseWorkTimeBean> findBaseWorkTimeList() {
		return baseWorkTimeAppService.findBaseWorkTimeList();
	}
	
	/* 해당년도에 기본근무시간을 추가하는 메서드 */
	@Override
	public void addBaseWorkTime(BaseWorkTimeBean baseWorkTimeBean) {
		baseWorkTimeAppService.addBaseWorkTime(baseWorkTimeBean);
	}

	/* 해당년도에 기본근무시간을 수정하는 메서드 */
	@Override
	public void editBaseWorkTime(BaseWorkTimeBean baseWorkTimeBean) {
		baseWorkTimeAppService.editBaseWorkTime(baseWorkTimeBean);
	}
	
	
	/* 휴일목록을 가지고 오는 메서드 */
	@Override
	public List<HolidayBean> findHolidayList(Map<String, Object> dateList) {
		return holidayAppService.findHolidayList(dateList);
	}
	
	/* 휴일관련 일괄처리를 하는 메서드 */
	@Override
	public void batchHoliday(List<HolidayBean> holidayList) {
		 holidayAppService.batchHoliday(holidayList);
	}

	
	/* 연차테이블을 조회하는 메서드 */
	@Override
	public List<AnnualBean> findAnnualList() {

		return annualAppService.findAnnualList();
	}
	
	/* 해당년도, 해당사원의 연차정보를 생성하는 메서드 */
	@Override
	public List<AnnualBean> createAnnual(String standardYear,String empCode,String hireDate) throws ProcedureException{
		return annualAppService.createAnnual(standardYear,empCode,hireDate);
	}
	
	/* 연차와 관련된 일괄처리를 하는 메서드 */
	@Override
	public void batchAnnual(List<AnnualBean> annualList) {
		annualAppService.batchAnnual(annualList);
	}
	
	/* 연차를 신청할때 연차관리테이블을 수정하기 위한 메서드 */
	@Override
	public List<AnnualBean> editAnnulMgt(String standardYear, String empCode, String days, String restDays){
		return annualAppService.editAnnualMgt(standardYear, empCode, days, restDays);
	}
	
	
	/* 보험공제목록을 불러오는 메서드 */
	@Override
	public List<DeductionTaxBean> findDeductionTaxList() {
		return deductionsInsurAppService.findDeductionTaxList();
	}

	/* 소득세정보를 불러오는 메서드 */
	@Override
	public List<IncomeTaxBean> findIncomeTaxList() {
		return deductionsInsurAppService.findIncomeTaxList();
	}

	
	/* 보험공제정보를 삭제하는 메서드 */
	@Override
	public void removeDeductionTax(DeductionTaxBean deductionsTaxBean) {
		deductionsInsurAppService.removeDeductionTax(deductionsTaxBean);
	}
	
	/* 소득세 관련정보를 삭제하는 메서드 */
	@Override
	public void removeIncomeTaxList(List<IncomeTaxBean> incomeTaxList) {
		deductionsInsurAppService.removeIncomeTaxList(incomeTaxList);
	}
	
	/* 보험공제정보를 등록하는 메서드 */
	@Override
	public void addDeductionTax(DeductionTaxBean deductionsTaxBean) {
		deductionsInsurAppService.addDeductionTax(deductionsTaxBean);
	}

	/* 소득세 관련 정보를 등록하는 메서드 */
	@Override
	public void addIncomeTaxList(List<IncomeTaxBean> incomeTaxList) {
		deductionsInsurAppService.addIncomeTaxList(incomeTaxList);
	}
	
	
	/* 지급/공제 목록을 조회하는 메서드 */
	@Override
	public List<PayDeductionBean> findPayDeductionList() {
		return payDeductionAppService.findPayDeductionList();
	}
	
	// 지급/공제 관련된 항목을 일괄처리하는 메서드 
	@Override
	public void batchPayDeduction(List<PayDeductionBean> payDeductionList) {
		payDeductionAppService.batchPayDeduction(payDeductionList);
	}
	
	
	/* 해당 귀속년월의 급/상여 지급일및 관련 정보를 조회하는 메서드 */
	@Override
	public List<SalPaymentDateBean> findSalPaymentDateList(String inputedYearMonth) {
		return salPaymentDateAppService.findSalPaymentDateList(inputedYearMonth);
	}

	
	// 급/상여 지급일 등록및 수정,삭제를 일괄처리하는 메서드 
	@Override
	public void batchSalPaymentDate(List<SalPaymentDateBean> salPaymentDateList) {
		salPaymentDateAppService.batchSalPaymentDate(salPaymentDateList);
	}
	
	
}
