package com.project.uniproduct.logistics.sales.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.uniproduct.logistics.sales.dao.EstimateDAO;
import com.project.uniproduct.logistics.sales.dao.EstimateDetailDAO;
import com.project.uniproduct.logistics.sales.to.EstimateDetailTO;
import com.project.uniproduct.logistics.sales.to.EstimateTO;

public class EstimateApplicationServiceImpl implements EstimateApplicationService {

	private EstimateDAO estimateDAO;
	private EstimateDetailDAO estimateDetailDAO;

	public void setEstimateDAO(EstimateDAO estimateDAO) {
		this.estimateDAO=estimateDAO;
	}
	
	public void setEstimateDetailDAO(EstimateDetailDAO estimateDetailDAO) {
		this.estimateDetailDAO=estimateDetailDAO;
	}
	
	public ArrayList<EstimateTO> getEstimateList(String dateSearchCondition, String startDate, String endDate) {



			ArrayList<EstimateTO> estimateTOList = estimateDAO.selectEstimateList(dateSearchCondition, startDate, endDate);

			for (EstimateTO bean : estimateTOList) {

				bean.setEstimateDetailTOList(estimateDetailDAO.selectEstimateDetailList(bean.getEstimateNo()));

			}

		
		return estimateTOList;
	}

	public ArrayList<EstimateDetailTO> getEstimateDetailList(String estimateNo) {

	

			ArrayList<EstimateDetailTO> estimateDetailTOList = estimateDetailDAO.selectEstimateDetailList(estimateNo);

		
		return estimateDetailTOList;

	}

	public String getNewEstimateNo(String estimateDate) {

	
		StringBuffer newEstimateNo = null;

			int i = estimateDAO.selectEstimateCount(estimateDate);

			newEstimateNo = new StringBuffer();
			newEstimateNo.append("ES");
			newEstimateNo.append(estimateDate.replace("-", ""));
			newEstimateNo.append(String.format("%02d", i));

		
		return newEstimateNo.toString();
	}

	public HashMap<String, Object> addNewEstimate(String estimateDate, EstimateTO newEstimateBean) {

		
		HashMap<String, Object> resultMap = null;


			// 새로운 견적일련번호 생성
			String newEstimateNo = getNewEstimateNo(estimateDate);

			// 뷰단에서 보낸 견적 Bean 에 새로운 견적일련번호 set
			newEstimateBean.setEstimateNo(newEstimateNo);

			// 견적 Bean 을 Insert
			estimateDAO.insertEstimate(newEstimateBean);

			// 견적상세 List
			ArrayList<EstimateDetailTO> estimateDetailTOList = newEstimateBean.getEstimateDetailTOList();

			StringBuffer newEstimateDetailNo = null;

			int i = 1;

			for (EstimateDetailTO bean : estimateDetailTOList) {

				// 앞서 생성된 견적 일련번호 set
				bean.setEstimateNo(newEstimateNo);

				// ( 앞에서 생성한 견적일련번호-01 ,02 , 03 .... ) 형식으로 견적상세일련번호 생성
				newEstimateDetailNo = new StringBuffer();

				newEstimateDetailNo.append(newEstimateNo);
				newEstimateDetailNo.append("-");
				newEstimateDetailNo.append(String.format("%02d", i++)); // 두자리 숫자, INSERT 될 때마다 하나씩 증가

				// 생성된 견적상세 일련번호 set
				bean.setEstimateDetailNo(newEstimateDetailNo.toString());

			}

			// 견적상세List 를 batchListProcess 로 Insert, 결과 맵 반환
			resultMap = batchEstimateDetailListProcess(estimateDetailTOList);

			// 결과 맵에 "estimateNo" 키값으로 새로 생성된 견적일련번호 저장
			resultMap.put("newEstimateNo", newEstimateNo);

		
		return resultMap;
	}

	public HashMap<String, Object> batchEstimateDetailListProcess(ArrayList<EstimateDetailTO> estimateDetailTOList) {

		

		HashMap<String, Object> resultMap = new HashMap<>();

		// 추가/수정/삭제된 견적상세일련번호를 담을 ArrayList
		ArrayList<String> insertList = new ArrayList<>();
		ArrayList<String> updateList = new ArrayList<>();
		ArrayList<String> deleteList = new ArrayList<>();


			// 첫 번째 반복문 : INSERT 만 먼저 처리 => DELETE 를 먼저 하면 새로운 번호가 기존에 매겨졌던 번호로 매겨질 수 있음,
			// UPDATE 는 상관없음
			for (EstimateDetailTO bean : estimateDetailTOList) {

				String status = bean.getStatus();

				// 새로운 견적상세일련번호를 담을 StringBuffer
				StringBuffer newEstimateDetailNo = new StringBuffer();

				switch (status) {

				case "INSERT":

					// 견적상세 일련번호 양식 : "ES20180101-01"
					// 기존에 등록된 견적에서 새로운 견적상세를 입력하는 경우 = > 견적상세일련번호가 '저장시 지정됨' => "ES" 로 시작하지 않음
					if (! bean.getEstimateDetailNo().startsWith("ES")) {

						// 견적상세 Bean 의 견적번호로 등록된 견적상세번호 중 마지막 번호 + 1 을 DAO 로 가져옴 : 새로운 견적상세번호
						int newNo = estimateDetailDAO.selectEstimateDetailCount(bean.getEstimateNo());

						// 새로운 견적상세일련번호 생성
						newEstimateDetailNo.append(bean.getEstimateNo());
						newEstimateDetailNo.append("-");
						newEstimateDetailNo.append(String.format("%02d", newNo));

						// 견적상세일련번호 set
						bean.setEstimateDetailNo(newEstimateDetailNo.toString());
					}

					estimateDetailDAO.insertEstimateDetail(bean);
					insertList.add(bean.getEstimateDetailNo());
					break;

				}

			}

			// 두 번째 반복문 : UPDATE, DELETE 처리

			for (EstimateDetailTO bean : estimateDetailTOList) {

				String status = bean.getStatus();

				switch (status) {

				case "UPDATE":

					estimateDetailDAO.updateEstimateDetail(bean);
					updateList.add(bean.getEstimateDetailNo());
					break;

				case "DELETE":

					estimateDetailDAO.deleteEstimateDetail(bean);
					deleteList.add(bean.getEstimateDetailNo());

					break;

				default:

				}

			}

			resultMap.put("INSERT", insertList);
			resultMap.put("UPDATE", updateList);
			resultMap.put("DELETE", deleteList);

		
		return resultMap;

	}

}
