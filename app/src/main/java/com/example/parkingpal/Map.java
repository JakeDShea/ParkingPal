package com.example.parkingpal;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends Fragment {
    private GoogleMap map;

    private boolean locationPermissionGranted = true;
    private Location lastKnownLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private Button switchLocationsButton;
    private boolean hasAddedMarker = false;
    public String currentAddress;
    private final String kaplanAddress = "751 Ukrop Way, Williamsburg, VA 23185";
    private String parkingDeckAddress = "24 Ukrop Way, Williamsburg, VA 23185";
    //37.2735° N, 76.7197° W

    private final LatLng kaplanParkingLot = new LatLng(37.2756, -76.7197);
    private final LatLng parkingDeck = new LatLng(37.2680, -76.7182);
    private static final int DEFAULT_ZOOM = 17;
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    //private PlacesClient placesClient;
    private hardcodedLocations currentLocation = hardcodedLocations.KaplanParkingLot;
    Marker currentMarker;
    private hardcodedLocations parkedLocation;
    private TextView destinationAddress;
    private TextView parkingLocation;
    private LocationTracker locationTracker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        this.locationTracker = new LocationTracker(this, this.getContext());
        Location location = this.locationTracker.getLocation();
        // map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                map = googleMap;
                moveCameraTo(getCurrentLocation());
                // when map is loaded
                /*googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        //get coordinates
                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                        googleMap.clear();
                        // map zoom
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                latLng, 10
                        ));
                        // add red marker
                        googleMap.addMarker(markerOptions);
                    }
                }); */

            }
        });
        //testing out distance function
        this.getDistanceBetweenTwoPoints(this.kaplanParkingLot, this.parkingDeck);

        //initialize location tracker
        //Location location = this.locationTracker.getLocation();



        return view;
    }

    //temporarily switches between kaplan parking and parking deck on map view
    public void switchLocations(){
        if (currentLocation == hardcodedLocations.KaplanParkingLot){
            currentLocation = hardcodedLocations.ParkingDeck;
            switchLocationsButton.setText("GOTO: Kaplan Arena Parking");
        }
        else if (currentLocation == hardcodedLocations.ParkingDeck){
            currentLocation = hardcodedLocations.KaplanParkingLot;
            switchLocationsButton.setText("GOTO: Parking Deck");
        }

        moveCameraTo(getCurrentLocation());
    }

    private LatLng getCurrentLocation(){
        if (currentLocation == hardcodedLocations.KaplanParkingLot){
            currentAddress = kaplanAddress;
            return kaplanParkingLot;
        }
        else if (currentLocation == hardcodedLocations.ParkingDeck){
            currentAddress = parkingDeckAddress;
            return parkingDeck;
        }
        else{
            return null;
        }
    }

    private void moveCameraTo(LatLng location){
        if(this.map == null){
            return;
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, DEFAULT_ZOOM));
        //addMarkerAtCurrentLocation();

    }

    public void setCurrentLocation(hardcodedLocations newLocation){
        currentLocation = newLocation;
        moveCameraTo(getCurrentLocation());

        //test
        Location location = this.locationTracker.getLocation();
    }
    public void addMarkerAtCurrentLocation(){
        parkedLocation = currentLocation;
        if (hasAddedMarker){
            currentMarker.setPosition(getCurrentLocation());
            return;
        }
        String markerText = "No Location Found";
        markerText = "Currently Parked";
        MarkerOptions marker = new MarkerOptions()
                .position(getCurrentLocation())
                .title(markerText);
        currentMarker = map.addMarker(marker);
        hasAddedMarker = true;

    }
    enum hardcodedLocations{
        KaplanParkingLot,
        ParkingDeck
    }
    public String getCurrentDesinationAddress(){
        if (currentLocation == hardcodedLocations.KaplanParkingLot){
            return kaplanAddress;
        }
        else if (currentLocation == hardcodedLocations.ParkingDeck){
            return parkingDeckAddress;
        }
        else{
            return "Not set";
        }
    }
    public String getCurrentParkedAddress(){
        if (parkedLocation == hardcodedLocations.KaplanParkingLot){
            return kaplanAddress;
        }
        else if (parkedLocation == hardcodedLocations.ParkingDeck){
            return parkingDeckAddress;
        }
        else{
            return "Not set";
        }
    }

    private float[] getDistanceBetweenTwoPoints(LatLng pointOne, LatLng pointTwo){
        float[] results = new float[1];
        Location.distanceBetween(pointOne.latitude, pointOne.longitude,
                pointTwo.latitude, pointTwo.longitude, results);

        System.out.println(results[0]);
        System.out.println("result is in meters");
        return results;
    }

    public float getDistanceToDestinationFromCurrent(LatLng destination){
        LatLng dest = new LatLng(destination.latitude, destination.longitude);
        Location cur = this.locationTracker.getLocation();
        if(cur == null){
            return -1;
        }
        LatLng curLatLng = new LatLng(cur.getLatitude(), cur.getLongitude());
        return getDistanceBetweenTwoPoints(curLatLng, dest)[0];
    }

    public LatLng getDestinationLatLng(){
        if (this.currentLocation == hardcodedLocations.KaplanParkingLot){
            return this.kaplanParkingLot;
        }
        else if (this.currentLocation == hardcodedLocations.ParkingDeck){
            return this.parkingDeck;
        }
        else{
            return null;
        }
    }
}

