package dingzhen.controller.fly;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xpath.internal.operations.And;

import dingzhen.common.base.BaseController;
import dingzhen.common.base.ResponseDate;
import dingzhen.common.util.StringUtil;
import dingzhen.common.util.WriterUtil;
import dingzhen.entity.fly.Flight;
import dingzhen.entity.info.City;
import dingzhen.entity.info.Plane;
import dingzhen.service.fly.FlightService;

@Controller
@RequestMapping("flight")
public class FlightController extends BaseController<Flight> {

	
	@Autowired
	private FlightService flightService;
	
	@RequestMapping("flightIndex")
	public String index(){
		return "fly/flight";
	}
	
	@RequestMapping("flightList")
	@ResponseBody
	public ResponseDate<Flight> flightList(HttpServletRequest request,HttpServletResponse response){
		ResponseDate<Flight> rd = new ResponseDate<Flight>();
		try {
			page = Integer.parseInt(request.getParameter("page"));
			rows = Integer.parseInt(request.getParameter("rows"));
			Flight flight = new Flight();
			flight.setPage((page-1)*rows);
			flight.setRows(rows);
			flight.setCode(request.getParameter("code"));
			String origincity = request.getParameter("origincity");
			if(StringUtils.isNotEmpty(origincity)){
				City o = new City();
				o.setName(origincity);
				flight.setOrigincity(o);
			}
			String destinationcity = request.getParameter("destinationcity");
			if(StringUtils.isNotEmpty(destinationcity)){
				City o = new City();
				o.setName(destinationcity);
				flight.setDestinationcity(o);
			}
			List<Flight> list= flightService.findList(flight);
			int total = flightService.count(flight);
			rd.setTotal(total);
			rd.setRows(list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString(),e);
		}
		return rd;
	}
	
	
	
	
	
	// 新增或修改
	@RequestMapping("reserveFlight")
	@ResponseBody
	public ResponseDate reserveFlight(HttpServletRequest request,Flight flight,HttpServletResponse response){
		String id = request.getParameter("id");
		ResponseDate rd = new ResponseDate();
		String weeks[] = request.getParameterValues("weeks");
		if(weeks!=null && weeks.length>1){
			flight.setWeeks(StringUtils.join(weeks,","));
		}
		try {
			if (StringUtil.isNotEmpty(id)) {   // id不为空 说明是修改
				flight.setId(id);
				flightService.update(flight);
				rd.setSuccess(true);
			}else {   // 添加
				flight.setId(get32UUID());
				flightService.add(flight);
				rd.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存航班信息错误",e);
			rd.setSuccess(true);
			rd.setErrorMsg(OPERATION_FAIL);
		}
		return rd;
	}
	
	
	@RequestMapping("deleteFlight")
	@ResponseBody
	public ResponseDate delFlight(HttpServletRequest request,HttpServletResponse response){
		ResponseDate rd = new ResponseDate();
		try {
			String[] ids=request.getParameter("ids").split(",");
			List<Flight> list = new ArrayList<Flight>();
			for (String id : ids) {
				Flight flight = new Flight();
				flight.setId(id);
				list.add(flight);
			}
			flightService.deleteBatch(list);
			rd.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除航班信息错误",e);
			rd.setErrorMsg(DELETE_FAIL);
		}
		return rd;
	}
	
	
	
	@RequestMapping("flightCombobox")
	public void flightCombobox(HttpServletRequest request,HttpServletResponse response){
		try {
			JSONArray jsonArray=new JSONArray();
			List<Flight> list = flightService.findList(new Flight());
			jsonArray.addAll(list);
			WriterUtil.write(response, jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
