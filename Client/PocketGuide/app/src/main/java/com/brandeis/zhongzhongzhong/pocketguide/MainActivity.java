package com.brandeis.zhongzhongzhong.pocketguide;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {
    private TabHost myTabHost;
    //private TabHost bookTabHost;
    public Socket socket;
	public int uid=-1;      //It is very important. When not logging in, it is -1
	public String uname="Not logged in";
	public PrintWriter uout;
    private static final String tag = "Event";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        /*TabHost.TabSpec spec = myTabHost.newTabSpec("Start");
        spec.setIndicator("Start");
        spec.setContent(R.id.Client_tab1);
*/
        myTabHost = (TabHost) findViewById(R.id.tabHost);
        myTabHost.setup();

        myTabHost.addTab(myTabHost.newTabSpec("Start")
                .setIndicator("Start")
                .setContent(R.id.Client_tab1));

        myTabHost.addTab(myTabHost.newTabSpec("Chat")
                .setIndicator("Chat")
                .setContent(R.id.Client_tab2));

        myTabHost.addTab(myTabHost.newTabSpec("Trip")
                .setIndicator("Trip")
                .setContent(R.id.Client_tab3));

        myTabHost.addTab(myTabHost.newTabSpec("Profile")
                .setIndicator("Profile")
                .setContent(R.id.Client_tab4));


        ImageButton search_button = (ImageButton) findViewById(R.id.index_search_button);
        search_button.setBackgroundColor(Color.TRANSPARENT);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(Index_Page.this, Choose_Destination.class);
                startActivity(new Intent("com.brandeis.Choose_Destination"));
                //Log.d(TAG, "start a new activity");

            }
        });

        Button chat_btn = (Button) findViewById(R.id.chat_btn);
        View.OnClickListener chat_btn_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("com.brandeis.ChatActivity"));
            }
        };
        chat_btn.setOnClickListener(chat_btn_listener);

	    try {
		    InitConnectTask ict = new InitConnectTask();
		    ict.execute();
		    socket=ict.get(30,TimeUnit.SECONDS);
	    }
	    catch (Exception e){
		    Toast.makeText(getBaseContext(), "Connection timeout", Toast.LENGTH_SHORT).show();
		    Log.d("PGD",e.toString());
	    }
	    if(socket==null) {
		    Log.d("PGD", "socket inital failed");
	    }
	    else{
		    try {
			    uout = new PrintWriter(socket.getOutputStream(), true);
		    } catch (IOException e) {
			    Toast.makeText(getBaseContext(), e.getStackTrace().toString(), Toast.LENGTH_SHORT).show();
		    }
	    }
		new RecieveTask().execute(socket,this);

	    Button login_button = (Button) findViewById(R.id.bLogin);
	    final EditText t4E1 = (EditText) findViewById(R.id.t4EName);
	    final EditText t4E2 = (EditText) findViewById(R.id.t4EPass);
	    TextView t4T1 = (TextView) findViewById(R.id.t4UName);
	    TextView t4T2 = (TextView) findViewById(R.id.t4UID);
	    TextView t4L1 = (TextView) findViewById(R.id.t4l3);
	    login_button.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    uname = t4E1.getText().toString();
			    String password = t4E2.getText().toString();
			    if(uout!=null && uname!=null && password!=null){
				    uout.write("*login#"+uname+"#"+password+"#\n");
				    uout.flush();
			    }
			    else{
				    Log.d("PGD","empty_uout");
			    }
		    }
	    });
