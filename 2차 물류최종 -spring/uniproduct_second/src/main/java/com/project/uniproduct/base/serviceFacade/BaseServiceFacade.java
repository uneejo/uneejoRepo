package com.project.uniproduct.base.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.uniproduct.base.to.ContractReportTO;
import com.project.uniproduct.base.to.EstimateReportTO;
import com.project.uniproduct.base.to.AddressTO;
import com.project.uniproduct.base.to.CodeDetailTO;
import com.project.uniproduct.base.to.CodeTO;

public interface BaseServiceFacade {

	public ArrayList<CodeDetailTO> getDetailCodeList(String divisionCode);

	public ArrayList<CodeTO> getCodeList();

	public Boolean checkCodeDuplication(String divisionCode, String newDetailCode);

	public HashMap<String, Object> batchCodeListProcess(ArrayList<CodeTO> codeList);

	public HashMap<String, Object> batchDetailCodeListProcess(ArrayList<CodeDetailTO> detailCodeList);

	public HashMap<String, Object> changeCodeUseCheckProcess(ArrayList<CodeDetailTO> detailCodeList);

	public ArrayList<AddressTO> getAddressList(String sidoName, String searchAddressType, String searchValue, String mainNumber);

	public ArrayList<EstimateReportTO> getEstimateReport(String estimateNo);

	public ArrayList<ContractReportTO> getContractReport(String contractNo);
}
