package com.project.uniproduct.basicInfo.dao;

import java.util.ArrayList;

import com.project.uniproduct.basicInfo.to.CustomerTO;

public interface CustomerDAO {

	public ArrayList<CustomerTO> selectCustomerListByCompany();

	public ArrayList<CustomerTO> selectCustomerListByWorkplace(String workplaceCode);
	
	public void insertCustomer(CustomerTO TO);

	public void updateCustomer(CustomerTO TO);

	public void deleteCustomer(CustomerTO TO);
}
