package com.example.oc_projet4_maru.Service;

import com.example.oc_projet4_maru.Model.Meeting;
import com.example.oc_projet4_maru.Model.Room;

import java.util.ArrayList;
import java.util.List;

public class MeetingApiService implements ApiService {

    private final List<Meeting> meetings = new ArrayList<>();
    private final List<Room> mRooms = RoomGenerator.generateRooms();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    @Override
    public void addMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    @Override
    public List<Room> getSalles() {
        return mRooms;
    }

    @Override
    public List<Meeting> getMeetingsListFilter(String date, String roomName) {
        List<Meeting> filteredMeetings = new ArrayList<>();

        for (Meeting meeting : meetings) {
            if (roomName.equals("Salles") || meeting.getPlace().getRoom().equals(roomName)){
                filteredMeetings.add(meeting);
            }
            if (date.equals("Dates") || meeting.getDate().equals(date)) {
                filteredMeetings.add(meeting);
            }

            if (roomName.equals("Salles") || meeting.getPlace().getRoom().equals(roomName)) {
                if (date.equals("Dates") || meeting.getDate().equals(date)) {
                    filteredMeetings.add(meeting);
                }
            }
        }
        return filteredMeetings;
    }

}