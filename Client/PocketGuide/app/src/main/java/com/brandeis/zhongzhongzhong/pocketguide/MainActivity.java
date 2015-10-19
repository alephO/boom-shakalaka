package com.brandeis.zhongzhongzhong.pocketguide;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

public class MainActivity extends Activity {
private TabHost myTabHost;
    private static final String TAG = "Event";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        myTabHost = (TabHost) findViewById(R.id.tabHost);
        myTabHost.setup();
        /*TabHost.TabSpec spec = myTabHost.newTabSpec("Start");
        spec.setIndicator("Start");
        spec.setContent(R.id.Client_tab1);
*/

        myTabHost.addTab(myTabHost.newTabSpec("Start")
                .setIndicator("Start")
                .setContent(R.id.Client_tab1));

        myTabHost.addTab(myTabHost.newTabSpec("Chat")
                .setIndicator("Chat")
                .setContent(R.id.Client_tab2));

        myTabHost.addTab(myTabHost.newTabSpec("Your Trip")
                .setIndicator("Your Trip")
                .setContent(R.id.Client_tab3));

        myTabHost.addTab(myTabHost.newTabSpec("Profile")
                .setIndicator("Profile")
                .setContent(R.id.Client_tab4));

/*


        class MyPagerAdapter extends PagerAdapter {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                TextView view = new TextView(MainActivity.this);
                view.setText("Item "+position);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundColor(Color.argb(255, position * 50, position * 10, position * 50));

                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View)object);
            }

            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return (view == object);
            }
        }

        //ImageView cover_flow = (ImageView) findViewById();
        PagerContainer mContainer = (PagerContainer) findViewById(R.id.pager_container);
        ViewPager pager = mContainer.getViewPager();
        PagerAdapter adapter = new MyPagerAdapter();
        pager.setAdapter(adapter);
        //Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        pager.setOffscreenPageLimit(adapter.getCount());
        //A little space between pages
        pager.setPageMargin(15);

        //If hardware acceleration is enabled, you should also remove
        // clipping on the pager for its children.
        pager.setClipChildren(false);
*/
        Button search_button = (Button) findViewById(R.id.index_search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(Index_Page.this, Choose_Destination.class);
                startActivity(new Intent("com.brandeis.Choose_Destination"));
                //Log.d(TAG, "start a new activity");
            }
        });
    }




}

