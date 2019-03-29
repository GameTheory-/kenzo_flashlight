package com.umang96.flashlight;

import android.content.Context;
import android.widget.Toast;

import com.jaredrummler.android.shell.CommandResult;
import com.jaredrummler.android.shell.Shell;


class TorchUtils {

    static boolean check(Context context) {
        boolean isRoot = Shell.SU.available();
        boolean onOff = false;
        if (isRoot) {
            CommandResult result = Shell.SU.run("cat /sys/class/leds/led:torch_0/brightness");
            if (result.getStdout().length() > 1) {
                flash_off();
            } else {
                flash_on();
                onOff = true;
            }
        } else {
            Toast.makeText(context, "Root was not detected!", Toast.LENGTH_LONG).show();
        }
        return onOff;
    }

    private static void flash_on() {
        String[] cmds = {
                "echo 100 > /sys/class/leds/led:torch_0/brightness",
                "echo 100 > /sys/class/leds/led:torch_1/brightness"
        };
        RootShell.exec(cmds);
    }

    private static void flash_off() {
        String[] cmds = {
                "echo 0 > /sys/class/leds/led:torch_0/brightness",
                "echo 0 > /sys/class/leds/led:torch_1/brightness"
        };
        RootShell.exec(cmds);
    }

}
