package com.midiative.plugin;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.util.Arrays;

@CapacitorPlugin(name = "MIDIPlugin")
public class MIDIPluginPlugin extends Plugin {

    private MIDIPlugin implementation;

    public MIDIPluginPlugin() {
        super();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void load() {
        this.implementation = new MIDIPlugin(this.getContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @PluginMethod
    public void listMIDIDevices(PluginCall call) {
        JSObject ret = new JSObject();
        JSArray devices = new JSArray();
        Arrays.stream(implementation.listMIDIDevices()).forEach(devices::put);
        ret.put("value", devices);
        call.resolve(ret);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @PluginMethod
    public void openDevice(PluginCall call) {
        implementation.openDevice((MIDIDeviceMessage message) -> {
            JSObject midiMessage = new JSObject();

            String rawType = String.valueOf(message.msg[1]);
            String type = "";
            switch(rawType) {
                case "-112":
                    type = "NoteOn";
                    break;
                case "-128":
                    type = "NoteOff";
                    break;
                default:
                    type = "UNKNOWN - " + rawType;
                    break;
            }
            midiMessage.put("type", type);
            midiMessage.put("note", String.valueOf(message.msg[2]));
            midiMessage.put("velocity", String.valueOf(message.msg[3]));

            notifyListeners("MIDIEventReceived", midiMessage);
        });
        call.resolve();
    }
}
