package com.example.project_callstate;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Output;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.WINDOW_SERVICE;
import static android.content.Intent.getIntent;
import static androidx.core.content.ContextCompat.getCodeCacheDir;
import static androidx.core.content.ContextCompat.getSystemService;

public class CallReceiver extends BroadcastReceiver {
    TextView textView;
    String p1="+919429704118";
    String p2="+919427960325";
    String p3="+919898002907";
    String p4="+919878472827";

    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        MyPhoneStateListener customPhoneListener = new MyPhoneStateListener();

        telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        Bundle bundle = intent.getExtras();
        String phone_number = bundle.getString("incoming_number");



        String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);

        int state = 0;
        if(stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)){
            state = TelephonyManager.CALL_STATE_IDLE;
        }
        else if(stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
            state = TelephonyManager.CALL_STATE_OFFHOOK;
        }
        else if(stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)){
            state = TelephonyManager.CALL_STATE_RINGING;
        }
        if (phone_number == null || "".equals(phone_number)) {
            return;
        }
        customPhoneListener.onCallStateChanged(context, state, phone_number);
        Toast.makeText(context, "This is " +phone_number , Toast.LENGTH_SHORT).show();

        
        // satuti calling defined is wrong , just call another activity and show name, number , call states .
        if(phone_number.equals(p1)|| phone_number.equals(p2) || phone_number.equals(p3) || phone_number.equals(p4)){

            Toast.makeText(context,"Match"+phone_number,Toast.LENGTH_LONG).show();
            // onButtonShowPopupWindowClick();


            Intent intent1 = ((Activity) context).getIntent();
            View view= (View) intent1.getCharSequenceExtra("view");
            showPopupWindow(view);

        }
        else{

            Toast.makeText(context,"notmatch"+phone_number,Toast.LENGTH_LONG).show();
        }

    }
    public void showPopupWindow(final View view) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.display, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        boolean focusable = true;


        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.CYAN));
        //popupWindow.setFocusable(true);
        View container = (View) popupWindow.getContentView().getParent();
        WindowManager wm = (WindowManager) view.getContext().getSystemService(WINDOW_SERVICE);
        //WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        // add flag
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.8f;
        wm.updateViewLayout(container, p);


    }
}
