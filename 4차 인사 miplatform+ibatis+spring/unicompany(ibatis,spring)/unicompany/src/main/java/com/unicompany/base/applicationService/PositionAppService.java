package com.unicompany.base.applicationService;

import java.util.List;

import com.unicompany.base.to.PositionBean;

public interface PositionAppService {
    public List<PositionBean> findPositionList();
    
    public PositionBean findPosition(String positionCode);
}
