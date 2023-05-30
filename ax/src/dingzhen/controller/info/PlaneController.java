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
import dingzhen.entity.info.Plane;
import dingzhen.service.info.PlaneService;

@RequestMapping("plane")
@Controller
public class PlaneController extends BaseController<Plane> {

	@Autowired
	private PlaneService planeService;
	
	
	@RequestMapping("planeIndex")
	public String index(){
		return "info/plane";
	}
	
	
	@RequestMapping("planeList")
	@ResponseBody
	public ResponseDate<Plane> planeList(HttpServletRequest request,HttpServletResponse response){
		ResponseDate<Plane> rd = new ResponseDate<Plane>();
		try {
			page = Integer.parseInt(request.getParameter("page"));
			rows = Integer.parseInt(request.getParameter("rows"));
			Plane plane = new Plane();
			plane.setPage((page-1)*rows);
			plane.setRows(rows);
			plane.setName(request.getParameter("name"));
			List<Plane> list= planeService.findList(plane);
			int total = planeService.count(plane);
			rd.setTotal(total);
			rd.setRows(list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString(),e);
		}
		return rd;
	}
	
	
	// 新增或修改
	@RequestMapping("reservePlane")
	@ResponseBody
	public ResponseDate reservePlane(HttpServletRequest request,Plane plane,HttpServletResponse response){
		String id = request.getParameter("id");
		ResponseDate rd = new ResponseDate();
		try {
			if (StringUtil.isNotEmpty(id)) {   // id不为空 说明是修改
				plane.setId(id);
				planeService.update(plane);
				rd.setSuccess(true);
			}else {   // 添加
					plane.setId(get32UUID());
					planeService.add(plane);
					rd.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存飞机信息错误",e);
			rd.setSuccess(true);
			rd.setErrorMsg(OPERATION_FAIL);
		}
		return rd;
	}
	
	
	@RequestMapping("deletePlane")
	@ResponseBody
	public ResponseDate delPlane(HttpServletRequest request,HttpServletResponse response){
		ResponseDate rd = new ResponseDate();
		try {
			String[] ids=request.getParameter("ids").split(",");
			List<Plane> list = new ArrayList<Plane>();
			for (String id : ids) {
				Plane plane = new Plane();
				plane.setId(id);
				list.add(plane);
			}
			planeService.deleteBatch(list);
			rd.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除飞机信息错误",e);
			rd.setErrorMsg(DELETE_FAIL);
		}
		return rd;
	}
	
	
	
	
	@RequestMapping("planeCombobox")
	public void planeCombobox(HttpServletRequest request,HttpServletResponse response){
		try {
			JSONArray jsonArray=new JSONArray();
			List<Plane> list = planeService.findList(new Plane());
			jsonArray.addAll(list);
			WriterUtil.write(response, jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
