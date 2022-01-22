package com.midiative.plugin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.midi.MidiDevice;
import android.media.midi.MidiDeviceInfo;
import android.media.midi.MidiManager;
import android.media.midi.MidiOutputPort;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MIDIPlugin {
    private Context context;
    private MidiManager midiManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public MIDIPlugin(Context context) {
        this.context = context;
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MIDI)) {
            // do MIDI stuff
            Log.i("MIDIPlugin", "MIDI feature enabled");

            this.midiManager = (MidiManager) context.getSystemService(Context.MIDI_SERVICE);


        } else {
            Log.i("MIDIPlugin", "No MIDI feature found");
            throw new Error("No MIDI feature found.");
        }
    }

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private MidiDeviceInfo[] getDeviceInfos() {
        return this.midiManager.getDevices();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public String[] listMIDIDevices() {
        MidiDeviceInfo[] infos = this.getDeviceInfos();
        List<String> devices = new ArrayList<>();

        if (infos != null) {
            for (int i = 0; i < infos.length; i++) {
                Log.i("MIDIPlugin", "handling device: " + infos[i]);
                int type = infos[i].getType();
                Object manufacturerProp = infos[i].getProperties().get("manufacturer");
                Object productProp = infos[i].getProperties().get("product");
                Object nameProp = infos[i].getProperties().get("name");

                String deviceString = "";
                if (manufacturerProp != null) {
                    deviceString = deviceString.concat(manufacturerProp.toString() + ", ");
                }
                if (productProp != null) {
                    deviceString = deviceString.concat(productProp.toString() + ", ");
                }
                if (nameProp != null) {
                    deviceString = deviceString.concat(nameProp.toString() + ", ");
                }

                Log.i("MIDIPlugin", "device found: " + deviceString);
                devices.add(deviceString);
            }

            return devices.toArray(new String[0]);
        } else {
            return new String[]{};
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void openDevice(Consumer<MIDIDeviceMessage> consumer) {
        MidiDeviceInfo devices[] = this.getDeviceInfos();
        if (devices != null && devices.length > 0) {
            this.midiManager.openDevice(devices[0],
                    (MidiDevice device) -> {
                        if (device != null) {
                            Log.i("MIDIPlugin", "Device opened: " + device);

                            MidiOutputPort midiOutputPort = device.openOutputPort(0);
                            midiOutputPort.connect(new MidiMessageReceiver(consumer));
                        }
                    }, new Handler(Looper.getMainLooper()));
        } else {
            Log.i("MIDIPlugin", "Cannot open device");
        }
    }

}
