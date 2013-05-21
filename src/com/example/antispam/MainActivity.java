package com.example.antispam;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	Context context = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		DBHelper dbHelper = new DBHelper(this);
		CallDA cda = dbHelper.getCallDA();
		cda.deleteAll();
		
		Button btnBlackList = (Button) findViewById(R.id.btnBlackList);
		btnBlackList.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stubS
				Intent intent = new Intent(context, BlackListActivity.class);
				startActivity(intent);
			}
		});
		Button btnBlockCall = (Button) findViewById(R.id.btnBlockCall);
		btnBlockCall.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stubS
				Intent intent = new Intent(context, CallLogActivity.class);
				startActivity(intent);
			}
		});
		Button btnBlockSms = (Button) findViewById(R.id.btnBlockSms);
		btnBlockSms.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stubS
				Intent intent = new Intent(context, SmsLogActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
