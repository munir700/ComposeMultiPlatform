//
//  GoogleMapsView.swift
//  iosApp
//
//  Created by Munir on 10/16/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import UIKit
import GoogleMaps
import shared

final class GoogleMapView: GMSMapView, IosNativeMap {
    private var onLocationSelected: ((Location) -> Void)?
    private var currentMarker: GMSMarker?
    private var zoomLevel: Float?
    
    init(frame: CGRect, config: UIViewType.GoogleMapsView) {
        let options = GMSMapViewOptions()
        let camera:GMSCameraPosition
        if let location = config.selectedLocation{
            camera = GMSCameraPosition.camera(
                withLatitude: location.latitude?.doubleValue ?? 0.0,
                longitude: location.longitude?.doubleValue ?? 0.0,
                zoom: config.zoomLevel
            )
        } else{
            camera = GMSCameraPosition.camera(
                withLatitude: 0.0,
                longitude: 0.0,
                zoom: config.zoomLevel
            )
        }
        options.camera = camera
        options.frame = frame
        
        super.init(options: options)
        setupMap(config: config)
    }
    
    required init?(coder: NSCoder) {
           fatalError("init(coder:) has not been implemented")
       }
    
    private func setupMap(config: UIViewType.GoogleMapsView) {
        self.delegate = self
        self.settings.myLocationButton = config.isLocationEnabled
        self.isMyLocationEnabled = config.isLocationEnabled
        
        self.onLocationSelected = config.onLocationSelected
        self.zoomLevel = config.zoomLevel
        
        // Add initial marker if location is provided
        if let location = config.selectedLocation {
            let position = CLLocationCoordinate2D(
                latitude: CLLocationDegrees(truncating: location.latitude ?? 0.0),
                longitude: CLLocationDegrees(truncating: location.longitude ?? 0.0)
            )
            addMarker(at: position)
            print("added marker")
        }
        self.backgroundColor = .red
    }
    
    private func addMarker(at coordinate: CLLocationCoordinate2D) {
        // Remove existing marker
        currentMarker?.map = nil
        
        // Add new marker
        let marker = GMSMarker(position: coordinate)
        marker.map = self
        currentMarker = marker
    }
    
    func updateSelectedLocation(location: Location) {
        self.camera = GMSCameraPosition.camera(
            withLatitude: location.latitude?.doubleValue ?? 0.0,
            longitude: location.longitude?.doubleValue ?? 0.0,
            zoom: zoomLevel ?? 10
        )
        
        let position = CLLocationCoordinate2D(
            latitude: CLLocationDegrees(truncating: location.latitude ?? 0.0),
            longitude: CLLocationDegrees(truncating: location.longitude ?? 0.0)
        )
        addMarker(at: position)
    }
}

extension GoogleMapView: GMSMapViewDelegate {
    func mapView(_ mapView: GMSMapView, didTapAt coordinate: CLLocationCoordinate2D) {
        addMarker(at: coordinate)
        
        let location = Location(
            latitude: KotlinDouble(value: coordinate.latitude),
            longitude: KotlinDouble(value: coordinate.longitude)
        )
        onLocationSelected?(location)
    }
    
    func mapView(_ mapView: GMSMapView, idleAt position: GMSCameraPosition) {
        let zoom = position.zoom
        self.zoomLevel = zoom
    }
}
