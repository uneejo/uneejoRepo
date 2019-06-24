package com.project.uniproduct.logistics.sales.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import com.project.uniproduct.common.dao.IbatisSupportDAO;
import com.project.uniproduct.logistics.production.to.ContractDetailInMpsAvailableTO;
import com.project.uniproduct.logistics.sales.to.ContractDetailTO;

public class ContractDetailDAOImpl extends IbatisSupportDAO implements ContractDetailDAO {

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<ContractDetailTO> selectContractDetailList(String contractNo) {

		return (ArrayList<ContractDetailTO>) getSqlMapClientTemplate().queryForList("contractDetail.selectContractDetailList",
				contractNo);

	}

	@Override
	public int selectContractDetailCount(String contractNo) {

		@SuppressWarnings({ "unchecked", "deprecation" })
		List<ContractDetailTO> contractDetailNoList = getSqlMapClientTemplate()
				.queryForList("contractDetail.selectContractDetailCount", contractNo);

		TreeSet<Integer> intSet = new TreeSet<>();

		for (ContractDetailTO bean : contractDetailNoList) {

			String contractDetailNo = bean.getContractDetailNo();
			int no = Integer.parseInt(contractDetailNo.split("-")[1]);

			intSet.add(no);
		}

		if (intSet.isEmpty()) {
			return 1;
		} else {
			return intSet.pollLast() + 1;
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<ContractDetailInMpsAvailableTO> selectContractDetailListInMpsAvailable(String searchCondition,
			String startDate, String endDate) {

		HashMap<String, String> map = new HashMap<>();

		map.put("searchCondition", searchCondition);
		map.put("startDate", startDate);
		map.put("endDate", endDate);

		return (ArrayList<ContractDetailInMpsAvailableTO>) getSqlMapClientTemplate()
				.queryForList("contractDetail.selectContractDetailListInMpsAvailable", map);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void insertContractDetail(ContractDetailTO bean) {

		getSqlMapClientTemplate().insert("contractDetail.insertContractDetail", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateContractDetail(ContractDetailTO bean) {

		getSqlMapClientTemplate().update("contractDetail.updateContractDetail", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void changeMpsStatusOfContractDetail(String contractDetailNo, String mpsStatus) {

		HashMap<String, String> map = new HashMap<>();

		map.put("contractDetailNo", contractDetailNo);
		map.put("mpsStatus", mpsStatus);

		getSqlMapClientTemplate().update("contractDetail.changeMpsStatusOfContractDetail", map);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void deleteContractDetail(ContractDetailTO bean) {

		getSqlMapClientTemplate().delete("contractDetail.deleteContractDetail", bean);

	}

}
