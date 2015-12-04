package com.brandeis.zhongzhongzhong.pocketguide;

import java.io.PrintWriter;

/**
 * Created by jalen on 12/3/15.
 */
public class WriterHandler {
	private static PrintWriter printWriter;

	public static synchronized PrintWriter getPrintWriter(){
		return printWriter;
	}

	public static synchronized void setPrintWriter(PrintWriter printWriter){
		WriterHandler.printWriter = printWriter;
	}
}
