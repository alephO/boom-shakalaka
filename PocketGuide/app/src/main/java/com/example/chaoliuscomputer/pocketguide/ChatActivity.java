package com.example.chaoliuscomputer.pocketguide;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.content.Intent;
import java.util.ArrayList;
import java.util.Calendar;

public class ChatActivity extends Activity {
    private static final String TAG = ChatActivity.class.getSimpleName();;

    private ListView talkView;

    private Button messageButton;

    private EditText messageText;

    // private ChatMsgViewAdapter myAdapter;

    private ArrayList<ChatMsgEntity> list = new ArrayList<ChatMsgEntity>();

    public void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate >>>>>>");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button button1=(Button)findViewById(R.id.btnService);
        button1.setOnClickListener(button_listener1);

        Button button2=(Button)findViewById(R.id.btnProfile);
        button2.setOnClickListener(button_listener2);

        talkView = (ListView) findViewById(R.id.list);
        messageButton = (Button) findViewById(R.id.MessageButton);
        messageText = (EditText) findViewById(R.id.MessageText);
        OnClickListener messageButtonListener = new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Log.v(TAG, "onclick >>>>>>>>");
                String name = getName();
                String date = getDate();
                String msgText = getText();
                int RId = R.layout.list_he_item;
                int RId2 = R.layout.list_me_item;

                ChatMsgEntity newMessage = new ChatMsgEntity("Chao Liu", date, msgText, RId);
                list.add(newMessage);
                ChatMsgEntity newMessage2 = new ChatMsgEntity("Tianjie Zhong", date, msgText, RId2);
                list.add(newMessage2);
                // list.add(d0);
                talkView.setAdapter(new ChatMsgViewAdapter(ChatActivity.this, list));
                messageText.setText("");
                // myAdapter.notifyDataSetChanged();
            }

        };
        messageButton.setOnClickListener(messageButtonListener);
    }

    private Button.OnClickListener button_listener1 = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(ChatActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };

    private Button.OnClickListener button_listener2 = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(ChatActivity.this, activity_profile.class);
            startActivity(intent);
        }
    };

    // should be redefine in the future
    private String getName() {
        return getResources().getString(R.string.myDisplayName);
    }

    // should be redefine in the future
    private String getDate() {
        Calendar c = Calendar.getInstance();
        String date = String.valueOf(c.get(Calendar.YEAR)) + "-"
                + String.valueOf(c.get(Calendar.MONTH)+1) + "-"
                + String.valueOf(c.get(Calendar.DAY_OF_MONTH)) + "-"
                + String.valueOf(c.get(Calendar.HOUR_OF_DAY)) + "-"
                + String.valueOf(c.get(Calendar.MINUTE)) + "-"
                + String.valueOf(c.get(Calendar.SECOND));
        return date;
    }

    // should be redefine in the future
    private String getText() {
        return messageText.getText().toString();
    }

    public void onDestroy() {
        Log.v(TAG, "onDestroy>>>>>>");
        // list = null;
        super.onDestroy();
    }
}