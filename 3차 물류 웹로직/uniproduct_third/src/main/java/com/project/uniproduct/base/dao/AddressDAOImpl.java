package com.project.uniproduct.base.dao;


import java.util.HashMap;
import java.util.ArrayList;

import com.project.uniproduct.base.to.AddressTO;
import com.project.uniproduct.common.dao.IbatisSupportDAO;


public class AddressDAOImpl extends IbatisSupportDAO implements AddressDAO {

	@SuppressWarnings("deprecation")
	@Override
	public String selectSidoCode(String sidoName) {

		return (String)getSqlMapClientTemplate().queryForObject("address.selectSidoCode", sidoName);
			
	}
    
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<AddressTO> selectRoadNameAddressList(String sidoCode, String searchValue, String buildingMainNumber) {

		HashMap<String, Object> map = new HashMap<>();
		map.put("sidoCode", sidoCode);
		map.put("searchValue", searchValue);
		map.put("buildingMainNumber", buildingMainNumber);
		
		return (ArrayList<AddressTO>)getSqlMapClientTemplate().queryForList("address.selectRoadNameAddressList",map);


			}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<AddressTO> selectJibunAddressList(String sidoCode, String searchValue, String jibunMainAddress) {


		HashMap<String, Object> map = new HashMap<>();
		map.put("sidoCode", sidoCode);
		map.put("searchValue", searchValue);
		map.put("jibunMainAddress", jibunMainAddress);
		
		return (ArrayList<AddressTO>)getSqlMapClientTemplate().queryForList("address.selectJibunAddressList",map);


	}

}
