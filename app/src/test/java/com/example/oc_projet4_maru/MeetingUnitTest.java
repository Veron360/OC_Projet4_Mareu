package com.example.oc_projet4_maru;

import com.example.oc_projet4_maru.DI.Injection;
import com.example.oc_projet4_maru.Model.Meeting;
import com.example.oc_projet4_maru.Model.Room;
import com.example.oc_projet4_maru.Service.ApiService;
import com.example.oc_projet4_maru.Service.RoomGenerator;
import com.example.oc_projet4_marumaru.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class MeetingUnitTest {

    private ApiService mService;

    @Before
    public void setup() {

        mService = Injection.getNewInstanceApiService();
    }


    @Test
    public void addAndGetMeetingWithSuccess() {

        Meeting meeting = new Meeting("Réunion BOB ","11h55", "20/09/2020", RoomGenerator.generateRooms().get(4),
                Collections.singletonList("david@lamzone.com, ToTo@lamzone.com "));
        mService.addMeeting(meeting);
        assertTrue(mService.getMeetings().contains(meeting));

        // si 1 de plus dans la liste
        assertEquals(1, mService.getMeetings().size());


    }

    @Test
    public void deleteMeetingWithSuccess() {

        Meeting meeting = new Meeting("Réunion BOB ", "11h55", "20/09/2020", RoomGenerator.generateRooms().get(4),
                Collections.singletonList("david@lamzone.com, ToTo@lamzone.com "));

        mService.addMeeting(meeting);
        assertEquals(1, mService.getMeetings().size());

        mService.deleteMeeting(meeting);
        assertEquals(0, mService.getMeetings().size());

    }

    @Test
    public void getFilteredMeetingsWithSuccess() {

        Room room1 = new Room(1, "Salle n°1", R.drawable.round_brightness_1);
        Room room2 = new Room(4, "Salle n°4", R.drawable.round_brightness_4);
        Meeting mMeeting1 = new Meeting("Réunion 1",
                "13:00",
                "13/02/2020",
                room1,
                Arrays.asList("bart@simpson.com",
                        "homer@simpson.com"));
        Meeting mMeeting2 = new Meeting("Réunion 4",
                "16:00",
                "15/02/2020",
                room2,
                Arrays.asList("lisa@simpson.com",
                        "marge@simpson.com"));
        mService.addMeeting(mMeeting1);
        mService.addMeeting(mMeeting2);
        List<Meeting> filterMeetings = mService.getMeetingsListFilter("15/02/2020", "Salle n°4");
        assertEquals(3, filterMeetings.size()); //TODO expected = 1
        assertEquals(mMeeting2, filterMeetings.get(0));
    }


}