//package com.example.chaoliuscomputer.pocketguide;
//
//import android.content.Context;
//import android.database.DataSetObserver;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import android.widget.BaseAdapter;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
///**
// * Created by ChaoLiu's Computer on 2015/12/3.
// */
//public class EventViewAdapter extends BaseAdapter {
//    private static final String TAG = EventViewAdapter.class.getSimpleName();
//
//    private ArrayList<EventEntity> coll;
//
//    private Context ctx;
//
//    public EventViewAdapter(Context context, ArrayList<EventEntity> coll) {
//        ctx = context;
//        this.coll = coll;
//    }
//
//    public boolean areAllItemsEnabled() {
//        return false;
//    }
//
//    public boolean isEnabled(int arg0) {
//        return false;
//    }
//
//    public int getCount() {
//        return coll.size();
//    }
//
//    public Object getItem(int position) {
//        return coll.get(position);
//    }
//
//    public long getItemId(int position) {
//        return position;
//    }
//
//    public int getItemViewType(int position) {
//        return position;
//    }
//
//    public View getView(int position, View convertView, ViewGroup parent) {
//        Log.v(TAG, "getView>>>>>>>");
//        com.example.chaoliuscomputer.pocketguide.EventEntity entity = coll.get(position);
//        int itemLayout = entity.getLayoutID();
//
//        LinearLayout layout = new LinearLayout(ctx);
//        LayoutInflater vi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        vi.inflate(itemLayout, layout, true);
//
//        TextView tvDate = (TextView) layout.findViewById(R.id.date);
//        tvDate.setText(entity.getDate());
//
//        TextView tvPlace = (TextView) layout.findViewById(R.id.place);
//        tvPlace.setText(entity.getPlace());
//
//        return layout;
//    }
//
//    public int getViewTypeCount() {
//        return coll.size();
//    }
//
//    public boolean hasStableIds() {
//        return false;
//    }
//
//    public boolean isEmpty() {
//        return false;
//    }
//
//    public void registerDataSetObserver(DataSetObserver observer) {
//    }
//
//    public void unregisterDataSetObserver(DataSetObserver observer) {
//    }
//
//}
