import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {DeviceOptions, MIDI, MidiMessage} from 'capacitor-midi';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit {
  devices: string[] = [];
  messages: MidiMessage[] = [];
  opened = false;

  constructor(private cd: ChangeDetectorRef) {
  }

  async ngOnInit(): Promise<void> {
    this.devices = (await MIDI.listMIDIDevices()).value;

    MIDI.addListener('MIDI_MSG_EVENT', (message: MidiMessage) => {
      this.messages.push(message);
      this.cd.detectChanges();
    });

    await MIDI.initConnectionListener();

    MIDI.addListener('MIDI_CON_EVENT', (devices: { value: string[] }) => {
      this.devices = devices.value;
      this.cd.detectChanges();
    });
  }

  updateDevices(): void {
    MIDI.listMIDIDevices()
      .then(devices => {
        this.devices = devices.value;
        this.cd.detectChanges();
      });
  }

  openDevice(deviceNumber: number): void {
    const deviceOptions: DeviceOptions = {
      deviceNumber
    };
    MIDI.openDevice(deviceOptions).then(r => {
      this.clearMessages();
    });
  }

  clearMessages(): void {
    this.messages = [];
  }

  msgToString(msg: MidiMessage): string {
    return JSON.stringify(msg);
  }
}
