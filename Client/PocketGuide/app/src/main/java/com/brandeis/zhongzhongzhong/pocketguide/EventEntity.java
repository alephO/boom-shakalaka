package com.example.chaoliuscomputer.pocketguide;

/**
 * Created by ChaoLiu's Computer on 2015/12/3.
 */
public class EventEntity {
    private String date;
    private String place;
    private int layoutID;

    public String getDate(){
        return date;
    }
    public String getPlace() {
        return place;
    }

    public void setDate(String date){
        this.date=date;
    }

    public void setPlace(String place){
        this.place=place;
    }

    public int getLayoutID() {
        return layoutID;
    }

    public void setLayoutID(int layoutID) {
        this.layoutID = layoutID;
    }

    public EventEntity() {
    }

    public EventEntity(String date, String place, int layoutID) {
        super();
        this.date = date;
        this.place = place;
        this.layoutID = layoutID;
    }

}
