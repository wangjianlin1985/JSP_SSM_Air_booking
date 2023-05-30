package dingzhen.controller.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dingzhen.common.base.ResponseDate;
import dingzhen.entity.fly.HB;
import dingzhen.service.fly.SchedularService;


@Controller
@RequestMapping("front/hb/")
public class HBController {

	@Autowired
	private SchedularService schedularService;
	
	@RequestMapping("index")
	public String index(HttpServletRequest request){
		String orgin = request.getParameter("orgin");
		String destination = request.getParameter("destination");
		String date = request.getParameter("date");
		request.setAttribute("date", date);
		request.setAttribute("orgin", orgin);
		request.setAttribute("destination", destination);
		return "front/hb";
	}
	
	
	
	@RequestMapping("searchHB")
	@ResponseBody
	public ResponseDate<HB> searchHB(HttpServletRequest request,HttpServletResponse response){
		ResponseDate<HB> rd = new ResponseDate<HB>();
		try {
			String o = request.getParameter("o");         // 出发地
			String d = request.getParameter("d");         // 目的地
			String date = request.getParameter("date");   // 日期
			Map<String, String> map = new HashMap<String, String>();
			map.put("o", o);
			map.put("d", d);
			map.put("date", date);
			List<HB> list = schedularService.findHB(map);
			rd.setRows(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rd;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
