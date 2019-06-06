package com.umang96.flashlight;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.jaredrummler.android.shell.CommandResult;
import com.jaredrummler.android.shell.Shell;
import com.umang96.flashlight.widget.TorchWidget;
import com.umang96.flashlight.widget.WidgetService;

import static android.content.Context.MODE_PRIVATE;


public class TorchUtils {
    private final static String torchAction = "TorchAction";

    public static void toggleTorch(Context context) {
        boolean isRoot = Shell.SU.available();
        if (isRoot) {
            CommandResult result = Shell.SU.run("cat /sys/class/leds/led:torch_0/brightness");
            if (result.getStdout().length() > 1) {
                torchOnOff(context, false);
            } else {
                torchOnOff(context, true);
            }
        } else {
            Toast.makeText(context, "Root was not detected!", Toast.LENGTH_LONG).show();
        }
    }

    private static void torchOnOff(Context context, boolean toggle) {
        SharedPreferences prefs = context.getSharedPreferences("tile_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("tile_state", toggle);
        editor.apply();

        String[] cmds;
        if (toggle) {
            cmds = new String[]{
                    "echo 100 > /sys/class/leds/led:torch_0/brightness",
                    "echo 100 > /sys/class/leds/led:torch_1/brightness"
            };
        } else {
            cmds = new String[]{
                    "echo 0 > /sys/class/leds/led:torch_0/brightness",
                    "echo 0 > /sys/class/leds/led:torch_1/brightness"
            };
        }
        RootShell.exec(cmds);

        Intent intent = new Intent(context, TorchWidget.class);
        intent.setAction(torchAction);
        WidgetService.enqueueWork(context, intent);
    }

}
