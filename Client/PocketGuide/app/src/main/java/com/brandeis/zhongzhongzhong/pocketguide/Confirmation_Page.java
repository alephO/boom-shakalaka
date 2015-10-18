package com.brandeis.zhongzhongzhong.pocketguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class Confirmation_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);
        Button donebtn = (Button) findViewById(R.id.confirmation_donebtn);
        View.OnClickListener clkdonebtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Confirmation_Page.this, MainActivity.class);
                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        };
        donebtn.setOnClickListener(clkdonebtn);
        }

}
