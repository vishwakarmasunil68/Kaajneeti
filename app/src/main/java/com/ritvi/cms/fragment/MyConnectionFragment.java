package com.ritvi.cms.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ritvi.cms.R;

import butterknife.ButterKnife;

/**
 * Created by sunil on 27-02-2018.
 */

public class MyConnectionFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_my_connection,container,false);
        ButterKnife.bind(this,view);
        return view;
    }
}
