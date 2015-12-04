package com.example.chaoliuscomputer.pocketguide;

/**
 * Created by ChaoLiu's Computer on 2015/12/3.
 */
public class chatName {
    String name;
    private int layoutID;

    public String getName() {
        return name;
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

    public chatName(String name,int layoutID) {
        super();
        this.name = name;
        this.layoutID = layoutID;
    }
}
