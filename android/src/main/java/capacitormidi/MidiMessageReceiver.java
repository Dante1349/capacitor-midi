package capacitormidi;

import android.media.midi.MidiReceiver;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.function.Consumer;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MidiMessageReceiver extends MidiReceiver {
    Consumer<MIDIDeviceMessage> consumer;
    public MidiMessageReceiver(Consumer<MIDIDeviceMessage> consumer) {
        this.consumer = consumer;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSend(byte[] msg, int offset, int count, long timestamp) throws IOException {
        String message = "["
                +String.valueOf(msg[0])+","
                +String.valueOf(msg[1])+","
                +String.valueOf(msg[2])+","
                +String.valueOf(msg[3])+","
                +String.valueOf(msg[4])+","
                +String.valueOf(msg[5])+","
                +"]";
        Log.i("MidiMessageReceiver", "msg: " + message + ", offset: " + offset + ", count: " + count + ", timestamp: " + timestamp);
        consumer.accept(new MIDIDeviceMessage(msg, offset, count, timestamp));
    }
}
