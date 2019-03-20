package com.project.uniproduct.base.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.project.uniproduct.base.to.ContractReportTO;
import com.project.uniproduct.base.to.EstimateReportTO;
import com.project.uniproduct.common.exception.DataAccessException;
import com.project.uniproduct.common.transaction.DataSourceTransactionManager;

public class ReportDAOImpl implements ReportDAO {

	private DataSourceTransactionManager dataSourceTransactionManager;

	public final void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager = dataSourceTransactionManager;
	}

	public ArrayList<EstimateReportTO> selectEstimateReport(String estimateNo) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<EstimateReportTO> estimateList = new ArrayList<>();

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();

			/*
			 * SELECT E.ESTIMATE_NO, E.ESTIMATE_DATE, D.UNIT_OF_ESTIMATE, D.ESTIMATE_AMOUNT,
			 * D.UNIT_PRICE_OF_ESTIMATE, D.SUM_PRICE_OF_ESTIMATE, I.ITEM_CODE, I.ITEM_NAME,
			 * C.CUSTOMER_NAME, C.CUSTOMER_TEL_NUMBER, C.CUSTOMER_FAX_NUMBER,
			 * D.SUM_PRICE_OF_ESTIMATE * 0.1 AS TAX, ( D.UNIT_PRICE_OF_ESTIMATE *
			 * D.ESTIMATE_AMOUNT ) + ( D.SUM_PRICE_OF_ESTIMATE * 0.1 ) AS TOTAL_AMOUNT , SUM
			 * ( ( D.UNIT_PRICE_OF_ESTIMATE * D.ESTIMATE_AMOUNT ) ) OVER ( PARTITION BY
			 * E.ESTIMATE_NO ORDER BY E.ESTIMATE_NO ROWS BETWEEN UNBOUNDED PRECEDING AND
			 * UNBOUNDED FOLLOWING ) AS TOTAL_ESTIMATE_PRICE, SUM ( (
			 * D.UNIT_PRICE_OF_ESTIMATE * D.ESTIMATE_AMOUNT ) + ( D.SUM_PRICE_OF_ESTIMATE *
			 * 0.1 ) ) OVER ( PARTITION BY E.ESTIMATE_NO ORDER BY E.ESTIMATE_NO ROWS BETWEEN
			 * UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING ) AS
			 * TOTAL_ESTIMATE_PRICE_WITH_TAX FROM ESTIMATE E INNER JOIN ESTIMATE_DETAIL D ON
			 * E.ESTIMATE_NO = D.ESTIMATE_NO INNER JOIN CUSTOMER C ON E.CUSTOMER_CODE =
			 * C.CUSTOMER_CODE INNER JOIN ITEM I ON D.ITEM_CODE = I.ITEM_CODE WHERE
			 * E.ESTIMATE_NO =?
			 */

			query.append("SELECT E.ESTIMATE_NO, E.ESTIMATE_DATE, D.UNIT_OF_ESTIMATE, D.ESTIMATE_AMOUNT, \r\n"
					+ "    D.UNIT_PRICE_OF_ESTIMATE, D.SUM_PRICE_OF_ESTIMATE, I.ITEM_CODE, I.ITEM_NAME, \r\n"
					+ "    C.CUSTOMER_NAME, C.CUSTOMER_TEL_NUMBER, C.CUSTOMER_FAX_NUMBER, \r\n"
					+ "    D.SUM_PRICE_OF_ESTIMATE * 0.1 AS TAX,\r\n"
					+ "    ( D.UNIT_PRICE_OF_ESTIMATE * D.ESTIMATE_AMOUNT ) + ( D.SUM_PRICE_OF_ESTIMATE * 0.1 ) AS TOTAL_AMOUNT ,\r\n"
					+ "    SUM ( ( D.UNIT_PRICE_OF_ESTIMATE * D.ESTIMATE_AMOUNT ) ) \r\n"
					+ "    OVER ( PARTITION BY E.ESTIMATE_NO ORDER BY E.ESTIMATE_NO ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING ) AS TOTAL_ESTIMATE_PRICE, \r\n"
					+ "    SUM ( ( D.UNIT_PRICE_OF_ESTIMATE * D.ESTIMATE_AMOUNT ) + ( D.SUM_PRICE_OF_ESTIMATE * 0.1 ) ) \r\n"
					+ "    OVER ( PARTITION BY E.ESTIMATE_NO ORDER BY E.ESTIMATE_NO ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING ) AS TOTAL_ESTIMATE_PRICE_WITH_TAX \r\n"
					+ "FROM\r\n" + "ESTIMATE E INNER JOIN ESTIMATE_DETAIL D ON E.ESTIMATE_NO = D.ESTIMATE_NO\r\n"
					+ "INNER JOIN CUSTOMER C ON E.CUSTOMER_CODE = C.CUSTOMER_CODE\r\n"
					+ "INNER JOIN ITEM I ON D.ITEM_CODE = I.ITEM_CODE\r\n" + "WHERE E.ESTIMATE_NO =?");

			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, estimateNo);

			rs = pstmt.executeQuery();

			EstimateReportTO bean = null;

			while (rs.next()) {

				bean = new EstimateReportTO();

				bean.setEstimateNo(rs.getString("ESTIMATE_NO"));
				bean.setEstimateDate(rs.getString("ESTIMATE_DATE"));
				bean.setItemCode(rs.getString("ITEM_CODE"));
				bean.setItemName(rs.getString("ITEM_NAME"));
				bean.setUnitOfEstimate(rs.getString("UNIT_OF_ESTIMATE"));
				bean.setEstimateAmount(rs.getString("ESTIMATE_AMOUNT"));
				bean.setUnitPriceOfEstimate(rs.getString("UNIT_PRICE_OF_ESTIMATE"));
				bean.setSumPriceOfEstimate(rs.getString("SUM_PRICE_OF_ESTIMATE"));
				bean.setCustomerName(rs.getString("CUSTOMER_NAME"));
				bean.setCustomerTelNumber(rs.getString("CUSTOMER_TEL_NUMBER"));
				bean.setCustomerFaxNumber(rs.getString("CUSTOMER_FAX_NUMBER"));
				bean.setTax(rs.getString("TAX"));
				bean.setTotalAmount(rs.getString("TOTAL_AMOUNT"));
				bean.setTotalEstimatePrice(rs.getString("TOTAL_ESTIMATE_PRICE"));
				bean.setTotalEstimatePriceWithTax(rs.getString("TOTAL_ESTIMATE_PRICE_WITH_TAX"));

				estimateList.add(bean);

			}

			return estimateList;

		} catch (SQLException e) {

			throw new DataAccessException(e.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

	public ArrayList<ContractReportTO> selectContractReport(String contractNo) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<ContractReportTO> contractList = new ArrayList<>();

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();

			/*
WITH CONTRACT_INFO AS
( SELECT
C.CONTRACT_NO, C.CONTRACT_DATE, D.ITEM_CODE, D.ITEM_NAME, 
D.UNIT_PRICE_OF_CONTRACT, D.SUM_PRICE_OF_CONTRACT, 
D.UNIT_OF_CONTRACT, D.CONTRACT_AMOUNT, CU.BUSINESS_LICENSE_NUMBER, CU.CUSTOMER_NAME, CU.CUSTOMER_CEO, CU.CUSTOMER_BASIC_ADDRESS, 
CU.CUSTOMER_BUSINESS_CONDITIONS, CU.CUSTOMER_BUSINESS_ITEMS,
( D.SUM_PRICE_OF_CONTRACT * 0.1 ) AS TAX,
( ( D.UNIT_PRICE_OF_CONTRACT * D.CONTRACT_AMOUNT) + ( D.SUM_PRICE_OF_CONTRACT * 0.1 ) ) AS TOTAL_AMOUNT
FROM
CONTRACT C INNER JOIN CONTRACT_DETAIL D ON C.CONTRACT_NO = D.CONTRACT_NO
INNER JOIN CUSTOMER CU ON C.CUSTOMER_CODE = CU.CUSTOMER_CODE
INNER JOIN ITEM I ON D.ITEM_CODE = I.ITEM_CODE
WHERE C.CONTRACT_NO = ? ),

CONTRACT_SUM AS
( SELECT SUM(SUM_PRICE_OF_CONTRACT) AS SUM_PRICE, SUM(CONTRACT_AMOUNT) AS SUM_AMOUNT,
SUM(SUM_PRICE_OF_CONTRACT)*0.1 AS SUM_TAX,
(SUM(SUM_PRICE_OF_CONTRACT)*0.1)+SUM(SUM_PRICE_OF_CONTRACT) AS SUM_TOTAL_AMOUNT
FROM
CONTRACT_INFO )

SELECT * FROM CONTRACT_INFO, CONTRACT_SUM
			 */

			query.append("WITH CONTRACT_INFO AS\r\n" + 
					"( SELECT\r\n" + 
					"C.CONTRACT_NO, C.CONTRACT_DATE, D.ITEM_CODE, D.ITEM_NAME, \r\n" + 
					"D.UNIT_PRICE_OF_CONTRACT, D.SUM_PRICE_OF_CONTRACT, \r\n" + 
					"D.UNIT_OF_CONTRACT, D.CONTRACT_AMOUNT, CU.BUSINESS_LICENSE_NUMBER, CU.CUSTOMER_NAME, CU.CUSTOMER_CEO, CU.CUSTOMER_BASIC_ADDRESS, \r\n" + 
					"CU.CUSTOMER_BUSINESS_CONDITIONS, CU.CUSTOMER_BUSINESS_ITEMS,\r\n" + 
					"( D.SUM_PRICE_OF_CONTRACT * 0.1 ) AS TAX,\r\n" + 
					"( ( D.UNIT_PRICE_OF_CONTRACT * D.CONTRACT_AMOUNT) + ( D.SUM_PRICE_OF_CONTRACT * 0.1 ) ) AS TOTAL_AMOUNT\r\n" + 
					"FROM\r\n" + 
					"CONTRACT C INNER JOIN CONTRACT_DETAIL D ON C.CONTRACT_NO = D.CONTRACT_NO\r\n" + 
					"INNER JOIN CUSTOMER CU ON C.CUSTOMER_CODE = CU.CUSTOMER_CODE\r\n" + 
					"INNER JOIN ITEM I ON D.ITEM_CODE = I.ITEM_CODE\r\n" + 
					"WHERE C.CONTRACT_NO = ? ),\r\n" + 
					"\r\n" + 
					"CONTRACT_SUM AS\r\n" + 
					"( SELECT SUM(SUM_PRICE_OF_CONTRACT) AS SUM_PRICE, SUM(CONTRACT_AMOUNT) AS SUM_AMOUNT,\r\n" + 
					"SUM(SUM_PRICE_OF_CONTRACT)*0.1 AS SUM_TAX,\r\n" + 
					"(SUM(SUM_PRICE_OF_CONTRACT)*0.1)+SUM(SUM_PRICE_OF_CONTRACT) AS SUM_TOTAL_AMOUNT\r\n" + 
					"FROM\r\n" + 
					"CONTRACT_INFO )\r\n" + 
					"\r\n" + 
					"SELECT * FROM CONTRACT_INFO, CONTRACT_SUM");

			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, contractNo);

			rs = pstmt.executeQuery();

			ContractReportTO bean = null;

			while (rs.next()) {

				bean = new ContractReportTO();

				bean.setContractNo(rs.getString("CONTRACT_NO"));
				bean.setContractDate(rs.getString("CONTRACT_DATE"));
				bean.setItemCode(rs.getString("ITEM_CODE"));
				bean.setItemName(rs.getString("ITEM_NAME"));
				bean.setUnitPriceOfContract(rs.getString("UNIT_PRICE_OF_CONTRACT"));
				bean.setSumPriceOfContract(rs.getString("SUM_PRICE_OF_CONTRACT"));
				bean.setUnitOfContract(rs.getString("UNIT_OF_CONTRACT"));
				bean.setContractAmount(rs.getString("CONTRACT_AMOUNT"));
				bean.setBusinessLicenseNumber(rs.getString("BUSINESS_LICENSE_NUMBER"));
				bean.setCustomerName(rs.getString("CUSTOMER_NAME"));
				bean.setCustomerCeo(rs.getString("CUSTOMER_CEO"));
				bean.setCustomerBasicAddress(rs.getString("CUSTOMER_BASIC_ADDRESS"));
				bean.setCustomerBusinessConditions(rs.getString("CUSTOMER_BUSINESS_CONDITIONS"));
				bean.setCustomerBusinessItems(rs.getString("CUSTOMER_BUSINESS_ITEMS"));
				bean.setSumPrice(rs.getString("SUM_PRICE"));
				bean.setSumAmount(rs.getString("SUM_AMOUNT"));
				bean.setSumTax(rs.getString("SUM_TAX"));
				bean.setSumTotalAmount(rs.getString("SUM_TOTAL_AMOUNT"));
				
				contractList.add(bean);

			}

			return contractList;

		} catch (SQLException e) {

			throw new DataAccessException(e.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);
		}
	}

}
