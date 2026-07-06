// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "native_dialog_plus",
    platforms: [
        .iOS("12.0"),
    ],
    products: [
        .library(name: "native-dialog-plus", targets: ["native_dialog_plus"])
    ],
    dependencies: [
        .package(name: "FlutterFramework", path: "../FlutterFramework"),
    ],
    targets: [
        .target(
            name: "native_dialog_plus",
            dependencies: [
                .product(name: "FlutterFramework", package: "FlutterFramework"),
            ],
            exclude: [
                // Exclude Objective-C files - they are used by CocoaPods only
                // Flutter's build system generates plugin registration code separately
                "NativeDialogPlusPlugin.m",
                "NativeDialogPlusPlugin.h"
            ],
        )
    ]
)
