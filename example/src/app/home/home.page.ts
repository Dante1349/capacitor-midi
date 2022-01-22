import {ChangeDetectorRef, Component} from '@angular/core';
import {Capacitor} from '@capacitor/core';
import {MIDIPlugin} from 'capacitor-midi';

interface MidiMessage {
  type: string;
  note: number;
  velocity: number;
  count: number;
  offset: number;
  timestamp: number;
}

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  devices: string[] = [];
  messages: MidiMessage[] = [];
  opened = false

  constructor(private cd: ChangeDetectorRef) {
    if (Capacitor.isNativePlatform()) {
      MIDIPlugin.listMIDIDevices()
        .then(devices => {
          this.devices = devices.value;
        });

      MIDIPlugin.addListener('MIDIEventReceived', (message: { value: MidiMessage }) => {
        this.messages.push(message.value);
        cd.detectChanges();
      });
    }
  }

  updateDevices() {
    MIDIPlugin.listMIDIDevices()
      .then(devices => {
        console.log(devices)
        this.devices = devices.value;
      });
  }

  openDevice() {
    MIDIPlugin.openDevice().then(r => {
      this.opened = true;
    });
  }

  clearMessages() {
    this.messages = [];
  }

  msgToString(msg: MidiMessage) {
    return JSON.stringify(msg);
  }
}
