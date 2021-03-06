package beans;

import java.util.ArrayList;

public class Company {
	
	private int companyId;
	private String name, email, password;
	private ArrayList<Coupon> coupons;
	
	
	public Company(int companyId, String name, String password, String email) {
		super();
		this.companyId = companyId;
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public Company(String name, String password, String email) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ArrayList<Coupon> getCoupons() {
		return coupons;
	}
	public void setCoupons(ArrayList<Coupon> coupons) {
		this.coupons = coupons;
	}
	public int getCompanyId() {
		return companyId;
	}
	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", name=" + name + ", email=" + email + ", password=" + password
				+ "]";
	}
	
	
	

}
