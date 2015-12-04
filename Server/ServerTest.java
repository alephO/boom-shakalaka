/*
 * ServerTest.java
 * 
 */


import java.net.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

public class ServerTest {
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		@SuppressWarnings("resource")
		ServerSocket ss=new ServerSocket(6179);
		ConcurrentHashMap<Integer,PrintWriter> concurrentHashMap = new ConcurrentHashMap<Integer,PrintWriter>();
		HashMapHandler.setConcurrentHashMap(concurrentHashMap);
		while(true){
			System.out.println("new circle");
			Socket s=ss.accept();
			MyThread t = new MyThread(s);
			new Thread(t).start();
		}		
	}
	public static class MyThread implements Runnable{
			private Socket s=null;
			public MyThread(Socket s){
				this.s=s;
			}
			public static void execute(Socket s) throws IOException{
				Scanner sc=new Scanner(s.getInputStream());
				boolean flag=true;
				String st;
				OutputStream os=s.getOutputStream();
				PrintWriter ps=new PrintWriter(os);
				int uid=-1;
				while (true){
					st= sc.nextLine();
					System.out.println(st);
					String [] parts = st.split("#");
					if(parts[0].equals("*discon")){
						break;
					}
					else if(parts[0].equals("*login")){
						if(parts[1].equals("user1")&&parts[2].equals("111"))
							uid=1;
						else if(parts[1].equals("user2")&&parts[2].equals("222"))
							uid=2;
						else if(parts[1].equals("user3")&&parts[2].equals("333"))
							uid=3;
						else if(parts[1].equals("000")&&parts[2].equals("000"))
							uid=4;
						else
							uid=-1;
						ps.println("*status#login#"+uid+"#");
						if(uid!=-1)
							HashMapHandler.getConcurrentHashMap().put(uid,ps);
					}
					else if(parts[0].equals("*logout")){
						ps.println("*status#logout#");
					}
					else if(parts[0].equals("*chatt")){
						int touid=Integer.parseInt(parts[1]);
						//PrintWriter pst = HashMapHandler.getConcurrentHashMap().get(touid);
						
						PrintWriter pst = null;
						for(Integer i :HashMapHandler.getConcurrentHashMap().keySet()){
							if(i.equals(touid)){
								pst = HashMapHandler.getConcurrentHashMap().get(i);
								break;
							}
						}
						if(pst!=null) {
							pst.println("*chatf#" + uid + "#" + parts[2] + "#");
							pst.flush();
						}
						else
							ps.println("*status#send#-1#");
							
					}					
					else
						ps.println("*unexp#"+st+"#");
					ps.flush();
				}
				for(Integer i :HashMapHandler.getConcurrentHashMap().keySet()){
					if(i.equals(uid)){
						HashMapHandler.getConcurrentHashMap().remove(i);
					}
				}				
				ps.close();
				sc.close();
				s.close();
			}
			@Override
			public void run(){
				try {
					execute(s);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
	}
}
