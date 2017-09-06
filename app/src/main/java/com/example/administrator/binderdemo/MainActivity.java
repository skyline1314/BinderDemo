package com.example.administrator.binderdemo;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static BinderInterface asInterface;
    private Button btn1;
    private Button btn2;
    private String Tag=this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if(!isServiceRunning(this,ProcessService.class.getName())){
            bindService();
        }
    }

    private void bindService() {
        Intent intent = new Intent(getApplicationContext(), ProcessService.class);
        getApplicationContext().bindService(intent, connection, BIND_AUTO_CREATE);
    }


    ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            asInterface = BinderInterface.Stub.asInterface(iBinder);
        }

        @Override

        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private void initView() {
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                try {
                    asInterface.sendMessage("hi,i send a message");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn2:
                try {
                    String message = asInterface.getMessage();
                    Log.e(Tag,message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    /*
     * return true if target service is running
     */
    public static boolean isServiceRunning(Context mContext, String className) {

        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(30);

        if (!(serviceList.size() > 0)) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

}
