package com.example.oc_projet4_maru.UI.Controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.oc_projet4_maru.DI.Injection;
import com.example.oc_projet4_maru.Model.Meeting;
import com.example.oc_projet4_maru.Model.Room;
import com.example.oc_projet4_maru.Service.ApiService;
import com.example.oc_projet4_maru.UI.Adapter.SpinnerAdapter;
import com.example.oc_projet4_marumaru.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailMeetingActivity extends AppCompatActivity {


    private Room mRoom;
    private ApiService mApiService;


    @BindView(R.id.EditTextSujet)
    public EditText EditTextSujet;

    @BindView(R.id.button_hour)
    public Button buttonHour;

    @BindView(R.id.hour)
    public TextView timeMeeting;

    @BindView(R.id.button_date)
    public Button buttonDate;

    @BindView(R.id.date)
    public TextView dateMeeting;

    @BindView(R.id.EditTextSalle)
    public Spinner spinner;

    @BindView(R.id.EditTextMail)
    public EditText EditTextMail;

    @BindView(R.id.Button_add_chip)
    public Button mButtonChip;

    @BindView(R.id.List_chip)
    ChipGroup mListChip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_meeting);
        ButterKnife.bind(this);


        mApiService = Injection.getMeetingApiService();
        List<Room> rooms = mApiService.getSalles();


        /**
         * Spinner
         */
        SpinnerAdapter spinner1 = new SpinnerAdapter(this, rooms);
        spinner.setAdapter(spinner1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mRoom = (Room) adapterView.getItemAtPosition(i);
                mRoom = (Room) spinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        /**
         *  Bouton ajouter Heure Réunion
         */
        buttonHour.setOnClickListener(view -> pickerActivity());


        /**
         *  Bouton ajouter Date Réunion
         */
        buttonDate.setOnClickListener(view -> pickerActivityDate());
        
    }

    /**
     * ChipTag
     */

    @OnClick(R.id.Button_add_chip)
    public void displayChipTag(){
        String chipTag = EditTextMail.getText().toString();
        if (isValidmail(chipTag)) {
            LayoutInflater inflater = LayoutInflater.from(DetailMeetingActivity.this);
            Chip chip = (Chip) inflater.inflate(R.layout.chip_user, null, false);
            chip.setText(chipTag);
            mListChip.addView(chip);
            EditTextMail.getText().clear();
            chip.setOnCloseIconClickListener(v -> mListChip.removeView(v));
        }else {
            Toast.makeText(this, getString(R.string.mail_invalid), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * vérifie les champs saisis e-mail
     */
    private boolean isValidmail(String mail) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return mail.matches(regex);
    }



    /**
     * ToolBar
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }


    /**
     * Bouton Sauvegarder
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Saved) {
            if (EditTextSujet.getText().toString().isEmpty() ||
                    timeMeeting.getText().toString().isEmpty() ||
                    dateMeeting.getText().toString().isEmpty() ||
                    generateList().size() == 0) {
                Toast.makeText(this, getString(R.string.erreur_detail), Toast.LENGTH_SHORT).show();
            } else {

                List<String> EmailsList = generateList();
                Meeting meeting = new Meeting(
                        EditTextSujet.getText().toString(),
                        timeMeeting.getText().toString(),
                        dateMeeting.getText().toString(),
                        mRoom,
                        EmailsList);
                mApiService.addMeeting(meeting);
                Log.d("TOTO", "Clique disquette détail");
                finish();
                return true;
            }
        }
        return false;


}


    /**
     * Picker pour horaire
     */

    private void pickerActivity(){

        final Calendar calendar1 = Calendar.getInstance();

        int HOUR = calendar1.get(Calendar.HOUR_OF_DAY);
        int MINUTE = calendar1.get(Calendar.MINUTE);

        boolean is24hourFormat = DateFormat.is24HourFormat(this);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (timePicker, hour, minute) -> {

            Calendar calendar11 = Calendar.getInstance();
            calendar11.set(Calendar.HOUR,hour);
            calendar11.set(Calendar.MINUTE,minute);

            CharSequence timeTexte = "- " + DateFormat.format("hh:mm a", calendar11) + " -";

            timeMeeting.setText(timeTexte);

        },HOUR,MINUTE,is24hourFormat);

        timePickerDialog.show();

    }


    /**
     * Picker pour date
     */

    private void pickerActivityDate(){

        final Calendar calendar2 = Calendar.getInstance();

        int Year = calendar2.get(Calendar.YEAR);
        int Month = calendar2.get(Calendar.MONTH);
        int Day = calendar2.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, year, month, dayOfMonth) -> {

            String dateString = dayOfMonth + "/" + (month+1) + "/" + year;
            dateMeeting.setText(dateString);

        },Year,Month,Day);

        datePickerDialog.show();
    }

    /**
     * List pour ajout chipTag
     */
    private List<String> generateList(){
        List<String> ListMail = new ArrayList<>();
        for (int i=0; i < mListChip.getChildCount(); i++){
            Chip chip = (Chip) mListChip.getChildAt(i);
            ListMail.add(chip.getText().toString());
        }
        return ListMail;
    }

}