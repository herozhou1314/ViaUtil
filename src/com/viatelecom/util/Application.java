package com.viatelecom.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.util.Log;

public class Application extends android.app.Application {

    public static final String APP_TAG = "VIA Util";  
    
    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(APP_TAG, "Application onCreate");
    
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.v(APP_TAG, "Application onTerminate");
        
    }
    
    public boolean isServiceRunning(String serviceClassName) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClassName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
