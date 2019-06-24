package com.project.uniproduct.basicInfo.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.uniproduct.basicInfo.to.CustomerTO;
import com.project.uniproduct.basicInfo.to.FinancialAccountAssociatesTO;

public interface CooperatorServiceFacade {

	public ArrayList<CustomerTO> getCustomerList(String searchCondition, String companyCode, String workplaceCode);

	public HashMap<String, Object> batchCustomerListProcess(ArrayList<CustomerTO> customerList);

	public ArrayList<FinancialAccountAssociatesTO> getFinancialAccountAssociatesList(String searchCondition,
			String workplaceCode);

	public HashMap<String, Object> batchFinancialAccountAssociatesListProcess(
			ArrayList<FinancialAccountAssociatesTO> financialAccountAssociatesList);

}
