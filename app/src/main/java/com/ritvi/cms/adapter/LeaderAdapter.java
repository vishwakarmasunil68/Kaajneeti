package com.ritvi.cms.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ritvi.cms.R;
import com.ritvi.cms.Util.Pref;
import com.ritvi.cms.Util.TagUtils;
import com.ritvi.cms.Util.ToastClass;
import com.ritvi.cms.activity.AllLeaderActivity;
import com.ritvi.cms.pojo.leader.LeaderPOJO;
import com.ritvi.cms.webservice.AdapterWebService;
import com.ritvi.cms.webservice.MsgPassInterface;
import com.ritvi.cms.webservice.WebServicesCallBack;
import com.ritvi.cms.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 03-11-2017.
 */

public class LeaderAdapter extends RecyclerView.Adapter<LeaderAdapter.ViewHolder> implements WebServicesCallBack {
    private List<LeaderPOJO> items;
    Activity activity;
    Fragment fragment;
    int device_height = 0;

    public LeaderAdapter(Activity activity, Fragment fragment, List<LeaderPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_leaders, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_leader_name.setText(items.get(position).getUser_fullname());
        holder.tv_leader_email.setText("email");


        if(activity instanceof AllLeaderActivity) {
            if (items.get(position).getMy_favourite().equals("1")) {
                holder.iv_favorite.setImageResource(R.drawable.ic_favorite);
            } else {
                holder.iv_favorite.setImageResource(R.drawable.ic_unfavorite);
            }

            holder.iv_favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callFavoriteAPI(items.get(position), holder.iv_favorite);
                }
            });
        }else{
            holder.iv_favorite.setVisibility(View.GONE);
        }

        holder.itemView.setTag(items.get(position));
    }

    public void callFavoriteAPI(LeaderPOJO leaderPOJO, final ImageView favorite_image) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action","SET_AS_FAVOURITE"));
        nameValuePairs.add(new BasicNameValuePair("user_id", Pref.GetUserProfile(activity.getApplicationContext()).getUserId()));
        nameValuePairs.add(new BasicNameValuePair("admin_id",leaderPOJO.getUser_id()));

        new AdapterWebService(activity, nameValuePairs, false, new MsgPassInterface() {
            @Override
            public void onMsgPassed(String response) {
                Log.d(TagUtils.getTag(),"api called:-"+response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.optString("status").equals("success")){
                        ToastClass.showShortToast(activity.getApplicationContext(),jsonObject.optString("message"));
                        if(jsonObject.optString("favourite").equals("0")){
                            favorite_image.setImageResource(R.drawable.ic_unfavorite);
                        }else{
                            favorite_image.setImageResource(R.drawable.ic_favorite);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).executeApi(WebServicesUrls.USER_ADMIN_PROCESS);
    }


    private final String TAG = getClass().getSimpleName();

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onGetMsg(String apicall, String response) {

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView cv_image;
        public ImageView iv_favorite;
        public TextView tv_leader_email;
        public TextView tv_leader_name;

        public ViewHolder(View itemView) {
            super(itemView);
            cv_image = (CircleImageView) itemView.findViewById(R.id.cv_image);
            iv_favorite = (ImageView) itemView.findViewById(R.id.iv_favorite);
            tv_leader_email = (TextView) itemView.findViewById(R.id.tv_leader_email);
            tv_leader_name = (TextView) itemView.findViewById(R.id.tv_leader_name);
        }
    }
}
