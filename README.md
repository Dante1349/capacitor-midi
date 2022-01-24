# 🎹 capacitor-midi

Grants access to midi devices via native libraries or WebMIDI.

❗Currently NO iOS support. Because I don't have access to a Mac. Contributors Welcome❗

## 🔌 Install

```bash
npm install capacitor-midi
npx cap sync
```

## 🎼 Usage

### Subscribe to MIDI events after a device is opened

```typescript
const options: DeviceOptions = {
    deviceNumber: 0 // Choose device from listMIDIDevices()
}

await MIDI.openDevice(options)
    .catch((e) => console.error(e))

MIDI.addDeviceListener((message: MidiMessage) => console.log(message));

interface MidiMessage {
    type: string; // NoteOn, NoteOff, UNKNOWN - XXX
    note: number;
    velocity: number;
}
```

## 🎛 API

<docgen-index>

* [`listMIDIDevices()`](#listmididevices)
* [`openDevice(...)`](#opendevice)
* [`addDeviceListener(...)`](#adddevicelistener)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### listMIDIDevices()

```typescript
listMIDIDevices() => Promise<{ value: string[]; }>
```

**Returns:** <code>Promise&lt;{ value: {}; }&gt;</code>

--------------------


### openDevice(...)

```typescript
openDevice(options: DeviceOptions) => Promise<void>
```

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#deviceoptions">DeviceOptions</a></code> |

--------------------


### addDeviceListener(...)

```typescript
addDeviceListener(callback: (message: MidiMessage) => any) => PluginListenerHandle
```

| Param          | Type                                                                     |
| -------------- | ------------------------------------------------------------------------ |
| **`callback`** | <code>(message: <a href="#midimessage">MidiMessage</a>) =&gt; any</code> |

**Returns:** <code><a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### Interfaces


#### DeviceOptions

| Prop               | Type                |
| ------------------ | ------------------- |
| **`deviceNumber`** | <code>number</code> |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### MidiMessage

| Prop           | Type                |
| -------------- | ------------------- |
| **`type`**     | <code>string</code> |
| **`note`**     | <code>number</code> |
| **`velocity`** | <code>number</code> |

</docgen-api>
