package com.example.antispam;

import java.lang.reflect.Method;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

public class BlockReceiver extends BroadcastReceiver {
	Context pContext = null;
	DBHelper dbHelper = null;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		try {
			pContext = context;
			// TELEPHONY MANAGER class object to register one listner
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			try {
				Class c = Class.forName(tm.getClass().getName());
				Method m = c.getDeclaredMethod("getITelephony");
				m.setAccessible(true);
				com.android.internal.telephony.ITelephony telephonyService = (com.android.internal.telephony.ITelephony) m.invoke(tm);
				Bundle bundle = intent.getExtras();
		        String phoneNumber = bundle.getString("incoming_number");
		        dbHelper = new DBHelper(pContext);
		        BlackListDA blda = dbHelper.getBlackListDA();
		        //Blocked Call
		        if (phoneNumber != null) {				    
					BlackList obj = blda.getNum(phoneNumber.toString());
					if (obj != null) {
						if(telephonyService.endCall()){
							CallDA cda = dbHelper.getCallDA();
							cda.add(new Call(phoneNumber));
						}
					}
				}
				//Blocked Sms
				try{
					if(bundle!=null){
						Object[] pdus = (Object[]) bundle.get("pdus");
						if(pdus!=null){
							for(int k=0; k<pdus.length; k++){
								SmsMessage smsmesg = SmsMessage.createFromPdu((byte[]) pdus[k]);
								String strMsgBody = smsmesg.getMessageBody().toString();
								if(strMsgBody.contains("test")){}
								BlackList obj = blda.getNum(smsmesg.getOriginatingAddress());
								if (obj != null) {
									abortBroadcast();										
									SmsDA smsda = dbHelper.getSmsDA();
									smsda.add(new Sms(strMsgBody));
								}
							}
						}
					}
				} catch (Exception e){e.printStackTrace();}
				
			} catch (Exception e) {e.printStackTrace();}
		} catch (Exception e) {
			Log.e("Phone Receive Error", " " + e);
		}
	}

	public class CallListener extends PhoneStateListener {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			super.onCallStateChanged(state, incomingNumber);
			if (state == TelephonyManager.CALL_STATE_RINGING) {
			}
		}
	}
}
