package com.brandeis.zhongzhongzhong.pocketguide;

import android.app.Activity;
import android.app.DatePickerDialog;

import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.util.Log;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;
import android.content.Intent;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class Fill_Information extends Activity {
    private static final String TAG = "test";
    //final EditText et = (EditText) findViewById(R.id.fillinformation_note);
    public String[] requirements;
    static String fromfromdate;
    static String totodate;
    static String notenote;
    PrintWriter uout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_information);

        final String location_name = getIntent().getStringExtra("location_name");
        TextView location_title = (TextView) findViewById(R.id.fillinformation_location_title);
        location_title.setText(location_name);
        //Log.d("blabla", location_name);
        uout = WriterHandler.getPrintWriter();

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
                fromfromdate = dateFormatter.format(newDate.getTime());
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH)
        );

        final DatePickerDialog toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                todate.setText(dateFormatter.format(newDate.getTime()));
                totodate = dateFormatter.format(newDate.getTime());
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


/*
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
*/
        /* requirement sort */
        requirements = getResources().getStringArray(R.array.requirements_array);

        ArrayList<String> requirementsList = new ArrayList<String>();
        for (int i = 0; i < requirements.length; ++i) {
            requirementsList.add(requirements[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this, R.layout.text_view, requirementsList);
        DynamicListView listView = (DynamicListView) findViewById(R.id.listview);

        listView.setCheeseList(requirementsList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        Button confirmbtn = (Button) findViewById(R.id.fillinformation_confirmbtn);


        EditText notes = (EditText) findViewById(R.id.fillinformation_note);
        notenote = notes.getText().toString();


        View.OnClickListener clkconfirmbtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                This is where all variables should be encoded and sent to server.

                 */
                Log.d("Destination", location_name);
                Log.d("fromdate",fromfromdate);
                Log.d("todate",totodate);
                Log.d("first choice", adapter.getItem(0));
                Log.d("second choice", adapter.getItem(1));
                Log.d("third choice", adapter.getItem(2));
                Log.d("fourth choice", adapter.getItem(3));
                Log.d("notes", notenote);

                uout.print("*request#" + location_name + "#" + fromfromdate + "#" + totodate + "#" + adapter.getItem(0) + "#" + adapter.getItem(1)
                        + "#" + adapter.getItem(2) + "#" + adapter.getItem(3) + "#" + notenote + "#\n");
                Log.d("PGD", "*request#" + location_name + "#" + fromfromdate + "#" + totodate + "#" + adapter.getItem(0) + "#" + adapter.getItem(1)
                        + "#" + adapter.getItem(2) + "#" + adapter.getItem(3) + "#" + notenote + "#\n");
                Log.d("PGD",uout.toString());
                uout.flush();
                try {
                    Thread.sleep(2000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }


                Intent intent = new Intent(Fill_Information.this, Confirmation_Page.class);
                startActivity(intent);
                finish();
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
