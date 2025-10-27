//
//  AppDelegate.swift
//  iosApp
//
//  Created by Munir on 13/10/2025.
//


import UIKit
import Firebase
import shared


class AppDelegate: UIResponder, UIApplicationDelegate, UNUserNotificationCenterDelegate {
    private var notificationManager: INotificationManager? = nil
    private var appViewModel: AppViewModel? = nil
    
    func application(_ application: UIApplication, 
                    didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        initialize(application: application)
        return true
    }
    
    private func initialize(application: UIApplication) {
            FirebaseApp.configure()
            
            UNUserNotificationCenter.current().delegate = self
            injectDiObjects()
            appViewModel?.setEvent(event: AppContract.EventInit())
        }
    
    private func injectDiObjects() {
        notificationManager = DiProvider().get(clazz: INotificationManagerKt.Class) as INotificationManager
        appViewModel = DiProvider().get(clazz: AppViewModelKt.Class) as AppViewModel
       }
}
