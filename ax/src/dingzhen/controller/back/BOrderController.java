package dingzhen.controller.back;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dingzhen.common.base.ResponseDate;
import dingzhen.entity.fly.Flight;
import dingzhen.entity.fly.Order;
import dingzhen.entity.fly.Schedular;
import dingzhen.entity.front.Customer;
import dingzhen.service.fly.OrderService;


@RequestMapping("back/order")
@Controller
public class BOrderController {

	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("index")
	public String index(){
		return "back/order";
	}
	
	
	@RequestMapping("list")
	@ResponseBody
	public ResponseDate<Order> list(HttpServletRequest request,HttpServletResponse response){
		ResponseDate<Order> rd = new ResponseDate<Order>();
		try {
			int page = Integer.parseInt(request.getParameter("page"));
			int rows = Integer.parseInt(request.getParameter("rows"));
			Order order = new Order();
			order.setPage((page-1)*rows);
			order.setRows(rows);
			String cname = request.getParameter("cname");
			if(StringUtils.isNotEmpty(cname)){
				Customer c = new Customer();
				c.setCname(cname);
				order.setCustomer(c);
			}
			order.setStatus(request.getParameter("status"));
			order.setStart(request.getParameter("start"));
			order.setEnd(request.getParameter("end"));
			String flightid = request.getParameter("flightid");
			if(StringUtils.isNotEmpty(flightid)){
				Flight flight = new Flight();
				flight.setId(flightid);;
				Schedular schedular = new Schedular();
				schedular.setFlight(flight);
				order.setSchedular(schedular);
			}
			List<Order> list = orderService.findList(order);
			int total = orderService.count(order);
			rd.setRows(list);
			rd.setTotal(total);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rd;
	}
	
	
}
