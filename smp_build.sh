#!/bin/bash
# quick_spm_build.sh

set -e

echo "🚀 Building KMP Compose project for SPM..."

echo "🧹 Cleaning..."
./gradlew clean

echo "📦 Building XCFramework..."
./gradlew :shared:assembleSharedReleaseXCFramework


echo "📦 Preparing Package.swift..."
mkdir -p spmPackage
cat > spmPackage/Package.swift <<EOF
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
EOF

echo "✅ Build completed!"
echo "📱 Add SPMPackage in Xcode via 'Add Local Package…'"
