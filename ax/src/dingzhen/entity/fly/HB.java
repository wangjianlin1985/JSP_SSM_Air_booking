package dingzhen.entity.fly;

import org.apache.ibatis.type.Alias;

@Alias("hb")
public class HB {

	private String flightid;  // 航班ID
	private String start;  // 出发时间
	private String end;  // 到达时间
	private String code;  // 班次
	private String price;  // 价格
	private String oport;  // 出发站
	private String dport;  // 到达站
	private String planemodel;  // 飞机型号
	
	
	public String getFlightid() {
		return flightid;
	}
	public void setFlightid(String flightid) {
		this.flightid = flightid;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getOport() {
		return oport;
	}
	public void setOport(String oport) {
		this.oport = oport;
	}
	public String getDport() {
		return dport;
	}
	public void setDport(String dport) {
		this.dport = dport;
	}
	public String getPlanemodel() {
		return planemodel;
	}
	public void setPlanemodel(String planemodel) {
		this.planemodel = planemodel;
	}
	
}
