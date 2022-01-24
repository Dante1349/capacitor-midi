import { registerPlugin } from '@capacitor/core';

import type { MIDIPlugin } from './definitions';

const MIDI = registerPlugin<MIDIPlugin>('MIDIPlugin', {
  web: () => import('./web').then(m => new m.MIDIPluginWeb()),
});

export * from './definitions';
export { MIDI };
