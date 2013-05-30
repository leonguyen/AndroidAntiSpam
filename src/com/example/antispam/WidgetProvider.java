package com.example.antispam;

import com.example.db.CallDA;
import com.example.db.SmsDA;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider {

	private static final String ACTION_CLICK = "ACTION_CLICK";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		// Get all ids
		ComponentName thisWidget = new ComponentName(context,
				WidgetProvider.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		for (int widgetId : allWidgetIds) {
			// Create some random data
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
					R.layout.widget);
			// Set the text
			DBHelper dbHelper = new DBHelper(context);
			SmsDA da = dbHelper.getSmsDA();
			CallDA cda = dbHelper.getCallDA();
			remoteViews.setTextViewText(R.id.txtSpam, String.valueOf("Spam:\n" + da.getCount() + " sms \n" +  cda.getCount() + " call"));

			// Register an onClickListener
			Intent intent = new Intent(context, WidgetProvider.class);

			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
					0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.txtSpam, pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
	}
}
