package dingzhen.controller.front;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import dingzhen.common.base.BaseController;
import dingzhen.common.base.BaseEntity;
import dingzhen.common.base.ResponseDate;
import dingzhen.common.util.EMail;
import dingzhen.common.util.WriterUtil;
import dingzhen.entity.front.Customer;
import dingzhen.service.front.CustomerService;

@Controller
@RequestMapping("front/customer")
public class CustomerController extends BaseController<Customer>{
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private CustomerService customerService;
	
	
	@RequestMapping("findOne")
	public void findone(HttpServletRequest request,HttpServletResponse response){
		try {
			String customerid = (String) request.getSession().getAttribute("customerid");
			Customer customer = new Customer();
			customer.setId(customerid);
			customer = customerService.findOne(customer);
			JSONObject o = new JSONObject();
			o.put("customer", customer);
			WriterUtil.write(response, o.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("toGister")
	public String toGister(){
		return "front/register";
	}
	
	
	@RequestMapping("checkExist")
	@ResponseBody
	public ResponseDate checkExist(HttpServletRequest request){
		ResponseDate rd = new ResponseDate();
		String type =  request.getParameter("type");
		String keyword = request.getParameter("keyword");
		String id = request.getParameter("id");
		Map map = new HashMap();
		map.put("type", type);
		map.put("keyword", keyword);
		try {
			Customer customer = customerService.checkExist(map);
			if(StringUtils.isNotEmpty(id)){ // 新增
				if(customer==null){        // 说明不存在，
					rd.setSuccess(true);
				} else {
					rd.setSuccess(false);
				}
			} else { // 修改
				if(customer==null){        // 说明不存在，
					rd.setSuccess(true);
				} else if(id.equals(customer.getId())){
					rd.setSuccess(true);
				} else {
					rd.setSuccess(false);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rd;
		
	}
	
	
	
	@RequestMapping("register")
	@ResponseBody
	public ResponseDate register(HttpServletRequest request,Customer customer){
		ResponseDate rd = new ResponseDate();
		customer.setRegtime(sdf.format(new Date()));
		try {
			customer.setId(get32UUID());
			customerService.add(customer);
			rd.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			rd.setErrorMsg("对不起注册失败");
		}
		return rd;
		
	}
	
	
	
	@RequestMapping("update")
	@ResponseBody
	public ResponseDate update(HttpServletRequest request,Customer customer){
		ResponseDate rd = new ResponseDate();
		customer.setRegtime(sdf.format(new Date()));
		try {
			customerService.update(customer);
			rd.setSuccess(true);
			List<Customer> list = new ArrayList<Customer>();
			list.add(customer);
			rd.setRows(list);
			request.getSession().setAttribute("currentCustomer", customer);
		} catch (Exception e) {
			e.printStackTrace();
			rd.setErrorMsg("对不起修改失败");
		}
		return rd;
		
	}
	
	
	@RequestMapping("toLogin")
	public String toLogin(){
		return "front/login";
	}
	
	
	@RequestMapping("login")
	@ResponseBody
	public ResponseDate login(HttpServletRequest request){
		ResponseDate rd = new ResponseDate();
		String password = request.getParameter("password");
		String keyword = request.getParameter("keyword");
		try {
			Map map = new HashMap();
			map.put("password", password);
			map.put("keyword", keyword);
			Customer customer = customerService.login(map);
			if(customer == null){
				rd.setErrorMsg("账户或密码错误");
			} else {
				request.getSession().setAttribute("customerid", customer.getId());
				request.getSession().setAttribute("currentCustomer", customer);
				rd.setSuccess(true);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			rd.setErrorMsg("对不起登陆失败");
		}
		return rd;
		
	}
	
	
	@RequestMapping("myinfo")
	public String myinfo(HttpServletRequest request){
		Customer customer = (Customer) request.getSession().getAttribute("currentCustomer");
		if(customer != null){
			request.setAttribute("customer", customer);
			return "front/myinfo";
		} else {
			return "front/login";
		}
		
	}
	
	@RequestMapping("tofindpwd")
	public String tofindpwd(){
		return "front/findpwd";
	}
	
	
	@RequestMapping("updatepwd")
	public void updatepwd(HttpServletRequest request,HttpServletResponse response){
		JSONObject o = new JSONObject();
		try {
			String keyword = request.getParameter("keyword");
			Map map = new HashMap();
			map.put("keyword", keyword);
			Customer customer = customerService.findCustomerByKeyword(map);
			if(customer == null){
				o.put("errorMsg", "账户不存在");
			} else {
				String password = customer.getPassword();
				String address = customer.getEmail();
				EMail email = new EMail(address, "安翔航空密码找回", "安翔航空通知您：您的密码为" + password);
		    	email.sendMail();
		    	o.put("success", true);
		    	o.put("email", address);
			}
		} catch (Exception e) {
			e.printStackTrace();
			o.put("errorMsg", "找回密码错误");
		}
		WriterUtil.write(response, o.toString());
	}
	
	@RequestMapping("updatepwdFront")
	public void updatepwdFront(HttpServletRequest request,HttpServletResponse response){
		JSONObject o = new JSONObject();
		try {
			String customerid = request.getParameter("customerid");
			String oldpwd = request.getParameter("oldpwd");
			String newpwd = request.getParameter("newpwd");
			
			Customer customer = new Customer();
			customer.setId(customerid);
			customer = customerService.findOne(customer);
			if(!oldpwd.equals(customer.getPassword())){
				o.put("errorMsg", "旧密码错误");
			} else {
				Map map = new HashMap();
				map.put("id", customerid);
				map.put("password", newpwd);
				customerService.updatePassword(map);
				
				String address = customer.getEmail();
				EMail email = new EMail(address, "安翔航空密码修改", "安翔航空通知您：您的密码修改为" + newpwd);
		    	email.sendMail();
				o.put("success", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			o.put("errorMsg", "密码修改错误");
		}
		WriterUtil.write(response, o.toString());
	}
	
	
}
