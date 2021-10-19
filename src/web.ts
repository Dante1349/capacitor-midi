import { WebPlugin } from '@capacitor/core';

import type { MIDIPluginPlugin } from './definitions';

export class MIDIPluginWeb extends WebPlugin implements MIDIPluginPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
