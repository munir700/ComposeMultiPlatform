//
//  Koin+Extensions.swift
//  iosApp
//
//

import Foundation
import shared

extension DiProvider {

    func get<T: AnyObject>(clazz: KotlinKClass) -> T {
        return getDependency(clazz: clazz) as! T
    }
}
