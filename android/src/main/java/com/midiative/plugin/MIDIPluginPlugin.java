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

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
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
            midiMessage.put("msg", this.castToString(message.msg));
            midiMessage.put("count", message.count);
            midiMessage.put("offset", message.offset);
            midiMessage.put("timestamp", message.timestamp);

            JSObject ret = new JSObject();
            ret.put("value", midiMessage);
            notifyListeners("MIDIEventReceived", ret);
        });
        call.resolve();
    }

    private String castToString(byte[] bytes) {
        // bytes[1] = -112 is vermutlich NoteOn
        // bytes[1] = -128 is vermutlich NoteOff
        // kp was des soll
        String key = String.valueOf(bytes[2]);
        String velocity = String.valueOf(bytes[3]);
        return "key: " + key + " velocity: " + velocity + " raw: " + Arrays.toString(bytes);
    }
}
