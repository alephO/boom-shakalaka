package com.example.chaoliuscomputer.pocketguide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by ChaoLiu's Computer on 2015/12/3.
 */
public class ChatListActivity extends Activity{
    private ChatDB chatDB;

    private static final String TAG = ChatListActivity.class.getSimpleName();

    private ArrayList<chatName> list = new ArrayList<chatName>();   //

    private ListView chat_name_list;


    public void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate >>>>>>");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting_list);

        chat_name_list=(ListView)findViewById(R.id.chat_name_list);

        chatDB = new ChatDB(this);

        int RId = R.layout.chat_name_entity;

        chatName newName = new chatName("TianjieZhong",RId);
        list.add(newName);
        chatDB.insert2(newName.getName());

        newName = new chatName("JialongChen",RId);
        list.add(newName);
        chatDB.insert2(newName.getName());

        Cursor mCursor = chatDB.select2();

        for(mCursor.moveToFirst();!mCursor.isAfterLast();mCursor.moveToNext())
        {
            String c_name = mCursor.getString(1);
            RId =R.layout.chat_name_entity;
            chatName newItem= new chatName(c_name,RId);
            list.add(newItem);
            chat_name_list.setAdapter(new ChatNameAdapter(ChatListActivity.this, list));
        }

    }

    public class ChatNameAdapter extends BaseAdapter {
        private final String TAG = ChatNameAdapter.class.getSimpleName();

        private ArrayList<chatName> coll;

        private Context ctx;

        public ChatNameAdapter(Context context, ArrayList<chatName> coll) {
            ctx = context;
            this.coll = coll;
        }

        public boolean areAllItemsEnabled() {
            return false;
        }

        public boolean isEnabled(int arg0) {
            return false;
        }

        public int getCount() {
            return coll.size();
        }

        public Object getItem(int position) {
            return coll.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public int getItemViewType(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            Log.v(TAG, "getView>>>>>>>");
            final chatName entity = coll.get(position);
            int itemLayout = entity.getLayoutID();

            LinearLayout layout = new LinearLayout(ctx);
            LayoutInflater vi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi.inflate(itemLayout, layout, true);

            TextView tvName = (TextView)layout.findViewById(R.id.chat_name);
            tvName.setText(entity.getName());

            Button Btn_guide=(Button)layout.findViewById(R.id.btn_chat_image);
            Btn_guide.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(ChatListActivity.this,ChatActivity.class);
                    Bundle bundle= new Bundle();
                    bundle.putString("name",entity.getName());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            return layout;
        }

        public int getViewTypeCount() {
            return coll.size();
        }

        public boolean hasStableIds() {
            return false;
        }

        public boolean isEmpty() {
            return false;
        }

        public void registerDataSetObserver(DataSetObserver observer) {
        }

        public void unregisterDataSetObserver(DataSetObserver observer) {
        }
    }

}
