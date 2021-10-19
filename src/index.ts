import { registerPlugin } from '@capacitor/core';

import type { MIDIPluginPlugin } from './definitions';

const MIDIPlugin = registerPlugin<MIDIPluginPlugin>('MIDIPlugin', {
  web: () => import('./web').then(m => new m.MIDIPluginWeb()),
});

export * from './definitions';
export { MIDIPlugin };
