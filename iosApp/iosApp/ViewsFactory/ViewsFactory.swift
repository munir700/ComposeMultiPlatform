//
//  CustomViewCreator.swift
//  iosApp
//
//  Created by Munir Ahmad on 05/102025.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared
import UIKit

final class ViewsFactory : IViewsFactory {
    
    func create(type: UIViewControllerType) -> UIViewController {
        switch type {
            
        default:
            return UIViewController()
        }
    }
    
    func create(type_ type: UIViewType) -> UIView {
        switch type {
         case is UIViewType.GoogleMapsView:
            return GoogleMapView(
                frame: .zero,
                config: type as! UIViewType.GoogleMapsView
            )
        default:
            return UIView()
        }
    }
}
