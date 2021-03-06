package com.example.antispam;

import java.lang.reflect.Method;
import java.util.Calendar;

import com.example.db.BlackList;
import com.example.db.BlackListDA;
import com.example.db.Call;
import com.example.db.CallDA;
import com.example.db.Sms;
import com.example.db.SmsDA;

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
		        String curTime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
		        //Blocked Call
		        if (phoneNumber != null) {				    
					BlackList objB = blda.getNum(phoneNumber.toString(),0);
					BlackList objW = blda.getNum(phoneNumber.toString(),1);
					if (objB != null && objW == null) {
						if(telephonyService.endCall()){							
							CallDA cda = dbHelper.getCallDA();
							cda.add(new Call(phoneNumber,curTime));
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
								BlackList objB = blda.getNum(smsmesg.getOriginatingAddress(),0);
								BlackList objW = blda.getNum(smsmesg.getOriginatingAddress(),1);
								if (objB != null && objW == null) {
									abortBroadcast();										
									SmsDA smsda = dbHelper.getSmsDA();
									smsda.add(new Sms(strMsgBody,curTime));
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
