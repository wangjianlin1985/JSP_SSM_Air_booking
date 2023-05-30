package dingzhen.controller.fly;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.net.www.content.text.plain;
import dingzhen.common.base.BaseController;
import dingzhen.common.base.ResponseDate;
import dingzhen.common.util.EMail;
import dingzhen.common.util.StochasticUtil;
import dingzhen.entity.fly.Flight;
import dingzhen.entity.fly.Flightstatus;
import dingzhen.entity.fly.Order;
import dingzhen.entity.fly.Schedular;
import dingzhen.entity.front.Customer;
import dingzhen.service.fly.FlightService;
import dingzhen.service.fly.FlightstatusService;
import dingzhen.service.fly.OrderService;
import dingzhen.service.fly.SchedularService;

@Controller
@RequestMapping("schedular")
public class SchedularController extends BaseController<Schedular> {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdfWeek = new SimpleDateFormat("EEEE");
	private static SimpleDateFormat sdfAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final String[] searts = {"A","B","C","D","E","F","G"};

	@Autowired
	private SchedularService schedularService;
	@Autowired
	private FlightService flightService;
	@Autowired
	private FlightstatusService flightstatusService;
	@Autowired
	private OrderService orderService;
	
	
	@RequestMapping("schedularIndex")
	public String index(){
		return "fly/schedular";
	}
	
	
	@RequestMapping("schedularList")
	@ResponseBody
	public ResponseDate<Schedular> schedularList(HttpServletRequest request,HttpServletResponse response){
		ResponseDate<Schedular> rd = new ResponseDate<Schedular>();
		try {
			page = Integer.parseInt(request.getParameter("page"));
			rows = Integer.parseInt(request.getParameter("rows"));
			Schedular schedular = new Schedular();
			schedular.setPage((page-1)*rows);
			schedular.setRows(rows);
			schedular.setKeyword(request.getParameter("keyword"));
			schedular.setStart(request.getParameter("start"));
			schedular.setEnd(request.getParameter("end"));
			List<Schedular> list= schedularService.findGroupList(schedular);
			int total = schedularService.countGroupList(schedular);
			rd.setTotal(total);
			rd.setRows(list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString(),e);
		}
		return rd;
	}
	
	
	// 新增或修改
	@RequestMapping("addSchedular")
	@ResponseBody
	public ResponseDate addSchedular(HttpServletRequest request, HttpServletResponse response) {
		String flightid = request.getParameter("flightid");
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String price = request.getParameter("price");
		ResponseDate rd = new ResponseDate();
		try {
			List<String> dateList = getDates(start, end);
			Flight flight = new Flight();
			flight.setId(flightid);
			flight = flightService.findOne(flight);
			int total = flight.getPlane().getTotal();
			int colnum = flight.getPlane().getColnum();
			String weeks = flight.getWeeks();
			int rownum = total / colnum;
			
			for(String date : dateList){
				String week = sdfWeek.format((sdf.parse(date)));  // 今天是星期几
				if(weeks.contains(week)){  // 说明这天需要生程数据
					
					// 判断该天该航班是否已经有数据了，有就不用管了，没有才会生成
					Schedular s = new Schedular();
					s.setFlight(flight);
					s.setStart(start);
					s.setEnd(start);
					int count = schedularService.count(s);
					
					if(count == 0){
						// 循环生成座位
						for(int i=1;i<=rownum;i++){  // 排数
							for(int j=0;j<colnum;j++){ // 列数
								Schedular schedular = new Schedular();
								schedular.setId(get32UUID());
								schedular.setDate(date);
								schedular.setWeek(week);
								schedular.setPrice(price);
								schedular.setFlight(flight);
								schedular.setName(i + "" + searts[j]);
								schedularService.add(schedular);
							}
						}
						
						// 插入航班状态表
						Flightstatus fs = new Flightstatus();
						fs.setId(get32UUID());
						fs.setDate(date);
						fs.setFlight(flight);
						flightstatusService.add(fs);
						
					}
					
				}
			}
			rd.setSuccess(true);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存航班信息错误", e);
			rd.setSuccess(true);
			rd.setErrorMsg(OPERATION_FAIL);
		}
		return rd;
	}
		
	
	public static void main(String[] args) {
		System.out.println(StochasticUtil.getRandom(7));
	}
	
	
	
	
	@RequestMapping("deleteSchedularByFlight")
	@ResponseBody
	public ResponseDate deleteSchedularByFlight(HttpServletRequest request,HttpServletResponse response){
		ResponseDate rd = new ResponseDate();
		try {
			String[] ids=request.getParameter("ids").split(",");
			String[] dates = request.getParameter("dates").split(",");
			for (int i=0;i<ids.length;i++) {
				String id = ids[i];
				String date = dates[i];
				Flight flight = new Flight();
				flight.setId(id);
				Schedular schedular = new Schedular();
				schedular.setDate(date);
				schedular.setFlight(flight);
				schedularService.deleteByFlightAndDate(schedular);
			}
			rd.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除信息错误",e);
			rd.setErrorMsg(DELETE_FAIL);
		}
		return rd;
	}
	
	
	public static List<String> getDates(String start,String end){
		List<String> dateList = new ArrayList<String>();
		try {
			Date d1 = sdf.parse(start);
			Date d2 = sdf.parse(end);
			Calendar dd = Calendar.getInstance();
			dd.setTime(d2);
			while(dd.getTime().getTime()>=d1.getTime()){
				String string = sdf.format(dd.getTime());
				dateList.add(string);
				dd.add(Calendar.DAY_OF_MONTH, -1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateList;
	}
	
	
	
	
	

	@RequestMapping("buy")
	@ResponseBody
	public ResponseDate buy(HttpServletRequest request,HttpServletResponse response){
		ResponseDate rd = new ResponseDate();
		try {
			String flightid = request.getParameter("flightid");
			String date = request.getParameter("date");
			Customer customer = (Customer) request.getSession().getAttribute("currentCustomer");
			Order order = new Order();
			customer.setCname(null);
			order.setCustomer(customer);
			Flight flight = new Flight();
			flight.setId(flightid);
			Schedular schedular = new Schedular();
			schedular.setFlight(flight);
			order.setSchedular(schedular);
			order.setDate(date);
			List<Order> list = orderService.findList(order);
			if(list.size() > 0){
				rd.setErrorMsg("已经购买该航班了。不能再购买了");
			} else {
				Map map = new HashMap();
				map.put("flightid", flightid);
				map.put("date", date);
				List<Schedular> schedularList = schedularService.findSchedularByFlightidAndDate(map);
				
				if(schedularList == null || schedularList.size() == 0){
					rd.setErrorMsg("该班次已经没有余票了，请选择其他班次");
				} else {
					int rand = StochasticUtil.getRandom(schedularList.size());  // 获取一个余票的随机数
					Schedular nextSchedular = schedularList.get(rand);  // 获取票信息
					// 插入到购买表
					Order newOrder = new Order();
					newOrder.setId(get32UUID());
					newOrder.setCustomer(customer);
					newOrder.setSchedular(nextSchedular);
					newOrder.setTime(sdfAll.format(new Date()));
					newOrder.setStatus("0");
					orderService.add(newOrder);
					rd.setSuccess(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("购票失败",e);
			rd.setErrorMsg("购买失败");
		}
		return rd;
	}
	
	
	
	@RequestMapping("flightstatusIndex")
	public String fligghtstatusindex(){
		return "fly/flightstatus";
	}
	

	@RequestMapping("flightstatusList")
	@ResponseBody
	public ResponseDate<Flightstatus> flightstatusList(HttpServletRequest request,HttpServletResponse response){
		ResponseDate<Flightstatus> rd = new ResponseDate<Flightstatus>();
		try {
			page = Integer.parseInt(request.getParameter("page"));
			rows = Integer.parseInt(request.getParameter("rows"));
			Flightstatus fs = new Flightstatus();
			fs.setPage((page-1)*rows);
			fs.setRows(rows);
			fs.setKeyword(request.getParameter("keyword"));
			fs.setDate(request.getParameter("date"));
			List<Flightstatus> list= flightstatusService.findList(fs);
			int total = flightstatusService.count(fs);
			rd.setTotal(total);
			rd.setRows(list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString(),e);
		}
		return rd;
	}
	
	
	
	@RequestMapping("updateFlightstatus")
	@ResponseBody
	public ResponseDate updateFlightstatus(HttpServletRequest request,HttpServletResponse response){
		ResponseDate rd = new ResponseDate();
		try {
			String flightid = request.getParameter("flightid");
			String date = request.getParameter("date");
			String code = request.getParameter("code");
			// 1更新飞机状态
			String id = request.getParameter("id");
			String status = request.getParameter("status");  // 1晚点  2取消
			String remarks = request.getParameter("remarks");
			Flightstatus fs = new Flightstatus();
			fs.setId(id);
			if("1".equals(status)){
				fs.setStatus("晚点");
			} else {
				fs.setStatus("取消");
			}
			fs.setRemarks(remarks);
			flightstatusService.update(fs);
			
			// 2找到所有购买了该航班的用户 
			Order order = new Order();
			order.setDate(date);
			Flight flight = new Flight();
			flight.setId(flightid);
			Schedular schedular = new Schedular();
			schedular.setFlight(flight);
			order.setSchedular(schedular);
			List<Order> orderList = orderService.findList(order);
			// 3晚点会发送EMAIL到用户邮箱
			// 4取消会发送EMAIL同时删除全部订单
			if("1".equals(status)){
				String content = "尊敬的用户您好，您购买的"+code+"已经晚点；大约晚点至"+remarks+"；详情请见安翔航空官网 http://www.axhk.com";
				for(Order o : orderList){
					if("1".equals(o.getStatus())){
						String address = o.getCustomer().getEmail();
						EMail eMail = new EMail(address, "安翔航空航班晚点通知", content);
						eMail.sendMail();
					}
				}
			} else {  
				String content = "尊敬的用户您好，您购买的"+code+"由于"+remarks+"；已经取消，购票金额已经退款到您的账户中，请注意查看！详情请见安翔航空官网 http://www.axhk.com";
				for(Order o : orderList){
					if("1".equals(o.getStatus())){
						String address = o.getCustomer().getEmail();
						EMail eMail = new EMail(address, "安翔航空航班取消通知", content);
						eMail.sendMail();
					}
					
					orderService.delete(o);
				}
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			rd.setErrorMsg("操作失败");
		}
		return rd;
	}
	
	
	
	
	
	
}
