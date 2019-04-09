package com.umang96.flashlight;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.jaredrummler.android.shell.CommandResult;
import com.jaredrummler.android.shell.Shell;

import static android.content.Context.MODE_PRIVATE;


class TorchUtils {

    static void checkState(Context context) {
        boolean isRoot = Shell.SU.available();
        if (isRoot) {
            CommandResult result = Shell.SU.run("cat /sys/class/leds/led:torch_0/brightness");
            if (result.getStdout().length() > 1) {
                flash_off(context);
            } else {
                flash_on(context);
            }
        } else {
            Toast.makeText(context, "Root was not detected!", Toast.LENGTH_LONG).show();
        }
    }

    private static void flash_on(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("tile_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("tile_state", true);
        editor.apply();

        String[] cmds = {
                "echo 100 > /sys/class/leds/led:torch_0/brightness",
                "echo 100 > /sys/class/leds/led:torch_1/brightness"
        };
        RootShell.exec(cmds);
    }

    private static void flash_off(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("tile_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("tile_state", false);
        editor.apply();

        String[] cmds = {
                "echo 0 > /sys/class/leds/led:torch_0/brightness",
                "echo 0 > /sys/class/leds/led:torch_1/brightness"
        };
        RootShell.exec(cmds);
    }

}
