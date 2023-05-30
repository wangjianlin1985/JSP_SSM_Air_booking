package dingzhen.dao.front;

import java.util.Map;

import org.springframework.stereotype.Repository;

import dingzhen.common.base.BaseDao;
import dingzhen.entity.front.Customer;

@Repository
public interface CustomerDao extends BaseDao<Customer> {

	public Customer login(Map map) throws Exception;
	
	public Customer checkExist(Map map) throws Exception;
	
	public void updatePassword(Map map) throws Exception;
	
	public Customer findCustomerByKeyword(Map map) throws Exception;
	
}
