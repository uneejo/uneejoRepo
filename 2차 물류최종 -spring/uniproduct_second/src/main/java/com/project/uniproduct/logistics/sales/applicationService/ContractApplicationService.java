package com.project.uniproduct.logistics.sales.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.uniproduct.logistics.sales.to.ContractDetailTO;
import com.project.uniproduct.logistics.sales.to.ContractInfoTO;
import com.project.uniproduct.logistics.sales.to.ContractTO;
import com.project.uniproduct.logistics.sales.to.EstimateTO;

public interface ContractApplicationService {

	public ArrayList<ContractInfoTO> getContractList(String searchCondition, String[] paramArray);

	public ArrayList<ContractDetailTO> getContractDetailList(String estimateNo);
	
	public ArrayList<EstimateTO> getEstimateListInContractAvailable(String startDate, String endDate);

	// ApplicationService 안에서만 호출
	public String getNewContractNo(String contractDate);

	public HashMap<String, Object> addNewContract(String contractDate, String personCodeInCharge, ContractTO workingContractTO, 
			EstimateTO previousEstimateTO);

	public HashMap<String, Object> batchContractDetailListProcess(ArrayList<ContractDetailTO> contractDetailTOList);

	public void changeContractStatusInEstimate(String estimateNo , String contractStatus);
	

}
