package com.project.uniproduct.base.dao;

import java.util.ArrayList;

import com.project.uniproduct.base.to.AddressTO;

public interface AddressDAO {

	public String selectSidoCode(String sidoName);
	
	public ArrayList<AddressTO> selectRoadNameAddressList(String sidoCode, String searchValue, String buildingMainNumber);
	
	public ArrayList<AddressTO> selectJibunAddressList(String sidoCode, String searchValue, String jibunMainAddress);

	
}
