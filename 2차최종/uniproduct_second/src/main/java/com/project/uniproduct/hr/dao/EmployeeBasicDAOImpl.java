package com.project.uniproduct.hr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.uniproduct.common.transaction.DataSourceTransactionManager;
import com.project.uniproduct.common.exception.DataAccessException;
import com.project.uniproduct.hr.to.EmployeeBasicTO;

public class EmployeeBasicDAOImpl implements EmployeeBasicDAO {

	
	private DataSourceTransactionManager dataSourceTransactionManager;
	
	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		
		this.dataSourceTransactionManager=dataSourceTransactionManager;
	}
	
	public ArrayList<EmployeeBasicTO> selectEmployeeBasicList(String companyCode) {

	
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<EmployeeBasicTO> employeeBasicList = new ArrayList<EmployeeBasicTO>();

		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();

			/*
			 * SELECT * FROM EMPLOYEE_BASIC WHERE COMPANY_CODE = ?
			 */

			query.append("SELECT * FROM EMPLOYEE_BASIC WHERE COMPANY_CODE = ? ");
			
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, companyCode);

			rs = pstmt.executeQuery();

			EmployeeBasicTO TO = null;

			while (rs.next()) {
				TO = new EmployeeBasicTO();

				TO.setCompanyCode(rs.getString("COMPANY_CODE"));
				TO.setEmpCode(rs.getString("EMP_CODE"));
				TO.setEmpEngName(rs.getString("EMP_NAME"));
				TO.setEmpEngName(rs.getString("EMP_ENG_NAME"));
				TO.setSocialSecurityNumber(rs.getString("SOCIAL_SECURITY_NUMBER"));
				TO.setHireDate(rs.getString("HIRE_DATE"));
				TO.setRetirementDate(rs.getString("RETIREMENT_DATE"));
				TO.setUserOrNot(rs.getString("USER_OR_NOT"));
				TO.setBirthDate(rs.getString("BIRTH_DATE"));
				TO.setGender(rs.getString("GENDER"));

				employeeBasicList.add(TO);
			}

			return employeeBasicList;

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}

	public EmployeeBasicTO selectEmployeeBasicTO(String companyCode, String empCode) {


		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();

			/*
			 * SELECT * FROM EMPLOYEE_BASIC WHERE COMPANY_CODE = ? AND EMP_CODE =?
			 */

			query.append("SELECT * FROM EMPLOYEE_BASIC WHERE COMPANY_CODE = ?  AND EMP_CODE =?");

			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, companyCode);
			pstmt.setString(2, empCode);

			rs = pstmt.executeQuery();

			EmployeeBasicTO TO = null;

			while (rs.next()) {
				TO = new EmployeeBasicTO();

				TO.setCompanyCode(rs.getString("COMPANY_CODE"));
				TO.setEmpCode(rs.getString("EMP_CODE"));
				TO.setEmpName(rs.getString("EMP_NAME"));
				TO.setEmpEngName(rs.getString("EMP_ENG_NAME"));
				TO.setSocialSecurityNumber(rs.getString("SOCIAL_SECURITY_NUMBER"));
				TO.setHireDate(rs.getString("HIRE_DATE"));
				TO.setRetirementDate(rs.getString("RETIREMENT_DATE"));
				TO.setUserOrNot(rs.getString("USER_OR_NOT"));
				TO.setBirthDate(rs.getString("BIRTH_DATE"));
				TO.setGender(rs.getString("GENDER"));

			}

			return TO;

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}

	public void insertEmployeeBasic(EmployeeBasicTO TO) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();

			/*
			 * Insert into EMPLOYEE_BASIC ( COMPANY_CODE , EMP_CODE , EMP_NAME ,
			 * EMP_ENG_NAME , SOCIAL_SECURITY_NUMBER , HIRE_DATE , RETIREMENT_DATE ,
			 * USER_OR_NOT , BIRTH_DATE , GENDER ) values ( ? , ? , ? , ? , ? , ? , ? , ? ,
			 * ? , ? )
			 * 
			 */

			query.append("Insert into EMPLOYEE_BASIC \r\n"
					+ "( COMPANY_CODE , EMP_CODE , EMP_NAME , EMP_ENG_NAME , \r\n"
					+ "SOCIAL_SECURITY_NUMBER , HIRE_DATE , RETIREMENT_DATE , \r\n"
					+ "USER_OR_NOT , BIRTH_DATE , GENDER ) \r\n" + "values ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )");
			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, TO.getCompanyCode());
			pstmt.setString(2, TO.getEmpCode());
			pstmt.setString(3, TO.getEmpName());
			pstmt.setString(4, TO.getEmpEngName());
			pstmt.setString(5, TO.getSocialSecurityNumber());
			pstmt.setString(6, TO.getHireDate());
			pstmt.setString(7, TO.getRetirementDate());
			pstmt.setString(8, TO.getUserOrNot());
			pstmt.setString(9, TO.getBirthDate());
			pstmt.setString(10, TO.getGender());

			rs = pstmt.executeQuery();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}

	@Override
	public void changeUserAccountStatus(String companyCode, String empCode, String userStatus) {

	
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();

			/*
			 * UPDATE EMPLOYEE_BASIC SET  USER_OR_NOT = ? WHERE COMPANY_CODE = ? AND EMP_CODE = ?
			 */

			query.append(
					"UPDATE EMPLOYEE_BASIC SET  USER_OR_NOT = ? WHERE COMPANY_CODE = ? AND EMP_CODE = ?");
			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, userStatus);
			pstmt.setString(2, companyCode);
			pstmt.setString(3, empCode);

			rs = pstmt.executeQuery();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}		
	}


}
