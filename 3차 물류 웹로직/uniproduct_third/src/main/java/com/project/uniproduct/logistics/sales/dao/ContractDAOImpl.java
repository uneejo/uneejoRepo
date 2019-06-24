package com.project.uniproduct.logistics.sales.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.uniproduct.common.dao.IbatisSupportDAO;
import com.project.uniproduct.logistics.sales.to.ContractInfoTO;
import com.project.uniproduct.logistics.sales.to.ContractTO;
import com.project.uniproduct.logistics.sales.to.EstimateTO;

public class ContractDAOImpl extends IbatisSupportDAO implements ContractDAO {

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<EstimateTO> selectEstimateListInContractAvailable(String startDate, String endDate) {

		HashMap<String, String> map = new HashMap<>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);

		return (ArrayList<EstimateTO>) getSqlMapClientTemplate()
				.queryForList("contract.selectEstimateListInContractAvailable", map);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<ContractInfoTO> selectContractListByDate(String startDate, String endDate) {
		HashMap<String, String> map = new HashMap<>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);

		return (ArrayList<ContractInfoTO>) getSqlMapClientTemplate().queryForList("contract.selectContractListByDate",
				map);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<ContractInfoTO> selectContractListByCustomer(String customerCode) {

		return (ArrayList<ContractInfoTO>) getSqlMapClientTemplate().queryForList("contract.selectContractListByCustomer",
				customerCode);

	}

	@SuppressWarnings("deprecation")
	@Override
	public int selectContractCount(String contractDate) {
		
		Integer i = (Integer) getSqlMapClientTemplate().queryForObject("contract.selectContractCount", contractDate);

		return i+1;

	}

	@SuppressWarnings("deprecation")
	@Override
	public void insertContract(ContractTO bean) {

		getSqlMapClientTemplate().insert("contract.insertContract", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateContract(ContractTO bean) {

		getSqlMapClientTemplate().update("contract.updateContract", bean);

	}

	@Override
	public void deleteContract(ContractTO bean) {

	}

}
