package com.ritvi.cms.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.google.gson.Gson;
import com.ritvi.cms.R;
import com.ritvi.cms.Util.Constants;
import com.ritvi.cms.Util.Pref;
import com.ritvi.cms.Util.StringUtils;
import com.ritvi.cms.Util.TagUtils;
import com.ritvi.cms.Util.ToastClass;
import com.ritvi.cms.pojo.user.UserProfilePOJO;
import com.ritvi.cms.webservice.WebServiceBase;
import com.ritvi.cms.webservice.WebServicesCallBack;
import com.ritvi.cms.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MpinActivity extends LocalizationActivity implements WebServicesCallBack{


    @BindView(R.id.btn_accept)
    Button btn_accept;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.et_mpin)
    EditText et_mpin;
    @BindView(R.id.et_confirm_mpin)
    EditText et_confirm_mpin;

    String mobile_number = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpin);
        ButterKnife.bind(this);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            mobile_number=bundle.getString("mobile_number");
        }

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_mpin.getText().toString().length() == 0 || et_confirm_mpin.getText().toString().length() == 0) {
                    ToastClass.showShortToast(getApplicationContext(), "Please Enter Mpin");
                } else {
                    if (et_confirm_mpin.getText().toString().equals(et_mpin.getText().toString())) {
//                        startActivity(new Intent(MpinActivity.this, CitizenHomeActivity.class));
                        callSetMpin();
                    } else {
                        ToastClass.showShortToast(getApplicationContext(), "Please Enter same MPIN");
                    }
                }
            }
        });

        et_confirm_mpin.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView tv, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btn_accept.callOnClick();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_confirm_mpin.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void callSetMpin() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "SET_MPIN"));
        nameValuePairs.add(new BasicNameValuePair("mobile", mobile_number));
        nameValuePairs.add(new BasicNameValuePair("mpin", et_confirm_mpin.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("mpin_confirm", et_confirm_mpin.getText().toString()));
        new WebServiceBase(nameValuePairs, this, this, Constants.CALL_MPIN_SET, true).execute(WebServicesUrls.REGISTER_URL);
    }

    @Override
    public void onGetMsg(String apicall, String response) {
        TagUtils.printResponse(apicall, response);
        if(apicall.equals(Constants.CALL_MPIN_SET)){
            parseMpinResponse(response);
        }
    }

    public void parseMpinResponse(String response) {
        try{
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString(Constants.API_STATUS).equals(Constants.API_SUCCESS)){
                String user_profile=jsonObject.optJSONObject("user_detail").optJSONObject("user_profile").toString();
                Gson gson=new Gson();
                UserProfilePOJO userProfilePOJO=gson.fromJson(user_profile,UserProfilePOJO.class);
                Pref.SaveUserProfile(getApplicationContext(),userProfilePOJO);
                Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN,true);
                if(userProfilePOJO.getFirstname().equals("")||userProfilePOJO.getMiddlename().equals("")||
                        userProfilePOJO.getLastname().equals("")||userProfilePOJO.getFullname().equals("")||
                        userProfilePOJO.getGender().equals("0")||userProfilePOJO.getDateOfBirth().equals("0000-00-00")||
                        userProfilePOJO.getState().equals("")){
                    Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_PROFILE_COMPLETED,false);
                    startActivity(new Intent(getApplicationContext(), ProfileInfoActivity.class));
                    finishAffinity();

                }else{
                    Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_PROFILE_COMPLETED,true);
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finishAffinity();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
