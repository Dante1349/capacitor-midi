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

        if (this.midi.inputs && this.midi.inputs.length > 0 && deviceNo < this.midi.inputs.length) {
            const device = this.midi.inputs[deviceNo]

            // prevent multiple event listener subscriptions
            this.midi.inputs.forEach((d: any) => {
                d.removeListener("noteon")
                d.removeListener("noteoff")
            })

            device.addListener("noteon", (e: any) => {
                callback(e)
            });
            device.addListener("noteoff", (e: any) => {
                callback(e)
            });
        } else {
            console.error("Could not open device")
        }
    }

    public getInputsAndOutputs(): string[] {
        if (!this.midi) {
            console.error("WebMidi not initialized!")
            return []
        }

        const devices = []
        for (const entry of this.midi.inputs) {
            if (entry?.type && entry.type == "input") {
                devices.push((entry.name) ? entry.name : "Unknown Device")
            }
        }
        return devices
    }

    public addConnectionListener(callback: (devices: { value: string[] }) => any): void {
        if (!this.midi) {
            console.error("WebMidi not initialized!")
            return
        }
        this.midi.removeListener("connected")
        this.midi.removeListener("disconnected")
        this.midi.addListener("connected", () => {
            callback({value: this.getInputsAndOutputs()})
        })
        this.midi.addListener("disconnected", () => {
            callback({value: this.getInputsAndOutputs()})
        })
    }
}
