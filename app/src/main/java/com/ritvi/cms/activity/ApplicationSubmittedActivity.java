package com.ritvi.cms.activity;

import android.content.Intent;
import android.os.Bundle;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.ritvi.cms.R;

public class ApplicationSubmittedActivity extends LocalizationActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_submitted);

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(ApplicationSubmittedActivity.this,CitizenHomeActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}
