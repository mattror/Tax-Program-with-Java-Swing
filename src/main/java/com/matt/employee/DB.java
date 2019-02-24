package com.matt.employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DB {
	private Connection con;
	private ResultSet rs;
	private PreparedStatement pstm;
	private String db = "jdbc:mysql://localhost:3306/tax";
	private String user = "root";
	private String pass = "";
	
	public DB() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		this.con=DriverManager.getConnection(db,user,pass);
		
	}
	
	// get list
	public List<Employee> getList(ResultSet rs) throws SQLException{
		Employee emp = new Employee();
		List<Employee> list = new ArrayList<Employee>();
		char gender;
		while(rs.next()) {
			gender = rs.getString("gender").charAt(0);
			emp = new Employee(
					rs.getString("id"),
					rs.getString("first_name"),
					rs.getString("last_name"),
					gender,
					rs.getString("phone"),
					rs.getString("email"),
					rs.getString("dob"),
					rs.getString("pob"),
					rs.getString("address"),
					rs.getString("department"),
					rs.getString("position"),
					rs.getDouble("salary"),
					rs.getDouble("benefit"),
					rs.getBoolean("has_spouse"),
					rs.getInt("minor_children"),
					rs.getString("image_path")
					);
			list.add(emp);
		}
		close();
		return list;
	}
	
	// read
	private List<Employee> listData(String query) throws SQLException {
		
		Employee emp = new Employee();
		List<Employee> list = new ArrayList<Employee>();
		char gender;
		while(this.rs.next()) {
			gender = rs.getString("gender").charAt(0);
			emp = new Employee(
					rs.getString("id"),
					rs.getString("first_name"),
					rs.getString("last_name"),
					gender,
					rs.getString("phone"),
					rs.getString("email"),
					rs.getString("dob"),
					rs.getString("pob"),
					rs.getString("address"),
					rs.getString("department"),
					rs.getString("position"),
					rs.getDouble("salary"),
					rs.getDouble("benefit"),
					rs.getBoolean("has_spouse"),
					rs.getInt("minor_children"),
					rs.getString("image_path")
					);
			list.add(emp);
		}
		close();
		return list;
	}
	public List<Employee> read() throws SQLException {
		String query = "SELECT * FROM tb_employee WHERE is_deleted=?";
		this.pstm = this.con.prepareStatement( query );
		pstm.setString(1, "0");
		this.rs = this.pstm.executeQuery();
		
		return listData(query);
	}
	
	public List<Employee> read(String id) throws SQLException{
		String query = "SELECT * FROM tb_employee WHERE is_deleted=? AND id=?";
		this.pstm = this.con.prepareStatement( query );
		pstm.setString(1, "0");
		pstm.setString(2, id);
		this.rs = this.pstm.executeQuery();
		return listData(query);
	}
	
	// delete
	public void delete(String id) throws SQLException {
		String query = "UPDATE tb_employee SET is_deleted=? WHERE id=?";
		this.pstm = this.con.prepareStatement( query );
		pstm.setString(1, "1");
		pstm.setString(2, id);
		this.pstm.executeUpdate();
		close();
	}
	
	// save 
	public void save(Employee em) throws SQLException {
		String query = "INSERT INTO tb_employee ("
				+ "id,"
				+ "first_name,"
				+ "last_name,"
				+ "gender,"
				+ "email,"
				+ "phone,"
				+ "dob,"
				+ "pob,"
				+ "address,"
				+ "department,"
				+ "position,"
				+ "salary,"
				+ "benefit,"
				+ "has_spouse,"
				+ "minor_children,"
				+ "image_path,"
				+ "is_deleted) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		this.pstm = this.con.prepareStatement(query);
		
		pstm.setString(1, em.getId());
		pstm.setString(2, em.getFirst_name());
		pstm.setString(3, em.getLast_name());
		pstm.setString(4, em.getGender()+"");
		pstm.setString(5, em.getEmail());
		pstm.setString(6, em.getPhone());
		pstm.setString(7, em.getDob());
		pstm.setString(8, em.getPob());
		pstm.setString(9, em.getAddress());
		pstm.setString(10, em.getDepartment());
		pstm.setString(11, em.getPosition());
		pstm.setString(12, em.getSalary()+"");
		pstm.setString(13, em.getBenefit()+"");
		pstm.setString(14, em.isHasSpouse()?"1":"0");
		pstm.setString(15, em.getMinorChild()+"");
		pstm.setString(16, em.getImage());
		pstm.setString(17, "0");
		this.pstm.executeUpdate();
		close();
	}
	
	// update 
	public void update(Employee em) throws SQLException {
		String query = "UPDATE tb_employee SET "
				+ "first_name=?,"
				+ "last_name=?, "
				+ "gender=?, "
				+ "email=?, "
				+ "phone=?, "
				+ "dob=?, "
				+ "pob=?, "
				+ "address=?, "
				+ "department=?, "
				+ "position=?, "
				+ "salary=?, "
				+ "benefit=?,"
				+ "has_spouse=?,"
				+ "minor_children=?,"
				+ "image_path=? "
				+ "WHERE id=?";
		this.pstm = this.con.prepareStatement(query);
		pstm.setString(1, em.getFirst_name());
		pstm.setString(2, em.getLast_name());
		pstm.setString(3, em.getGender()+"");
		pstm.setString(4, em.getEmail());
		pstm.setString(5, em.getPhone());
		pstm.setString(6, em.getDob());
		pstm.setString(7, em.getPob());
		pstm.setString(8, em.getAddress());
		pstm.setString(9, em.getDepartment());
		pstm.setString(10, em.getPosition());
		pstm.setString(11, em.getSalary()+"");
		pstm.setString(12, em.getBenefit()+"");
		pstm.setString(13, em.isHasSpouse()?"1":"0");
		pstm.setString(14, em.getMinorChild()+"");
		pstm.setString(15, em.getImage());
		pstm.setString(16, em.getId());
		this.pstm.executeUpdate();
		close();
	}
	
	// search
	public List<Employee> search(String filter, String search) throws SQLException {
		String query="SELECT * FROM tb_employee";
		if(filter.equalsIgnoreCase("id")) {
			query ="SELECT * FROM tb_employee WHERE id LIKE ? AND is_deleted=?";
			this.pstm = this.con.prepareStatement(query);
			pstm.setString(1,"%"+search+"%");
			pstm.setString(2,"0");
			this.rs = this.pstm.executeQuery();
			return getList(this.rs);
			
		}else if(filter.equalsIgnoreCase("name")) {
			query ="SELECT * FROM tb_employee WHERE is_deleted=? AND (first_name LIKE ? OR last_name LIKE ?)";
			this.pstm = this.con.prepareStatement(query);
			pstm.setString(2,"%"+search+"%");
			pstm.setString(3,"%"+search+"%");
			pstm.setString(1,"0");
			this.rs = this.pstm.executeQuery();
			return getList(this.rs);
		}else if(filter.equalsIgnoreCase("gender")) {
			query ="SELECT * FROM tb_employee WHERE is_deleted=? AND gender=?";
			this.pstm = this.con.prepareStatement(query);
			pstm.setString(1,"0");
			pstm.setString(2,search);
			this.rs = this.pstm.executeQuery();
			return getList(this.rs);
		}
		
		return null;
	}
	
	// get image
	public String getImage(String i) throws SQLException {
		String query = "SELECT image_path FROM tb_employee WHERE id=?";
		String result="";
		this.pstm = this.con.prepareStatement( query );
		pstm.setString(1, i);
		this.rs = this.pstm.executeQuery();
		if(this.rs.next()) result = rs.getString(1);
		close();
		return result;
	}
	
	// close connect
	public void close() throws SQLException {
		this.con.close();
	}
}



