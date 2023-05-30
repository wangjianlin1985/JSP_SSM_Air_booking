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
import dingzhen.entity.fly.Orderbk;
import dingzhen.entity.fly.Schedular;
import dingzhen.entity.front.Customer;
import dingzhen.service.fly.OrderbkService;


@RequestMapping("back/orderbk")
@Controller
public class BOrderbkController {

	
	@Autowired
	private OrderbkService orderbkService;
	
	@RequestMapping("index")
	public String index(){
		return "back/orderbk";
	}
	
	
	@RequestMapping("list")
	@ResponseBody
	public ResponseDate<Orderbk> list(HttpServletRequest request,HttpServletResponse response){
		ResponseDate<Orderbk> rd = new ResponseDate<Orderbk>();
		try {
			int page = Integer.parseInt(request.getParameter("page"));
			int rows = Integer.parseInt(request.getParameter("rows"));
			Orderbk orderbk = new Orderbk();
			orderbk.setPage((page-1)*rows);
			orderbk.setRows(rows);
			String cname = request.getParameter("cname");
			if(StringUtils.isNotEmpty(cname)){
				Customer c = new Customer();
				c.setCname(cname);
				orderbk.setCustomer(c);
			}
			orderbk.setStart(request.getParameter("start"));
			orderbk.setEnd(request.getParameter("end"));
			String flightid = request.getParameter("flightid");
			if(StringUtils.isNotEmpty(flightid)){
				Flight flight = new Flight();
				flight.setId(flightid);;
				Schedular schedular = new Schedular();
				schedular.setFlight(flight);
				orderbk.setSchedular(schedular);
			}
			List<Orderbk> list = orderbkService.findList(orderbk);
			int total = orderbkService.count(orderbk);
			rd.setRows(list);
			rd.setTotal(total);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rd;
	}
	
	
}
