import type {PluginListenerHandle} from "@capacitor/core";

export interface MIDIPlugin {
  listMIDIDevices(): Promise<{ value: string[] }>

  openDevice(options: DeviceOptions): Promise<void>

  addDeviceListener(callback: ((message: MidiMessage) => any)): PluginListenerHandle
}

export interface MidiMessage {
  type: string;
  note: number;
  velocity: number;
}

export interface DeviceOptions {
  deviceNumber: number
}
