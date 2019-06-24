package com.project.uniproduct.base.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.project.uniproduct.base.to.CodeDetailTO;
import com.project.uniproduct.common.transaction.DataSourceTransactionManager;
import com.project.uniproduct.common.exception.DataAccessException;

public class CodeDetailDAOImpl implements CodeDetailDAO {


	private DataSourceTransactionManager dataSourceTransactionManager;
	
	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		
		this.dataSourceTransactionManager=dataSourceTransactionManager;
	}
	
	@Override
	public ArrayList<CodeDetailTO> selectDetailCodeList(String divisionCode) {


		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<CodeDetailTO> detailCodeList = new ArrayList<CodeDetailTO>();

		try {
			con = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT * FROM CODE_DETAIL WHERE DIVISION_CODE_NO LIKE ?");

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, divisionCode);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				CodeDetailTO bean = new CodeDetailTO();

				bean.setCodeUseCheck(rs.getString("CODE_USE_CHECK"));
				bean.setDescription(rs.getString("DESCRIPTION"));
				bean.setDetailCode(rs.getString("DETAIL_CODE"));
				bean.setDetailCodeName(rs.getString("DETAIL_CODE_NAME"));
				bean.setDivisionCodeNo(rs.getString("DIVISION_CODE_NO"));

				detailCodeList.add(bean);
			}

			return detailCodeList;

		} catch (SQLException e) {

			throw new DataAccessException(e.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}

	@Override
	public void insertDetailCode(CodeDetailTO bean) {

	
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			/*
			 * Insert into CODE_DETAIL ( DIVISION_CODE_NO , DETAIL_CODE , DETAIL_CODE_NAME ,
			 * CODE_USE_CHECK , DESCRIPTION ) values ('IT-MA','MM-02','액정모니터 2.0"
			 * TFT',null,null)
			 */
			query.append("Insert into CODE_DETAIL \r\n"
					+ "( DIVISION_CODE_NO , DETAIL_CODE , DETAIL_CODE_NAME , CODE_USE_CHECK , DESCRIPTION ) \r\n"
					+ "values ( ? , ? , ? , ? , ? )");

			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, bean.getDivisionCodeNo());
			pstmt.setString(2, bean.getDetailCode());
			pstmt.setString(3, bean.getDetailCodeName());
			pstmt.setString(4, bean.getCodeUseCheck());
			pstmt.setString(5, bean.getDescription());

			rs = pstmt.executeQuery();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}

	@Override
	public void updateDetailCode(CodeDetailTO bean) {

		

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			/*
			 * UPDATE CODE_DETAIL SET DETAIL_CODE_NAME = ? , CODE_USE_CHECK = ? ,
			 * DESCRIPTION = ? WHERE DIVISION_CODE_NO = ? AND DETAIL_CODE = ?
			 */
			query.append("UPDATE CODE_DETAIL SET \r\n"
					+ "DETAIL_CODE_NAME  = ? , CODE_USE_CHECK  = ? , DESCRIPTION  = ?  \r\n"
					+ "WHERE DIVISION_CODE_NO = ? AND DETAIL_CODE  = ?");

			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, bean.getDetailCodeName());
			pstmt.setString(2, bean.getCodeUseCheck());
			pstmt.setString(3, bean.getDescription());
			pstmt.setString(4, bean.getDivisionCodeNo());
			pstmt.setString(5, bean.getDetailCode());

			rs = pstmt.executeQuery();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}

	@Override
	public void deleteDetailCode(CodeDetailTO bean) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			/*
			 * DELETE FROM CODE_DETAIL WHERE DIVISION_CODE_NO = ? AND DETAIL_CODE = ?
			 */
			query.append("DELETE FROM CODE_DETAIL WHERE DIVISION_CODE_NO = ? AND DETAIL_CODE  = ?");

			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, bean.getDivisionCodeNo());
			pstmt.setString(2, bean.getDetailCode());

			rs = pstmt.executeQuery();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}

	}

	@Override
	public void changeCodeUseCheck(String divisionCodeNo, String detailCode, String codeUseCheck) {

	
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			/*
			 * UPDATE CODE_DETAIL SET CODE_USE_CHECK = ? WHERE DIVISION_CODE_NO = ? AND
			 * DETAIL_CODE = ?
			 */
			query.append("UPDATE CODE_DETAIL SET \r\n" + "CODE_USE_CHECK  = ?  \r\n"
					+ "WHERE DIVISION_CODE_NO = ? AND DETAIL_CODE  = ?");

			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, ((codeUseCheck.equals("N") || codeUseCheck.equals("n")) ? "N" : ""));
			pstmt.setString(2, divisionCodeNo);
			pstmt.setString(3, detailCode);

			rs = pstmt.executeQuery();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}

}
