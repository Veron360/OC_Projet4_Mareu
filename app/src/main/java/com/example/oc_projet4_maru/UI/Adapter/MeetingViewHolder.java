package com.example.oc_projet4_maru.UI.Adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oc_projet4_maru.Event.DeleteMeeting;
import com.example.oc_projet4_maru.Model.Meeting;
import com.example.oc_projet4_marumaru.R;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.Image_Fragment)
    ImageView imageRoom;

    @BindView(R.id.Salle_reunion)
    public TextView mSalleReunion;

    @BindView(R.id.Heure_Fragment)
    public TextView mHoraire;

    @BindView(R.id.date_Fragment)
    public TextView mDate;

    @BindView(R.id.Nom_Fragment)
    public TextView mNom;

    @BindView(R.id.Mail_Fragment)
    public TextView mMail;

    @BindView(R.id.DeleteButton)
    public ImageButton mDeleteButton;




    public MeetingViewHolder(@NonNull View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void bind(final Meeting meeting){

        mNom.setText(meeting.getName());
        mHoraire.setText(meeting.getTime());
        mDate.setText(meeting.getDate());
        mSalleReunion.setText(meeting.getPlace().getRoom());
        mMail.setText(StringUtils.join(meeting.getMails(), ", "));
        imageRoom.setImageResource(meeting.getPlace().getImageRoom());


        mDeleteButton.setOnClickListener(v -> EventBus.getDefault().post(new DeleteMeeting(meeting)));
    }

}