package com.project.uniproduct.base.applicationService;

import java.util.ArrayList;

import com.project.uniproduct.base.to.ContractReportTO;
import com.project.uniproduct.base.to.EstimateReportTO;

public interface ReportApplicationService {

	public ArrayList<EstimateReportTO> getEstimateReport(String estimateNo);

	public ArrayList<ContractReportTO> getContractReport(String contractNo);
	
}
