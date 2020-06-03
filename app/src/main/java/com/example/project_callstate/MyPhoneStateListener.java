package com.example.project_callstate;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MyPhoneStateListener extends PhoneStateListener {

    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static Date callStartTime;
    private static boolean isIncoming;

    TextView textView;


    public void onCallStateChanged(Context context, int state, String phoneNumber){
        if(lastState == state){
            return;
        }

        System.out.println("Number inside onCallStateChange : "  + phoneNumber);
        switch(state){
            case TelephonyManager.CALL_STATE_RINGING:
                isIncoming = true;
                callStartTime = new Date();

                Toast.makeText(context, "Incoming Call Ringing " , Toast.LENGTH_SHORT).show();
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                if(lastState != TelephonyManager.CALL_STATE_RINGING){
                    isIncoming = false;
                    callStartTime = new Date();
                    Toast.makeText(context, "Outgoing Call Started " +phoneNumber , Toast.LENGTH_SHORT).show();
                }
                break;

            case TelephonyManager.CALL_STATE_IDLE:
                if(lastState == TelephonyManager.CALL_STATE_RINGING){
                    Toast.makeText(context, "Ringing but no pickup"  +phoneNumber+ " Call time " + callStartTime +" Date " + new Date() , Toast.LENGTH_SHORT).show();
                }
                else if(isIncoming){

                    Toast.makeText(context, "Incoming " + phoneNumber+ " Call time " +callStartTime  , Toast.LENGTH_SHORT).show();
                }
                else{

                    Toast.makeText(context, "outgoing " + phoneNumber+ " Call time " + callStartTime +" Date " + new Date() , Toast.LENGTH_SHORT).show();

                }

                break;
        }
        lastState = state;
    }
}