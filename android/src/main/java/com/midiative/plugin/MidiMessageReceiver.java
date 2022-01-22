package com.midiative.plugin;

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
        Log.i("MidiMessageReceiver", "msg: " + msg + ", offset: " + offset + ", count: " + count + ", timestamp: " + timestamp);
        consumer.accept(new MIDIDeviceMessage(msg, offset, count, timestamp));
    }
}
