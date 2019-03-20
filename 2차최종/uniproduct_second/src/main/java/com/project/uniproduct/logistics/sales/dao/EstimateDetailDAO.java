package com.project.uniproduct.logistics.sales.dao;

import java.util.ArrayList;

import com.project.uniproduct.logistics.sales.to.EstimateDetailTO;

public interface EstimateDetailDAO {

	public ArrayList<EstimateDetailTO> selectEstimateDetailList(String estimateNo);

	public int selectEstimateDetailCount(String estimateNo);

	public void insertEstimateDetail(EstimateDetailTO TO);

	public void updateEstimateDetail(EstimateDetailTO TO);

	public void deleteEstimateDetail(EstimateDetailTO TO);

}