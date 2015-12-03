/*
 * ServerTest.java
 * 
 */


import java.net.*;
import java.util.*;
import java.io.*;

public class ServerTest {
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		@SuppressWarnings("resource")
		ServerSocket ss=new ServerSocket(6179);
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
				while (true){
					st= sc.nextLine();
					System.out.println(st);
					String [] parts = st.split("#");
					if(parts[0].equals("*discon")){
						break;
					}
					else if(parts[0].equals("*login")){
						if(parts[1].equals("user1")&&parts[2].equals("111"))
							ps.println("*status#login#1#");
						else if(parts[1].equals("user2")&&parts[2].equals("222"))
							ps.println("*status#login#2#");
						else if(parts[1].equals("user3")&&parts[2].equals("333"))
							ps.println("*status#login#3#");
						else
							ps.println("*status#login#-1#");
					}
					else if(parts[0].equals("*logout")){
						ps.println("*status#logout#");
					}
					else if(parts[0].equals("*chatt")){
						ps.println("*chatf#0#I'm not listening. FUCK YOU!#");
					}					
					else
						ps.println("*unexp#"+st+"#");
					ps.flush();
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
