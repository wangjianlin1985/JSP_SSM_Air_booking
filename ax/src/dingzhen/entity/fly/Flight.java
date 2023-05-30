package dingzhen.entity.fly;

import org.apache.ibatis.type.Alias;

import dingzhen.common.base.BaseEntity;
import dingzhen.entity.info.Airport;
import dingzhen.entity.info.City;
import dingzhen.entity.info.Plane;

@Alias("flight")
public class Flight extends BaseEntity{

	private String id;
	private String code;            // 班次
	private City origincity;        // 出发城市
	private City destinationcity;   // 目的城市
	private Airport originport;     // 出发机场
	private Airport destinationport;  // 目的机场
	private Plane plane;           // 飞机信息
	private String weeks;          // 周期
	// 还有start和end属性表示开始和到达时间，继承BaseEntity
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public City getOrigincity() {
		return origincity;
	}
	public void setOrigincity(City origincity) {
		this.origincity = origincity;
	}
	public City getDestinationcity() {
		return destinationcity;
	}
	public void setDestinationcity(City destinationcity) {
		this.destinationcity = destinationcity;
	}
	public Airport getOriginport() {
		return originport;
	}
	public void setOriginport(Airport originport) {
		this.originport = originport;
	}
	public Airport getDestinationport() {
		return destinationport;
	}
	public void setDestinationport(Airport destinationport) {
		this.destinationport = destinationport;
	}
	public Plane getPlane() {
		return plane;
	}
	public void setPlane(Plane plane) {
		this.plane = plane;
	}
	public String getWeeks() {
		return weeks;
	}
	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}
	
	
}
