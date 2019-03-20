package com.project.uniproduct.logistics.purchase.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.uniproduct.logistics.purchase.dao.BomDAO;
import com.project.uniproduct.logistics.purchase.to.BomDeployTO;
import com.project.uniproduct.logistics.purchase.to.BomInfoTO;
import com.project.uniproduct.logistics.purchase.to.BomTO;

public class BomApplicationServiceImpl implements BomApplicationService {

	
	private BomDAO bomDAO;
	
	public void setBomDAO(BomDAO bomDAO) {
		this.bomDAO=bomDAO;
	}

	public ArrayList<BomDeployTO> getBomDeployList(String deployCondition, String itemCode) {


			ArrayList<BomDeployTO> bomDeployList = bomDAO.selectBomDeployList(deployCondition, itemCode);

		
		return bomDeployList;
	}

	public ArrayList<BomInfoTO> getBomInfoList(String parentItemCode) {



			ArrayList<BomInfoTO> bomInfoList = bomDAO.selectBomInfoList(parentItemCode);

		
		return bomInfoList;
	}

	public ArrayList<BomInfoTO> getAllItemWithBomRegisterAvailable() {



			ArrayList<BomInfoTO> allItemWithBomRegisterAvailable = bomDAO.selectAllItemWithBomRegisterAvailable();

		
		return allItemWithBomRegisterAvailable;

	}

	public HashMap<String, Object> batchBomListProcess(ArrayList<BomTO> batchBomList) {

		
		HashMap<String, Object> resultMap = new HashMap<>();

			int insertCount = 0;
			int updateCount = 0;
			int deleteCount = 0;

			for (BomTO TO : batchBomList) {

				String status = TO.getStatus();

				switch (status) {

				case "INSERT":

					bomDAO.insertBom(TO);

					insertCount++;

					break;

				case "UPDATE":

					bomDAO.updateBom(TO);

					updateCount++;

					break;

				case "DELETE":

					bomDAO.deleteBom(TO);

					deleteCount++;

					break;

				}

			}

			resultMap.put("INSERT", insertCount);
			resultMap.put("UPDATE", updateCount);
			resultMap.put("DELETE", deleteCount);

		
		return resultMap;
	}

}
