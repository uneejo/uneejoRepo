package com.project.uniproduct.hr.dao;


import java.util.ArrayList;
import java.util.HashMap;

import com.project.uniproduct.hr.to.EmpInfoTO;
import com.project.uniproduct.common.dao.IbatisSupportDAO;

public class EmpSearchingDAOImpl extends IbatisSupportDAO implements EmpSearchingDAO {

	
	@SuppressWarnings({"unchecked","deprecation"})
	public ArrayList<EmpInfoTO> selectAllEmpList(String searchCondition, String[] paramArray) {

	
		HashMap<String, String> map = new HashMap<>();
		map.put("searchCondition", searchCondition);

		for (int i = 0; i < paramArray.length; i++) {

			switch (i + "") {

			case "0":
				map.put("companyCode", paramArray[0]);
				break;

			case "1":
				map.put("workplaceCode", paramArray[1]);
				break;

			case "2":
				map.put("deptCode", paramArray[2]);
				break;

			}
		}
			
			return(ArrayList<EmpInfoTO>)getSqlMapClientTemplate().queryForList("empInfo.selectAllEmpList",map);

		}


		@SuppressWarnings({"unchecked","deprecation"})
	public ArrayList<EmpInfoTO> getTotalEmpInfo(String companyCode, String workplaceCode, String userId) {
	
		HashMap<String, String> map = new HashMap<>();
		map.put("companyCode", companyCode);
		map.put("workplaceCode", workplaceCode);
		map.put("userId", userId);
         
		return (ArrayList<EmpInfoTO>)getSqlMapClientTemplate().queryForList("empInfo.getTotalEmpInfo",map);
	}
	
	
}
