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

import com.alibaba.fastjson.JSONObject;

import dingzhen.common.base.BaseController;
import dingzhen.common.base.ResponseDate;
import dingzhen.common.util.StringUtil;
import dingzhen.common.util.WriterUtil;
import dingzhen.entity.info.City;
import dingzhen.service.info.CityService;

@RequestMapping("city")
@Controller
public class CityController extends BaseController<City> {

	@Autowired
	private CityService cityService;
	
	
	@RequestMapping("cityIndex")
	public String index(){
		return "info/city";
	}
	
	
	@RequestMapping("cityList")
	@ResponseBody
	public ResponseDate<City> cityList(HttpServletRequest request,HttpServletResponse response){
		ResponseDate<City> rd = new ResponseDate<City>();
		try {
			page = Integer.parseInt(request.getParameter("page"));
			rows = Integer.parseInt(request.getParameter("rows"));
			City city = new City();
			city.setPage((page-1)*rows);
			city.setRows(rows);
			city.setName(request.getParameter("name"));
			city.setFirstletter(request.getParameter("firstletter"));
			city.setIshot(request.getParameter("ishot"));
			List<City> list= cityService.findList(city);
			int total = cityService.count(city);
			rd.setTotal(total);
			rd.setRows(list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString(),e);
		}
		return rd;
	}
	
	
	// 新增或修改
	@RequestMapping("reserveCity")
	@ResponseBody
	public ResponseDate reserveCity(HttpServletRequest request,City city,HttpServletResponse response){
		String id = request.getParameter("id");
		ResponseDate rd = new ResponseDate();
		try {
			if (StringUtil.isNotEmpty(id)) {   // id不为空 说明是修改
				city.setId(id);
				cityService.update(city);
				rd.setSuccess(true);
			}else {   // 添加
					city.setId(get32UUID());
					cityService.add(city);
					rd.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存城市信息错误",e);
			rd.setSuccess(true);
			rd.setErrorMsg(OPERATION_FAIL);
		}
		return rd;
	}
	
	
	@RequestMapping("deleteCity")
	@ResponseBody
	public ResponseDate delCity(HttpServletRequest request,HttpServletResponse response){
		ResponseDate rd = new ResponseDate();
		try {
			String[] ids=request.getParameter("ids").split(",");
			List<City> list = new ArrayList<City>();
			for (String id : ids) {
				City city = new City();
				city.setId(id);
				list.add(city);
			}
			cityService.deleteBatch(list);
			rd.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除城市信息错误",e);
			rd.setErrorMsg(DELETE_FAIL);
		}
		return rd;
	}
	
	
	
	
	@RequestMapping("cityCombobox")
	public void cityCombobox(HttpServletRequest request,HttpServletResponse response){
		try {
			JSONArray jsonArray=new JSONArray();
			List<City> list = cityService.findList(new City());
			jsonArray.addAll(list);
			WriterUtil.write(response, jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 判断距离
	@RequestMapping("distince")
	public void distince(HttpServletRequest request,HttpServletResponse response){
		try {
			String minDistinceCity = "";
			double dis = 99999999;
			String lng = request.getParameter("lng");
			String lat = request.getParameter("lat");
			List<City> list = cityService.findList(new City());
			for(City city : list){
				if(StringUtil.isEmpty(city.getLat()+"") || StringUtil.isEmpty(city.getLng()+"")){
					continue;
				} else {
					double diss = getDistance(Double.parseDouble(lat), Double.parseDouble(lng), city.getLat(), city.getLng());
					System.out.println("距离" + city.getName() + "的长度是===" + diss);
					if(diss < dis){
						minDistinceCity = city.getId();
						dis = diss;
					}
				}
			}
			JSONObject o = new JSONObject();
			o.put("success", true);
			o.put("min", minDistinceCity);
			WriterUtil.write(response, o.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static double getDistance(double lat1, double lng1, double lat2,double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000d) / 10000d;
		s = s * 1000;
		return s;
	}
	
	private static double EARTH_RADIUS = 6378.137;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	} 
}
