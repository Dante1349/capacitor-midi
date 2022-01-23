import {WebMidi} from "webmidi";

export class WebMIDIHandler {
    private static _instance?: WebMIDIHandler;
    private midi: any = null;

    private constructor() {
        if (WebMIDIHandler._instance)
            throw new Error("Use WebMIDIHandler.instance instead of new.");
        WebMIDIHandler._instance = this;
    }

    static get instance(): WebMIDIHandler {
        return WebMIDIHandler._instance ?? (WebMIDIHandler._instance = new WebMIDIHandler());
    }

    public async initWebMidi(): Promise<void> {
        if (!this.midi) {
            this.midi = await WebMidi.enable()
                .catch(e => console.log(e))
        }
    }

    public addDeviceListener(deviceNo: number, callback: (arg: any) => any): void {
        if (!this.midi) {
            console.error("WebMidi not initialized!")
            return
        }
        const device = this.midi.inputs[deviceNo]
        device.removeListener("noteon");
        device.removeListener("noteoff");
        device.addListener("noteon",(e: any) => {
            callback(e)
        });
        device.addListener("noteoff",(e: any) => {
            callback(e)
        });
    }

    public listInputsAndOutputs(): string[] {
        if (!this.midi) {
            console.error("WebMidi not initialized!")
            return []
        }

        const devices = []
        for (const entry of this.midi.inputs) {
            if (entry?.type && entry.type == "input") {
                devices.push({
                    "manufacturer": entry.manufacturer,
                    "name": entry.name
                })
            }
        }
        return devices.map(d => JSON.stringify(d))
    }
}
