package dingzhen.controller.info;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import dingzhen.common.base.BaseController;
import dingzhen.common.util.StringUtil;
import dingzhen.common.util.WriterUtil;
import dingzhen.entity.info.Ad;
import dingzhen.service.info.AdService;

@Controller
@RequestMapping("ad")
public class AdController extends BaseController<Ad> {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	@Autowired
	private AdService adService;
	
	@RequestMapping("index")
	public String index(){
		return "info/adIndex";
	}
	
	
	@RequestMapping("list")
	public void list(HttpServletRequest request,HttpServletResponse response){
		try {
			int page = Integer.parseInt(request.getParameter("page"));
			int rows = Integer.parseInt(request.getParameter("rows"));
			Ad ad = new Ad();
			ad.setTitle(request.getParameter("title"));
			ad.setPage((page-1)*rows);
			ad.setRows(rows);
			ad.setIsshow(request.getParameter("isshow"));
			List<Ad> list = adService.findList(ad);
			int total = adService.count(ad);
			JSONObject o = new JSONObject();
			o.put("rows", list);
			o.put("total", total);
			WriterUtil.write(response, o.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	@RequestMapping("reserveAd")
	public void reserveAd(@RequestParam(value = "file",required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response,Ad ad){
		String id = request.getParameter("id");
		JSONObject o = new JSONObject();
		String date = sdf.format(new Date());
		String filePath = request.getSession().getServletContext().getRealPath("/")+ "upload/"+date+".JPG";
		try {
			if(file!=null && !file.isEmpty()){
				file.transferTo(new File(filePath));
				ad.setCover("upload/"+date + ".JPG");
			}
			if(StringUtil.isEmpty(id)){
				ad.setId(get32UUID());
				adService.add(ad);
			} else {
				ad.setId(id);
				adService.update(ad);
			}
			o.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			o.put("errorMsg","保存失败");
		}
		WriterUtil.write(response, o.toString());
	}
	
	
	
	
	
	@RequestMapping("deleteAd")
	public void deleteAd(HttpServletRequest request,HttpServletResponse response){
		JSONObject o = new JSONObject();
		try {
			String ids[] = request.getParameter("ids").split(",");
			for(String id : ids){
				Ad ad = new Ad();
				ad.setId(id);
				adService.delete(ad);
			}
			o.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			o.put("errorMsg", "删除失败");
		}
		WriterUtil.write(response, o.toString());
	}
	
	
	
	
}
