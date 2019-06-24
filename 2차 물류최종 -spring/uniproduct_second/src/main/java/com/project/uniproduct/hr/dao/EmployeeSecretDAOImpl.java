package com.project.uniproduct.hr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.uniproduct.common.transaction.DataSourceTransactionManager;
import com.project.uniproduct.common.exception.DataAccessException;
import com.project.uniproduct.hr.to.EmployeeSecretTO;

public class EmployeeSecretDAOImpl implements EmployeeSecretDAO {

private DataSourceTransactionManager dataSourceTransactionManager;
    
	
	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		
		this.dataSourceTransactionManager=dataSourceTransactionManager;
	}
	
	public ArrayList<EmployeeSecretTO> selectEmployeeSecretList(String companyCode, String empCode) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<EmployeeSecretTO> employeeSecretList = new ArrayList<EmployeeSecretTO>();

		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();

			/*
			 * SELECT * FROM EMPLOYEE_SECRET WHERE COMPANY_CODE = ? AND EMP_CODE =?
			 */

			query.append("SELECT * FROM EMPLOYEE_SECRET WHERE COMPANY_CODE = ? AND EMP_CODE =?");
			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, companyCode);
			pstmt.setString(2, empCode);

			rs = pstmt.executeQuery();

			EmployeeSecretTO TO = null;

			while (rs.next()) {
				TO = new EmployeeSecretTO();

				TO.setCompanyCode(rs.getString("COMPANY_CODE"));
				TO.setEmpCode(rs.getString("EMP_CODE"));
				TO.setSeq(rs.getInt("SEQ"));
				TO.setUserPassword(rs.getString("USER_PASSWORD"));

				employeeSecretList.add(TO);
			}

			return employeeSecretList;

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}

	public EmployeeSecretTO selectUserPassWord(String companyCode, String empCode) {

	

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();

			query.append("SELECT EMP_CODE, COMPANY_CODE, SEQ, USER_PASSWORD\r\n" + "		FROM EMPLOYEE_SECRET\r\n"
					+ "		WHERE ( EMP_CODE, COMPANY_CODE, SEQ) IN ( SELECT EMP_CODE, COMPANY_CODE, MAX(SEQ)\r\n"
					+ "		FROM EMPLOYEE_SECRET\r\n" + "		GROUP BY EMP_CODE, COMPANY_CODE )\r\n"
					+ "		AND COMPANY_CODE = ? AND EMP_CODE = ?");

			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, companyCode);
			pstmt.setString(2, empCode);

			rs = pstmt.executeQuery();

			EmployeeSecretTO TO = null;

			while (rs.next()) {

				TO = new EmployeeSecretTO();

				TO.setCompanyCode(rs.getString("COMPANY_CODE"));
				TO.setEmpCode(rs.getString("EMP_CODE"));
				TO.setSeq(rs.getInt("SEQ"));
				TO.setUserPassword(rs.getString("USER_PASSWORD"));
			}

			return TO;

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}

	public void insertEmployeeSecret(EmployeeSecretTO TO) {

		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();

			/*
			 * Insert into EMPLOYEE_SECRET ( COMPANY_CODE , EMP_CODE , SEQ, USER_PASSWORD )
			 * values ( ? , ? , ? , ? )
			 * 
			 */

			query.append("Insert into EMPLOYEE_SECRET " + "( COMPANY_CODE , EMP_CODE , SEQ, USER_PASSWORD ) "
					+ "values ( ? , ? , ? , ? )");
			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, TO.getCompanyCode());
			pstmt.setString(2, TO.getEmpCode());
			pstmt.setInt(3, TO.getSeq());
			pstmt.setString(4, TO.getUserPassword());

			rs = pstmt.executeQuery();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}

	@Override
	public int selectUserPassWordCount(String companyCode, String empCode) {

		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			/*
			 * SELECT COUNT(*) FROM EMPLOYEE_SECRET WHERE COMPANY_CODE = ? AND EMP_CODE = ?
			 */

			query.append("SELECT COUNT(*) FROM EMPLOYEE_SECRET WHERE COMPANY_CODE = ? AND EMP_CODE = ?");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, companyCode);
			pstmt.setString(2, empCode);

			rs = pstmt.executeQuery();

			int i = 0;

			while (rs.next()) {
				i = rs.getInt(1);
			}

			return i + 1;

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}

}
