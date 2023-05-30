package dingzhen.controller.info;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dingzhen.common.base.BaseController;
import dingzhen.common.base.ResponseDate;
import dingzhen.common.util.StringUtil;
import dingzhen.common.util.WriterUtil;
import dingzhen.entity.info.Airport;
import dingzhen.entity.info.City;
import dingzhen.entity.info.Plane;
import dingzhen.service.info.AirportService;

@RequestMapping("airport")
@Controller
public class AirportController extends BaseController<Airport> {

	@Autowired
	private AirportService airportService;
	
	
	@RequestMapping("airportIndex")
	public String index(){
		return "info/airport";
	}
	
	
	@RequestMapping("airportList")
	@ResponseBody
	public ResponseDate<Airport> airportList(HttpServletRequest request,HttpServletResponse response){
		ResponseDate<Airport> rd = new ResponseDate<Airport>();
		try {
			page = Integer.parseInt(request.getParameter("page"));
			rows = Integer.parseInt(request.getParameter("rows"));
			Airport airport = new Airport();
			airport.setPage((page-1)*rows);
			airport.setRows(rows);
			airport.setKeyword(request.getParameter("keyword"));
			List<Airport> list= airportService.findList(airport);
			int total = airportService.count(airport);
			rd.setTotal(total);
			rd.setRows(list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString(),e);
		}
		return rd;
	}
	
	
	// 新增或修改
	@RequestMapping("reserveAirport")
	@ResponseBody
	public ResponseDate reserveAirport(HttpServletRequest request,Airport airport,HttpServletResponse response){
		String id = request.getParameter("id");
		ResponseDate rd = new ResponseDate();
		System.out.println(airport.getCity().getId());
		try {
			if (StringUtil.isNotEmpty(id)) {   // id不为空 说明是修改
				airport.setId(id);
				airportService.update(airport);
				rd.setSuccess(true);
			}else {   // 添加
				airport.setId(get32UUID());
				airportService.add(airport);
				rd.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存站点信息错误",e);
			rd.setSuccess(true);
			rd.setErrorMsg(OPERATION_FAIL);
		}
		return rd;
	}
	
	
	@RequestMapping("deleteAirport")
	@ResponseBody
	public ResponseDate delAirport(HttpServletRequest request,HttpServletResponse response){
		ResponseDate rd = new ResponseDate();
		try {
			String[] ids=request.getParameter("ids").split(",");
			List<Airport> list = new ArrayList<Airport>();
			for (String id : ids) {
				Airport airport = new Airport();
				airport.setId(id);
				list.add(airport);
			}
			airportService.deleteBatch(list);
			rd.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除站点信息错误",e);
			rd.setErrorMsg(DELETE_FAIL);
		}
		return rd;
	}
	
	
	
	@RequestMapping("airportCombobox")
	public void airportCombobox(HttpServletRequest request,HttpServletResponse response){
		try {
			String cityid = request.getParameter("cityid");
			JSONArray jsonArray=new JSONArray();
			City city = new City();
			city.setId(cityid);
			Airport ar = new Airport();
			ar.setCity(city);
			List<Airport> list = airportService.findList(ar);
			jsonArray.addAll(list);
			WriterUtil.write(response, jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
}
