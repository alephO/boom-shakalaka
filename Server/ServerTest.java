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
				String st= sc.nextLine();
				System.out.println(st);
				OutputStream os=s.getOutputStream();
				PrintWriter ps=new PrintWriter(os);
				ps.println("You sent: "+st);
				ps.flush();
				while (flag){
					if(st.equals("disconnect")){
						flag=false;
					}
					st= sc.nextLine();
					System.out.println(st);
					ps.println("You sent: "+st);
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
