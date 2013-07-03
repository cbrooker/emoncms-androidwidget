package ca.chrisbrooker.android.widget;


import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.text.format.Time;
import android.widget.RemoteViews;

public class HelloWidget extends AppWidgetProvider {
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new MyTime(context, appWidgetManager), 1, 30000);
	}
	
	
	private class MyTime extends TimerTask {
		RemoteViews remoteViews;
		AppWidgetManager appWidgetManager;
		ComponentName thisWidget;
		//DateFormat format = SimpleDateFormat.getTimeInstance(SimpleDateFormat.MEDIUM, Locale.getDefault());
		
		public MyTime(Context context, AppWidgetManager appWidgetManager) {
			this.appWidgetManager = appWidgetManager;
			remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
			thisWidget = new ComponentName(context, HelloWidget.class);
		}
	
		@Override
		public void run() {
			
			String outside = null;
			String masterbedroom = null;
			String natesroom = null;
			String livingRoom = null;
			String office = null;
			String backroom = null;
			try {
				outside = new Communicator().executeHttpGet("http://192.168.1.168/emoncms/feed/value.json?apikey=4598c7bc07e9c7380df636265340beea&id=26");
				masterbedroom = new Communicator().executeHttpGet("http://192.168.1.168/emoncms/feed/value.json?apikey=4598c7bc07e9c7380df636265340beea&id=21");
				natesroom = new Communicator().executeHttpGet("http://192.168.1.168/emoncms/feed/value.json?apikey=4598c7bc07e9c7380df636265340beea&id=2");
				livingRoom = new Communicator().executeHttpGet("http://192.168.1.168/emoncms/feed/value.json?apikey=4598c7bc07e9c7380df636265340beea&id=22");
				office = new Communicator().executeHttpGet("http://192.168.1.168/emoncms/feed/value.json?apikey=4598c7bc07e9c7380df636265340beea&id=14");
				backroom = new Communicator().executeHttpGet("http://192.168.1.168/emoncms/feed/value.json?apikey=4598c7bc07e9c7380df636265340beea&id=24");
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			remoteViews.setTextViewText(R.id.outside, "Out: " + outside.toString().substring(1, outside.length() - 3) +"c                  ");
			remoteViews.setTextViewText(R.id.masterBedroom, "MB: " + masterbedroom.toString().substring(1, masterbedroom.length() - 3) +"c");
			remoteViews.setTextViewText(R.id.natesRoom, "NR: " + natesroom.toString().substring(1, natesroom.length() - 3) +"c                  ");
			remoteViews.setTextViewText(R.id.livingRoom, "LR: " + livingRoom.toString().substring(1, livingRoom.length() - 3) +"c");
			remoteViews.setTextViewText(R.id.office, "Off: " + office.toString().substring(1, office.length() - 3) +"c                  ");
			remoteViews.setTextViewText(R.id.backRoom, "BR: " + backroom.toString().substring(1, backroom.length() - 3) +"c");
			
			Time today = new Time(Time.getCurrentTimezone());
			today.setToNow();
			
			remoteViews.setTextViewText(R.id.lastUpdate, "Last Updated: " + today.format("%k:%M:%S"));
			
			appWidgetManager.updateAppWidget(thisWidget, remoteViews);
		}
	}
} 



