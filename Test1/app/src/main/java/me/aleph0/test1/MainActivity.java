package me.aleph0.test1;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

	static final String hostname="boom.aleph0.me";
	static final int portnum = 6179;
	static final int request_code=1;
	Socket socket=null;
	String username;
	int userid=-1;
	TextView txtUNAME;
	TextView txtUID;
	PrintWriter uout=null;
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle=intent.getExtras();
			txtUID.setText(bundle.getString("message"));
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txtUNAME=(TextView)findViewById(R.id.tUName);
		txtUID=(TextView)findViewById(R.id.tUID);
		try {
			socket = new InitConnectTask().execute().get(30, TimeUnit.SECONDS);
		}
		catch (Exception e){
			Toast.makeText(getBaseContext(), "Connection timeout", Toast.LENGTH_SHORT).show();
		}
		try {
			uout = new PrintWriter(socket.getOutputStream(), true);
		}
		catch (IOException e){
			Toast.makeText(getBaseContext(), e.getStackTrace().toString(), Toast.LENGTH_SHORT).show();
		}
		new RecieveTask().execute(socket);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			socket.close();
		}
		catch (IOException e){
			;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}
		else if(id == R.id.mlogin){
			startActivityForResult(new Intent("me.aleph0.Test1.LOGIN"),request_code);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if (requestCode == request_code) {
			if (resultCode == RESULT_OK) {
				username = data.getStringExtra("extra_UNAME");
				String password = data.getStringExtra("extra_PWD");
				Log.d("PGD",password+username);
				if(uout!=null){
					uout.write("*login#"+username+"#"+password+"#\n");
					uout.flush();
				}
				else{
					Log.d("PGD","empty_uout");
				}

			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction("me.aleph0.pocketguide.recievem");

		//this.registerReceiver(this.receiver, filter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		this.unregisterReceiver(this.receiver);
	}

	class InitConnectTask extends AsyncTask<Void, Void, Socket> {
        Exception excpt;
        String r;
        protected Socket doInBackground(Void...v){
            Socket echoSocket=null;
            try {
                echoSocket = new Socket(hostname, portnum);
                //PrintWriter uout = new PrintWriter(echoSocket.getOutputStream(), true);
                //BufferedReader uin =
                //        new BufferedReader(
                //                new InputStreamReader(echoSocket.getInputStream()));
                //uout.write("*inital\n");
                //uout.flush();
                //r = uin.readLine();

            }
            catch (Exception e){
                excpt=e;
                this.cancel(true);
            }
            return echoSocket;
        }

        @Override
        protected void onPostExecute(Socket socket) {
            super.onPostExecute(socket);
            Toast.makeText(getBaseContext(), "Success connect to server!", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled(Socket socket) {
            super.onCancelled(socket);
            Toast.makeText(getBaseContext(), excpt.toString(), Toast.LENGTH_SHORT).show();

        }
    }

	class RecieveTask extends AsyncTask<Socket,String,String>{
		Socket socket;
		String recieve;
		Exception excpt;
		protected String doInBackground(Socket...s){
			socket=s[0];

			/*try{
				//PrintWriter uout = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader uin =
						new BufferedReader(
								new InputStreamReader(socket.getInputStream()));
				//while(true){
				//	recieve=uin.readLine();
				//	publishProgress(recieve);
				//}
				while(int i=0;i<10000;i++){
					Log.d("PGD","fuck you"+i);
				}
			}
			catch (Exception e){
				excpt=e;
				this.cancel(true);
				Log.d("PGD",e.getStackTrace().toString());
			}*/
			try {
				for (int i = 0; i < 10000; i++) {
					Log.d("PGD", "fuck you" + i);
					publishProgress("fuck you" + i);
					Thread.sleep(1000);
				}
			}
			catch (Exception e){
				;
			}

			return "OK";
		}
		public void SendBroadcast(String s){
			Intent intent = new Intent();
			intent.setAction("me.aleph0.pocketguide.recievem");
			intent.putExtra("message",s);
			sendBroadcast(intent);
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			Log.d("PGD",values[0]);
			/*String[] parts=values[0].split("#");
			if(parts[0].equals("*status")){
				if(parts[1].equals("login")){
					if(parts[2].equals("-1")){
						Toast.makeText(getBaseContext(), "Login Failed. Check username or password", Toast.LENGTH_SHORT).show();
					}
					else{
						Toast.makeText(getBaseContext(), "Suceessful loged in!", Toast.LENGTH_SHORT).show();
						userid= Integer.parseInt(parts[2]);
						txtUNAME.setText(username);
						txtUID.setText(parts[2]);
					}
				}
			}*/
			SendBroadcast(values[0]);
		}

		@Override
		protected void onCancelled(String strings) {
			super.onCancelled(strings);
			Toast.makeText(getBaseContext(), excpt.toString(), Toast.LENGTH_SHORT).show();

		}
	}
}
