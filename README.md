# capacitor-midi 🎹

Grants access to midi devices via native libraries or WebMIDI. 

Currently, only Android and Web is available, iOS is blocked at the moment because I don't have access to a mac. I hope this changes soon.

## Install

```bash
npm install capacitor-midi
npx cap sync
```

## Usage
### Subscribe to MIDI events after a device is opened
```angular2html
await MIDIPlugin.openDevice()

MIDIPlugin.addListener('MIDIEventReceived', 
    (message: MidiMessage) => console.log(message));

interface MidiMessage {
    type: string; // NoteOn, NoteOff, UNKNOWN - XXX
    note: number;
    velocity: number;
}
```

## API

<docgen-index>

* [`listMIDIDevices()`](#listmididevices)
* [`openDevice()`](#opendevice)
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


### openDevice()

```typescript
openDevice() => Promise<void>
```

--------------------


### addListener(...)

```typescript
addListener(eventName: string, listenerFunc: ListenerCallback) => PluginListenerHandle
```

| Param              | Type                                            |
| ------------------ | ----------------------------------------------- |
| **`eventName`**    | <code>string</code>                             |
| **`listenerFunc`** | <code>(err: any, ...args: {}) =&gt; void</code> |

**Returns:** <code><a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### Interfaces


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |

</docgen-api>
