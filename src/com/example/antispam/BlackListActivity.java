package com.example.antispam;

import java.util.List;
import java.util.Map;
import com.example.db.BlackList;
import com.example.db.BlackListDA;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.PhoneNumberUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
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
	public static final int PICK_CONTACT = 1;

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
			}
		});
	}

	private void refresh() {
		List<Map> list = da.getAllByMap(0);
		/*
		 * for (BlackList call : list) { String str = "<b>" + call.getNumber() +
		 * "</b>"; arr.add(Html.fromHtml(str).toString()); }
		 */
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
					EditText txt = (EditText) dialog
							.findViewById(R.id.txtAddNum);
					da.add(new BlackList(txt.getText().toString()));
					dialog.dismiss();
					refresh();
				}
			});
			dialog.show();
			return true;
		case R.id.mnuAddNumContact:
			Intent intent = new Intent(Intent.ACTION_PICK,
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
			startActivityForResult(intent, PICK_CONTACT);
			return true;
		case R.id.mnuAddNumLog:
			getCallLog();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void getCallLog() {
		String[] strFields = { android.provider.CallLog.Calls._ID,
                android.provider.CallLog.Calls.NUMBER,
                android.provider.CallLog.Calls.CACHED_NAME, };
        String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
        final Cursor cursorCall = getContentResolver().query(
                android.provider.CallLog.Calls.CONTENT_URI, strFields,
                null, null, strOrder);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Select from Call Log");
        android.content.DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int item) {
                cursorCall.moveToPosition(item);
                da.add(new BlackList(cursorCall.getString(cursorCall.getColumnIndex(android.provider.CallLog.Calls.NUMBER))));
				refresh();
                cursorCall.close();
                return;
            }
        };
        dialog.setCursor(cursorCall, listener, android.provider.CallLog.Calls.NUMBER);
        dialog.create().show();
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case (PICK_CONTACT): {
			if (resultCode == Activity.RESULT_OK) {				
				Uri contentUri = data.getData();
				String contactId = contentUri.getLastPathSegment();
				Cursor cursor = getContentResolver().query(Phone.CONTENT_URI,
						null, Phone._ID + "=?",	new String[] { contactId }, null);// < - Note, not CONTACT_ID!
				startManagingCursor(cursor);
				Boolean numbersExist = cursor.moveToFirst();
				int phoneNumberColumnIndex = cursor.getColumnIndex(Phone.NUMBER);
				String phoneNumber = "";
				while (numbersExist) {
					phoneNumber = cursor.getString(phoneNumberColumnIndex);
					phoneNumber = phoneNumber.trim();
					numbersExist = cursor.moveToNext();
				}
				stopManagingCursor(cursor);
				if (!phoneNumber.equals("")) {
					da.add(new BlackList(PhoneNumberUtils.stripSeparators(phoneNumber)));
					refresh();
				} // phoneNumber != ""
			}
			break;
		}
		}
	}
}
