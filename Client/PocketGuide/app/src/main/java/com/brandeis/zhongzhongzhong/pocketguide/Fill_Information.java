package com.brandeis.zhongzhongzhong.pocketguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.util.Log;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class Fill_Information extends AppCompatActivity {
    private static final String TAG = "test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_information);

        String location_name = getIntent().getStringExtra("location_name");
        TextView location_title = (TextView) findViewById(R.id.fillinformation_location_title);
        location_title.setText(location_name);
        //Log.d("blabla", location_name);

        Spinner spinner = (Spinner) findViewById(R.id.fillinformation_location_requirements_dropdown);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(),
                        "OnItemSelectedListener:" + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button confirmbtn = (Button) findViewById(R.id.fillinformation_confirmbtn);

        View.OnClickListener clkconfirmbtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Log.d(TAG,"test");
            }
        };
        confirmbtn.setOnClickListener(clkconfirmbtn);
    }
}
