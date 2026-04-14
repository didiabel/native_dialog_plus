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
    dependencies: [],
    targets: [
        .target(
            name: "native_dialog_plus",
            dependencies: [],
            path: "Classes",
            publicHeadersPath: "."
        )
    ]
)
