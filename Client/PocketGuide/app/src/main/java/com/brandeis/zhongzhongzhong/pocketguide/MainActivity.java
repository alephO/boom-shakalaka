package com.brandeis.zhongzhongzhong.pocketguide;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
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
	private ChatDB chatDB;

	//private static final String TAG = ChatListActivity.class.getSimpleName();

	private ArrayList<chatName> list = new ArrayList<chatName>();   //

	private ListView chat_name_list;
	RecieveTask recieveTask;

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
			    WriterHandler.setPrintWriter(uout);
			} catch (IOException e) {
			    Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_SHORT).show();
		    }
	    }
		recieveTask = new RecieveTask();
		recieveTask.execute(socket,this);

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
				if (uout != null && uname != null && password != null && uid == -1) {
					uout.write("*login#" + uname + "#" + password + "#\n");
					uout.flush();
				} else if (uout != null && uid != -1) {
					uout.write("*logout#\n");
					uout.flush();
				} else {
					Log.d("PGD", "empty_uout");
				}
			}
		});

		chat_name_list=(ListView)findViewById(R.id.chat_name_list);

		chatDB = new ChatDB(this);

		int RId = R.layout.chat_name_entity;

//        chatName newName = new chatName("TianjieZhong",RId,1);
//
//        chatDB.insert2(newName.getName(),1);
//
//        newName = new chatName("JialongChen",RId,2);
//
//        chatDB.insert2(newName.getName(),2);

		Cursor mCursor = chatDB.select2();

		for(mCursor.moveToFirst();!mCursor.isAfterLast();mCursor.moveToNext())
		{
			String c_name = mCursor.getString(1);
			RId =R.layout.chat_name_entity;
			int uid=mCursor.getInt(2);
			chatName newItem= new chatName(c_name,RId,uid);
			list.add(newItem);
			chat_name_list.setAdapter(new ChatNameAdapter(MainActivity.this, list));
		}



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
		if(socket.isClosed()){
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
					WriterHandler.setPrintWriter(uout);
				} catch (IOException e) {
					Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_SHORT).show();
				}
			}
			recieveTask = new RecieveTask();
			recieveTask.execute(socket,this);
		}
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
	    if(uout!=null&&uid!=-1){
		    uout.write("*discon#\n");
		    uout.flush();
	    }
	    try {
		    uout.close();
		    socket.close();
	    }
	    catch (IOException e){
		    e.printStackTrace();
	    }
	    Log.d(tag, "In the onDestroy() event");
    }

	public class ChatNameAdapter extends BaseAdapter {
		private final String TAG = ChatNameAdapter.class.getSimpleName();

		private ArrayList<chatName> coll;

		private Context ctx;

		public ChatNameAdapter(Context context, ArrayList<chatName> coll) {
			ctx = context;
			this.coll = coll;
		}

		public boolean areAllItemsEnabled() {
			return false;
		}

		public boolean isEnabled(int arg0) {
			return false;
		}

		public int getCount() {
			return coll.size();
		}

		public Object getItem(int position) {
			return coll.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public int getItemViewType(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			Log.v(TAG, "getView>>>>>>>");
			final chatName entity = coll.get(position);
			int itemLayout = entity.getLayoutID();

			LinearLayout layout = new LinearLayout(ctx);
			LayoutInflater vi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			vi.inflate(itemLayout, layout, true);

			TextView tvName = (TextView)layout.findViewById(R.id.chat_name);
			tvName.setText(entity.getName());

			Button Btn_guide=(Button)layout.findViewById(R.id.btn_chat_image);
			Btn_guide.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v){
					Intent intent = new Intent(MainActivity.this,ChatActivity.class);
					Bundle bundle= new Bundle();
					bundle.putString("name",entity.getName());
					bundle.putInt("hisid",entity.getUID());
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});

			return layout;
		}

		public int getViewTypeCount() {
			return coll.size();
		}

		public boolean hasStableIds() {
			return false;
		}

		public boolean isEmpty() {
			return false;
		}

		public void registerDataSetObserver(DataSetObserver observer) {
		}

		public void unregisterDataSetObserver(DataSetObserver observer) {
		}
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
		ChatDB chatDB;

		protected String doInBackground(Object...o){
			socket=(Socket)o[0];
			maina = (Activity)o[1];

			try{
				//PrintWriter uout = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader uin =
						new BufferedReader(
								new InputStreamReader(socket.getInputStream()));
				chatDB=new ChatDB(maina);
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
						btn.setText("Log in");
					}
					else{
						Toast.makeText(getBaseContext(), "Suceessful logged in!", Toast.LENGTH_SHORT).show();
						uid= Integer.parseInt(parts[2]);
						t4T1.setText(uname);
						t4T2.setText(parts[2]);
						t4E1.setVisibility(View.GONE);
						t4E2.setVisibility(View.GONE);
						t4T1.setVisibility(View.VISIBLE);
						t4T2.setVisibility(View.VISIBLE);
						t4L1.setText("User ID\t\t\t\t");
						btn.setText("Log out");
					}
				}
				if(parts[1].equals("logout")){
					Toast.makeText(getBaseContext(), "Suceessful logged out!", Toast.LENGTH_SHORT).show();
					t4E1.setVisibility(View.VISIBLE);
					t4E2.setVisibility(View.VISIBLE);
					t4T1.setVisibility(View.GONE);
					t4T2.setVisibility(View.GONE);
					uid=-1;
					t4L1.setText("Password\t\t\t");
					btn.setText("Log in");
				}
			}
			if(parts[0].equals("*chatf")){
				int fffff = Integer.parseInt(parts[1]);
				if(fffff==1)
					chatDB.insert1("TianjieZhong", "TianjieZhong", "last centry", parts[2]);
				else if(fffff==2)
					chatDB.insert1("JialongChen","JialongChen","next centry",parts[2]);
				//sendBroadcast(new Intent("com.brandeis.zhongzhongzhong.REFRESH"));
				if(ChatActHandler.getOpen()==true){
					ChatActivity aaa =(ChatActivity) ChatActHandler.getActivity();
					/*Intent in= aaa.getIntent();
					aaa.overridePendingTransition(0, 0);
					in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					aaa.finish();
					aaa.overridePendingTransition(0,0);
					startActivity(in);*/
					aaa.fresh();
				}
			}
			if(parts[0].equals("*requestf")){
				String request_id = parts[1];
				String request_name = parts[2];
				String request_destination = parts[3];
				String request_fromdate = parts[4];
				String request_todate = parts[5];
				String request_fir = parts[6];
				String request_sec = parts[7];
				String request_thi = parts[8];
				String request_fou = parts[9];
				String request_notes = parts[10];
				NotificationCompat.Builder notification_request = new NotificationCompat.Builder(maina).setSmallIcon(R.mipmap.ic_launcher)
						.setContentTitle("You have a new request")
						.setContentText("accept or not?");
				Intent resultIntent = new Intent(maina,guide_accept_request.class);
				resultIntent.putExtra("request_id", request_id);
				resultIntent.putExtra("request_name", request_name);
				resultIntent.putExtra("request_destination", request_destination);
				resultIntent.putExtra("request_fromdate", request_fromdate);
				resultIntent.putExtra("request_todate", request_todate);
				resultIntent.putExtra("request_notes", request_notes);

				TaskStackBuilder stackBuilder = TaskStackBuilder.create(maina);
				stackBuilder.addParentStack(guide_accept_request.class);
				stackBuilder.addNextIntent(resultIntent);
				PendingIntent resultPendingIntent =
						stackBuilder.getPendingIntent(
								0,
								PendingIntent.FLAG_UPDATE_CURRENT
						);
				notification_request.setContentIntent(resultPendingIntent);

				NotificationManager notificationmanager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				notificationmanager.notify(0, notification_request.build());
			}
			SendBroadcast(values[0]);
		}

		@Override
		protected void onCancelled(String strings) {
			super.onCancelled(strings);
			Toast.makeText(getBaseContext(), excpt.toString(), Toast.LENGTH_SHORT).show();

		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {
			if(resultCode == Activity.RESULT_OK){
			}
			if (resultCode == Activity.RESULT_CANCELED) {
				//Write your code if there's no result
			}
		}
	}//onActivityResult






}

