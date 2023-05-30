package dingzhen.controller.front;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dingzhen.common.base.BaseController;
import dingzhen.common.util.WriterUtil;
import dingzhen.entity.info.Ad;
import dingzhen.entity.info.Airport;
import dingzhen.entity.info.City;
import dingzhen.entity.info.Notice;
import dingzhen.service.info.AdService;
import dingzhen.service.info.NoticeService;

@Controller
@RequestMapping("front/noticead")
public class NoticeADController extends BaseController<Notice>{
	
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private AdService adservice;

	@RequestMapping("tonotice")
	public String tonotice(HttpServletRequest request) throws Exception{
		List<Notice> list = noticeService.findList(new Notice());
		request.setAttribute("list", list);
		return "front/notice";
	}
	
	@RequestMapping("loadad")
	public void loadad(HttpServletRequest request,HttpServletResponse response){
		try {
			int page = Integer.parseInt(request.getParameter("page"));
			int rows = Integer.parseInt(request.getParameter("rows"));
			Ad ad = new Ad();
			ad.setPage((page-1)*rows);
			ad.setRows(rows);
			ad.setIsshow("1");
			List<Ad> list = adservice.findList(ad);
			JSONArray jsonArray=new JSONArray();
			jsonArray.addAll(list);
			WriterUtil.write(response, jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
