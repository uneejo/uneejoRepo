package com.project.uniproduct.logistics.production.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import com.project.uniproduct.common.dao.IbatisSupportDAO;
import com.project.uniproduct.logistics.production.to.MpsTO;

public class MpsDAOImpl extends IbatisSupportDAO implements MpsDAO {

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<MpsTO> selectMpsList(String startDate, String endDate, String includeMrpApply) {

		HashMap<String, String> map = new HashMap<>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("includeMrpApply", includeMrpApply);

		return (ArrayList<MpsTO>) getSqlMapClientTemplate().queryForList("mps.selectMpsList", map);

	}

	@Override
	public int selectMpsCount(String mpsPlanDate) {

		@SuppressWarnings({ "unchecked", "deprecation" })
		List<MpsTO> mpsTOlist = getSqlMapClientTemplate().queryForList("mps.selectMpsCount", mpsPlanDate);

		TreeSet<Integer> intSet = new TreeSet<>();

		for (MpsTO bean : mpsTOlist) {

			String mpsNo = bean.getMpsNo();

			// MPS 일련번호에서 마지막 2자리만 가져오기
			int no = Integer.parseInt(mpsNo.substring(mpsNo.length() - 2, mpsNo.length()));

			intSet.add(no);

		}

		if (intSet.isEmpty()) {
			return 1;
		} else {
			return intSet.pollLast() + 1; // 가장 높은 번호 + 1
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public void insertMps(MpsTO bean) {

		getSqlMapClientTemplate().insert("mps.insertMps", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateMps(MpsTO bean) {

		getSqlMapClientTemplate().update("mps.updateMps", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void changeMrpApplyStatus(String mpsNo, String mrpStatus) {

		HashMap<String, String> map = new HashMap<>();
		map.put("mpsNo", mpsNo);
		map.put("mrpStatus", mrpStatus);

		getSqlMapClientTemplate().update("mps.changeMrpApplyStatus", map);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void deleteMps(MpsTO bean) {

		getSqlMapClientTemplate().delete("mps.deleteMps", bean);

	}

}
