package com.brandeis.zhongzhongzhong.pocketguide;

import android.content.BroadcastReceiver;
import android.database.Cursor;
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

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;

public class ChatActivity extends Activity {
    private static final String TAG = ChatActivity.class.getSimpleName();;

    ChatDB chatDB;

    ListView talkView;

    Button messageButton;

    EditText messageText;

    ChatMsgViewAdapter a;

    PrintWriter uout;


    // private ChatMsgViewAdapter myAdapter;

    private ArrayList<ChatMsgEntity> list = new ArrayList<ChatMsgEntity>();


    public void fresh(){
        this.onCreate(null);
    }

    public void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate >>>>>>");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_content);


        talkView = (ListView) findViewById(R.id.list);
        messageButton = (Button) findViewById(R.id.MessageButton);
        messageText = (EditText) findViewById(R.id.MessageText);


        chatDB = new ChatDB(this);
        Cursor mCursor = chatDB.select();
        uout=WriterHandler.getPrintWriter();

        for(mCursor.moveToFirst();!mCursor.isAfterLast();mCursor.moveToNext())
        {
            String c_name = mCursor.getString(1);
            String c_date = mCursor.getString(2);
            String c_text = mCursor.getString(3);
            int RId ;
            if(c_name.equals("ChaoLiu")){
                RId= R.layout.list_he_item;
            }
            else{
                RId=R.layout.list_me_item;
            }
            ChatMsgEntity newItem= new ChatMsgEntity(c_name,c_date,c_text,RId);
            list.add(newItem);
        }
        //list.add(new ChatMsgEntity("ChaoLiu","dwqd","QDWD",R.layout.list_me_item));
        a = new ChatMsgViewAdapter(ChatActivity.this, list);
        talkView.setAdapter(a);
        Log.d("FFFFFF", Thread.currentThread().getName());

        OnClickListener messageButtonListener = new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Log.v(TAG, "onclick >>>>>>>>");
                //String name = getName();
                String date = getDate();
                String msgText = getText();
                int RId = R.layout.list_he_item;
                int RId2 = R.layout.list_me_item;
                Log.d("FFF", Thread.currentThread().getName());
                ChatMsgEntity newMessage = new ChatMsgEntity("ChaoLiu", date, msgText,RId);
                //list.add(newMessage);
                chatDB.insert(newMessage.getName(), newMessage.getDate(), newMessage.getText());
                uout.print("*chatt#aed#dea#\n");
                uout.flush();
                //ChatMsgEntity newMessage2 = new ChatMsgEntity("TianjieZhong",date, msgText,RId2);
                //list.add(newMessage2);
                // list.add(d0);
                //chatDB.insert(newMessage2.getName(), newMessage2.getDate(), newMessage2.getText());

                //talkView.setAdapter(new ChatMsgViewAdapter(ChatActivity.this, list));
                messageText.setText("");
                onCreate(null);
                //a.notifyDataSetChanged();
                // myAdapter.notifyDataSetChanged();
            }

        };
        messageButton.setOnClickListener(messageButtonListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ChatActHandler.setOpen(true);
        ChatActHandler.setActivity(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        ChatActHandler.setOpen(false);
        ChatActHandler.setActivity(null);
    }

    // should be redefine in the future
    private String getName() {
        return "ChaoLiu";
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