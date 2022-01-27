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
* [`initConnectionListener()`](#initconnectionlistener)
* [`addListener(...)`](#addlistener)
* [`addListener(...)`](#addlistener)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### listMIDIDevices()

```typescript
listMIDIDevices() => any
```

**Returns:** <code>any</code>

--------------------


### openDevice(...)

```typescript
openDevice(options: DeviceOptions) => any
```

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#deviceoptions">DeviceOptions</a></code> |

**Returns:** <code>any</code>

--------------------


### initConnectionListener()

```typescript
initConnectionListener() => any
```

**Returns:** <code>any</code>

--------------------


### addListener(...)

```typescript
addListener(eventName: 'MIDI_MSG_EVENT', listenerFunc: (message: MidiMessage) => void) => Promise<PluginListenerHandle> & PluginListenerHandle
```

| Param              | Type                                                                      |
| ------------------ | ------------------------------------------------------------------------- |
| **`eventName`**    | <code>"MIDI_MSG_EVENT"</code>                                             |
| **`listenerFunc`** | <code>(message: <a href="#midimessage">MidiMessage</a>) =&gt; void</code> |

**Returns:** <code>any</code>

--------------------


### addListener(...)

```typescript
addListener(eventName: 'MIDI_CON_EVENT', listenerFunc: (devices: { value: string[]; }) => void) => Promise<PluginListenerHandle> & PluginListenerHandle
```

| Param              | Type                                              |
| ------------------ | ------------------------------------------------- |
| **`eventName`**    | <code>"MIDI_CON_EVENT"</code>                     |
| **`listenerFunc`** | <code>(devices: { value: {}; }) =&gt; void</code> |

**Returns:** <code>any</code>

--------------------


### Interfaces


#### DeviceOptions

| Prop               | Type                |
| ------------------ | ------------------- |
| **`deviceNumber`** | <code>number</code> |


#### MidiMessage

| Prop           | Type                |
| -------------- | ------------------- |
| **`type`**     | <code>string</code> |
| **`note`**     | <code>number</code> |
| **`velocity`** | <code>number</code> |


#### PluginListenerHandle

| Prop         | Type                      |
| ------------ | ------------------------- |
| **`remove`** | <code>() =&gt; any</code> |

</docgen-api>
