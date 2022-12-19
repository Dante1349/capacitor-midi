package capacitormidi;

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
public class MIDIPlugin extends Plugin {

    private AndroidMIDIHandler androidMidiHandler;

    public MIDIPlugin() {
        super();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void load() {
        this.androidMidiHandler = new AndroidMIDIHandler(this.getContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @PluginMethod
    public void listMIDIDevices(PluginCall call) {
        JSObject ret = new JSObject();
        JSArray devices = new JSArray();
        Arrays.stream(androidMidiHandler.listMIDIDevices()).forEach(devices::put);
        ret.put("value", devices);
        call.resolve(ret);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @PluginMethod
    public void openDevice(PluginCall call) {
        int deviceNumber = call.getInt("deviceNumber");
        androidMidiHandler.openDevice(deviceNumber, (MIDIDeviceMessage message) -> {
            JSObject midiMessage = new JSObject();

            String rawType = String.valueOf(message.msg[1]);
            int note = message.msg[2];
            int velocity = message.msg[3];

            String type = "";
            if ( rawType.equals("-112") && velocity != 0 ) {
                type = "NoteOn";
            } else if ( (rawType.equals("-128") || rawType.equals("-112")) && velocity == 0 ) {
                type = "NoteOff";
            } else {
                type = "UNKNOWN - " + rawType;
            }

            midiMessage.put("type", type);
            midiMessage.put("note", note);
            midiMessage.put("velocity", velocity);

            notifyListeners("MIDI_MSG_EVENT", midiMessage);
        });
        call.resolve();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @PluginMethod
    public void initConnectionListener(PluginCall call) {
        androidMidiHandler.addDeviceConnectionListener((String[] devices) -> {
            JSObject conMsg = new JSObject();
            JSArray values = new JSArray();
            Arrays.stream(devices).forEach(values::put);
            conMsg.put("value", values);

            notifyListeners("MIDI_CON_EVENT", conMsg);
        });
        call.resolve();
    }
}
