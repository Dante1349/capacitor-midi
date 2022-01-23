import {ChangeDetectorRef, Component} from '@angular/core';
import {MIDIPlugin, MidiMessage} from 'capacitor-midi';

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

      MIDIPlugin.addListener('MIDIEventReceived', (message: MidiMessage ) => {
        this.messages.push(message);
        cd.detectChanges();
      });
  }

  updateDevices() {
    MIDIPlugin.listMIDIDevices()
      .then(devices => {
        console.log(devices);
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
