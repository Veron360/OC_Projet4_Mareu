package com.example.oc_projet4_maru.DI;

public class RefreshDialogList {

    private final String date;
    private final String room;

    public RefreshDialogList(String date, String room){
        this.date = date;
        this.room = room;
    }

    public String getDate() {
        return date;
    }

    public String getRoom() {
        return room;
    }

}


