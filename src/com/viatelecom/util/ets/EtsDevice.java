package com.viatelecom.util.ets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

import android.os.SystemProperties;
import android.util.Log;

import com.viatelecom.util.Application;
import com.viatelecom.util.serialport.SerialPort;
import com.viatelecom.util.ets.cfg.CfgBase;
import com.viatelecom.util.ets.cfg.CfgCbp7;

public abstract class EtsDevice {

    static protected final CfgBase mCfg = new CfgCbp7();
    
    private SerialPort mSerialPort = null;
    
    protected OutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;


    private class ReadThread extends Thread {
        
        @Override
        public void run() {
            super.run();
            Log.i(Application.APP_TAG, "ets device read thread start");
            
            // read data
            while(!isInterrupted()) {
                int size;
                try {
                    if (mInputStream == null) return;
                    
                    byte[] buffer = new byte[2048];
                    size = mInputStream.read(buffer);
                    if (size > 0) {
                        
                        //Log.v("EtsDevice", "revice " + size + " bytes");
                        onDataReceived(buffer, size);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
            
            Log.i(Application.APP_TAG, "ets device read thread exit");
        }
    }
    
    private String getEtsDevPath(){
        String dev_path = "/dev/ttyUSB"+SystemProperties.get("cbp.ets","1");
        Log.i(Application.APP_TAG, "ets device path:" + dev_path);
        return dev_path;
    }

    private SerialPort getSerialPort() throws SecurityException, IOException, InvalidParameterException {
        if (mSerialPort == null) {
            
            /* Read serial port parameters */
            String path = getEtsDevPath();
            int baudrate = 115200;

            /* Check parameters */
            if ( (path.length() == 0) || (baudrate == -1)) {
                throw new InvalidParameterException();
            }

            /* Open the serial port */
            mSerialPort = new SerialPort(new File(path), baudrate, 0);
        }
        return mSerialPort;
    }

    private void closeSerialPort() {
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }

    protected Boolean create(){
        
        try {
            mSerialPort = getSerialPort();
            
            mOutputStream = mSerialPort.getOutputStream();
            mInputStream = mSerialPort.getInputStream();

            /* Create a receiving thread */
            new ReadThread().start();
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } 
        return true;
    }

    protected abstract void onDataReceived(final byte[] buffer, final int size);

    protected void destroy() {
        if (mReadThread != null)
            mReadThread.interrupt();
        
        closeSerialPort();
        mSerialPort = null;
    }

    /**
     * Write a message to serial port
     * @param msg
     */
    protected void write(EtsMsg msg) {
        try {
            //Log.v("EtsDevice", "write a msg:" + msg.getId());
            byte[] buf = msg.getBuf();
            mOutputStream.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
