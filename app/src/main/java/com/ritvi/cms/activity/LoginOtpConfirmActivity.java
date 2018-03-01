package com.ritvi.cms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ritvi.cms.R;
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

public class LoginOtpConfirmActivity extends AppCompatActivity implements WebServicesCallBack {
    private static final String CALL_LOGIN_OTP_VERIFY = "call_login_otp_verify";
    private static final String CALL_RESEND_OTP = "call_resend_otp";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_otp)
    EditText et_otp;
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.pb_otp)
    ProgressBar pb_otp;
    @BindView(R.id.tv_resend_otp)
    TextView tv_resend_otp;

    String mobile_number = "";
    boolean is_timer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp_confirm);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mobile_number = bundle.getString("mobile_number");
        }

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_otp.getText().toString().length() != 6) {
                    ToastClass.showShortToast(getApplicationContext(), "Please Enter Valid OTP");
                } else {
                    callOTPAPI();
//                    startActivity(new Intent(LoginOtpConfirmActivity.this, CitizenHomeActivity.class));
                }
            }
        });


        startTimer();

        tv_resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_timer) {
//                    ToastClass.showShortToast(getApplicationContext(),"");
                } else {
                    callResendOtpAPI();
                }
            }
        });

    }

    public void startTimer() {
        new CountDownTimer(60000, 100) {

            @Override
            public void onTick(long l) {
                Log.d(TagUtils.getTag(), "time:-" + l);
                pb_otp.setProgress((int) l);
                is_timer = true;
            }

            @Override
            public void onFinish() {
                is_timer = false;
            }
        }.start();
    }

    public void callResendOtpAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "REGENERATE_MOBILE_OTP"));
        nameValuePairs.add(new BasicNameValuePair("mobile", mobile_number));
        new WebServiceBase(nameValuePairs, this, this, CALL_RESEND_OTP, true).execute(WebServicesUrls.LOGIN_URL);
    }

    public void callOTPAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "VALIDATE_MOBILE_OTP"));
        nameValuePairs.add(new BasicNameValuePair("mobile", mobile_number));
        nameValuePairs.add(new BasicNameValuePair("otp", et_otp.getText().toString()));
        new WebServiceBase(nameValuePairs, this, this, CALL_LOGIN_OTP_VERIFY, true).execute(WebServicesUrls.LOGIN_URL);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetMsg(String apicall, String response) {
        Log.d(TagUtils.getTag(), apicall + ":-" + response);
        switch (apicall) {
            case CALL_LOGIN_OTP_VERIFY:
                parseLoginOTPResponse(response);
                break;
            case CALL_RESEND_OTP:
                parseResendOTP(response);
                break;
        }
    }


    public void parseResendOTP(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("status").equals("success")) {
                startTimer();
            }
            ToastClass.showShortToast(getApplicationContext(), jsonObject.optString("message"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseLoginOTPResponse(String response) {
        try{
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString("status").equals("success")){
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
                    startActivity(new Intent(getApplicationContext(), CitizenHomeActivity.class));
                    finishAffinity();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
