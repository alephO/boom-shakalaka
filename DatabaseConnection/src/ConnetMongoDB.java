import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.Block;

import java.lang.String;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.bson.types.*;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.*;

import static java.util.Arrays.asList;


public class ConnetMongoDB {
	
	public static void main(String[] args) {
		try{
			MongoClient mongoClient = new MongoClient ("localhost", 27017);
			MongoDatabase db = mongoClient.getDatabase("test");
			/*
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
			db.getCollection("restaurant").insertOne(
					new Document(
							"address", new Document()
							.append("street","2 Avenue")
							.append("zipcode","02453")
							.append("building","1408")
							.append("coord",asList(-73.12314,40.12342))
							)
					.append("borough","Manhatten")
					.append("cuisine","Italian")
					.append("grades", asList(
							new Document()
							.append("date", format.parse("2014-10-01T00:00:00Z"))
							.append("grade","A")
							.append("score","11"),
							
							new Document()
							.append("data", format.parse("2014-01-16T00:00:00Z"))
							.append("grade","B")
							.append("score","17")
							))
					.append("name","VELLA")
					.append("restaurant_id","41704620")
					
			);*/
				
			
			
			
			/*Tourist add a new record to the requirement */
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			BasicDBObject request = new BasicDBObject();
			
			String User_name= new String();
			String Guide_name = new String();
			DateFormat DateFrom = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			DateFormat DateTo = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			String Note = new String();
			String Requirement_1 = new String();
			String Requirement_2 = new String();
			String Requirement_3 = new String();
			String Requirement_4 = new String();
			String Location = new String();
			String Status = "pending";
			
			BasicDBObject query = new BasicDBObject();
			query.put("username", User_name);
			DBCollection a = (DBCollection) db.getCollection("User");
			Object tourist_id = a.findOne(query).get("_id");
			a.findOne(query).get("tourist_request").;
			
			
			DBCollection requestcollection = (DBCollection) db.getCollection("Request"); 
			
			request.append("datefrom", DateFrom);
			request.append("dateto", DateTo);
			request.append("note", Note);
			request.append("requirement_1", Requirement_1);
			request.append("requirement_2",Requirement_2);
			request.append("requirement_3",Requirement_3);
			request.append("requirement_4",Requirement_4);
			request.append("location",Location);
			request.append("status", Status);
			request.append("username_id",new Document()
				.append("tourist", tourist_id)
			);
					
			requestcollection.insert(request);
			Object request_id = request.get("_id");
			
			
			db.getCollection("User").insertOne(arg0);
			
			
			/* user registration */
			BasicDBObject user = new BasicDBObject();
			String Username = new String();
			String Email = new String();
			String Password = new String();
			String Account = new String();  /* tourist or guide */
			String tourist_request[] = new String[]{};
			
			DBCollection usercollection = (DBCollection) db.getCollection("User");
			
			user.append("username", Username);
			user.append("password", Password);
			user.append("Email", Email);
			user.append("tourist_request", asList(tourist_request)); // tourist refer to request.
			usercollection.insert(user);
			
			
			
			ObjectId user_id = (ObjectId)user.get("_id");
			
			
			
			
			
			
			/*
			FindIterable<Document> iterable = db.getCollection("restaurant").find();
			iterable.forEach(new Block<Document>(){
				public void apply(final Document document){
					System.out.print(document);
					System.out.print("\n");
					System.out.print("\n"); 
				}
			});*/
			
			
		
		
			
			
			
			mongoClient.close();
			
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}

	}
	
}
