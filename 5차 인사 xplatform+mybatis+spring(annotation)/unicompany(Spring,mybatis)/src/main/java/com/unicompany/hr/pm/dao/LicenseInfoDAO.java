package com.unicompany.hr.pm.dao;

import java.util.List;

import com.unicompany.hr.pm.to.LicenseInfoBean;

public interface LicenseInfoDAO {
	public List<LicenseInfoBean> selectLicenseInfoAll();
	
	public void insertLicenseInfo(LicenseInfoBean licenseInfoBean);
	public void updateLicenseInfo(LicenseInfoBean licenseInfoBean);
	public void deleteLicenseInfo(LicenseInfoBean licenseInfoBean);
	
}
