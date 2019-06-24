package com.project.uniproduct.logistics.production.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.uniproduct.logistics.production.to.ContractDetailInMpsAvailableTO;
import com.project.uniproduct.logistics.production.to.MpsTO;
import com.project.uniproduct.logistics.production.to.MrpGatheringTO;
import com.project.uniproduct.logistics.production.to.MrpTO;
import com.project.uniproduct.logistics.production.to.SalesPlanInMpsAvailableTO;

public interface ProductionServiceFacade {

	public ArrayList<MpsTO> getMpsList(String startDate, String endDate, String includeMrpApply);
	
	public ArrayList<ContractDetailInMpsAvailableTO> 
		getContractDetailListInMpsAvailable(String searchCondition, String startDate, String endDate);

	public ArrayList<SalesPlanInMpsAvailableTO> 
		getSalesPlanListInMpsAvailable(String searchCondition, String startDate, String endDate);

	public HashMap<String, Object> convertContractDetailToMps(
			ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList);

	public HashMap<String, Object> convertSalesPlanToMps(
			ArrayList<SalesPlanInMpsAvailableTO> contractDetailInMpsAvailableList);
	
	public HashMap<String, Object> batchMpsListProcess(ArrayList<MpsTO> mpsTOList);

	public ArrayList<MrpTO> searchMrpList(String mrpGatheringStatusCondition);
	
	public ArrayList<MrpTO> searchMrpList(String dateSearchCondtion, String startDate, String endDate);
	
	public ArrayList<MrpTO> searchMrpListAsMrpGatheringNo(String mrpGatheringNo);
	
	public ArrayList<MrpGatheringTO> searchMrpGatheringList(String dateSearchCondtion, String startDate, String endDate);
	
	public HashMap<String, Object> openMrp(ArrayList<String> mpsNoArr);

	public HashMap<String, Object> registerMrp(String mrpRegisterDate, 
			ArrayList<MrpTO> newMrpList);
	
	public HashMap<String, Object> batchMrpListProcess(ArrayList<MrpTO> mrpTOList);
	
	public ArrayList<MrpGatheringTO> getMrpGathering(ArrayList<String> mrpNoArr);
	
	public HashMap<String, Object> registerMrpGathering(String mrpGatheringRegisterDate, 
			ArrayList<MrpGatheringTO> newMrpGatheringList, HashMap<String, String> mrpNoAndItemCodeMap );
	
	
	
}
