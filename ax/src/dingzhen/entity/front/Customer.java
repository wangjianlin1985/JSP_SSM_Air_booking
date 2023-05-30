package dingzhen.entity.front;

import org.apache.ibatis.type.Alias;

import dingzhen.common.base.BaseEntity;

@Alias("customer")
public class Customer extends BaseEntity {

	private String id;
	private String sex;
	private String idcardno;
	private String birth;
	private String email;
	private String phone;
	private String password;
	private String cname;
	private String ename;
	private String regtime;
	
	
	public String getRegtime() {
		return regtime;
	}
	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getIdcardno() {
		return idcardno;
	}
	public void setIdcardno(String idcardno) {
		this.idcardno = idcardno;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", sex=" + sex + ", idcardno=" + idcardno
				+ ", birth=" + birth + ", email=" + email + ", phone=" + phone
				+ ", password=" + password + ", cname=" + cname + ", ename="
				+ ename + ", regtime=" + regtime + "]";
	}
	
	
}
