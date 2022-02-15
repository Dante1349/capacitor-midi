import Foundation
import CoreMIDI

@objc public class IOSMIDIHandler: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
    
    @objc public func listDevices() -> [String] {
        //var client = MIDIClientRef()
        let sourceCount = MIDIGetNumberOfSources()
        let sourceNames: [String] = []
        for i in stride(from: 0, to: sourceCount, by: 1) {
            let source: MIDIEndpointRef = MIDIGetSource(i)
            print("source:")
            print(source)
        }
        return ["test", "test2"]
    }
}
