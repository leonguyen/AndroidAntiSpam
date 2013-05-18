package com.example.antispam;

import java.util.ArrayList;
import java.util.List;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class BlackListActivity extends ListActivity {
	Context context = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		refresh();
	}
	
	private void refresh(){
		ArrayList<String> arr = new ArrayList<String>();
		DBHelper dbHelper = new DBHelper(this);
		BlackListDA da = dbHelper.getBlackListDA();
		List<BlackList> list = da.getAll();
		for (BlackList call : list) {
			String str = "<b>" + call.getNumber() + "</b>";
			arr.add(Html.fromHtml(str).toString());
		}
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_blacklist,
				R.id.txtBlackList, arr));
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.blacklist, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.mnuAddNum:
			// Custom dialog
			final Dialog dialog = new Dialog(this);
			dialog.setContentView(R.layout.addnum_dialog);
			dialog.setTitle("Add Number");
			Button dialogButton = (Button) dialog.findViewById(R.id.btnOK);
			// if button is clicked, close the custom dialog
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					DBHelper dbHelper = new DBHelper(context);
					BlackListDA blda = dbHelper.getBlackListDA();
					EditText txt = (EditText) dialog.findViewById(R.id.txtAddNum);
					blda.add(new BlackList(txt.getText().toString()));
					dialog.dismiss();
					refresh();
				}
			});
			dialog.show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
