package com.brandeis.zhongzhongzhong.pocketguide;

/**
 * Created by ChaoLiu's Computer on 2015/12/3.
 */
public class chatName {
    String name;
    private int layoutID;
    private int uid;

    public String getName() {
        return name;
    }

    public int getUID(){
        return uid;
    }

    public void setUid(int uid){
        this.uid=uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLayoutID() {
        return layoutID;
    }

    public void setLayoutID(int layoutID) {
        this.layoutID = layoutID;
    }

    public chatName(String name,int layoutID, int uid) {
        super();
        this.name = name;
        this.layoutID = layoutID;
        this.uid=uid;
    }
}
