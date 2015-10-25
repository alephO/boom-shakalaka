import java.sql.*;

public class ConnectPSQL {
	static boolean from_tour_or_guide; // from client: 1 ; from guide: 0;
	
	public static void main(String arg[]){
		try{
			Class.forName("org.postgresql.Driver");
			Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/zhongzhongzhong", "zhongzhongzhong", "zhongtianjie");
			
			
			if (from_tour_or_guide){
			// add a new user
			add_tourist(db);}
			// add a new guide
			else{
			add_guide(db);}
			// add a new request, called when tourist confirm a request
			add_request(db);
			// guide accept a request, call when guide accept a request
			accept_request(db);
			// tourists cancel a booking
			cancel_booking(db);
			// query pending request
			pending_request(db);
			// query upcoming request
			upcoming_request(db);
			// query previous request
			previous_request(db);
			// message
			insert_message(db);
			
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		//System.out.println("Opened database successfully");
	}
	
	public static void add_tourist(Connection db){
		Statement stmt;
		try {
			stmt = db.createStatement();
			String sql = "SELECT user_id FROM tourists WHERE user_name ="+ user_name +";"; // whether the user exists.
			ResultSet rs = stmt.executeQuery(sql);
			if (!rs.isBeforeFirst()){ // doesn't exist, then insert a new user to the tourists table
				sql = "insert into tourists(user_name,password) values('"+ user_name + "','" + password + "');";
			}
			else {
				/* send back a user-name(tourists) exists message */
			}
					
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void add_guide(Connection db){
		Statement stmt;
		try {
			stmt = db.createStatement();
			String sql = "SELECT user_id FROM guides WHERE user_name ="+ user_name +";"; // whether the user exists.
			ResultSet rs = stmt.executeQuery(sql);
			if (!rs.isBeforeFirst()){ // doesn't exist, then insert a new guide to the guides table
				sql = "insert into guides(user_name,password) values('"+ user_name + "','" + password + "');";
			}
			else {
				/* send back a user-name(guide) exists message */
			}
					
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void add_request(Connection db){
		Statement stmt;
		try {
			stmt = db.createStatement();
			String sql = "insert into requst(tourist,requirement_1,requirement_2,requirement_3,requirement_4,fromdate,todate,status,locations)"
					+ "values((select user_id from tourists where user_name = '" + user_name +"'),"
					+ "(select requirement_id from requirement where requirement_name = '"+requirement_1+"'),"
					+ "(select requirement_id from requirement where requirement_name = '"+requirement_2+"'),"
					+ "(select requirement_id from requirement where requirement_name = '"+requirement_3+"'),"
					+ "(select requirement_id from requirement where requirement_name = '"+requirement_4+"'),"
					+ "'"+fromdate+"','"+todate+"','pending'"
					+ "(select nation_id from nation where nation_name = '"+nation_name+"'));";
			ResultSet rs = stmt.executeQuery(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void accept_request(Connection db){
		Statement stmt;
		try {
			stmt = db.createStatement();
			String sql = "Update request set guide = (select user_id from guides where user_name = '"+ guide_name +"'),"
				+"status = 'upcoming' where request_id = '"+ request_id +"';";
			ResultSet rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public static void cancel_booking(Connection db){
		Statement stmt;
		try {
			stmt = db.createStatement();
			String sql = "Delete from request where request_id = '" + request_id +"';";
			ResultSet rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
	}
	
	public static void pending_request(Connection db){
		Statement stmt;
		try {
			stmt = db.createStatement();
			String sql = "select * from request where tourist_id = (select user_id from tourists where user_name = '"+user_name+"') and status = 'pending';";
			ResultSet rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public static void upcoming_request(Connection db){
		Statement stmt;
		try {
			stmt = db.createStatement();
			String sql = "select * from request where tourist_id = (select user_id from tourists where user_name = '"+user_name+"') and status = 'upcoming';";
			ResultSet rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public static void previous_request(Connection db){
		Statement stmt;
		try {
			stmt = db.createStatement();
			String sql = "select * from request where tourist_id = (select user_id from tourists where user_name = '"+user_name+"') and status = 'previous';";
			ResultSet rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public static void insert_message(Connection db){
		Statement stmt;
		try {
			stmt = db.createStatement();
			String sql = "insert into message(user_1, user_2, time, content) values ("
			+ "(select user_id from tourists where user_name = '"+tourist_name+"'),"
			+ "(select user_id from guides where user_name = '"+guide_name+"'),"
			+ "'2015-10-25 10:23:42+02', 'hello world!');";
			ResultSet rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
}
