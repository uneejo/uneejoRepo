package com.unicompany.base.dao;

import java.util.List;
import java.util.Map;

import com.unicompany.base.to.AddressBean;
import com.unicompany.base.to.SidoBean;
import com.unicompany.base.to.SigunguBean;
import com.unicompany.common.dao.IBatisDAO;

public class PostDAOImpl extends IBatisDAO implements PostDAO {

    @SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	/* 특별시, 광역시, 도를 조회하는 메서드 */
	public List<SidoBean> findSido() {
		return getSqlMapClientTemplate().queryForList("post.findSido");
	}

    /* 특별시,광역시,도에 해당하는 시,군,구를 조회하는 메서드 */
    @SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<SigunguBean> findSiGunGuList(String sido) {
		return getSqlMapClientTemplate().queryForList("post.findSiGunGuList",sido);
	}

    /* 전체조건에 맞는 주소를 가지고 오는 메서드 */
    @SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<AddressBean> findAddrList(Map<String, Object> addrList) {
		return getSqlMapClientTemplate().queryForList("post.findAddrList",addrList);
	}
}
