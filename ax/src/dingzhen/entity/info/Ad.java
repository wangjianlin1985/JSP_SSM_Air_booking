package dingzhen.entity.info;

import org.apache.ibatis.type.Alias;

import dingzhen.common.base.BaseEntity;

@Alias("ad")
public class Ad extends BaseEntity {

	private String id;
	private String title;
	private String cover;
	private String link;
	private String isshow;
	
	
	public String getIsshow() {
		return isshow;
	}
	public void setIsshow(String isshow) {
		this.isshow = isshow;
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
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}

}
