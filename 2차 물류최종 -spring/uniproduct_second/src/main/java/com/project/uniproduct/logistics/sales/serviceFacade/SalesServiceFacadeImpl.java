package com.project.uniproduct.logistics.sales.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;


import com.project.uniproduct.logistics.sales.applicationService.ContractApplicationService;
import com.project.uniproduct.logistics.sales.applicationService.EstimateApplicationService;
import com.project.uniproduct.logistics.sales.applicationService.SalesPlanApplicationService;
import com.project.uniproduct.logistics.sales.to.ContractDetailTO;
import com.project.uniproduct.logistics.sales.to.ContractInfoTO;
import com.project.uniproduct.logistics.sales.to.ContractTO;
import com.project.uniproduct.logistics.sales.to.EstimateDetailTO;
import com.project.uniproduct.logistics.sales.to.EstimateTO;
import com.project.uniproduct.logistics.sales.to.SalesPlanTO;

public class SalesServiceFacadeImpl implements SalesServiceFacade {

	
	private static EstimateApplicationService estimateAS;
	private static ContractApplicationService contractAS;
	private static SalesPlanApplicationService salesPlanAS;
	
	public void setEstimateAS(EstimateApplicationService estimateAS) {
		this.estimateAS=estimateAS;
	}

	public void setContractAS(ContractApplicationService contractAS) {
		this.contractAS=contractAS;
	}
	
	public void setSalesPlanAS(SalesPlanApplicationService salesPlanAS) {
		this.salesPlanAS=salesPlanAS;
	}
	
	@Override
	public ArrayList<EstimateTO> getEstimateList(String dateSearchCondition, String startDate, String endDate) {


			return estimateAS.getEstimateList(dateSearchCondition, startDate, endDate);
			
	}

	@Override
	public ArrayList<EstimateDetailTO> getEstimateDetailList(String estimateNo) {

	
			return estimateAS.getEstimateDetailList(estimateNo);
			
	}

	@Override
	public HashMap<String, Object> addNewEstimate(String estimateDate, EstimateTO newEstimateTO) {

			return estimateAS.addNewEstimate(estimateDate, newEstimateTO);
			
	}

	@Override
	public HashMap<String, Object> batchEstimateDetailListProcess(
			ArrayList<EstimateDetailTO> estimateDetailTOList) {
	
			return estimateAS.batchEstimateDetailListProcess(estimateDetailTOList);
			
	}

	@Override
	public ArrayList<ContractInfoTO> getContractList(String searchCondition, String[] paramArray) {

	
			return contractAS.getContractList(searchCondition, paramArray);

	}

	@Override
	public ArrayList<ContractDetailTO> getContractDetailList(String estimateNo) {

	
			return contractAS.getContractDetailList(estimateNo);
		
	}

	@Override
	public ArrayList<EstimateTO> getEstimateListInContractAvailable(String startDate, String endDate) {
	
			return contractAS.getEstimateListInContractAvailable(startDate, endDate);
			
	}

	@Override
	public HashMap<String, Object> addNewContract(String contractDate, String personCodeInCharge,
			ContractTO workingContractTO, EstimateTO previousEstimateTO) {

			return contractAS.addNewContract(contractDate, personCodeInCharge, workingContractTO, previousEstimateTO);
				
	}

	@Override
	public HashMap<String, Object> batchContractDetailListProcess(
			ArrayList<ContractDetailTO> contractDetailTOList) {

	
			return contractAS.batchContractDetailListProcess(contractDetailTOList);
		
	}

	@Override
	public void changeContractStatusInEstimate(String estimateNo, String contractStatus) {

		
			contractAS.changeContractStatusInEstimate(estimateNo, contractStatus);
		
	}

	@Override
	public ArrayList<SalesPlanTO> getSalesPlanList(String dateSearchCondition, String startDate, String endDate) {

		
			return salesPlanAS.getSalesPlanList(dateSearchCondition, startDate, endDate);
			
	}

	@Override
	public HashMap<String, Object> batchSalesPlanListProcess(ArrayList<SalesPlanTO> salesPlanTOList) {
		
			return salesPlanAS.batchSalesPlanListProcess(salesPlanTOList);
			
	}

}
