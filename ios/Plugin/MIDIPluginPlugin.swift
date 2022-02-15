import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(MIDIPluginPlugin)
public class MIDIPluginPlugin: CAPPlugin {
    private let implementation = IOSMIDIHandler()

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }
    
    @objc func listMIDIDevices(_ call: CAPPluginCall) {
        call.resolve([
            "value": implementation.listDevices()
        ])
    }
    
    @objc func openDevice(_ call: CAPPluginCall) {
        print("open not implemented")
        call.resolve()
    }
    
    @objc func initConnectionListener(_ call: CAPPluginCall) {
        print("init connection listener not implemented")
        call.resolve()
    }
    
}
