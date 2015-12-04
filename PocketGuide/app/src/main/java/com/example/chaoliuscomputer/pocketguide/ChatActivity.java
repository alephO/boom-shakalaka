package com.example.chaoliuscomputer.pocketguide;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    private static final String TAG = ChatActivity.class.getSimpleName();

    private ChatDB chatDB;

    private ListView talkView;

    private Button messageButton;

    private EditText messageText;

    private ArrayList<ChatMsgEntity> list = new ArrayList<ChatMsgEntity>();

    public void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate >>>>>>");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        /////////////////////receive the chat name message

        Bundle bundle = this.getIntent().getExtras();
        final String pass_name=bundle.getString("name");
        //////////////////////

        Button button1=(Button)findViewById(R.id.btnService);
        button1.setOnClickListener(button_listener1);

        Button button2=(Button)findViewById(R.id.btnProfile);
        button2.setOnClickListener(button_listener2);

        Button button3=(Button)findViewById(R.id.btnChat);
        button3.setOnClickListener(button_listener3);

        talkView = (ListView) findViewById(R.id.list);
        messageButton = (Button) findViewById(R.id.MessageButton);
        messageText = (EditText) findViewById(R.id.MessageText);

        chatDB = new ChatDB(this);
        SQLiteDatabase db = chatDB.getReadableDatabase();

        chatDB.CreateTable(db,pass_name);
        Cursor mCursor = chatDB.select1(pass_name);

        for(mCursor.moveToFirst();!mCursor.isAfterLast();mCursor.moveToNext())
        {
            String c_name = mCursor.getString(1);
            String c_date = mCursor.getString(2);
            String c_text = mCursor.getString(3);
            int RId ;
            if(c_name.equals("Me")){
                RId= R.layout.list_he_item;
            }
            else{
                RId=R.layout.list_me_item;
            }
            ChatMsgEntity newItem= new ChatMsgEntity(c_name,c_date,c_text,RId);
            list.add(newItem);
            talkView.setAdapter(new ChatMsgViewAdapter(ChatActivity.this, list));
        }

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

                ChatMsgEntity newMessage = new ChatMsgEntity("Me", date, msgText,RId);
                list.add(newMessage);
                chatDB.insert1(pass_name,newMessage.getName(), newMessage.getDate(), newMessage.getText());

                ChatMsgEntity newMessage2 = new ChatMsgEntity(pass_name,date, msgText,RId2);
                list.add(newMessage2);
                chatDB.insert1(pass_name,newMessage2.getName(), newMessage2.getDate(), newMessage2.getText());

                talkView.setAdapter(new ChatMsgViewAdapter(ChatActivity.this, list));
                messageText.setText("");
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

    private Button.OnClickListener button_listener3 = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(ChatActivity.this, ChatListActivity.class);
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

    private String getText() {
        return messageText.getText().toString();
    }

    public void onDestroy() {
        Log.v(TAG, "onDestroy>>>>>>");
        // list = null;
        super.onDestroy();
    }
}