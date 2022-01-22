import { WebPlugin} from '@capacitor/core';

import {WebMIDIHandler} from "./WebMIDIHandler";
import type { MIDIPluginPlugin } from './definitions';

export class MIDIPluginWeb extends WebPlugin implements MIDIPluginPlugin {
  async listMIDIDevices(): Promise<{value: string[]}> {
    const wmh = WebMIDIHandler.instance;
    await wmh.initWebMidi()
    console.log(wmh)

    return { value: wmh.listInputsAndOutputs()};
  }

  async openDevice(): Promise<void> {
    const wmh = WebMIDIHandler.instance;
    await wmh.initWebMidi();
    wmh.addDeviceListener(1)
    return
  }
}
