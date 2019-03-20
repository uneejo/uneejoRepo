package com.project.uniproduct.basicInfo.dao;

import java.util.ArrayList;

import com.project.uniproduct.basicInfo.to.DepartmentTO;

public interface DepartmentDAO {

	public ArrayList<DepartmentTO> selectDepartmentListByCompany(String companyCode);

	public ArrayList<DepartmentTO> selectDepartmentListByWorkplace(String workplaceCode);

	public void insertDepartment(DepartmentTO TO);

	public void updateDepartment(DepartmentTO TO);

	public void deleteDepartment(DepartmentTO TO);
}
