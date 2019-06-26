package com.unicompany.base.applicationService;

import java.util.List;

import com.unicompany.base.to.BusinessPlaceBean;


public interface BusinessPlaceAppService{
    public List<BusinessPlaceBean> findBusinessPlaceList();
    public void batchBusinessPlaceList(List<BusinessPlaceBean> businessPlaceList);
}

	