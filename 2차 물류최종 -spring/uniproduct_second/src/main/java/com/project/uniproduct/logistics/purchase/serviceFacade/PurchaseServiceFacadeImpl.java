package com.project.uniproduct.logistics.purchase.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.uniproduct.logistics.purchase.applicationService.BomApplicationService;
import com.project.uniproduct.logistics.purchase.to.BomDeployTO;
import com.project.uniproduct.logistics.purchase.to.BomInfoTO;
import com.project.uniproduct.logistics.purchase.to.BomTO;

public class PurchaseServiceFacadeImpl implements PurchaseServiceFacade {


	
	private BomApplicationService bomAS;
	
	public void setBomAS(BomApplicationService bomAS) {
		this.bomAS=bomAS;
	}

	@Override
	public ArrayList<BomDeployTO> getBomDeployList(String deployCondition, String itemCode) {



			return bomAS.getBomDeployList(deployCondition, itemCode);
			
	}

	@Override
	public ArrayList<BomInfoTO> getBomInfoList(String parentItemCode) {

			return bomAS.getBomInfoList(parentItemCode);
			
	}

	@Override
	public ArrayList<BomInfoTO> getAllItemWithBomRegisterAvailable() {

			return bomAS.getAllItemWithBomRegisterAvailable();
			
	}

	@Override
	public HashMap<String, Object> batchBomListProcess(ArrayList<BomTO> batchBomList) {


			return bomAS.batchBomListProcess(batchBomList);
			
	}

}
