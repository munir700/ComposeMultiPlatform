//
//  AppInitializer.swift
//  iosApp
//
//  Created by Munir ahmad on 09/28/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import shared
import GoogleMaps

final class AppInitializer : IAppInitializer {
    
    func doInitGoogleMaps() {
        GMSServices.provideAPIKey("TODO add google map key")
    }
}
