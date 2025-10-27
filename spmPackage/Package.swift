// swift-tools-version:5.9
import PackageDescription

let package = Package(
    name: "shared",
    platforms: [
        .iOS(.v14)
    ],
    products: [
        .library(
            name: "shared",
            targets: ["shared"]
        )
    ],
    targets: [
        .binaryTarget(
            name: "coreMobile",
            path: "coreMobile/build/XCFrameworks/release/coreMobile.xcframework"
        ),
        .binaryTarget(
            name: "shared",
            path: "shared/build/XCFrameworks/release/shared.xcframework"
        ),
        .target(
            name: "SharedWithDeps",
            dependencies: [
                "shared",
                "coreMobile"
            ]
        )
    ]
)
