package com.viatelecom.util;

import java.util.ArrayList;

import com.viatelecom.util.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EABActivity extends Activity {
    
    private ListView lv_log;
    private ArrayAdapter<String> lv_adapter;
    private ArrayList<String> list_data = new ArrayList<String>();
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(Application.APP_TAG, "EAB Activity OnCreate");
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eab);
        
        lv_adapter = new ArrayAdapter<String>(this,R.layout.loglistitem, list_data);
        lv_log = (ListView)findViewById(R.id.lvLog);
        lv_log.setAdapter(lv_adapter); 
    }
    
    @Override
    protected void onDestroy() {
        Log.v(Application.APP_TAG, "EAB Activity OnDestroy");
        super.onDestroy();
        
    }
    
    @Override  
    public void onBackPressed() {  
        Log.v(Application.APP_TAG, "EAB Activity OnBackPressed");
        super.onBackPressed();  
          
    }
}
