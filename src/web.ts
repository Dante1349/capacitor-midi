import {WebPlugin} from '@capacitor/core';

import {WebMIDIHandler} from "./WebMIDIHandler";
import type {DeviceOptions, MIDIPluginPlugin} from './definitions';

export class MIDIPluginWeb extends WebPlugin implements MIDIPluginPlugin {
    async listMIDIDevices(): Promise<{ value: string[] }> {
        const wmh = WebMIDIHandler.instance;
        await wmh.initWebMidi()

        return {value: wmh.listInputsAndOutputs()};
    }

    async openDevice(options: DeviceOptions): Promise<void> {
        const wmh = WebMIDIHandler.instance;
        await wmh.initWebMidi();
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

            this.notifyListeners('MIDIEventReceived', msg)
        }
        wmh.addDeviceListener(options.deviceNumber, callback)
        console.log("MIDIPlugin", "Device opened: " + options.deviceNumber)
        return
    }

}
