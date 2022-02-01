import {WebPlugin} from '@capacitor/core';

import {WebMIDIHandler} from "./WebMIDIHandler";
import type {DeviceOptions, MIDIPlugin} from './definitions';

export class MIDIPluginWeb extends WebPlugin implements MIDIPlugin {
    private wmh: WebMIDIHandler = WebMIDIHandler.instance

    async listMIDIDevices(): Promise<{ value: string[] }> {
        const wmh = WebMIDIHandler.instance;
        await wmh.initWebMidi()

        return wmh.getInputsAndOutputs();
    }

    async openDevice(options: DeviceOptions): Promise<void> {
        const wmh = WebMIDIHandler.instance;
        await wmh.initWebMidi()
        const callback = (ret: any) => {
            let msgType
            switch (ret.type) {
                case("noteon"):
                    msgType = "NoteOn"
                    break;
                case("noteoff"):
                    msgType = "NoteOff"
                    break;
                default:
                    msgType = "UNKNOWN - " + ret.type
                    break;
            }

            const msg = {
                type: msgType,
                note: ret.data[1],
                velocity: ret.data[2],
            }

            this.notifyListeners('MIDI_MSG_EVENT', msg)
        }
        this.wmh.addDeviceListener(options.deviceNumber, callback)
        console.log("MIDIPlugin", "Device opened: " + options.deviceNumber)
    }

    async initConnectionListener(): Promise<void> {
        const wmh = WebMIDIHandler.instance;
        await wmh.initWebMidi()
        const callback = (devices: { value: string[] }) => {
            this.notifyListeners('MIDI_CON_EVENT', devices)
        }
        this.wmh.addConnectionListener(callback)
    }
}


