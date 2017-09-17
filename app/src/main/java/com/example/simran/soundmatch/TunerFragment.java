package com.example.simran.soundmatch;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.simran.soundmatch.utils.PermissionUtils;

public class TunerFragment extends Fragment {
    public static final String TAG = TunerFragment.class.getSimpleName();
    public static final int AUDIO_PERMISSION_REQUEST_CODE = 4;
    private Tuner tuner;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.tuner_fragment, parent, false);

        final Button button = (Button)v.findViewById(R.id.stop_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tuner.updateScore();
                tuner.stop();
                button.setVisibility(View.GONE);
            }
        });

        return v;
    }

    @Override
    public void onResume(){
        super.onResume();
        if(tuner != null && tuner.isInitialized()) {
            tuner.start();
        }else if(!PermissionUtils.hasPermission(getActivity(), Manifest.permission.RECORD_AUDIO)){
            PermissionUtils.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, AUDIO_PERMISSION_REQUEST_CODE);
        }else{
            init();
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        if(tuner != null) {
            tuner.stop();
        }
    }


    @Override
    public void onDestroy(){
        if(tuner != null) {
            tuner.stop();
            tuner.release();
        }
        super.onDestroy();
    }

    public void init(){
        if(PermissionUtils.hasPermission(getActivity(), Manifest.permission.RECORD_AUDIO)){
            tuner = new Tuner(getContext());
            tuner.start();
        }
    }

}