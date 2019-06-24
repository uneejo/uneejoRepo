package com.project.uniproduct.base.applicationService;

import java.util.ArrayList;

import com.project.uniproduct.base.to.AddressTO;

public interface AddressApplicationService {
		
	public ArrayList<AddressTO> getAddressList(String sidoName, String searchAddressType, String searchValue, String mainNumber);
	
}
