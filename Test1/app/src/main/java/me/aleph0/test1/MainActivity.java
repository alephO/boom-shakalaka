package me.aleph0.test1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.*;
import java.net.Socket;

public class MainActivity extends Activity {

	static final String hostname="boom.aleph0.me";
	static final int portnum = 6179;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        new InitConnectTask().execute();
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
	class InitConnectTask extends AsyncTask<Void, Void, Socket> {
        Exception excpt;
        String r;
        protected Socket doInBackground(Void...v){
            Socket echoSocket=null;
            try {
                echoSocket = new Socket(hostname, portnum);
                PrintWriter uout = new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader uin =
                        new BufferedReader(
                                new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in));
                uout.write("inital\n");
                uout.flush();
                r = uin.readLine();

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
            Toast.makeText(getBaseContext(), r+"from"+socket.getLocalPort()+"to"+socket.getLocalPort()+socket.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled(Socket socket) {
            super.onCancelled(socket);
            Toast.makeText(getBaseContext(), excpt.toString(), Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
