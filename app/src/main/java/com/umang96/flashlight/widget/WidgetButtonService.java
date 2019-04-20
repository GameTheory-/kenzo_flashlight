package com.umang96.flashlight.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;

public class WidgetButtonService extends IntentService {
    private static int[] ids;

    public WidgetButtonService() {
        super("WidgetButtonService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ids = AppWidgetManager.getInstance(this).getAppWidgetIds(new ComponentName(this, TorchWidget.class));
        updateWidgets();
    }

    private void updateWidgets() {
        if (ids != null && ids.length > 0) {
            for (int id : ids) {
                TorchWidget.updateAppWidget(this, AppWidgetManager.getInstance(this), id);
            }
        }
    }

}
