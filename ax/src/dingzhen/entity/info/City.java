package dingzhen.entity.info;

import org.apache.ibatis.type.Alias;

import dingzhen.common.base.BaseEntity;

@Alias("city")
public class City extends BaseEntity{

	private String id;
	private String name;
	private String ishot;
	private String firstletter;
	private Integer seq;
	private Double lng;
	private Double lat; // 维度
	
	
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIshot() {
		return ishot;
	}
	public void setIshot(String ishot) {
		this.ishot = ishot;
	}
	public String getFirstletter() {
		return firstletter;
	}
	public void setFirstletter(String firstletter) {
		this.firstletter = firstletter;
	}
	
}
