package com.project.uniproduct.hr.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.uniproduct.hr.to.EmployeeBasicTO;
import com.project.uniproduct.common.dao.IbatisSupportDAO;

public class EmployeeBasicDAOImpl extends IbatisSupportDAO implements EmployeeBasicDAO {
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public ArrayList<EmployeeBasicTO> selectEmployeeBasicList(String companyCode) {
     
		return (ArrayList<EmployeeBasicTO>) getSqlMapClientTemplate().queryForList("employeeBasic.selectEmployeeBasicList",companyCode);
	}
   
	@SuppressWarnings("deprecation")
	public EmployeeBasicTO selectEmployeeBasicTO(String companyCode, String empCode) {
         
		HashMap<String, String> map = new HashMap<>();
		
		map.put("companyCode", companyCode);
		map.put("empCode", empCode);
		
		return (EmployeeBasicTO)getSqlMapClientTemplate().queryForList("employeeBasic.selectEmployeeBasicTO",map);
	}

	@SuppressWarnings("deprecation")
	public void insertEmployeeBasic(EmployeeBasicTO TO) {
     
		getSqlMapClientTemplate().queryForList("employeeBasic.insertEmployeeBasic",TO);
	
	}
	

	@SuppressWarnings("deprecation")
	@Override
	public void changeUserAccountStatus(String companyCode, String empCode, String userStatus) {

        HashMap<String, String> map = new HashMap<>();
		
		map.put("companyCode", companyCode);
		map.put("empCode", empCode);
		map.put("userStatus", userStatus);
		
		getSqlMapClientTemplate().queryForList("employeeBasic.changeUserAccountStatus",map);
		
	}


}
