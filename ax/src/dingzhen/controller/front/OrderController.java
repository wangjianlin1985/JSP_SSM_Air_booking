package dingzhen.controller.front;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import dingzhen.common.base.BaseController;
import dingzhen.common.base.ResponseDate;
import dingzhen.common.util.StochasticUtil;
import dingzhen.common.util.WriterUtil;
import dingzhen.entity.fly.Flight;
import dingzhen.entity.fly.Order;
import dingzhen.entity.fly.Orderbk;
import dingzhen.entity.fly.Schedular;
import dingzhen.entity.front.Customer;
import dingzhen.service.fly.OrderService;
import dingzhen.service.fly.OrderbkService;


@RequestMapping("front/order")
@Controller
public class OrderController extends BaseController<Order> {
	
	private static SimpleDateFormat sdfAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderbkService orderbkService;
	
	@RequestMapping("toMyOrder")
	public String toMyOrder(){
		return "front/myorder";
	}
	
	
	@RequestMapping("searchMyOrder")
	public void searchMyOrder(HttpServletResponse response,HttpServletRequest request){
		try {
			String start = request.getParameter("start");
			String end = request.getParameter("end");
			Customer customer = (Customer)request.getSession().getAttribute("currentCustomer");
			Order order = new Order();
			order.setStart(start + " 00:00:00");
			order.setEnd(end + " 23:59:59");
			order.setCustomer(customer);
			List<Order> list = orderService.findList(order);
			JSONArray jsonArray=new JSONArray();
			jsonArray.addAll(list);
			WriterUtil.write(response, jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	@RequestMapping("findOne")
	public void findOne(HttpServletRequest request,HttpServletResponse response){
		try {
			String orderid = request.getParameter("orderid");
			Order order = new Order();
			order.setId(orderid);
			order = orderService.findOne(order);
			JSONObject a = new JSONObject();
			a.put("order", order);
			WriterUtil.write(response, a.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("tuipiao")
	@ResponseBody
	public ResponseDate tuipiao(HttpServletRequest request,HttpServletResponse response){
		ResponseDate rd = new ResponseDate();
		try {
			String id = request.getParameter("id");
			Order order = new Order();
			order.setId(id);
			order = orderService.findOne(order);
			orderService.delete(order); // 删除
			
			Orderbk orderbk = new Orderbk();
			orderbk.setId(order.getId());
			orderbk.setTime(order.getTime());
			orderbk.setSchedular(order.getSchedular());
			orderbk.setCustomer(order.getCustomer());
			orderbk.setTptime(sdfAll.format(new Date()));
			orderbkService.add(orderbk);
			rd.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("退失败",e);
			rd.setErrorMsg("退买失败");
		}
		return rd;
	}
	
	@RequestMapping("fukuan")
	@ResponseBody
	public ResponseDate fukuan(HttpServletRequest request,HttpServletResponse response){
		ResponseDate rd = new ResponseDate();
		try {
			String orderid = request.getParameter("orderid");
			Order order = new Order();
			order.setId(orderid);
			order.setStatus("1");
			orderService.update(order);
			rd.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			rd.setErrorMsg("付款失败");
		}
		return rd;
	}
	
	
	@RequestMapping("toMyTPOrder")
	public String toMyTPOrder(){
		return "front/mytporder";
	}
	
	
	@RequestMapping("searchMyTPOrder")
	public void searchMyTPOrder(HttpServletResponse response,HttpServletRequest request){
		try {
			String start = request.getParameter("start");
			String end = request.getParameter("end");
			Customer customer = (Customer)request.getSession().getAttribute("currentCustomer");
			Orderbk ordebkr = new Orderbk();
			ordebkr.setStart(start + " 00:00:00");
			ordebkr.setEnd(end + " 23:59:59");
			ordebkr.setCustomer(customer);
			List<Orderbk> list = orderbkService.findList(ordebkr);
			JSONArray jsonArray=new JSONArray();
			jsonArray.addAll(list);
			WriterUtil.write(response, jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("findOneTP")
	public void findOneTP(HttpServletRequest request,HttpServletResponse response){
		try {
			String orderid = request.getParameter("orderid");
			Orderbk orderbk = new Orderbk();
			orderbk.setId(orderid);
			orderbk = orderbkService.findOne(orderbk);
			JSONObject a = new JSONObject();
			a.put("orderbk", orderbk);
			WriterUtil.write(response, a.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
