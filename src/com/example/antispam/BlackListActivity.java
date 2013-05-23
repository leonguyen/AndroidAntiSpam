package com.example.antispam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.db.BlackList;
import com.example.db.BlackListDA;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BlackListActivity extends ListActivity {
	Context context = null;
	DBHelper dbHelper = null;
	BlackListDA da = null;
	BlackList blSel = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		dbHelper = new DBHelper(this);
		da = dbHelper.getBlackListDA();
		refresh();
		ListView listview = getListView();
		registerForContextMenu(listview);
		listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				TextView txtId = (TextView) view.findViewById(R.id.txtBlackId);
				int id = Integer.parseInt(txtId.getText().toString());
				blSel = da.get(id);
				return false;
		}});
	}
	
	private void refresh(){
		List<Map> list = da.getAllByMap();
		/*for (BlackList call : list) {
			String str = "<b>" + call.getNumber() + "</b>";
			arr.add(Html.fromHtml(str).toString());
		}*/
		setListAdapter(new BlackListAdapter(this, R.layout.list_blacklist, list));
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
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onContextItemSelected(item);
		switch (item.getItemId()) {
        case R.id.mnu_del:
        	da.delete(blSel);
        	refresh();
            return true;
        default:
            return super.onContextItemSelected(item);
    }
	}
}
