package com.example.oc_projet4_maru.Service;

import com.example.oc_projet4_maru.Model.Meeting;
import com.example.oc_projet4_maru.Model.Room;

import java.util.List;

public interface ApiService {

    List<Meeting> getMeetings();

    void deleteMeeting(Meeting meeting);

    void addMeeting(Meeting meeting);

    List<Room> getSalles();

    List<Meeting>  getMeetingsListFilter(String date, String Name);
}
