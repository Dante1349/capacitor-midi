package capacitormidi;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.midi.MidiDevice;
import android.media.midi.MidiDeviceInfo;
import android.media.midi.MidiManager;
import android.media.midi.MidiOutputPort;
import android.media.midi.MidiManager.DeviceCallback;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AndroidMIDIHandler {
    private static AndroidMIDIHandler INSTANCE;
    private String info = "Initial info class";

    private Context context;
    private MidiManager midiManager;

    private MidiOutputPort lastOutputPort = null;

    @RequiresApi(api = Build.VERSION_CODES.M)
    AndroidMIDIHandler(Context context) {
        this.context = context;
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MIDI)) {
            Log.i("MIDIPlugin", "MIDI feature enabled");

            this.midiManager = (MidiManager) context.getSystemService(Context.MIDI_SERVICE);
        } else {
            Log.e("MIDIPlugin", "No MIDI feature found");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    AndroidMIDIHandler AndroidMIDIHandler(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new AndroidMIDIHandler(context);
        }

        return INSTANCE;
    }

    private void logNoMIDIFeatureFound() {
        Log.e("MIDIPlugin", "No MIDI feature found");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public String[] listMIDIDevices() {
        if (this.midiManager == null) {
            this.logNoMIDIFeatureFound();
            return new String[]{};
        }

        MidiDeviceInfo[] infos = this.midiManager.getDevices();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void openDevice(int deviceNumber, Consumer<MIDIDeviceMessage> consumer) {
        if (this.midiManager == null) {
            this.logNoMIDIFeatureFound();
            return;
        }

        MidiDeviceInfo deviceInfos[] = this.midiManager.getDevices();
        if (deviceInfos.length > 0 && deviceNumber < deviceInfos.length) {
            // Prevent multiple device subscriptions
            if (lastOutputPort != null) {
                try {
                    lastOutputPort.close();
                } catch (IOException e) {
                    Log.e("MIDIPlugin", "Could not close previously connected device");
                }
            }

            this.midiManager.openDevice(deviceInfos[deviceNumber],
                    (MidiDevice device) -> {
                        if (device != null) {
                            Log.i("MIDIPlugin", "Device opened: " + device);


                            MidiOutputPort midiOutputPort = device.openOutputPort(0);
                            lastOutputPort = midiOutputPort;
                            midiOutputPort.connect(new MidiMessageReceiver(consumer));
                        }
                    }, new Handler(Looper.getMainLooper()));
        } else {
            Log.e("MIDIPlugin", "Could not open device");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addDeviceConnectionListener(Consumer<String[]> consumer) {
        if (this.midiManager == null) {
            this.logNoMIDIFeatureFound();
            return;
        }

        this.midiManager.registerDeviceCallback(new DeviceCallback() {
            public void onDeviceAdded(MidiDeviceInfo info) {
                consumer.accept(listMIDIDevices());
            }

            public void onDeviceRemoved(MidiDeviceInfo info) {
                consumer.accept(listMIDIDevices());
            }
        }, null);
    }
}