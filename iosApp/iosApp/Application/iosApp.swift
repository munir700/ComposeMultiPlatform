//
//  iosAppApp.swift
//  iosApp
//
//  Created by Munir Ahmed on 9/17/25.
//

import SwiftUI
import shared

@main
struct iosApp: App {
    private let deepLinkManager: IDeepLinkManager

    init() {
        
        // Prepare environment
        var environment: AppEnvironment
        #if DEBUG
        // TODO: update to dev after finish testing
        environment = AppEnvironment.companion.dev
        #else
        environment = AppEnvironment.companion.prod
        #endif


        // Prepare app version
        let appVersion = (Bundle.main.infoDictionary?["CFBundleShortVersionString"]) as? String
        let appInfo = AppInfo(
            appVersion: appVersion ?? "6.0.0"
        )


        // Init koin
        Koin_iosKt.doInitKoin(
            environment: environment,
            appInfo: appInfo,
            viewsFactory: ViewsFactory(),
            componentContext: ComponentContextProvider().provide(),
            appInitializer: { () -> IAppInitializer in
                return AppInitializer()
            }
        )

        // Inject dependencies
        let diProvider = DiProvider()
        deepLinkManager = diProvider.get(clazz: IDeepLinkManagerKt.Class) as IDeepLinkManager

    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
