 package com.unicompany.base.applicationService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unicompany.base.dao.DeptDAO;
import com.unicompany.base.to.DepartmentBean;


@Component
public class BasicDeptAppServiceImpl implements BasicDeptAppService {
    
	@Autowired
    private DeptDAO deptDAO;
    

    @Override
    public List<DepartmentBean> findDeptList(String businessPlaceCode) {
        return deptDAO.selectDeptList(businessPlaceCode);
    }

	@Override
	public void batchDept(List<DepartmentBean> deptList) {
		for(DepartmentBean deptBean:deptList){
			switch(deptBean.getStatus()){ /*mapper에서 bean들의  rowType를 읽어 온듯?? 필요없는경우에 받아만 놓고 사용 안함*/
				case "insert" : deptDAO.insertDept(deptBean); break;
				case "update" : deptDAO.updateDept(deptBean); break;
				case "delete" : deptDAO.deleteDept(deptBean); break;
			}
		}
	}

}
