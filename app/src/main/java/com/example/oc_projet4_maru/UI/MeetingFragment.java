package com.example.oc_projet4_maru.UI;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oc_projet4_maru.DI.Injection;
import com.example.oc_projet4_maru.DI.RefreshDialogList;
import com.example.oc_projet4_maru.Event.DeleteMeeting;
import com.example.oc_projet4_maru.Model.Meeting;
import com.example.oc_projet4_maru.Service.ApiService;
import com.example.oc_projet4_maru.UI.Adapter.MeetingViewAdapter;
import com.example.oc_projet4_marumaru.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingFragment extends Fragment {

    @BindView(R.id.List_Meeting_RecycleurView)
    public RecyclerView mRecyclerView;
    private ApiService mApiService;
    private List<Meeting> mMeetings;
    private String dateFilter;
    private String roomFilter;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mApiService = Injection.getMeetingApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_meeting_list, container, false);
        ButterKnife.bind(this, view);
        configureRecyclerView();

        return view;
    }

    private void configureRecyclerView(){
        mRecyclerView.setAdapter(new MeetingViewAdapter(mMeetings));
        MeetingViewAdapter adapter = new MeetingViewAdapter(mMeetings);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));



    }

    @Override
    public void onResume() {
        super.onResume();
        initList();


    }



    private void initList(){

        if (dateFilter != null && roomFilter != null) {

            mMeetings = mApiService.getMeetingsListFilter(dateFilter, roomFilter);
        }else {
            mMeetings = mApiService.getMeetings();
        }

        mRecyclerView.setAdapter(new MeetingViewAdapter(mMeetings));
        Log.d("TOTO", "initlistFragment" + mMeetings.size());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }



    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onDeleteNeighbour(DeleteMeeting event) {
        mApiService.deleteMeeting(event.meeting);
        initList();

    }

    @Subscribe
    public void onRefreshFilterList(RefreshDialogList event) {

        dateFilter = event.getDate();
        roomFilter = event.getRoom();
        initList();
    }

}