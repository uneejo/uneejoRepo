package com.project.uniproduct.basicInfo.dao;

import java.util.ArrayList;

import com.project.uniproduct.basicInfo.to.CompanyTO;
import com.project.uniproduct.common.dao.IbatisSupportDAO;

public class CompanyDAOImpl extends IbatisSupportDAO implements CompanyDAO {

  
    @SuppressWarnings({ "unchecked", "deprecation" })
	public ArrayList<CompanyTO> selectCompanyList() {

		return (ArrayList<CompanyTO>)getSqlMapClientTemplate().queryForList("company.selectCompanyList");
	}

    @SuppressWarnings("deprecation")
	public void insertCompany(CompanyTO bean) {

		getSqlMapClientTemplate().queryForList("company.insertCompany",bean);
	}
   
    @SuppressWarnings("deprecation")
	public void updateCompany(CompanyTO bean) {
		
		getSqlMapClientTemplate().queryForList("company.updateCompany",bean);
	}

    @SuppressWarnings("deprecation")
	public void deleteCompany(CompanyTO bean) {
		
		getSqlMapClientTemplate().queryForList("company.deleteCompany",bean);
		
	}

}
