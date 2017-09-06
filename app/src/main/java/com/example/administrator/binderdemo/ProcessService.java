package com.example.administrator.binderdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2017/9/6.
 */
public class ProcessService extends Service {

    private String message="";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return (IBinder) mBinder;
    }


    BinderInterface mBinder= new BinderInterface.Stub(){

        @Override
        public void sendMessage(String obj) throws RemoteException {
                message=obj;
        }

        @Override
        public String getMessage() throws RemoteException {
            return message;
        }
    };

}
