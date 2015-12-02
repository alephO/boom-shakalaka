package me.aleph0.test1;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.*;
import java.net.Socket;

public class MainActivity extends Activity {

	String hostname="45.55.56.17";
	int portnum = 6179;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
			Socket echoSocket = new Socket(hostname, portnum);
			PrintWriter uout = new PrintWriter(echoSocket.getOutputStream(), true);
			BufferedReader uin =
					new BufferedReader(
							new InputStreamReader(echoSocket.getInputStream()));
			BufferedReader stdIn =
					new BufferedReader(
							new InputStreamReader(System.in));
			uout.write("inital\n");
			uout.flush();
			String recieve = uin.readLine();
			Toast.makeText(getBaseContext(), recieve, Toast.LENGTH_SHORT).show();
		}
		catch (Exception e){
			Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_SHORT).show();
			Log.d("Fuck",e.toString());
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

		return super.onOptionsItemSelected(item);
	}
}
