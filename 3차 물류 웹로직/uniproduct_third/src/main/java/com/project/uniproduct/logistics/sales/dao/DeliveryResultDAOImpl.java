package com.project.uniproduct.logistics.sales.dao;

import com.project.uniproduct.common.dao.IbatisSupportDAO;
import com.project.uniproduct.logistics.sales.to.DeliveryResultTO;

public class DeliveryResultDAOImpl extends IbatisSupportDAO implements DeliveryResultDAO {

	@SuppressWarnings("deprecation")
	@Override
	public void insertDeliveryResult(DeliveryResultTO bean) {

		getSqlMapClientTemplate().insert("deliveryResult.insertDeliveryResult", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateDeliveryResult(DeliveryResultTO bean) {

		getSqlMapClientTemplate().insert("deliveryResult.updateDeliveryResult", bean);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void deleteDeliveryResult(DeliveryResultTO bean) {

		getSqlMapClientTemplate().delete("deliveryResult.deleteDeliveryResult", bean);

	}

}
