import {ChangeDetectorRef, Component} from '@angular/core';
import {MidiMessage, DeviceOptions, MIDIPlugin} from 'capacitor-midi';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  devices: string[] = [];
  messages: MidiMessage[] = [];
  opened = false;

  constructor(private cd: ChangeDetectorRef) {
    MIDIPlugin.listMIDIDevices()
      .then(devices => {
        this.devices = devices.value;
      });

    MIDIPlugin.addListener('MIDIEventReceived', (message: MidiMessage) => {
      this.messages.push(message);
      cd.detectChanges();
    });
  }

  updateDevices(): void {
    MIDIPlugin.listMIDIDevices()
      .then(devices => {
        this.devices = devices.value;
      });
  }

  openDevice(deviceNumber: number): void {
    const deviceOptions: DeviceOptions = {
      deviceNumber: deviceNumber
    };
    MIDIPlugin.openDevice(deviceOptions).then(r => {
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
