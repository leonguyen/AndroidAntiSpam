package com.example.antispam;

import java.util.ArrayList;
import java.util.List;

import com.example.db.Sms;
import com.example.db.SmsDA;

import android.app.ListActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SmsLogActivity extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ArrayList<String> arr = new ArrayList<String>();
		
		DBHelper dbHelper = new DBHelper(this);
		SmsDA da = dbHelper.getSmsDA();
		List<Sms> list = da.getAll();
		for (Sms sms : list) {
			String str = sms.getSms() + " - " + sms.getTime(); 
			arr.add(Html.fromHtml(str).toString());
		}
		
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_smslog, R.id.txtSms, arr));
 
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
	}
}
