package dingzhen.controller.back;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import dingzhen.common.base.BaseController;
import dingzhen.common.util.WriterUtil;
import dingzhen.entity.fly.Flight;
import dingzhen.entity.fly.Schedular;
import dingzhen.service.fly.FlightService;
import dingzhen.service.fly.SchedularService;

@RequestMapping("back/schedular")
@Controller
public class BSchedularController extends BaseController<Schedular>{

	@Autowired
	private SchedularService schedularService;
	@Autowired
	private FlightService flightService;
	
	
	@RequestMapping("index")
	public String index(){
		return "back/schedular";
	}
	
	
	@RequestMapping("loadSchedular")
	public void loadSchedular(HttpServletRequest request,HttpServletResponse response){
		JSONObject o = new JSONObject();
		try {
			String flightid = request.getParameter("flightid");
			String date = request.getParameter("date");
			Map map = new HashMap();
			map.put("flightid", flightid);
			map.put("date", date);
			Flight flight = new Flight();
			flight.setId(flightid);
			flight = flightService.findOne(flight);
			int total = flight.getPlane().getTotal();    // 总座位数
			int colnum = flight.getPlane().getColnum();  // 列数
			int rownum = total / colnum;                 // 行数
			
			
			List<Schedular> list = schedularService.findListByMap(map);
			if(list == null || list.size() == 0){
				o.put("errorMsg", "该航班在该日未安排飞行");
			} else {
				int buyed = 0;  // 已经买了的人数
				for(Schedular s : list){
					if(StringUtils.isNotEmpty(s.getCustomerid())){
						buyed++;
					}
				}
				int other = total - buyed;  //剩余座位
				
				o.put("total", total);
				o.put("buyed", buyed);
				o.put("other", other);
				JSONArray o5 = new JSONArray();
				for(int j=0;j<rownum;j++){
					JSONObject o4 = new JSONObject();
					List<Schedular> newlist = list.subList(j*colnum, (j+1)*colnum);
					JSONArray o3 = new JSONArray();
					for(Schedular ss : newlist){
						JSONObject o2 = new JSONObject();
						o2.put("schedular", ss);
						o3.add(o2);
					}
					o4.put("schedular", o3);
					o5.add(o4);
				}
				o.put("schedulars", o5);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		WriterUtil.write(response, o.toString());
	}
	
	
	
}
