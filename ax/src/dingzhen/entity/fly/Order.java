package dingzhen.entity.fly;

import org.apache.ibatis.type.Alias;

import dingzhen.common.base.BaseEntity;
import dingzhen.entity.front.Customer;

@Alias("order")
public class Order extends BaseEntity {

	private String id;
	private Schedular schedular;
	private Customer customer;
	private String time;
	private String status;
	private String date;
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Schedular getSchedular() {
		return schedular;
	}
	public void setSchedular(Schedular schedular) {
		this.schedular = schedular;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
