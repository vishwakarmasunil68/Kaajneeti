package com.ritvi.cms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ritvi.cms.R;
import com.ritvi.cms.Util.TagUtils;
import com.ritvi.cms.Util.ToastClass;
import com.ritvi.cms.webservice.WebServiceBase;
import com.ritvi.cms.webservice.WebServicesCallBack;
import com.ritvi.cms.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginWithOTPActivity extends AppCompatActivity implements WebServicesCallBack{
    private static final String CALL_LOGIN_OTP = "call_login_otp";
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.et_login_otp)
    EditText et_login_otp;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_otp);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_login_otp.getText().toString().length()!=10){
                    ToastClass.showShortToast(getApplicationContext(),"Please Enter Valid Mobile Number");
                }else{
                    callLoginAPI();
                }
            }
        });

    }

    public void callLoginAPI(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action","LOGIN_MOBILE"));
        nameValuePairs.add(new BasicNameValuePair("mobile","+91"+et_login_otp.getText().toString()));
        new WebServiceBase(nameValuePairs,this,this,CALL_LOGIN_OTP,true).execute(WebServicesUrls.LOGIN_URL);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGetMsg(String apicall, String response) {
        Log.d(TagUtils.getTag(),apicall+":-"+response);
        switch (apicall){
            case CALL_LOGIN_OTP:
                parseLoginResponse(response);
                break;
        }
    }

    public void parseLoginResponse(String response){
        try{
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString("status").equals("success")){
                startActivity(new Intent(LoginWithOTPActivity.this,LoginOtpConfirmActivity.class).putExtra("mobile_number","+91"+et_login_otp.getText().toString()));
            }
            ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("message"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
