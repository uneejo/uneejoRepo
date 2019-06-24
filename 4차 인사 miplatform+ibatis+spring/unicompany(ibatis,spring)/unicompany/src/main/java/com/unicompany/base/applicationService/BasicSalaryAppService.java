package com.unicompany.base.applicationService;

import java.util.List;

import com.unicompany.base.to.EtcSalBean;
import com.unicompany.base.to.OvertimeSalBean;
import com.unicompany.base.to.SudangInfoBean;

public interface BasicSalaryAppService {
    public List<OvertimeSalBean> findOvertimeSalList();
    public List<EtcSalBean> findEtcSalList();
    
    public void batchSudang(SudangInfoBean sudangInfoBean);
}
