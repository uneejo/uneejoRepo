package com.project.uniproduct.logistics.production.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.uniproduct.logistics.production.applicationService.MpsApplicationService;
import com.project.uniproduct.logistics.production.applicationService.MrpApplicationService;
import com.project.uniproduct.logistics.production.to.ContractDetailInMpsAvailableTO;
import com.project.uniproduct.logistics.production.to.MpsTO;
import com.project.uniproduct.logistics.production.to.MrpGatheringTO;
import com.project.uniproduct.logistics.production.to.MrpTO;
import com.project.uniproduct.logistics.production.to.SalesPlanInMpsAvailableTO;

public class ProductionServiceFacadeImpl implements ProductionServiceFacade {


	private MpsApplicationService mpsAS;
	private MrpApplicationService mrpAS;
	
	public void setMpsAS(MpsApplicationService mpsAS) {
		this.mpsAS=mpsAS;
	}
	
	public void setMrpAS(MrpApplicationService mrpAS) {
		this.mrpAS=mrpAS;
	}

	@Override
	public ArrayList<MpsTO> getMpsList(String startDate, String endDate, String includeMrpApply) {


			return mpsAS.getMpsList(startDate, endDate, includeMrpApply);
			
	}

	@Override
	public ArrayList<ContractDetailInMpsAvailableTO> getContractDetailListInMpsAvailable(String searchCondition,
			String startDate, String endDate) {

	
			return mpsAS.getContractDetailListInMpsAvailable(searchCondition, startDate,
					endDate);
			
	}

	@Override
	public ArrayList<SalesPlanInMpsAvailableTO> getSalesPlanListInMpsAvailable(String searchCondition,
			String startDate, String endDate) {

	
			return mpsAS.getSalesPlanListInMpsAvailable(searchCondition, startDate, endDate);
			

	}

	@Override
	public HashMap<String, Object> convertContractDetailToMps(
			ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList) {

	
			return mpsAS.convertContractDetailToMps(contractDetailInMpsAvailableList);
			

	}

	@Override
	public HashMap<String, Object> convertSalesPlanToMps(
			ArrayList<SalesPlanInMpsAvailableTO> contractDetailInMpsAvailableList) {

	
			return mpsAS.convertSalesPlanToMps(contractDetailInMpsAvailableList);
			

	}

	@Override
	public HashMap<String, Object> batchMpsListProcess(ArrayList<MpsTO> mpsTOList) {

	
			return mpsAS.batchMpsListProcess(mpsTOList);
			

	}

	@Override
	public ArrayList<MrpTO> searchMrpList(String mrpGatheringStatusCondition) {

	
			return mrpAS.searchMrpList(mrpGatheringStatusCondition);
			

	}

	@Override
	public ArrayList<MrpTO> searchMrpList(String dateSearchCondtion, String startDate, String endDate) {

	
			return mrpAS.searchMrpList(dateSearchCondtion, startDate, endDate);
			
	}

	@Override
	public ArrayList<MrpTO> searchMrpListAsMrpGatheringNo(String mrpGatheringNo) {

		
			return mrpAS.searchMrpListAsMrpGatheringNo(mrpGatheringNo);
			
	}

	@Override
	public ArrayList<MrpGatheringTO> searchMrpGatheringList(String dateSearchCondtion, String startDate,
			String endDate) {

			return mrpAS.searchMrpGatheringList(dateSearchCondtion, startDate, endDate);
			
	}

	@Override
	public HashMap<String, Object> openMrp(ArrayList<String> mpsNoArr) {

			return mrpAS.openMrp(mpsNoArr);
			
	}

	@Override
	public HashMap<String, Object> registerMrp(String mrpRegisterDate, ArrayList<MrpTO> newMrpList) {

	
			return mrpAS.registerMrp(mrpRegisterDate, newMrpList);
			
	}

	@Override
	public HashMap<String, Object> batchMrpListProcess(ArrayList<MrpTO> mrpTOList) {

			return mrpAS.batchMrpListProcess(mrpTOList);
			
	}

	@Override
	public ArrayList<MrpGatheringTO> getMrpGathering(ArrayList<String> mrpNoArr) {

	
			return mrpAS.getMrpGathering(mrpNoArr);
			
	}

	@Override
	public HashMap<String, Object> registerMrpGathering(String mrpGatheringRegisterDate,
			ArrayList<MrpGatheringTO> newMrpGatheringList, HashMap<String, String> mrpNoAndItemCodeMap) {

	
			return mrpAS.registerMrpGathering(mrpGatheringRegisterDate, newMrpGatheringList, mrpNoAndItemCodeMap);
			
	}

}
