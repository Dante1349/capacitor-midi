import {ChangeDetectorRef, Component} from '@angular/core';
import {MidiMessage, DeviceOptions, MIDI} from 'capacitor-midi';

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
    MIDI.listMIDIDevices()
      .then(devices => {
        this.devices = devices.value;
      });

    MIDI.addDeviceListener((message: MidiMessage) => {
      this.messages.push(message);
      cd.detectChanges();
    });
  }

  updateDevices(): void {
    MIDI.listMIDIDevices()
      .then(devices => {
        this.devices = devices.value;
      });
  }

  openDevice(deviceNumber: number): void {
    const deviceOptions: DeviceOptions = {
      deviceNumber: deviceNumber
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
