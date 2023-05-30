package dingzhen.controller.back;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dingzhen.common.base.BaseController;
import dingzhen.common.base.ResponseDate;
import dingzhen.common.util.EMail;
import dingzhen.common.util.StochasticUtil;
import dingzhen.entity.front.Customer;
import dingzhen.service.front.CustomerService;

@RequestMapping("back/customer")
@Controller
public class BCustomerController  extends BaseController<Customer>{

	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("index")
	public String index(){
		return "back/customer";
	}
	
	
	@RequestMapping("list")
	@ResponseBody
	public ResponseDate<Customer> list(HttpServletRequest request,HttpServletResponse response){
		ResponseDate<Customer> rd = new ResponseDate<Customer>();
		try {
			int page = Integer.parseInt(request.getParameter("page"));
			int rows = Integer.parseInt(request.getParameter("rows"));
			Customer customer = new Customer();
			customer.setPage((page-1)*rows);
			customer.setRows(rows);
			customer.setCname(request.getParameter("cname"));
			customer.setPhone(request.getParameter("phone"));
			List<Customer> list = customerService.findList(customer);
			int total = customerService.count(customer);
			rd.setRows(list);
			rd.setTotal(total);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rd;
	}
	
	
	@RequestMapping("updatePwd")
	@ResponseBody
	public ResponseDate<Customer> updatePwd(HttpServletRequest request,HttpServletResponse response){
		ResponseDate rd = new ResponseDate();
		try {
			String id = request.getParameter("id");
			String pwd = StochasticUtil.getRandom(6.0);
			Customer customer = new Customer();
			customer.setId(id);
			String address = customerService.findOne(customer).getEmail();
			
			Map map = new HashMap();
			map.put("id", id);
			map.put("password", pwd);
			customerService.updatePassword(map);
			
			EMail email = new EMail(address, "安翔航空密码重置通知", "安翔航空通知您：您的新密码重置为" + pwd);
	    	email.sendMail();
			
			rd.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			rd.setErrorMsg("重置密码错误");
		}
		return rd;
	}
	public static void main(String[] args) {
			String s1 = "2018-04-12 12:00:00";
			String s2 = "2018-04-12 11:30:30";
			System.out.println(s1.compareTo(s2));
	}
	
}
