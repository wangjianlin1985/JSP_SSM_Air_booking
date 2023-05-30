package dingzhen.entity.info;

import org.apache.ibatis.type.Alias;

import dingzhen.common.base.BaseEntity;

@Alias("airport")
public class Airport extends BaseEntity {

	private String id;
	private String name;
	private City city;
	private String remarks;
	
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
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
