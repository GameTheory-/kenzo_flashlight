package com.umang96.flashlight.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;


public class WidgetService extends JobIntentService {
    final static int job_id = 95;

    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, WidgetService.class, job_id, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        if (intent.getAction() != null) {
            int[] ids = AppWidgetManager.getInstance(this).getAppWidgetIds(new ComponentName(this, TorchWidget.class));
            if (ids != null && ids.length > 0) {
                for (int id : ids) {
                    TorchWidget.updateAppWidget(this, AppWidgetManager.getInstance(this), id);
                }
            }
        }
    }
}
