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

    public addDeviceListener(deviceNo: number): void {
        if (!this.midi) {
            console.error("WebMidi not initialized!")
            return
        }
        console.log(this.midi.inputs, deviceNo)
        this.midi.inputs[deviceNo].addListener("noteon", (e: any) => {
            console.log(e)
        });
    }

    public listInputsAndOutputs(): string[] {
        if (!this.midi) {
            console.error("WebMidi not initialized!")
            return []
        }

        const devices = []
        for (const entry of this.midi.inputs) {
            console.log("Input port [type:'" + entry.type + "'] id:'" + entry.id +
                "' manufacturer:'" + entry.manufacturer + "' name:'" + entry.name +
                "' version:'" + entry.version + "'");
            if (entry?.type && entry.type == "input") {
                devices.push({
                    "manufacturer": entry.manufacturer,
                    "name": entry.name
                })
            }
        }
        console.log("in plug", devices)
        return devices.map(d => JSON.stringify(d))
    }
}
