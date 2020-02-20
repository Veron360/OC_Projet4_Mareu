package com.example.oc_projet4_maru.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.oc_projet4_maru.Model.Meeting;
import com.example.oc_projet4_marumaru.R;

import java.util.List;

public class MeetingViewAdapter extends RecyclerView.Adapter <MeetingViewHolder> {

    private final List<Meeting> mMeeting;

    public MeetingViewAdapter(List<Meeting> meeting) {
        mMeeting = meeting;
    }



    @Override
    public MeetingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_meeting, parent, false);
        return new MeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MeetingViewHolder viewHolder, int position) {
        final Meeting meeting = mMeeting.get(position);
        viewHolder.bind(meeting);


    }

    @Override
    public int getItemCount() {
        return mMeeting.size();
    }

}
