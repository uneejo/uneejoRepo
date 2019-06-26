package com.unicompany.base.dao;

import java.util.List;

import com.unicompany.base.to.CodeBean;
import com.unicompany.base.to.DetailCodeBean;

public interface CodeDAO {

	public List<CodeBean> selectCodeList();
	
	public void insertCode(CodeBean codeBean);
    public void updateCode(CodeBean codeBean);
    public void deleteCode(CodeBean codeBean); //삭제는 사용 안함!!
    public void insertDetailCode(DetailCodeBean detailCodeBean);
    public void updateDetailCode(DetailCodeBean detailCodeBean);
    public void deleteDetailCode(DetailCodeBean detailCodeBean);

}
