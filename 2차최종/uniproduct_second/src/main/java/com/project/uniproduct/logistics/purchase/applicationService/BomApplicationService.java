package com.project.uniproduct.logistics.purchase.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.uniproduct.logistics.purchase.to.BomDeployTO;
import com.project.uniproduct.logistics.purchase.to.BomInfoTO;
import com.project.uniproduct.logistics.purchase.to.BomTO;

public interface BomApplicationService {

	public ArrayList<BomDeployTO> getBomDeployList(String deployCondition, String itemCode);
	
	public ArrayList<BomInfoTO> getBomInfoList(String parentItemCode);
	
	public ArrayList<BomInfoTO> getAllItemWithBomRegisterAvailable();
	
	public HashMap<String, Object> batchBomListProcess(ArrayList<BomTO> batchBomList);


}
