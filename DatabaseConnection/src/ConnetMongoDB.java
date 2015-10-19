import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.Block;

import java.lang.String;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.bson.Document;

import static java.util.Arrays.asList;


public class ConnetMongoDB {
	
	public static void main(String[] args) {
		String a = "123";
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
			String User_name= new String();
			DateFormat DateFrom = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			DateFormat DateTo = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			String Note = new String();
			String Requirement_1 = new String();
			String Requirement_2 = new String();
			String Requirement_3 = new String();
			String Requirement_4 = new String();
			String Location = new String();
			
			
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			db.getCollection("Requst").insertOne(
					new Document()
					.append("username", User_name)
					.append("datefrom", DateFrom)
					.append("dateto", DateTo)
					.append("note", Note)
					.append("requirement_1", Requirement_1)
					.append("requirement_2",Requirement_2)
					.append("requirement_3",Requirement_3)
					.append("requirement_4",Requirement_4)
					.append("location",Location)
					);
			
			FindIterable<Document> iterable = db.getCollection("restaurant").find();
			iterable.forEach(new Block<Document>(){
				public void apply(final Document document){
					System.out.print(document);
					System.out.print("\n");
					System.out.print("\n"); 
				}
			});
			
			
		
		
			
			
			
			mongoClient.close();
			
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}

	}

}
