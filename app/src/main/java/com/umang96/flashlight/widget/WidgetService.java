package com.umang96.flashlight.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;

import com.umang96.flashlight.TorchUtils;

public class WidgetService extends IntentService {
    private static int[] ids;

    public WidgetService() {
        super("WidgetService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ids = AppWidgetManager.getInstance(this).getAppWidgetIds(new ComponentName(this, TorchWidget.class));
        TorchUtils.checkState(this);
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
