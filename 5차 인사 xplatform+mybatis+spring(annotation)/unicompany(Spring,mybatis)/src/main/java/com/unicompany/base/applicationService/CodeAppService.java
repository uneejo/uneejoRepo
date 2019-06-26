package com.unicompany.base.applicationService;

import java.util.List;

import com.unicompany.base.to.CodeBean;
import com.unicompany.base.to.CodeInfoBean;
import com.unicompany.base.to.DetailCodeBean;

/*import com.worldMiplatform.base.to.CodeInfoBean;*/

public interface CodeAppService {
    public List<CodeBean> findCodeList(); 
   public void batchCode(CodeInfoBean codeInfoBean);
    public void batchDetailCode(List<DetailCodeBean> detailCodeList);
}
