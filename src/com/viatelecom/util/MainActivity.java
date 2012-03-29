package com.viatelecom.util;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    
    private Button btn_eab;
    private Application  app;
    
    android.view.View.OnClickListener listerner_btn_eab_click = new android.view.View.OnClickListener(){

        @Override
        public void onClick(View v) {
            Log.v(Application.APP_TAG, "EAB Button clicked");
            launchEABActivity();
        }
        
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(Application.APP_TAG, "Main Activity OnCreate");
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        app = (Application)getApplication();
        btn_eab = (Button)findViewById(R.id.btnEAB);
        btn_eab.setOnClickListener(listerner_btn_eab_click);
        
        //app.isServiceRunning(EABService.class.getName());
    }
    
    @Override
    protected void onDestroy() {
        Log.v(Application.APP_TAG, "Main Activity OnDestroy");
        super.onDestroy();
        
    }
    
    @Override  
    public void onBackPressed() {  
        Log.v(Application.APP_TAG, "Main Activity OnBackPressed");
        super.onBackPressed();  
          
    }
    
    private void launchEABActivity () {    
        Intent intent = new Intent(this, EABActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}