package com.brandeis.zhongzhongzhong.pocketguide;

import android.app.Activity;

/**
 * Created by jalen on 12/3/15.
 */
public class ChatActHandler {
	private static boolean open;
	private static Activity activity;
	private static int uid;

	public static synchronized boolean getOpen(){
		return open;
	}

	public static synchronized void setOpen(boolean open){
		ChatActHandler.open = open;
	}

	public static synchronized Activity getActivity(){
		return activity;
	}

	public static synchronized void setActivity(Activity activity){
		ChatActHandler.activity = activity;
	}

	public static synchronized int getuid(){
		return uid;
	}

	public static synchronized void setuid(int uid){
		ChatActHandler.uid = uid;
	}
}
