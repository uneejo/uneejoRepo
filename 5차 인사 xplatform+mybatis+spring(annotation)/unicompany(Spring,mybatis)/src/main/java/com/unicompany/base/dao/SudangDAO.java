package com.unicompany.base.dao;

import java.util.List;

import com.unicompany.base.to.EtcSalBean;
import com.unicompany.base.to.OvertimeSalBean;

public interface SudangDAO {

	public List<OvertimeSalBean> selectOvertimeSalList();
	public List<EtcSalBean> selectEtcSalList();

	public void insertOvertimeSal(OvertimeSalBean overtimeSalBean);
    public void updateOvertimeSal(OvertimeSalBean overtimeSalBean);
    public void deleteOvertimeSal(OvertimeSalBean overtimeSalBean);

    public void insertEtcSal(EtcSalBean etcSalBean);
    public void updateEtcSal(EtcSalBean etcSalBean);
    public void deleteEtcSal(EtcSalBean etcSalBean);
   

}