//
//        bookTabHost = (TabHost) findViewById(R.id.booktabHost);
//        bookTabHost.setup();
//
//        bookTabHost.addTab(bookTabHost.newTabSpec("Upcoming")
//        .setIndicator("Upcoming")
//        .setContent(R.id.book_tab1));
//
//        bookTabHost.addTab(bookTabHost.newTabSpec("pending")
//        .setIndicator("pending")
//                .setContent(R.id.book_tab2)
//        );
//
//        bookTabHost.addTab(bookTabHost.newTabSpec("Previous")
//        .setIndicator("Previous")
//        .setContent(R.id.book_tab3));

    }

    public void onStart()
    {
        super.onStart();
        Log.d(tag, "In the onStart() event");
    }

    public void onRestart()
    {
        super.onRestart();
        Log.d(tag, "In the onRestart() event");
    }

    public void onResume()
    {
        super.onResume();
        Log.d(tag, "In the onResume() event");
    }

    public void onPause(){
        super.onPause();
        Log.d(tag, "In the onPause() event");
    }

    public void onStop(){
        super.onStop();
        Log.d(tag, "In the onStop() event");
    }

    public void onDestroy(){
        super.onDestroy();
        Log.d(tag, "In the onDestroy() event");
    }


	class InitConnectTask extends AsyncTask<Void, Void, Socket> {
		Exception excpt;
		static final String hostname="boom.aleph0.me";
		static final int portnum = 6179;
		protected Socket doInBackground(Void...v){
			Socket echoSocket=null;
			try {
				echoSocket = new Socket(hostname, portnum);
			}
			catch (Exception e){
				excpt=e;
				Log.d("PGD",excpt.toString());
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
			Log.d("PGD1",excpt.getStackTrace().toString());
			Toast.makeText(getBaseContext(), excpt.toString(), Toast.LENGTH_SHORT).show();

		}
	}


	class RecieveTask extends AsyncTask<Object,String,String>{
		Socket socket;
		String recieve;
		Exception excpt;
		Activity maina;
		Button login_button = (Button) findViewById(R.id.bLogin);
		final EditText t4E1 = (EditText) findViewById(R.id.t4EName);
		final EditText t4E2 = (EditText) findViewById(R.id.t4EPass);
		TextView t4T1 = (TextView) findViewById(R.id.t4UName);
		TextView t4T2 = (TextView) findViewById(R.id.t4UID);
		TextView t4L1 = (TextView) findViewById(R.id.t4l3);
		Button btn = (Button)findViewById(R.id.bLogin);

		protected String doInBackground(Object...o){
			socket=(Socket)o[0];
			maina = (Activity)o[1];

			try{
				//PrintWriter uout = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader uin =
						new BufferedReader(
								new InputStreamReader(socket.getInputStream()));
				while(true){
					recieve=uin.readLine();
					publishProgress(recieve);
				}

			}
			catch (Exception e){
				excpt=e;
				this.cancel(true);
				Log.d("PGD", e.toString());
			}

			return null;
		}
		public void SendBroadcast(String s){
			Intent intent = new Intent();
			intent.setAction("me.aleph0.pocketguide.recievem");
			intent.putExtra("message", s);
			sendBroadcast(intent);
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			Log.d("PGD", values[0]);
			String[] parts=values[0].split("#");
			if(parts[0].equals("*status")){
				if(parts[1].equals("login")){
					if(parts[2].equals("-1")){
						Toast.makeText(getBaseContext(), "Login Failed. Check username or password", Toast.LENGTH_SHORT).show();
						t4E1.setVisibility(View.VISIBLE);
						t4E2.setVisibility(View.VISIBLE);
						t4T1.setVisibility(View.GONE);
						t4T2.setVisibility(View.GONE);
						uid=-1;
						t4L1.setText("Password\t\t\t");
						btn.setText("Log out");
					}
					else{
						Toast.makeText(getBaseContext(), "Suceessful logged in!", Toast.LENGTH_SHORT).show();
						uid= Integer.parseInt(parts[2]);
						uname = parts[2];
						t4T1.setText(uname);
						t4T2.setText(parts[2]);
						t4E1.setVisibility(View.GONE);
						t4E2.setVisibility(View.GONE);
						t4T1.setVisibility(View.VISIBLE);
						t4T2.setVisibility(View.VISIBLE);
						t4L1.setText("User ID\t\t\t\t");
						btn.setText("Log in");
					}
				}
			}
			SendBroadcast(values[0]);
		}

		@Override
		protected void onCancelled(String strings) {
			super.onCancelled(strings);
			Toast.makeText(getBaseContext(), excpt.toString(), Toast.LENGTH_SHORT).show();

		}
	}


}

