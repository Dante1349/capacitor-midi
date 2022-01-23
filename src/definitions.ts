import type {ListenerCallback, PluginListenerHandle} from "@capacitor/core";

export interface MIDIPluginPlugin{
  listMIDIDevices(): Promise<{ value: string[] }>

  openDevice(): Promise<void>

  addListener(eventName: string, listenerFunc: ListenerCallback): PluginListenerHandle
}

export interface MidiMessage {
  type: string;
  note: number;
  velocity: number;
}
