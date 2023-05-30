package dingzhen.controller.info;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

import dingzhen.common.base.BaseController;
import dingzhen.common.util.StringUtil;
import dingzhen.common.util.WriterUtil;
import dingzhen.entity.info.Notice;
import dingzhen.service.info.NoticeService;

@Controller
@RequestMapping("notice")
public class NoticeController extends BaseController<Notice> {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	@Autowired
	private NoticeService noticeService;
	
	@RequestMapping("index")
	public String index(){
		return "info/noticeIndex";
	}
	
	
	@RequestMapping("list")
	public void list(HttpServletRequest request,HttpServletResponse response){
		try {
			int page = Integer.parseInt(request.getParameter("page"));
			int rows = Integer.parseInt(request.getParameter("rows"));
			Notice notice = new Notice();
			notice.setTitle(request.getParameter("title"));
			notice.setPage((page-1)*rows);
			notice.setRows(rows);
			List<Notice> list = noticeService.findList(notice);
			int total = noticeService.count(notice);
			JSONObject o = new JSONObject();
			o.put("rows", list);
			o.put("total", total);
			WriterUtil.write(response, o.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	@RequestMapping("toAdd")
	public String toAdd(){
		return "info/noticeAdd";
	}
	
	@RequestMapping("toUpdate")
	public String toUpdate(HttpServletRequest request){
		try {
			String id = request.getParameter("id");
			Notice notice = new Notice();
			notice.setId(id);
			notice = noticeService.findOne(notice);
			request.setAttribute("notice", notice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "info/noticeUpdate";
	}
	
	
	@RequestMapping("reserveNotice")
	public void reserveNotice(HttpServletRequest request,HttpServletResponse response,Notice notice){
		String id = request.getParameter("id");
		notice.setTime(sdf.format(new Date()));
		JSONObject o = new JSONObject();
		try {
			if(StringUtil.isEmpty(id)){
				notice.setId(get32UUID());
				noticeService.add(notice);
			} else {
				notice.setId(id);
				noticeService.update(notice);
			}
			o.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			o.put("success", true);
			o.put("errorMsg","保存失败");
		}
		WriterUtil.write(response, o.toString());
	}
	
	
	
	@RequestMapping("findOne")
	public String findOne(HttpServletRequest request){
		try {
			String id = request.getParameter("id");
			Notice notice = new Notice();
			notice.setId(id);
			notice = noticeService.findOne(notice);
			request.setAttribute("notice", notice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "info/noticeView";
	}
	
	
	
	
	@RequestMapping("deleteNotice")
	public void deleteNotice(HttpServletRequest request,HttpServletResponse response){
		JSONObject o = new JSONObject();
		try {
			String ids[] = request.getParameter("ids").split(",");
			for(String id : ids){
				Notice notice = new Notice();
				notice.setId(id);
				noticeService.delete(notice);
			}
			o.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			o.put("errorMsg", "删除失败");
		}
		WriterUtil.write(response, o.toString());
	}
	
	
	
	
}
