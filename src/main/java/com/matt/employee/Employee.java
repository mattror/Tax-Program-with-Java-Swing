package com.matt.employee;

import com.matt.utilities.Utility;

public class Employee {

	private String id;
	private String first_name;
	private String last_name;
	private char gender;
	private String phone;
	private String email;
	private String dob;
	private String pob;
	private String address;
	private String department;
	private String position;
	private double salary;
	private double benefit;
	private boolean hasSpouse;
	private int minorChild;
	private String image;

	public Employee(String id, String first_name, String last_name, char gender, 
			String phone, String email, String dob,String pob, String address, 
			String department, String position, double salary, double benefit,
			boolean hasSpouse, int minorChild, String image) {
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.gender = gender;
		this.phone = phone;
		this.email = email;
		this.dob = dob;
		this.pob = pob;
		this.address = address;
		this.department = department;
		this.position = position;
		this.salary = salary;
		this.benefit = benefit;
		this.hasSpouse = hasSpouse;
		this.minorChild = minorChild;
		this.image = image;
	}

	public Employee(double salary, double benefit, boolean hasSpouse, int minorChild) {
		this.salary = salary;
		this.benefit = benefit;
		this.hasSpouse = hasSpouse;
		this.minorChild = minorChild;
	}
	
	public Employee() {
	}

	// exchange salary from dollar to riels
	public double exchangeSalary(double exchangeRate) {
		return this.salary*exchangeRate;
	}
	
	// get supported family money
	private double getSupportFamilyMoney() {
		if(this.hasSpouse == true) {
			return (this.minorChild+1)*150000;
		}else return 0.0;
	}
	
	// get Tax rate
	private double getTaxRate(double moneyForTax) {
		if(moneyForTax <= 1200000.0) {
			return 0.0;
		}else if(moneyForTax <= 2000000.0) {
			return 0.05;
		}else if(moneyForTax <= 8500000.0) {
			return 0.1;
		}else if(moneyForTax <= 12500000.0) {
			return 0.15;
		}else {
			return 0.2;
		}
	}
	
	// get bias money
	private double getBiasMoney(double moneyForTax) {
		if(moneyForTax <= 1200000) return 0.0;
		else if(moneyForTax <= 2000000) return 60000;
		else if(moneyForTax <= 8500000) return 160000;
		else if(moneyForTax <= 12500000) return 585000;
		else return 1210000;
	}
	
	// get tax benefit
	private double getTaxBenefit(double exchange) {
		return (this.benefit*exchange)*0.2;
	}
	
	// get tax
	public double getTax(double exchangeRate) {
		// Exchange Salary
		double salaryR = this.exchangeSalary(exchangeRate);
		// Money for family support
		double supportFamilyMoney = this.getSupportFamilyMoney();
		// salary for tax calculate
		double moneyForTax = salaryR - supportFamilyMoney;
		// get tax rate
		double taxRate = this.getTaxRate(moneyForTax);
		//get bias money
		double biasMoney  = this.getBiasMoney(moneyForTax);
		// get tax on benefit
		double taxBenefit = this.getTaxBenefit(exchangeRate);
		// total = 
		double totalTaxR = ((moneyForTax*taxRate)-biasMoney)+taxBenefit;
		return Math.abs(totalTaxR);
	}
	
	// get net salary
	public double getNetSalary(double exchangeRate) {
		return ((this.salary+this.benefit)*exchangeRate) - this.getTax(exchangeRate);
	}
	
	// list employee
	public String[] listEmpRowData(double exchange) {
		Utility uti = new Utility();
		String spouse = this.hasSpouse?"Yes":"No";
		String taxR = String.format("%.2f", getTax(exchange));
		String taxU = String.format("%.2f", getTax(exchange)/exchange);
		String netR = String.format("%.2f", getNetSalary(exchange));
		String netU = String.format("%.2f", getNetSalary(exchange)/exchange);
		String salary = String.format("%.2f", this.salary);
		String benefit = String.format("%.2f", this.benefit);
		String data[]= {
				this.id,
				this.first_name,
				this.last_name,
				this.gender+"",
				this.dob,
				this.pob,
				this.address,
				this.email,
				this.phone,
				this.department,
				this.position,
				spouse,
				this.minorChild+"",
				//cal.currencySign(emp.getSalary(), "us"),
				salary+"",
				//cal.currencySign(emp.getBenefit(), "us"),
				benefit+"",
				//cal.currencySign(taxS,"us"),
				uti.currencySign(Double.parseDouble(taxU), "us")+"",
				uti.currencySign(Double.parseDouble(taxR), "kh")+"",
				//cal.currencySign(netS,"Us")
				uti.currencySign(Double.parseDouble(netU), "us")+"",
				uti.currencySign(Double.parseDouble(netR), "kh")+""
			};
		return data;
	}
	
	// string report
	public String[] stringReport(double exchange) {
		Utility uti = new Utility();
		String taxR = String.format("%.2f", getTax(exchange));
		String taxU = String.format("%.2f", getTax(exchange)/exchange);
		String netR = String.format("%.2f", getNetSalary(exchange));
		String netU = String.format("%.2f", getNetSalary(exchange)/exchange);
		String salaryU = String.format("%.2f", this.salary);
		String salaryR = String.format("%.2f", this.salary*exchange);
		String data[]= {
				this.id,
				this.first_name,
				this.last_name,
				this.gender+"",
				this.dob,
				uti.currencySign(Double.parseDouble(salaryU), "us")+"",
				uti.currencySign(Double.parseDouble(salaryR), "kh")+"",
				uti.currencySign(Double.parseDouble(taxU), "us")+"",
				uti.currencySign(Double.parseDouble(taxR), "kh")+"",
				uti.currencySign(Double.parseDouble(netU), "us")+"",
				uti.currencySign(Double.parseDouble(netR), "kh")+""
			};
		return data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPob() {
		return pob;
	}

	public void setPob(String pob) {
		this.pob = pob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public double getBenefit() {
		return benefit;
	}

	public void setBenefit(double benefit) {
		this.benefit = benefit;
	}

	public boolean isHasSpouse() {
		return hasSpouse;
	}

	public void setHasSpouse(boolean hasSpouse) {
		this.hasSpouse = hasSpouse;
	}

	public int getMinorChild() {
		return minorChild;
	}

	public void setMinorChild(int minorChild) {
		this.minorChild = minorChild;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
