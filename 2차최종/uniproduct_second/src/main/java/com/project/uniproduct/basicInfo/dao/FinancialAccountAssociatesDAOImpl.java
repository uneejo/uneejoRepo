package com.project.uniproduct.basicInfo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.uniproduct.basicInfo.to.FinancialAccountAssociatesTO;
import com.project.uniproduct.common.transaction.DataSourceTransactionManager;
import com.project.uniproduct.common.exception.DataAccessException;

public class FinancialAccountAssociatesDAOImpl implements FinancialAccountAssociatesDAO {

private DataSourceTransactionManager dataSourceTransactionManager;
	
	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		
		this.dataSourceTransactionManager=dataSourceTransactionManager;
	}
	@Override
	public ArrayList<FinancialAccountAssociatesTO> selectFinancialAccountAssociatesListByCompany() {

		

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<FinancialAccountAssociatesTO> financialAccountList = new ArrayList<FinancialAccountAssociatesTO>();

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();

			query.append("SELECT * FROM FINANCIAL_ACCOUNT_ASSOCIATES");
			pstmt = conn.prepareStatement(query.toString());
			
			rs = pstmt.executeQuery();

			FinancialAccountAssociatesTO bean = null;
			
			while (rs.next()) {

				bean = new FinancialAccountAssociatesTO();

				bean.setAccountAssociatesCode(rs.getString("ACCOUNT_ASSOCIATES_CODE"));
				bean.setWorkplaceCode(rs.getString("WORKPLACE_CODE"));
				bean.setAccountAssociatesName(rs.getString("ACCOUNT_ASSOCIATES_NAME"));
				bean.setAccountAssociatesType(rs.getString("ACCOUNT_ASSOCIATES_TYPE"));
				bean.setAccountNumber(rs.getString("ACCOUNT_NUMBER"));
				bean.setAccountOpenPlace(rs.getString("ACCOUNT_OPEN_PLACE"));
				bean.setCardNumber(rs.getString("CARD_NUMBER"));
				bean.setCardType(rs.getString("CARD_TYPE"));
				bean.setCardMemberName(rs.getString("CARD_MEMBER_NAME"));
				bean.setCardOpenPlace(rs.getString("CARD_OPEN_PLACE"));
				bean.setBusinessLicenseNumber(rs.getString("BUSINESS_LICENSE_NUMBER"));
				bean.setFinancialInstituteCode(rs.getString("FINANCIAL_INSTITUTE_CODE"));
				bean.setFinancialInstituteName(rs.getString("FINANCIAL_INSTITUTE_NAME"));
				bean.setFinancialAccountNote(rs.getString("FINANCIAL_ACCOUNT_NOTE"));

				financialAccountList.add(bean);
				
			}

			return financialAccountList;

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}

	public ArrayList<FinancialAccountAssociatesTO> selectFinancialAccountAssociatesListByWorkplace(
			String workplaceCode) {

		

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<FinancialAccountAssociatesTO> financialAccountList = new ArrayList<FinancialAccountAssociatesTO>();

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();

			query.append("SELECT * FROM FINANCIAL_ACCOUNT_ASSOCIATES WHERE WORKPLACE_CODE = ?");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, workplaceCode);

			rs = pstmt.executeQuery();

			FinancialAccountAssociatesTO bean = null;
			
			while (rs.next()) {

				bean = new FinancialAccountAssociatesTO();

				bean.setAccountAssociatesCode(rs.getString("ACCOUNT_ASSOCIATES_CODE"));
				bean.setWorkplaceCode(rs.getString("WORKPLACE_CODE"));
				bean.setAccountAssociatesName(rs.getString("ACCOUNT_ASSOCIATES_NAME"));
				bean.setAccountAssociatesType(rs.getString("ACCOUNT_ASSOCIATES_TYPE"));
				bean.setAccountNumber(rs.getString("ACCOUNT_NUMBER"));
				bean.setAccountOpenPlace(rs.getString("ACCOUNT_OPEN_PLACE"));
				bean.setCardNumber(rs.getString("CARD_NUMBER"));
				bean.setCardType(rs.getString("CARD_TYPE"));
				bean.setCardMemberName(rs.getString("CARD_MEMBER_NAME"));
				bean.setCardOpenPlace(rs.getString("CARD_OPEN_PLACE"));
				bean.setBusinessLicenseNumber(rs.getString("BUSINESS_LICENSE_NUMBER"));
				bean.setFinancialInstituteCode(rs.getString("FINANCIAL_INSTITUTE_CODE"));
				bean.setFinancialInstituteName(rs.getString("FINANCIAL_INSTITUTE_NAME"));
				bean.setFinancialAccountNote(rs.getString("FINANCIAL_ACCOUNT_NOTE"));

				financialAccountList.add(bean);
				
			}

			return financialAccountList;

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}

	@Override
	public void insertFinancialAccountAssociates(FinancialAccountAssociatesTO bean) {

		

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();

			/*
			 * 
			 * Insert into FINANCIAL_ACCOUNT_ASSOCIATES ( ACCOUNT_ASSOCIATES_CODE ,
			 * WORKPLACE_CODE , ACCOUNT_ASSOCIATES_NAME , ACCOUNT_ASSOCIATES_TYPE ,
			 * ACCOUNT_NUMBER , ACCOUNT_OPEN_PLACE , CARD_NUMBER , CARD_TYPE ,
			 * CARD_MEMBER_NAME , CARD_OPEN_PLACE , BUSINESS_LICENSE_NUMBER ,
			 * FINANCIAL_INSTITUTE_CODE , FINANCIAL_INSTITUTE_NAME , FINANCIAL_ACCOUNT_NOTE
			 * ) values ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)
			 * 
			 * 
			 */

			query.append("Insert into FINANCIAL_ACCOUNT_ASSOCIATES \r\n"
					+ "( ACCOUNT_ASSOCIATES_CODE , WORKPLACE_CODE , ACCOUNT_ASSOCIATES_NAME , \r\n"
					+ "ACCOUNT_ASSOCIATES_TYPE , ACCOUNT_NUMBER , ACCOUNT_OPEN_PLACE , \r\n"
					+ "CARD_NUMBER , CARD_TYPE , CARD_MEMBER_NAME , \r\n"
					+ "CARD_OPEN_PLACE , BUSINESS_LICENSE_NUMBER , FINANCIAL_INSTITUTE_CODE , \r\n"
					+ "FINANCIAL_INSTITUTE_NAME , FINANCIAL_ACCOUNT_NOTE ) \r\n"
					+ "values ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");

			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, bean.getAccountAssociatesCode());
			pstmt.setString(2, bean.getWorkplaceCode());
			pstmt.setString(3, bean.getAccountAssociatesName());
			pstmt.setString(4, bean.getAccountAssociatesType());
			pstmt.setString(5, bean.getAccountNumber());
			pstmt.setString(6, bean.getAccountOpenPlace());
			pstmt.setString(7, bean.getCardNumber());
			pstmt.setString(8, bean.getCardType());
			pstmt.setString(9, bean.getCardMemberName());
			pstmt.setString(10, bean.getCardOpenPlace());
			pstmt.setString(11, bean.getBusinessLicenseNumber());
			pstmt.setString(12, bean.getFinancialInstituteCode());
			pstmt.setString(13, bean.getFinancialInstituteName());
			pstmt.setString(14, bean.getFinancialAccountNote());

			rs = pstmt.executeQuery();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}

	@Override
	public void updateFinancialAccountAssociates(FinancialAccountAssociatesTO bean) {


		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();

			/*
			 * 
			 * UPDATE FINANCIAL_ACCOUNT_ASSOCIATES SET WORKPLACE_CODE = ? ,
			 * ACCOUNT_ASSOCIATES_NAME = ? , ACCOUNT_ASSOCIATES_TYPE = ? , ACCOUNT_NUMBER =
			 * ? , ACCOUNT_OPEN_PLACE = ? , CARD_NUMBER = ? , CARD_TYPE = ? ,
			 * CARD_MEMBER_NAME = ? , CARD_OPEN_PLACE = ? , BUSINESS_LICENSE_NUMBER = ? ,
			 * FINANCIAL_INSTITUTE_CODE = ? , FINANCIAL_INSTITUTE_NAME = ? ,
			 * FINANCIAL_ACCOUNT_NOTE = ? WHERE ACCOUNT_ASSOCIATES_CODE = ?
			 * 
			 * 
			 */

			query.append("UPDATE FINANCIAL_ACCOUNT_ASSOCIATES SET \r\n"
					+ "WORKPLACE_CODE = ? , ACCOUNT_ASSOCIATES_NAME = ? , ACCOUNT_ASSOCIATES_TYPE = ? , \r\n"
					+ "ACCOUNT_NUMBER = ? , ACCOUNT_OPEN_PLACE = ? , CARD_NUMBER = ? , \r\n"
					+ "CARD_TYPE = ? , CARD_MEMBER_NAME = ? , CARD_OPEN_PLACE = ? , \r\n"
					+ "BUSINESS_LICENSE_NUMBER = ? , FINANCIAL_INSTITUTE_CODE = ? , \r\n"
					+ "FINANCIAL_INSTITUTE_NAME = ? , FINANCIAL_ACCOUNT_NOTE = ?\r\n"
					+ "WHERE ACCOUNT_ASSOCIATES_CODE = ?");

			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, bean.getWorkplaceCode());
			pstmt.setString(2, bean.getAccountAssociatesName());
			pstmt.setString(3, bean.getAccountAssociatesType());
			pstmt.setString(4, bean.getAccountNumber());
			pstmt.setString(5, bean.getAccountOpenPlace());
			pstmt.setString(6, bean.getCardNumber());
			pstmt.setString(7, bean.getCardType());
			pstmt.setString(8, bean.getCardMemberName());
			pstmt.setString(9, bean.getCardOpenPlace());
			pstmt.setString(10, bean.getBusinessLicenseNumber());
			pstmt.setString(11, bean.getFinancialInstituteCode());
			pstmt.setString(12, bean.getFinancialInstituteName());
			pstmt.setString(13, bean.getFinancialAccountNote());
			pstmt.setString(14, bean.getAccountAssociatesCode());

			rs = pstmt.executeQuery();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}

	}

	@Override
	public void deleteFinancialAccountAssociates(FinancialAccountAssociatesTO bean) {

		

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();

			query.append("DELETE FROM FINANCIAL_ACCOUNT_ASSOCIATES WHERE ACCOUNT_ASSOCIATES_CODE = ?");

			pstmt = conn.prepareStatement(query.toString());
			
			pstmt.setString(1, bean.getAccountAssociatesCode());

			rs = pstmt.executeQuery();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}

	}

}
