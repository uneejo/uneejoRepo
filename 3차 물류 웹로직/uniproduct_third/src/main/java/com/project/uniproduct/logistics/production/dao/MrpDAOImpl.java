package com.project.uniproduct.logistics.production.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import com.project.uniproduct.common.dao.IbatisSupportDAO;
import com.project.uniproduct.logistics.production.to.MrpTO;
import com.project.uniproduct.logistics.production.to.OpenMrpTO;

public class MrpDAOImpl extends IbatisSupportDAO implements MrpDAO {

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<MrpTO> selectMrpList(String mrpGatheringStatusCondition) {

		HashMap<String, String> map = new HashMap<>();

		map.put("mrpGatheringStatusCondition", mrpGatheringStatusCondition);
		
		return (ArrayList<MrpTO>) getSqlMapClientTemplate().queryForList("mrp.selectMrpList", map);

	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<MrpTO> selectMrpList(String dateSearchCondtion, String startDate, String endDate) {

		HashMap<String, String> map = new HashMap<>();

		map.put("dateSearchCondtion", dateSearchCondtion);
		map.put("startDate", startDate);
		map.put("endDate", endDate);

		return (ArrayList<MrpTO>) getSqlMapClientTemplate().queryForList("mrp.selectMrpListByDate", map);

	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<MrpTO> selectMrpListAsMrpGatheringNo(String mrpGatheringNo) {

		return (ArrayList<MrpTO>) getSqlMapClientTemplate().queryForList("mrp.selectMrpListAsMrpGatheringNo",
				mrpGatheringNo);

	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public ArrayList<OpenMrpTO> openMrp(String mpsNoList) {

		HashMap<String,String> map = new HashMap<>();
		map.put("mpsNoList", mpsNoList);
		
		
		ArrayList<OpenMrpTO> OpenMrpList =(ArrayList<OpenMrpTO>)getSqlMapClientTemplate().queryForList("mrp.openMrp", map);
		
		System.out.println("errorCode : " + String.valueOf(map.get("errorcode")));
		System.out.println("errorMsg : " + map.get("errorMsg"));
		return OpenMrpList;
	}

	
	@Override
	public int selectMrpCount(String mrpRegisterDate) {

		@SuppressWarnings({ "unchecked", "deprecation" })
		List<MrpTO> mrpTOList = getSqlMapClientTemplate().queryForList("mrp.selectMrpCount", mrpRegisterDate);

		TreeSet<Integer> intSet = new TreeSet<>();

		for (MrpTO bean : mrpTOList) {

			String mrpNo = bean.getMrpNo();

			// MRP 일련번호에서 마지막 3자리만 가져오기
			int no = Integer.parseInt(mrpNo.substring(mrpNo.length() - 3, mrpNo.length()));

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
	public void insertMrp(MrpTO bean) {

		getSqlMapClientTemplate().insert("mrp.insertMrp", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateMrp(MrpTO bean) {

		getSqlMapClientTemplate().update("mrp.updateMrp", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void changeMrpGatheringStatus(String mrpNo, String mrpGatheringNo, String mrpGatheringStatus) {

		HashMap<String, String> map = new HashMap<>();

		map.put("mrpNo", mrpNo);
		map.put("mrpGatheringNo", mrpGatheringNo);
		map.put("mrpGatheringStatus", mrpGatheringStatus);

		getSqlMapClientTemplate().update("mrp.changeMrpGatheringStatus", map);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void deleteMrp(MrpTO bean) {

		getSqlMapClientTemplate().delete("mrp.deleteMrp", bean);

	}

}
