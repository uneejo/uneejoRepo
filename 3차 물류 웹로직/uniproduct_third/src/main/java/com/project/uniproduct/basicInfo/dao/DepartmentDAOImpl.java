package com.project.uniproduct.basicInfo.dao;

import java.util.ArrayList;

import com.project.uniproduct.basicInfo.to.DepartmentTO;
import com.project.uniproduct.common.dao.IbatisSupportDAO;


public class DepartmentDAOImpl extends IbatisSupportDAO implements DepartmentDAO {

    
	@SuppressWarnings({"unchecked","deprecation"})
	public ArrayList<DepartmentTO> selectDepartmentListByCompany(String companyCode) {
		
		return (ArrayList<DepartmentTO>)getSqlMapClientTemplate().queryForList("department.selectDepartmentListByCompany",companyCode);
		
			
	}
	
	
	@SuppressWarnings({"unchecked","deprecation"})
	public ArrayList<DepartmentTO> selectDepartmentListByWorkplace(String workplaceCode) {

		return (ArrayList<DepartmentTO>)getSqlMapClientTemplate().queryForList("department.selectDepartmentListByWorkplace",workplaceCode);
	}
	
	@SuppressWarnings("deprecation")
	public void insertDepartment(DepartmentTO bean) {

		getSqlMapClientTemplate().queryForList("department.insertDepartment",bean);
	}
	
	
	@SuppressWarnings("deprecation")
	public void updateDepartment(DepartmentTO bean) {
     
		getSqlMapClientTemplate().queryForList("department.updateDepartment",bean);
	}
	
    
	@SuppressWarnings("deprecation")
	public void deleteDepartment(DepartmentTO bean) {

		getSqlMapClientTemplate().queryForList("department.deleteDepartment",bean);
	}
	
}
