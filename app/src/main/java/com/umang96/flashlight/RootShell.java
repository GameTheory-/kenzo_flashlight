package com.umang96.flashlight;

import android.os.AsyncTask;

import com.jaredrummler.android.shell.Shell;

class RootShell {
    static void exec(String[] cmds) {
        new Execute().execute(cmds);
    }

    private static class Execute extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... cmds) {
            Shell.SU.run(cmds);
            return null;
        }
    }
}
