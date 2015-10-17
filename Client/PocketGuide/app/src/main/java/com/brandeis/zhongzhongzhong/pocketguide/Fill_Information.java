package com.brandeis.zhongzhongzhong.pocketguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.util.Log;

public class Fill_Information extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_information);

        String location_name = getIntent().getStringExtra("location_name");
        TextView location_title = (TextView) findViewById(R.id.fillinformation_location_title);
        location_title.setText(location_name);
        //Log.d("blabla", location_name);
    }
}
