package com.example.oc_projet4_maru.UI.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.oc_projet4_maru.DI.Injection;
import com.example.oc_projet4_maru.DI.RefreshDialogList;
import com.example.oc_projet4_maru.Model.Meeting;
import com.example.oc_projet4_maru.Service.ApiService;
import com.example.oc_projet4_maru.UI.MeetingFragment;
import com.example.oc_projet4_marumaru.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListMeetingActivity extends AppCompatActivity {

    private Spinner mDialogRoomsSpinner;
    private Spinner mDialogDatesSpinner;
    private String filteredDate;
    private String filteredRoom;
    private ApiService mApiService ;


    @BindView(R.id.fab)
    public FloatingActionButton mButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApiService = Injection.getNewInstanceApiService();
        configureAndShowMainFragment();
        Log.d("TOTO", "onCreat List");
        ButterKnife.bind(this);




        // New activity
        mButton.setOnClickListener(view -> {
            Log.d("TOTO", "click détail activity");
            Intent DetailMeeting = new Intent(ListMeetingActivity.this, DetailMeetingActivity.class);
            startActivity(DetailMeeting);

        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.dialog) {
            //Viewgroup de l'activité actuel
            ViewGroup viewGroup = findViewById(android.R.id.content);
            //xlm du filtre
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_filtre, viewGroup, false);
            //AlertDialog
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this)
                    .setPositiveButton("ok", null)
                    .setNegativeButton("Cancel", null);
            dialogBuilder.setView(dialogView);
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();

            // spinner date
            mDialogDatesSpinner = alertDialog.findViewById(R.id.date_spinner_dialog);
            configureDateSpinner();

            // spinner rooms
            mDialogRoomsSpinner = alertDialog.findViewById(R.id.room_spinner_dialog);
            configureRoomSpinner();




            Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new RefreshDialogList(filteredDate, filteredRoom));
                    alertDialog.dismiss();
                    Log.d("TOTO", "Clique bouton confirme dialog");
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }



    public ApiService getApiService() {
        return mApiService;
    }


    /**
     * Date spinner
     */
    private void configureDateSpinner() {

        List<String> MeetingDate = getParserMeetingsDates(mApiService.getMeetings());


        //Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, MeetingDate);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        mDialogDatesSpinner.setAdapter(adapter);
        mDialogDatesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                filteredDate = MeetingDate.get(pos);

            }
            public void onNothingSelected(AdapterView<?> parent)
            {
                /*This automatic method may be used so that you can set which item will be selected given that the previous item is no
                 *longer available. This is instead of letting the spinner automatically select the next item in the list.
                 */
            }


        });
    }


    /**
     * Room Spinner
     */

    private void configureRoomSpinner() {

        List<String> RoomsName = getParserMeetingsRooms(mApiService.getMeetings());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, RoomsName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        mDialogRoomsSpinner.setAdapter(adapter);
        mDialogRoomsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                filteredRoom = RoomsName.get(pos);

            }
            public void onNothingSelected(AdapterView<?> parent)
            {
                /*This automatic method may be used so that you can set which item will be selected given that the previous item is no
                 *longer available. This is instead of letting the spinner automatically select the next item in the list.
                 */
            }
        });
    }


    /**
     * Parser Date
     */
    private static List<String> getParserMeetingsDates(List<Meeting> meetings) {

        List<String> plannedMeetingDatesList = new ArrayList<>();
        for (Meeting meeting : meetings) {
            plannedMeetingDatesList.add(meeting.getDate());
        }
        //HashSet pour garder qu'une seule occurence et on re-transfrome HashSet en List
        List<String> listDistinct = new ArrayList<>(new HashSet<>(plannedMeetingDatesList));
        listDistinct.add(0, "Toutes Dates");
        return listDistinct;
    }

    /**
     * Parser Room
     */

    private static List<String> getParserMeetingsRooms(List<Meeting> meetings){

        List<String> RoomsList = new ArrayList<>();
        for (Meeting meeting : meetings) {
            RoomsList.add(meeting.getPlace().getRoom());
        }
        //HashSet pour garder qu'une seule occurence et on re-transfrome HashSet en List
        List<String> listDistinct = new ArrayList<>(new HashSet<>(RoomsList));
        listDistinct.add(0, "Toutes Salles");
        return listDistinct;
    }



    private void configureAndShowMainFragment() {
        MeetingFragment meetingFragment = (MeetingFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        if (meetingFragment == null) {
            meetingFragment = new MeetingFragment();
            // C - Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, meetingFragment)
                    .commit();
        }

    }
}