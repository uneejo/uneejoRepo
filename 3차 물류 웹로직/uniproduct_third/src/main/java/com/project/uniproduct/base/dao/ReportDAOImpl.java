package com.project.uniproduct.base.dao;

import java.util.ArrayList;

import com.project.uniproduct.base.to.ContractReportTO;
import com.project.uniproduct.base.to.EstimateReportTO;
import com.project.uniproduct.common.dao.IbatisSupportDAO;

public class ReportDAOImpl extends IbatisSupportDAO implements ReportDAO {

	@SuppressWarnings({ "unchecked", "deprecation" })
	public ArrayList<EstimateReportTO> selectEstimateReport(String estimateNo) {


			return (ArrayList<EstimateReportTO>)getSqlMapClientTemplate().queryForList("report.selectEstimateReport",estimateNo);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public ArrayList<ContractReportTO> selectContractReport(String contractNo) {

		return (ArrayList<ContractReportTO>)getSqlMapClientTemplate().queryForList("report.selectContractReport",contractNo);
		
	}

}
