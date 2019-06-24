package com.unicompany.base.applicationService;

import java.util.List;
import java.util.Map;

import com.unicompany.base.to.AddressBean;
import com.unicompany.base.to.SidoBean;
import com.unicompany.base.to.SigunguBean;

public interface PostAppService {
	public List<SidoBean> findSido();
	public List<SigunguBean> findSiGunGuList(String sido);
	public List<AddressBean> findAddrList(Map<String, Object> addrList);
}
