import java.sql.*;
import java.text.SimpleDateFormat;

public class ConnectPSQL {
	public String password;
	public String requirement_1;
	public String requirement_2;
	public String requirement_3;
	public String requirement_4;
	public SimpleDateFormat fromdate;
	public SimpleDateFormat todate;
	public String nation_name;
	public String tourist_name;
	public String guide_name;
	public int request_id;
	public SimpleDateFormat message_timestamp;
	
	public Connection db;
	
	public ConnectPSQL(){
		try{
			Class.forName("org.postgresql.Driver");
			db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/zhongzhongzhong", "zhongzhongzhong", "zhongtianjie");	
			
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	};
	
	public Connection get_db(){
		return this.db;
	}
	
	
	public void set_tourist_name(String a){
		this.tourist_name = a;
	}
	
	public void set_guide_name(String a){
		this.tourist_name = a;
	}
	
	public void set_password(String a){
		this.password = a;
	}
	
	public void set_requirement_1(String a){
		this.requirement_1 = a;
	}
	
	public void set_requirement_2(String a){
		this.requirement_2 = a;
	}
	
	public void set_requirement_3(String a){
		this.requirement_3 = a;
	}
	
	public void set_requirement_4(String a){
		this.requirement_4 = a;
	}
	
	public void set_fromdate(SimpleDateFormat a){
		this.fromdate = a;
	}
	
	public void set_todate(SimpleDateFormat a){
		this.todate = a;
	}
	
	public void set_nations(String a){
		this.nation_name = a;
	}
	
	public void set_requestid(int a){
		this.request_id = a;
	}
	
	public void set_message_timestamp(SimpleDateFormat a){
		this.message_timestamp = a;
	}
	
	
	public void add_tourist(){
		Statement stmt;
		try {
			stmt = db.createStatement();
			String sql = "SELECT user_id FROM tourists WHERE user_name ="+ tourist_name +";"; // whether the user exists.
			ResultSet rs = stmt.executeQuery(sql);
			if (!rs.isBeforeFirst()){ // doesn't exist, then insert a new user to the tourists table
				sql = "insert into tourists(user_name,password) values('"+ tourist_name + "','" + password + "');";
			int rs_2 = stmt.executeUpdate(sql);
			}
			else {
				/* send back a user-name(tourists) exists message */
			}
					
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void add_guide(){
		Statement stmt;
		try {
			stmt = db.createStatement();
			String sql = "SELECT user_id FROM guides WHERE user_name ="+ guide_name +";"; // whether the user exists.
			ResultSet rs = stmt.executeQuery(sql);
			if (!rs.isBeforeFirst()){ // doesn't exist, then insert a new guide to the guides table
				sql = "insert into guides(user_name,password) values('"+ guide_name + "','" + password + "');";
				int rs_2 = stmt.executeUpdate(sql);
			}
			else {
				/* send back a user-name(guide) exists message */
			}
					
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void add_request(){
		Statement stmt;
		try {
			stmt = db.createStatement();
			String sql = "insert into requst(tourist,requirement_1,requirement_2,requirement_3,requirement_4,fromdate,todate,status,locations)"
					+ "values((select user_id from tourists where user_name = '" + tourist_name +"'),"
					+ "(select requirement_id from requirement where requirement_name = '"+requirement_1+"'),"
					+ "(select requirement_id from requirement where requirement_name = '"+requirement_2+"'),"
					+ "(select requirement_id from requirement where requirement_name = '"+requirement_3+"'),"
					+ "(select requirement_id from requirement where requirement_name = '"+requirement_4+"'),"
					+ "'"+fromdate+"','"+todate+"','pending'"
					+ "(select nation_id from nation where nation_name = '"+nation_name+"'));";
			int rs = stmt.executeUpdate(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void accept_request(){
		Statement stmt;
		try {
			stmt = db.createStatement();
			String sql = "Update request set guide = (select user_id from guides where user_name = '"+ guide_name +"'),"
				+"status = 'upcoming' where request_id = '"+ request_id +"';";
			int rs = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void cancel_booking(){
		Statement stmt;
		try {
			stmt = db.createStatement();
			String sql = "Update request set status = 'canceled' where request_id = '"+ request_id +"';";
			int rs = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
	}
	
	public ResultSet pending_request(){
		Statement stmt;
		ResultSet rs = null;
		try {
			stmt = db.createStatement();
			String sql = "select * from request where tourist_id = (select user_id from tourists where user_name = '"+tourist_name+"') and status = 'pending';";
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return rs;
	}
	
	public ResultSet upcoming_request(){
		Statement stmt;
		ResultSet rs = null;
		try {
			stmt = db.createStatement();
			String sql = "select * from request where tourist_id = (select user_id from tourists where user_name = '"+tourist_name+"') and status = 'upcoming';";
		    rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return rs;
	}
	
	public ResultSet previous_request(){
		Statement stmt;
		ResultSet rs = null;
		try {
			stmt = db.createStatement();
			String sql = "select * from request where tourist_id = (select user_id from tourists where user_name = '"+tourist_name+"') and status = 'previous';";
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return rs;
	}
	
	public ResultSet insert_message(){
		Statement stmt;
		ResultSet rs = null;
		try {
			stmt = db.createStatement();
			String sql = "insert into message(user_1, user_2, time, content) values ("
			+ "(select user_id from tourists where user_name = '"+tourist_name+"'),"
			+ "(select user_id from guides where user_name = '"+guide_name+"'),"
			+ "'"+message_timestamp+"', 'hello world!');";
			rs = stmt.executeQuery(sql);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return rs;
	}
	
}
