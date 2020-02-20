package com.example.oc_projet4_maru.Model;

import java.util.List;
import java.util.Objects;

public class Meeting {

    private static int count = 0;

    private int id;
    private String name;
    private String time;
    private String date;
    private Room place;
    private List<String> mails;



    public Meeting(String name, String time, String date, Room place, List<String> mails) {
        this.name = name;
        this.time = time;
        this.date = date;
        this.place = place;
        this.mails = mails;
        id = count++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Room getPlace() {
        return place;
    }

    public void setPlace(Room place) {
        this.place = place;
    }

    public List<String> getMails() {
        return mails;
    }

    public void setMails(List<String> mails) {
        this.mails = mails;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return id == meeting.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "name=" + name +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", place='" + place + '\'' +
                ", mails=" + mails +
                '}';
    }


}