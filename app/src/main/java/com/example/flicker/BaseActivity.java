package com.example.flicker;

import android.util.Log;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    static  final String FLICKR_QUERY="FLICKR_QUERY";
    static final String PHOTO_TRANSFER="PHOTO_TRANSFER";

    void activateToolbar(boolean enableHome){
        Log.d(TAG, "activateToolbar: starts");
        ActionBar actionBar=getSupportActionBar();
        if(actionBar !=null){

            Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);

        }
    }



}
