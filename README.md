# 🎹 capacitor-midi

Grants access to midi devices via native libraries or WebMIDI.

❗Currently NO iOS support. Because I don't have access to a Mac. Contributors Welcome❗

## 🔌 Install

```bash
npm install capacitor-midi
npx cap sync
```

## 🎼 Usage

#### Open device and subscribe to MidiMessages
```typescript
const options: DeviceOptions = {
    deviceNumber: 0 // Choose device from listMIDIDevices()
}

this.devices = await MIDI.listMIDIDevices();

MIDI.addListener('MIDI_MSG_EVENT', (message: MidiMessage) => {
    console.log(message);
});
```
#### Subscribe to device connection changes
```typescript
await MIDI.initConnectionListener();

MIDI.addListener('MIDI_CON_EVENT', (devices: { value: string[] }) => {
    console.log(devices.value);
});
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


### initConnectionListener()

```typescript
initConnectionListener() => Promise<void>
```

--------------------


### addListener(...)

```typescript
addListener(eventName: 'MIDI_MSG_EVENT', listenerFunc: (message: MidiMessage) => void) => Promise<PluginListenerHandle>
```

| Param              | Type                                                                      |
| ------------------ | ------------------------------------------------------------------------- |
| **`eventName`**    | <code>"MIDI_MSG_EVENT"</code>                                             |
| **`listenerFunc`** | <code>(message: <a href="#midimessage">MidiMessage</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

--------------------


### addListener(...)

```typescript
addListener(eventName: 'MIDI_CON_EVENT', listenerFunc: (devices: { value: string[]; }) => void) => Promise<PluginListenerHandle>
```

| Param              | Type                                              |
| ------------------ | ------------------------------------------------- |
| **`eventName`**    | <code>"MIDI_CON_EVENT"</code>                     |
| **`listenerFunc`** | <code>(devices: { value: {}; }) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

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
