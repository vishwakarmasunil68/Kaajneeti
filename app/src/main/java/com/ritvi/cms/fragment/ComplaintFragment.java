package com.ritvi.cms.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.ritvi.cms.R;
import com.ritvi.cms.Util.Pref;
import com.ritvi.cms.Util.TagUtils;
import com.ritvi.cms.activity.LeaderHomeActivity;
import com.ritvi.cms.adapter.ComplaintAdapter;
import com.ritvi.cms.pojo.complaint.AllVoterComplaintPOJO;
import com.ritvi.cms.pojo.complaint.VoterComplaintPOJO;
import com.ritvi.cms.webservice.WebServiceBase;
import com.ritvi.cms.webservice.WebServicesCallBack;
import com.ritvi.cms.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 07-02-2018.
 */

public class ComplaintFragment extends Fragment implements WebServicesCallBack{

    private static final String CALL_COMPLAINT_API = "call_complaint_api";
    @BindView(R.id.rv_complaint)
    RecyclerView rv_complaint;
    @BindView(R.id.pb_complaint)
    ProgressBar pb_complaint;
    ComplaintAdapter complaintAdapter;
    List<VoterComplaintPOJO> voterComplaintPOJOS=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_complaint,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachAdapter();
//        callComplaintAPI();
    }

    public void callComplaintAPI(){
        pb_complaint.setVisibility(View.VISIBLE);
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action","ALL_COMPLAINTS"));
        nameValuePairs.add(new BasicNameValuePair("c_profile_id", Pref.GetUserProfile(getActivity().getApplicationContext()).getUserId()));
        new WebServiceBase(nameValuePairs,getActivity(),this,CALL_COMPLAINT_API,false).execute(WebServicesUrls.USER_ADMIN_PROCESS);
    }

    public void callLeaderComplaintAPI(){
        if(getActivity() instanceof LeaderHomeActivity) {
            LeaderHomeActivity leaderHomeActivity= (LeaderHomeActivity) getActivity();
            pb_complaint.setVisibility(View.VISIBLE);
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("request_action", "ALL_COMPLAINTS"));
            nameValuePairs.add(new BasicNameValuePair("l_profile_id", leaderHomeActivity.leaderProfile.getUpUserProfileId()));
            new WebServiceBase(nameValuePairs, getActivity(), this, CALL_COMPLAINT_API, false).execute(WebServicesUrls.ADMIN_PROCESS);
        }
    }

    @Override
    public void onGetMsg(String apicall, String response) {
        Log.d(TagUtils.getTag(),apicall+":-"+response);
        switch (apicall){
            case CALL_COMPLAINT_API:
                    parseComplaintResponse(response);
                break;
        }
    }

    public void parseComplaintResponse(String response){
        pb_complaint.setVisibility(View.GONE);
        voterComplaintPOJOS.clear();
        try{
            Gson gson=new Gson();
            AllVoterComplaintPOJO allVoterComplaintPOJO=gson.fromJson(response,AllVoterComplaintPOJO.class);
            if(allVoterComplaintPOJO.getStatus().equals("success")){
                voterComplaintPOJOS.addAll(allVoterComplaintPOJO.getVoterComplaintPOJOS());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        complaintAdapter.notifyDataSetChanged();
    }


    public void attachAdapter() {
        complaintAdapter = new ComplaintAdapter(getActivity(), this, voterComplaintPOJOS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_complaint.setHasFixedSize(true);
        rv_complaint.setAdapter(complaintAdapter);
        rv_complaint.setLayoutManager(linearLayoutManager);
        rv_complaint.setItemAnimator(new DefaultItemAnimator());

    }
}
