package com.brandeis.zhongzhongzhong.pocketguide;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.app.Activity;

public class MainActivity extends Activity {
    private TabHost myTabHost;
    private TabHost bookTabHost;

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





}

