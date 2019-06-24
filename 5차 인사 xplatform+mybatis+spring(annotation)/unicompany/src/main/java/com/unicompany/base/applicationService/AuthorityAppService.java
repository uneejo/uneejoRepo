package com.unicompany.base.applicationService;

import java.util.List;

import com.unicompany.base.to.AuthorityBean;
import com.unicompany.base.to.AuthorityInfoBean;
import com.unicompany.base.to.MenuAuthorityBean;

public interface AuthorityAppService {
    public List<MenuAuthorityBean> findMenuAuthorityList(String authorityCode);
    public List<AuthorityBean> findAuthorityList();
    public List<MenuAuthorityBean> findMenuAuthorityListAll();
    public void batchAuthority(AuthorityInfoBean authorityInfoBean);
   
}