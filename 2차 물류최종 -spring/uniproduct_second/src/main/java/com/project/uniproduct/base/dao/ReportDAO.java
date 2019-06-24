package com.project.uniproduct.base.dao;

import java.util.ArrayList;

import com.project.uniproduct.base.to.ContractReportTO;
import com.project.uniproduct.base.to.EstimateReportTO;

public interface ReportDAO {

	public ArrayList<EstimateReportTO> selectEstimateReport(String estimateNo);

	public ArrayList<ContractReportTO> selectContractReport(String contractNo);
	
}
