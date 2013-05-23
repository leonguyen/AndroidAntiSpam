package com.example.antispam;

import java.util.ArrayList;
import java.util.List;

import com.example.db.Call;
import com.example.db.CallDA;

import android.app.ListActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CallLogActivity extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ArrayList<String> arr = new ArrayList<String>();
		
		DBHelper dbHelper = new DBHelper(this);
		CallDA da = dbHelper.getCallDA();
		List<Call> list = da.getAll();
		for (Call call : list) {
			String str = call.getNumber() + " - " + call.getTime(); 
			arr.add(Html.fromHtml(str).toString());
		}
		
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_calllog, R.id.txtCall, arr));
 
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
	}
}
