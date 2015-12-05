package com.brandeis.zhongzhongzhong.pocketguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class guide_accept_request extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_accept_request);
        String request_name = getIntent().getStringExtra("request_name");
        String request_location = getIntent().getStringExtra("request_location");
        String request_from = getIntent().getStringExtra("request_from");
        String request_to = getIntent().getStringExtra("request_to");
        String request_notes = getIntent().getStringExtra("request_notes");


        TextView request_name_text = (TextView) findViewById(R.id.textView5);
        request_name_text.setText(request_name);

        TextView request_location_text = (TextView) findViewById(R.id.textView9);
        request_location_text.setText(request_location);

        TextView request_from_text = (TextView) findViewById(R.id.textView11);
        request_from_text.setText(request_from);

        TextView request_to_text = (TextView) findViewById(R.id.textView13);
        request_to_text.setText(request_to);

        TextView request_note_text = (TextView) findViewById(R.id.textView15);
        request_note_text.setText(request_notes);

        Button acp_btn = (Button) findViewById(R.id.button);
        Button dec_btn = (Button) findViewById(R.id.button2);

        acp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guide_accept_request, menu);
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
