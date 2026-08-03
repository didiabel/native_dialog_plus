// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "native_dialog_plus",
    platforms: [
        .iOS("12.0"),
    ],
    products: [
        .library(name: "native-dialog-plus", targets: ["native_dialog_plus"]),
    ],
    dependencies: [
        // Flutter generates this local package during SwiftPM integration; it
        // provides the Flutter module the plugin links against. Required by
        // Flutter 3.44+ (warns without it today, will become an error).
        .package(name: "FlutterFramework", path: "../FlutterFramework"),
    ],
    targets: [
        .target(
            name: "native_dialog_plus",
            dependencies: [
                .product(name: "FlutterFramework", package: "FlutterFramework"),
            ]
        ),
    ]
)
