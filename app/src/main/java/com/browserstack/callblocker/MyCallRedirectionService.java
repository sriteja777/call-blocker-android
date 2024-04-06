package com.browserstack.callblocker;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.telecom.CallRedirectionService;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import org.lsposed.hiddenapibypass.HiddenApiBypass;

import java.lang.reflect.Method;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.Q)
public class MyCallRedirectionService extends CallRedirectionService {
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onPlaceCall(@NonNull Uri uri, @NonNull PhoneAccountHandle phoneAccountHandle, boolean b) {
        System.out.println("emergency dial....");
        cancelCall();
//        getSystemService()
        com.android.internal.telecom.ITelecomService telephonyService;
        try {
//            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
//            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

//            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)){

//                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                TelecomManager tm = (TelecomManager) getSystemService(Context.TELECOM_SERVICE);
                try {
//                    @SuppressLint("SoonBlockedPrivateApi") Method m = tm.getClass().getDeclaredMethod("getITelephony");
                    List allMethods = HiddenApiBypass.getDeclaredMethods(tm.getClass());

                    for (Object allMethod : allMethods) {
                        System.out.println("Value is : "
                                + allMethod);
                    }


                    Method m = HiddenApiBypass.getDeclaredMethod(tm.getClass(), "getTelecomService" /*, args */);
                    System.out.println("Hii method ");
                    System.out.println(m);
                    m.setAccessible(true);
                    telephonyService = (com.android.internal.telecom.ITelecomService) m.invoke(tm);
//                    System.out.println("number is... " + number);

//                    if ((number != null)) {
                    System.out.println("Ending call...");

                    telephonyService.endCall("com.browserstack.incomingcallblocker");
//                    Toast.makeText(context, "Ending the call from: " + number, Toast.LENGTH_SHORT).show();
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

//                Toast.makeText(context, "Ring " + number, Toast.LENGTH_SHORT).show();

//            }
//            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK)){
//                Toast.makeText(context, "Answered " + number, Toast.LENGTH_SHORT).show();
//            }
//            if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)){
//                Toast.makeText(context, "Idle "+ number, Toast.LENGTH_SHORT).show();
//            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
