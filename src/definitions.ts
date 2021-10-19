export interface MIDIPluginPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
