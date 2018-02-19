package com.ritvi.cms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.ritvi.cms.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApplicationSubmittedActivity extends LocalizationActivity {

    @BindView(R.id.tv_complaint_id)
    TextView tv_complaint_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_submitted);
        ButterKnife.bind(this);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            tv_complaint_id.setText("Complaint ID : "+bundle.getString("complaint_id"));
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(ApplicationSubmittedActivity.this,CitizenHomeActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}
