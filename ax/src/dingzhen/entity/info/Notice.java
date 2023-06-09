package dingzhen.entity.info;

import org.apache.ibatis.type.Alias;

import dingzhen.common.base.BaseEntity;

@Alias("notice")
public class Notice extends BaseEntity{

	private String id;
	private String title;
	private String time;
	private String istop;
	private String content;
	
	
	public String getIstop() {
		return istop;
	}
	public void setIstop(String istop) {
		this.istop = istop;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	
}
