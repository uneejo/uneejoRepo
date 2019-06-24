package com.project.uniproduct.logistics.sales.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.TreeSet;

import com.project.uniproduct.common.transaction.DataSourceTransactionManager;
import com.project.uniproduct.common.exception.DataAccessException;
import com.project.uniproduct.logistics.production.to.ContractDetailInMpsAvailableTO;
import com.project.uniproduct.logistics.sales.to.ContractDetailTO;

public class ContractDetailDAOImpl implements ContractDetailDAO {

private DataSourceTransactionManager dataSourceTransactionManager;
	
	public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
		this.dataSourceTransactionManager=dataSourceTransactionManager;
	}
	
	@Override
	public ArrayList<ContractDetailTO> selectContractDetailList(String contractNo) {

		

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<ContractDetailTO> contractDetailTOList = new ArrayList<ContractDetailTO>();

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("SELECT * FROM CONTRACT_DETAIL WHERE CONTRACT_NO =?");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, contractNo);

			rs = pstmt.executeQuery();

			ContractDetailTO bean = null;

			while (rs.next()) {

				bean = new ContractDetailTO();

				bean.setContractDetailNo(rs.getString("CONTRACT_DETAIL_NO"));
				bean.setContractNo(rs.getString("CONTRACT_NO"));
				bean.setItemCode(rs.getString("ITEM_CODE"));
				bean.setItemName(rs.getString("ITEM_NAME"));
				bean.setUnitOfContract(rs.getString("UNIT_OF_CONTRACT"));
				bean.setDueDateOfContract(rs.getString("DUE_DATE_OF_CONTRACT"));
				bean.setContractAmount(rs.getInt("CONTRACT_AMOUNT"));
				bean.setUnitPriceOfContract(rs.getInt("UNIT_PRICE_OF_CONTRACT"));
				bean.setSumPriceOfContract(rs.getInt("SUM_PRICE_OF_CONTRACT"));
				bean.setMpsApplyStatus(rs.getString("MPS_APPLY_STATUS"));
				bean.setDeliveryStatus(rs.getString("DELIVERY_STATUS"));
				bean.setDescription(rs.getString("DESCRIPTION"));

				contractDetailTOList.add(bean);

			}

			return contractDetailTOList;

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}

	@Override
	public int selectContractDetailCount(String contractNo) {

		

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			/*
			 * SELECT * FROM ESTIMATE_DETAIL WHERE ESTIMATE_NO = ?
			 */
			query.append("SELECT * FROM CONTRACT_DETAIL WHERE CONTRACT_NO = ?");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, contractNo);

			rs = pstmt.executeQuery();

			TreeSet<Integer> intSet = new TreeSet<>();

			while (rs.next()) {
				String contractDetailNo = rs.getString("CONTRACT_DETAIL_NO");
				int no = Integer.parseInt(contractDetailNo.split("-")[1]);

				intSet.add(no);
			}

			if (intSet.isEmpty()) {
				return 1;
			} else {
				return intSet.pollLast() + 1;
			}

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}

	public ArrayList<ContractDetailInMpsAvailableTO> selectContractDetailListInMpsAvailable(String searchCondition,
			String startDate, String endDate) {

		

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList = new ArrayList<ContractDetailInMpsAvailableTO>();

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();

			/*
			 * WITH SEARCHED_CONTRACT_DETAIL AS ( SELECT C2.CONTRACT_DETAIL_NO,
			 * C1.CONTRACT_NO, C1.CONTRACT_TYPE, C1.CONTRACT_DATE, C1.CUSTOMER_CODE,
			 * C1.CONTRACT_REQUESTER, C2.ITEM_CODE, C2.ITEM_NAME, C2.UNIT_OF_CONTRACT,
			 * C2.DUE_DATE_OF_CONTRACT, C2.CONTRACT_AMOUNT, C2.UNIT_PRICE_OF_CONTRACT,
			 * C2.SUM_PRICE_OF_CONTRACT, C2.DESCRIPTION FROM CONTRACT C1, ( SELECT * FROM
			 * CONTRACT_DETAIL WHERE MPS_APPLY_STATUS IS NULL ) C2 WHERE ( CASE ? WHEN
			 * 'contractDate' THEN TO_DATE(C1.CONTRACT_DATE, 'YYYY-MM-DD') WHEN
			 * 'dueDateOfContract' THEN TO_DATE(C2.DUE_DATE_OF_CONTRACT, 'YYYY-MM-DD') END )
			 * BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') AND
			 * C1.CONTRACT_NO = C2.CONTRACT_NO ),
			 * 
			 * CONTRACT_DETAIL_WITH_DELIVERY AS ( SELECT C.CONTRACT_DETAIL_NO,
			 * C.CONTRACT_TYPE, C.CONTRACT_DATE, D.DELIVERY_DATE, C.CUSTOMER_CODE,
			 * C.CONTRACT_REQUESTER, C.ITEM_CODE, C.ITEM_NAME, C.UNIT_OF_CONTRACT,
			 * C.CONTRACT_AMOUNT, D.DELIVERY_AMOUNT, ( C.CONTRACT_AMOUNT -
			 * NVL(D.DELIVERY_AMOUNT,0) ) AS PLAN_AMOUNT, C.DUE_DATE_OF_CONTRACT,
			 * C.DESCRIPTION FROM SEARCHED_CONTRACT_DETAIL C, ( SELECT * FROM
			 * DELIVERY_RESULT WHERE CONTRACT_DETAIL_NO IN ( SELECT CONTRACT_DETAIL_NO FROM
			 * SEARCHED_CONTRACT_DETAIL ) ) D WHERE ( C.CONTRACT_AMOUNT -
			 * NVL(D.DELIVERY_AMOUNT,0) ) > 0 AND C.CONTRACT_DETAIL_NO =
			 * D.CONTRACT_DETAIL_NO (+) )
			 * 
			 * SELECT T1.CONTRACT_DETAIL_NO, T1.CONTRACT_TYPE, T1.CONTRACT_DATE,
			 * T1.DELIVERY_DATE, T1.CUSTOMER_CODE, T2.CUSTOMER_NAME, T1.CONTRACT_REQUESTER,
			 * T1.ITEM_CODE, T1.ITEM_NAME, T1.UNIT_OF_CONTRACT, T1.CONTRACT_AMOUNT,
			 * T1.DELIVERY_AMOUNT, T1.PLAN_AMOUNT, T1.DUE_DATE_OF_CONTRACT, T1.DESCRIPTION
			 * FROM CONTRACT_DETAIL_WITH_DELIVERY T1, CUSTOMER T2 WHERE T1.CUSTOMER_CODE =
			 * T2.CUSTOMER_CODE
			 */
			query.append("WITH SEARCHED_CONTRACT_DETAIL AS\r\n"
					+ "( SELECT C2.CONTRACT_DETAIL_NO, C1.CONTRACT_NO, C1.CONTRACT_TYPE, C1.CONTRACT_DATE, C1.CUSTOMER_CODE, C1.CONTRACT_REQUESTER,\r\n"
					+ "    C2.ITEM_CODE, C2.ITEM_NAME, C2.UNIT_OF_CONTRACT, C2.DUE_DATE_OF_CONTRACT, C2.CONTRACT_AMOUNT, \r\n"
					+ "    C2.UNIT_PRICE_OF_CONTRACT, C2.SUM_PRICE_OF_CONTRACT, C2.DESCRIPTION \r\n"
					+ "    FROM CONTRACT C1, ( SELECT * FROM CONTRACT_DETAIL WHERE MPS_APPLY_STATUS IS NULL ) C2\r\n"
					+ "WHERE ( CASE ? WHEN 'contractDate' THEN TO_DATE(C1.CONTRACT_DATE, 'YYYY-MM-DD') \r\n"
					+ "                        	WHEN 'dueDateOfContract' THEN TO_DATE(C2.DUE_DATE_OF_CONTRACT, 'YYYY-MM-DD') END ) \r\n"
					+ "BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD')\r\n"
					+ "AND C1.CONTRACT_NO = C2.CONTRACT_NO ),\r\n" + "\r\n" + "CONTRACT_DETAIL_WITH_DELIVERY AS\r\n"
					+ "( SELECT C.CONTRACT_DETAIL_NO, C.CONTRACT_TYPE, C.CONTRACT_DATE, D.DELIVERY_DATE, C.CUSTOMER_CODE, C.CONTRACT_REQUESTER,\r\n"
					+ "    C.ITEM_CODE, C.ITEM_NAME, C.UNIT_OF_CONTRACT, \r\n"
					+ "    C.CONTRACT_AMOUNT, D.DELIVERY_AMOUNT, ( C.CONTRACT_AMOUNT - NVL(D.DELIVERY_AMOUNT,0) ) AS PLAN_AMOUNT, \r\n"
					+ "    C.DUE_DATE_OF_CONTRACT, C.DESCRIPTION  \r\n" + "FROM SEARCHED_CONTRACT_DETAIL C, \r\n"
					+ "        ( SELECT * FROM DELIVERY_RESULT  \r\n"
					+ "			WHERE CONTRACT_DETAIL_NO IN ( SELECT CONTRACT_DETAIL_NO FROM SEARCHED_CONTRACT_DETAIL ) ) D\r\n"
					+ "WHERE ( C.CONTRACT_AMOUNT - NVL(D.DELIVERY_AMOUNT,0) ) > 0\r\n"
					+ "AND C.CONTRACT_DETAIL_NO = D.CONTRACT_DETAIL_NO (+) )\r\n" + "    \r\n"
					+ "SELECT T1.CONTRACT_DETAIL_NO, T1.CONTRACT_TYPE, T1.CONTRACT_DATE, T1.DELIVERY_DATE, T1.CUSTOMER_CODE, \r\n"
					+ "    T2.CUSTOMER_NAME, T1.CONTRACT_REQUESTER, T1.ITEM_CODE, T1.ITEM_NAME, T1.UNIT_OF_CONTRACT, \r\n"
					+ "    T1.CONTRACT_AMOUNT, T1.DELIVERY_AMOUNT, T1.PLAN_AMOUNT, \r\n"
					+ "    T1.DUE_DATE_OF_CONTRACT, T1.DESCRIPTION  \r\n"
					+ "FROM CONTRACT_DETAIL_WITH_DELIVERY T1, CUSTOMER T2\r\n"
					+ "WHERE T1.CUSTOMER_CODE = T2.CUSTOMER_CODE");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, searchCondition);
			pstmt.setString(2, startDate);
			pstmt.setString(3, endDate);

			rs = pstmt.executeQuery();

			ContractDetailInMpsAvailableTO bean = null;

			while (rs.next()) {

				bean = new ContractDetailInMpsAvailableTO();

				bean.setContractDetailNo(rs.getString("CONTRACT_DETAIL_NO"));
				bean.setContractType(rs.getString("CONTRACT_TYPE"));
				bean.setContractDate(rs.getString("CONTRACT_DATE"));
				bean.setDeliveryDate(rs.getString("DELIVERY_DATE"));
				bean.setCustomerCode(rs.getString("CUSTOMER_CODE"));
				bean.setCustomerName(rs.getString("CUSTOMER_NAME"));
				bean.setContractRequester(rs.getString("CONTRACT_REQUESTER"));
				bean.setItemCode(rs.getString("ITEM_CODE"));
				bean.setItemName(rs.getString("ITEM_NAME"));
				bean.setUnitOfContract(rs.getString("UNIT_OF_CONTRACT"));
				bean.setContractAmount(rs.getInt("CONTRACT_AMOUNT"));
				bean.setDeliveryAmount(rs.getInt("DELIVERY_AMOUNT"));
				bean.setPlanAmount(rs.getInt("PLAN_AMOUNT"));
				bean.setDueDateOfContract(rs.getString("DUE_DATE_OF_CONTRACT"));
				bean.setDescription(rs.getString("DESCRIPTION"));

				contractDetailInMpsAvailableList.add(bean);

			}

			return contractDetailInMpsAvailableList;

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}

	@Override
	public void insertContractDetail(ContractDetailTO bean) {

		

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			/*
			 * Insert into CONTRACT_DETAIL (CONTRACT_DETAIL_NO, CONTRACT_NO, ITEM_CODE,
			 * ITEM_NAME, UNIT_OF_CONTRACT, DUE_DATE_OF_CONTRACT, CONTRACT_AMOUNT,
			 * UNIT_PRICE_OF_CONTRACT, SUM_PRICE_OF_CONTRACT, MPS_APPLY_STATUS,
			 * DELIVERY_STATUS,DESCRIPTION) values
			 * ('CO2018070301-01','CO2018070301','DK-01','디지털카메라
			 * NO.1','EA','2018-07-13',200,210000,42000000,'Y','Y',null)
			 */
			query.append("Insert into CONTRACT_DETAIL (CONTRACT_DETAIL_NO, CONTRACT_NO, ITEM_CODE,\r\n"
					+ "ITEM_NAME, UNIT_OF_CONTRACT, DUE_DATE_OF_CONTRACT, CONTRACT_AMOUNT,\r\n"
					+ "UNIT_PRICE_OF_CONTRACT, SUM_PRICE_OF_CONTRACT, MPS_APPLY_STATUS,\r\n"
					+ "DELIVERY_STATUS,DESCRIPTION) values\r\n" + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, bean.getContractDetailNo());
			pstmt.setString(2, bean.getContractNo());
			pstmt.setString(3, bean.getItemCode());
			pstmt.setString(4, bean.getItemName());
			pstmt.setString(5, bean.getUnitOfContract());
			pstmt.setString(6, bean.getDueDateOfContract());
			pstmt.setInt(7, bean.getContractAmount());
			pstmt.setInt(8, bean.getUnitPriceOfContract());
			pstmt.setInt(9, bean.getSumPriceOfContract());
			pstmt.setString(10, bean.getMpsApplyStatus());
			pstmt.setString(11, bean.getDeliveryStatus());
			pstmt.setString(12, bean.getDescription());

			rs = pstmt.executeQuery();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}

	@Override
	public void updateContractDetail(ContractDetailTO bean) {

		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			/*
			 * UPDATE CONTRACT_DETAIL SET CONTRACT_NO = ?, ITEM_CODE = ?, ITEM_NAME = ?,
			 * UNIT_OF_CONTRACT = ?, DUE_DATE_OF_CONTRACT = ?, CONTRACT_AMOUNT = ?,
			 * UNIT_PRICE_OF_CONTRACT = ?, SUM_PRICE_OF_CONTRACT = ?, MPS_APPLY_STATUS = ?,
			 * DELIVERY_STATUS = ?, DESCRIPTION = ? WHERE CONTRACT_DETAIL_NO = ?
			 */
			query.append("UPDATE CONTRACT_DETAIL SET  \r\n" + "CONTRACT_NO = ?, ITEM_CODE = ?, ITEM_NAME = ?, \r\n"
					+ "UNIT_OF_CONTRACT = ?, DUE_DATE_OF_CONTRACT = ?, \r\n"
					+ "CONTRACT_AMOUNT = ?, UNIT_PRICE_OF_CONTRACT = ?, \r\n"
					+ "SUM_PRICE_OF_CONTRACT = ?, MPS_APPLY_STATUS = ?, \r\n"
					+ "DELIVERY_STATUS = ?, DESCRIPTION = ? \r\n" + "WHERE CONTRACT_DETAIL_NO = ?");
			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, bean.getContractNo());
			pstmt.setString(2, bean.getItemCode());
			pstmt.setString(3, bean.getItemName());
			pstmt.setString(4, bean.getUnitOfContract());
			pstmt.setString(5, bean.getDueDateOfContract());
			pstmt.setInt(6, bean.getContractAmount());
			pstmt.setInt(7, bean.getUnitPriceOfContract());
			pstmt.setInt(8, bean.getSumPriceOfContract());
			pstmt.setString(9, bean.getMpsApplyStatus());
			pstmt.setString(10, bean.getDeliveryStatus());
			pstmt.setString(11, bean.getDescription());
			pstmt.setString(12, bean.getContractDetailNo());

			rs = pstmt.executeQuery();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}

	}

	public void changeMpsStatusOfContractDetail(String contractDetailNo, String mpsStatus) {


		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			/*
			 * UPDATE CONTRACT_DETAIL SET MPS_APPLY_STATUS = ? WHERE CONTRACT_DETAIL_NO = ?
			 */
			query.append("UPDATE CONTRACT_DETAIL SET MPS_APPLY_STATUS = ?\r\n" + "WHERE CONTRACT_DETAIL_NO = ?");
			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, mpsStatus);
			pstmt.setString(2, contractDetailNo);

			rs = pstmt.executeQuery();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}

	}

	@Override
	public void deleteContractDetail(ContractDetailTO bean) {


		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			/*
			 * DELETE FROM CONTRACT_DETAIL WHERE CONTRACT_DETAIL_NO = ?
			 */
			query.append("DELETE FROM CONTRACT_DETAIL WHERE CONTRACT_DETAIL_NO = ?");
			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, bean.getContractDetailNo());

			rs = pstmt.executeQuery();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}

	}

}
