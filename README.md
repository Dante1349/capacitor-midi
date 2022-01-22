# capacitor-midi 🎹

Grants access to midi devices via native libraries or WebMIDI. Currently only Android is available and the inital Web part, iOS is blocked for the moment because I don't have private access to a mac. I hope this changes soon.

## Install

```bash
npm install capacitor-midi
npx cap sync
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
