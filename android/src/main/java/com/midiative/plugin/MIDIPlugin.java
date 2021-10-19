package com.midiative.plugin;

import android.util.Log;

public class MIDIPlugin {

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }
}
