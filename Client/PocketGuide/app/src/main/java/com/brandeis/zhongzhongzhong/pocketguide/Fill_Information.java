package com.brandeis.zhongzhongzhong.pocketguide;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.util.Log;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Fill_Information extends AppCompatActivity {
    private static final String TAG = "test";
    //final EditText et = (EditText) findViewById(R.id.fillinformation_note);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_information);

        String location_name = getIntent().getStringExtra("location_name");
        TextView location_title = (TextView) findViewById(R.id.fillinformation_location_title);
        location_title.setText(location_name);
        //Log.d("blabla", location_name);

        final SimpleDateFormat dateFormatter;
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        final EditText fromdate = (EditText) findViewById(R.id.fillinformation_fromdate_editText);
        final EditText todate = (EditText) findViewById(R.id.fillinformation_todate_editText);
        fromdate.setInputType(InputType.TYPE_NULL);
        fromdate.requestFocus();
        todate.setInputType(InputType.TYPE_NULL);

        Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromdate.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH)
        );

        final DatePickerDialog toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                todate.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH)
        );


       /* public boolean onCreateOptionsMenu(Menu menu){
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }*/

        View.OnClickListener fromDateCLK = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        };

        View.OnClickListener toDateCLK = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDatePickerDialog.show();
            }
        };

        fromdate.setOnClickListener(fromDateCLK);
        todate.setOnClickListener(toDateCLK);



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

                /*
                This is where all variables should be encoded and sent to server.

                 */

                Intent intent = new Intent(Fill_Information.this, Confirmation_Page.class);
                startActivity(intent);
            }
        };
        confirmbtn.setOnClickListener(clkconfirmbtn);
    }

    public void onPause(){
        super.onPause();
        Log.d(TAG, "In the onPause() event");
    }
    public void onStop() {
        super.onStop();
        Log.d(TAG, "In the onStop() event");
    }
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "In the onDestroy() event");
    }
}
