package com.umang96.flashlight.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.widget.RemoteViews;

import com.umang96.flashlight.R;

import static android.content.Context.MODE_PRIVATE;


public class TorchWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        SharedPreferences prefs = context.getSharedPreferences("tile_preferences", MODE_PRIVATE);
        boolean tileState = prefs.getBoolean("tile_state", false);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.torch_widget);

        if (tileState) {
            Icon icon = Icon.createWithResource(context, R.drawable.torch_on);
            views.setImageViewIcon(R.id.button_switch_widget, icon);
        } else {
            Icon icon = Icon.createWithResource(context, R.drawable.ic_launcher);
            views.setImageViewIcon(R.id.button_switch_widget, icon);
        }

        Intent intent = new Intent(context, WidgetService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.button_switch_widget, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

}

