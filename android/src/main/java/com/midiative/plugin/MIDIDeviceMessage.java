package com.midiative.plugin;

public class MIDIDeviceMessage {
    public byte[] msg;
    public int offset;
    public int count;
    public long timestamp;

    public MIDIDeviceMessage(byte[] msg, int offset, int count, long timestamp) {
        this.msg = msg;
        this.offset = offset;
        this.count = count;
        this.timestamp = timestamp;
    }
}
