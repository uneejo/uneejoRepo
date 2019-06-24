package com.project.uniproduct.basicInfo.dao;


import java.util.ArrayList;

import com.project.uniproduct.basicInfo.to.CustomerTO;
import com.project.uniproduct.common.dao.IbatisSupportDAO;

public class CustomerDAOImpl extends IbatisSupportDAO implements CustomerDAO {

	@SuppressWarnings({"unchecked","deprecation"})
	public ArrayList<CustomerTO> selectCustomerListByCompany() {

           return (ArrayList<CustomerTO>)getSqlMapClientTemplate().queryForList("customer.selectCustomerListByCompany");
	}

	
	@SuppressWarnings({"unchecked","deprecation"})
	public ArrayList<CustomerTO> selectCustomerListByWorkplace(String workplaceCode) {

         return (ArrayList<CustomerTO>)getSqlMapClientTemplate().queryForList("customer.selectCustomerListByWorkplace",workplaceCode);
	}

	
	@SuppressWarnings("deprecation")
	public void insertCustomer(CustomerTO bean) {

		getSqlMapClientTemplate().queryForList("customer.insertCustomer",bean);
	}

	@SuppressWarnings("deprecation")
	public void updateCustomer(CustomerTO bean) {

		getSqlMapClientTemplate().queryForList("customer.updateCustomer",bean);
	}

	@SuppressWarnings("deprecation")
	public void deleteCustomer(CustomerTO bean) {

		getSqlMapClientTemplate().queryForList("customer.deleteCustomer",bean);
	}

}
