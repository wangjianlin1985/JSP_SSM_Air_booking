package dingzhen.service.front;

import java.util.Map;

import dingzhen.common.base.BaseService;
import dingzhen.entity.front.Customer;

public interface CustomerService extends BaseService<Customer>{
	public Customer login(Map map) throws Exception;
	public void updatePassword(Map map) throws Exception;
	public Customer checkExist(Map map) throws Exception;
	public Customer findCustomerByKeyword(Map map) throws Exception;
}
