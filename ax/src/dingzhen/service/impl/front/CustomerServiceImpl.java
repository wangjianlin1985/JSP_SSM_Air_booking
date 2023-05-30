package dingzhen.service.impl.front;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dingzhen.common.base.BaseServiceImpl;
import dingzhen.dao.front.CustomerDao;
import dingzhen.entity.front.Customer;
import dingzhen.service.front.CustomerService;

@Service("customerService")
public class CustomerServiceImpl extends BaseServiceImpl<Customer> implements CustomerService {
	
	@Autowired
	private CustomerDao dao;

	public Customer login(Map map) throws Exception {
		return dao.login(map);
	}

	public void updatePassword(Map map) throws Exception {
		dao.updatePassword(map);
	}

	public Customer checkExist(Map map) throws Exception {
		return dao.checkExist(map);
	}

	public Customer findCustomerByKeyword(Map map) throws Exception {
		return dao.findCustomerByKeyword(map);
	}

}
