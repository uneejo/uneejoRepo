package com.project.uniproduct.logistics.purchase.dao;

import java.util.ArrayList;

import com.project.uniproduct.logistics.purchase.to.BomDeployTO;
import com.project.uniproduct.logistics.purchase.to.BomInfoTO;
import com.project.uniproduct.logistics.purchase.to.BomTO;

public interface BomDAO {

	public ArrayList<BomDeployTO> selectBomDeployList(String deployCondition, String itemCode);
	
	public ArrayList<BomInfoTO> selectBomInfoList(String parentItemCode);
	
	public ArrayList<BomInfoTO> selectAllItemWithBomRegisterAvailable();
	
	public void insertBom(BomTO TO);
	
	public void updateBom(BomTO TO);
	
	public void deleteBom(BomTO TO);
	
}
