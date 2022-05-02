package com.example.parkingpal;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
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

    // TODO: Do we need this?
    private Location lastKnownLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private Button switchLocationsButton;
    private boolean hasAddedMarker = false;
    public String currentAddress;

    /// LOT ADDRESSES
    private final String boundaryLot = "206 S Boundary St, Williamsburg, VA 23185";
    private final String jamestownLot = "232 Jamestown Rd, Williamsburg, VA 23185";
    private final String richmondLot = "732 Scotland St, Williamsburg, VA 23185";
    private final String adairGravelLot = "(Gravel  lot behind Adair Hall via Ukrop Way)";
    private final String admissionLot = "114 Grigsby Dr, Williamsburg, VA 23185";
    private final String alumniHouseLot = "500 Richmond Rd, Williamsburg, VA 23185";
    private final String armisteadLot = "196 Armistead Ave, Williamsburg, VA 23185";
    private final String boswellLot = "656 Jamestown Rd, Williamsburg, VA 23185";
    private final String bridgesLot = "228 Jamestown Rd, Williamsburg, VA 23185";
    private final String brooksSt = "Common's Park, Williamsburg, VA 23188";
    private final String brooksSt2 = "Brooks St, Williamsburg, VA 23185";
    private final String bryanLot = "100 Stadium Dr, Williamsburg, VA 23186";
    private final String cammLot = "300 Richmond Rd, Williamsburg, VA 23186";
    private final String collegeTerraceSt = "Williamsburg, Virginia 23185";
    private final String commonsLot = " lot, Williamsburg, VA 23188";
    private final String commuterHouseLot = "404 Jamestown Rd, Williamsburg, VA 23185";
    private final String comptonLot = "751 Ukrop Way, Williamsburg, VA 23185";
    private final String cornerHouseLot = "402 VA-5, Williamsburg, VA 23185";
    private final String dawsonCircleLot = " lot, Williamsburg, VA 23186";
    private final String dillardLot = "Williamsburg, Virginia";
    private final String fraternityComplexLot = "Williamsburg, Virginia";
    private final String gradComplexLot = " lot, Williamsburg, VA 23185";
    private final String grigsbyDrSt = "108 Jamestown Rd, Williamsburg, VA 23185";
    private final String harrisonAveLot = "700 Ukrop Way, Williamsburg, VA 23185";
    private final String huntCircleLot = "108 Jamestown Rd, Williamsburg, VA 23185";
    private final String jamesBlairDrSt = "250 James Blair Dr, Williamsburg, VA 23185";
    private final String jamesBlairLot = " lot, Williamsburg, VA 23186";
    private final String jimmyeLaycockCenter = "101-100 Gooch Dr, Williamsburg, VA 23186";
    private final String jonesLot = "Williamsburg, Virginia 23185";
    private final String kaplanArenaLot = "751 Ukrop Way, Williamsburg, VA 23185";
    private final String keckLabLot = "Williamsburg, Virginia 23188";
    private final String keckLabRd = "100 Wake Dr, Williamsburg, VA 23188";
    private final String kingHealthCenterLot = "230 Gooch Dr, Williamsburg, VA 23185";
    private final String landrumSt = "540 Landrum Dr, Williamsburg, VA 23185";
    private final String lawSchoolLot = " lot, Williamsburg, VA 23185";
    private final String legacyCircleLot = "209-231 Jamestown Rd, Williamsburg, VA 23185";
    private final String ludwellLot = " lot, Rolfe Rd, Williamsburg, VA 23185";
    private final String maintenanceLot = "14 Grigsby Dr, Williamsburg, VA 23185";
    private final String mcclurgSt = "McClurg Dr, Williamsburg, VA 23185";
    private final String mccormackNagelsenTennisCenterLot = "Endocrinology (Pop) Lab., Williamsburg, VA 23185";
    private final String mcglothlinStHallLot = "251 Jamestown Rd, Williamsburg, VA 23185";
    private final String oldDominionLot = "Zipcar, and, Stadium Drive, James Blair Dr, Williamsburg, VA 23187";
    private final String parkingDeck = "24 Ukrop Way, Williamsburg, VA 23185";
    private final String phiBetaKappaCircleLot= "601 Jamestown Rd, Williamsburg, VA 23185";
    private final String plantLot = "Williamsburg, VA 23185";
    private final String presbyterianLot = "616 Prince George St, Williamsburg, VA 23185";
    private final String rolfeRdSt = "Rolfe Rd, Williamsburg, VA 23185";
    private final String roweHouseLot = "336 Jamestown Rd, Williamsburg, VA 23185";
    private final String sadlerCenterLot = " lot, Williamsburg, VA";
    private final String schoolOfEducationLot = " lot, Williamsburg, VA 23185";
    private final String sororityCourtLot = "110 N Boundary St, Williamsburg, VA 23185";
    private final String stadiumDrLot = "100 Stadium Dr, Williamsburg, VA 23186";
    private final String swemFacultyStaffLot = " lot, Williamsburg, VA 23185";
    private final String swemLot = "Earl Gregg Swem Library, Williamsburg, VA 23185";
    private final String tribeSquareLot = " lot, Williamsburg, VA 23185";
    private final String ukropWaySt = "126-236 Ukrop Way, Williamsburg, VA 23185";
    private final String vimsRaleighLot = "Gloucester Point, Virginia 23062";
    private final String vimsTriangleLot = "Gloucester Point District, Gloucester Point, VA";
    private final String wakeDrSt = "100 Wake Dr, Williamsburg, VA 23188";
    private final String washingtonHallLot = "Williamsburg, Virginia 23185";
    private final String yatesDrSt = "Yates Dr, Williamsburg, VA 23185";
    private final String yatesLot = "566 Ukrop Way, Williamsburg, VA 23188";
    private final String zipcarOldDominionLot = "Williamsburg, Virginia 23185";
    private final String zipcarWakeDr = "100-116 Wake Dr, Williamsburg, VA 23185";
    //37.2735° N, 76.7197° W

    // Lot Coordinates
    private final LatLng boundaryLotCoord = new LatLng(37.26864604615994, -76.70751686380015);
    private final LatLng jamestownLotCoord = new LatLng(37.26893936998421, -76.70989764828418);
    private final LatLng richmondLotCoord = new LatLng(37.27367029620086, -76.71171439442458);
    private final LatLng adairGravelLotCoord = new LatLng(37.26928378883404, -76.71835122963023);
    private final LatLng admissionLotCoord = new LatLng(37.26955480651268, -76.70889000911058);
    private final LatLng alumniHouseLotCoord = new LatLng(37.275725811521795, -76.71371436677555);
    private final LatLng armisteadLotCoord = new LatLng(37.27318667331704, -76.70918249137087);
    private final LatLng boswellLotCoord = new LatLng(37.26746352908389, -76.71679261605637);
    private final LatLng bridgesLotCoord = new LatLng(37.26913566192235, -76.70977059324117);
    private final LatLng brooksStParkingCoord = new LatLng(37.27319767245215, -76.72046553861658);
    private final LatLng brooksStParking2Coord = new LatLng(37.274364868542975, -76.72075015266111);
    private final LatLng bryanLotCoord = new LatLng(37.27724138300784, -76.71370943861001);
    private final LatLng cammLotCoord = new LatLng(37.27349434966323, -76.71268413360774);
    private final LatLng collegeTerraceStParkingCoord = new LatLng(37.275419949107516, -76.71589013857451);
    private final LatLng commonsLotCoord = new LatLng(37.27264627434025, -76.72061196012226);
    private final LatLng commuterHouseLotCoord = new LatLng(37.268498519460515, -76.71219983618312);
    private final LatLng comptonLotCoord = new LatLng(37.27420151015906, -76.71972451875408);
    private final LatLng cornerHouseLotCoord = new LatLng(37.26839432996833, -76.71178200222649);
    private final LatLng dawsonCircleLotCoord = new LatLng(37.27279856506131, -76.71198013885189);
    private final LatLng dillardLotCoord = new LatLng(37.29075986803421, -76.73114856228938);
    private final LatLng fraternityComplexLotCoord = new LatLng(37.27520505819804, -76.71616339581634);
    private final LatLng gradComplexLotCoord = new LatLng(37.263845588802155, -76.70574249340805);
    private final LatLng grigsbyDrStParkingCoord = new LatLng(37.268925561583025, -76.70828599324747);
    private final LatLng harrisonAveLotCoord = new LatLng(37.27666024965606, -76.71800790688978);
    private final LatLng huntCircleLotCoord = new LatLng(37.27001842139828, -76.70759934776659);
    private final LatLng jamesBlairDrStParkingCoord = new LatLng(37.272402690641854, -76.71277836139727);
    private final LatLng jamesBlairLotCoord = new LatLng(37.272101772178765, -76.71257833864126);
    private final LatLng jimmyeLaycockCenterParkingCoord = new LatLng(37.27438616490452, -76.7157041158487);
    private final LatLng jonesLotCoord = new LatLng(37.267521138219216, -76.71693829328143);
    private final LatLng kaplanArenaLotCoord = new LatLng(37.27629794717896, -76.7195415930509);
    private final LatLng keckLabLotCoord = new LatLng(37.27078328607493, -76.72345276142948);
    private final LatLng keckLabRdParkingCoord = new LatLng(37.270206975993936, -76.72292510691945);
    private final LatLng kingHealthCenterLotCoord = new LatLng(37.273265390921345, -76.71675693864736);
    private final LatLng landrumStParkingCoord = new LatLng(37.27024730327673, -76.71446709323241);
    private final LatLng lawSchoolLotCoord = new LatLng(37.265433680535935, -76.70633443886587);
    private final LatLng legacyCircleLotCoord = new LatLng(37.27078711148255, -76.71023293870532);
    private final LatLng ludwellLotCoord = new LatLng(37.26238005101276, -76.71957611618427);
    private final LatLng maintenanceLotCoord = new LatLng(37.2686649790987, -76.7092800932635);
    private final LatLng mcclurgStParkingCoord = new LatLng(37.27812307311119, -76.719407938518);
    private final LatLng mccormackNagelsenTennisCenterLotCoord = new LatLng(37.26243646183668, -76.70604802917121);
    private final LatLng mcglothlinStHallLotCoord = new LatLng(37.269719349745834, -76.7120946159559);
    private final LatLng oldDominionLotCoord = new LatLng(37.27299068926199, -76.71337321590696);
    private final LatLng parkingDeckCoord = new LatLng(37.26862351833031, -76.71791577054069);
    private final LatLng phiBetaKappaCircleLotCoord = new LatLng(37.26819091835602, -76.71526132496625);
    private final LatLng plantLotCoord = new LatLng(37.268716017472805, -76.70981948424011);
    private final LatLng presbyterianLotCoord = new LatLng(37.272797067836436, -76.70989931587178);
    private final LatLng rolfeRdStParkingCoord = new LatLng(37.26351622707459, -76.72030366164745);
    private final LatLng roweHouseLotCoord = new LatLng(37.2693663114442, -76.71133249325884);
    private final LatLng sadlerCenterLotCoord = new LatLng(37.27323268526201, -76.71603443864012);
    private final LatLng schoolOfEducationLotCoord = new LatLng(37.27995799317047, -76.72456598391103);
    private final LatLng sororityCourtLotCoord = new LatLng(37.27228497760192, -76.70799981590355);
    private final LatLng stadiumDrLotCoord = new LatLng(37.2732155699361, -76.71406571586742);
    private final LatLng swemFacultyStaffLotCoord = new LatLng(37.27056070037129, -76.71771201596346);
    private final LatLng swemLotCoord = new LatLng(37.270852510638925, -76.71641081596289);
    private final LatLng tribeSquareLotCoord = new LatLng(37.27321406851004, -76.7115826385997);
    private final LatLng ukropWayStParkingCoord = new LatLng(37.26609978123486, -76.71770329330765);
    private final LatLng vimsRaleighLotCoord = new LatLng(37.24909725469868, -76.5001233710853);
    private final LatLng vimsTriangleLotCoord = new LatLng(37.25086936324949, -76.49977532465512);
    private final LatLng wakeDrStParkingCoord = new LatLng(37.27102660839312, -76.72120849321725);
    private final LatLng washingtonHallLotCoord = new LatLng(37.27005987629012, -76.71156403870253);
    private final LatLng yatesDrStParkingCoord = new LatLng(37.274313267400686, -76.71855073860773);
    private final LatLng yatesLotCoord = new LatLng(37.272665887246596, -76.71885046138938);
    private final LatLng zipcarOldDominionLotCoord = new LatLng(37.27238389088436, -76.71216639317652);
    private final LatLng zipcarWakeDrCoord = new LatLng(37.26897880439169, -76.72025189321309);
    private static final int DEFAULT_ZOOM = 17;
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    //private PlacesClient placesClient;
    private hardcodedLocations currentLocation = hardcodedLocations.KaplanArena;
    Marker currentMarker;
    private hardcodedLocations parkedLocation;
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

                // TODO: Do we need these comments?
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
        this.getDistanceBetweenTwoPoints(this.kaplanArenaLotCoord, this.parkingDeckCoord);

        // TODO: Do we need these comments?
        //initialize location tracker
        //Location location = this.locationTracker.getLocation();



        return view;
    }

    // TODO: Do we need this
    //temporarily switches between kaplan parking and parking deck on map view
    public void switchLocations(){
        if (currentLocation == hardcodedLocations.KaplanArena){
            currentLocation = hardcodedLocations.ParkingDeck;
            switchLocationsButton.setText("GOTO: Kaplan Arena Parking");
        }
        else if (currentLocation == hardcodedLocations.ParkingDeck){
            currentLocation = hardcodedLocations.KaplanArena;
            switchLocationsButton.setText("GOTO: Parking Deck");
        }

        moveCameraTo(getCurrentLocation());
    }

    private LatLng getCurrentLocation(){
        switch (currentLocation) {
            case BoundaryLot: currentAddress = boundaryLot;return boundaryLotCoord;
            case JamestownParkingLot: currentAddress = jamestownLot; return jamestownLotCoord;
            case RichmondLot: currentAddress = richmondLot; return richmondLotCoord;
            case AdairLot: currentAddress = adairGravelLot; return stadiumDrLotCoord;
            case AdmissionLot: currentAddress = admissionLot; return admissionLotCoord;
            case AlumniLot: currentAddress = alumniHouseLot; return alumniHouseLotCoord;
            case ArmisteadLot: currentAddress = armisteadLot; return armisteadLotCoord;
            case BoswellLot: currentAddress = boswellLot; return boswellLotCoord;
            case BridgesLot: currentAddress = bridgesLot; return bridgesLotCoord;
            case BrooksParking: currentAddress = brooksSt; return brooksStParkingCoord;
            case BrooksParking2: currentAddress = brooksSt2; return brooksStParking2Coord;
            case BryanLot: currentAddress = bryanLot; return bryanLotCoord;
            case CammLot: currentAddress = cammLot; return cammLotCoord;
            case CollegeTerraceParking: currentAddress = collegeTerraceSt; return collegeTerraceStParkingCoord;
            case CommonsLot: currentAddress = commonsLot; return commonsLotCoord;
            case CommuterHouseLot: currentAddress = commuterHouseLot; return commonsLotCoord;
            case ComptonLot: currentAddress = comptonLot; return comptonLotCoord;
            case CornerHouseLot: currentAddress = cornerHouseLot; return cornerHouseLotCoord;
            case DawsonCircleLot: currentAddress = dawsonCircleLot; return dawsonCircleLotCoord;
            case DillardLot: currentAddress = dillardLot; return dillardLotCoord;
            case FraternityRearLot: currentAddress = fraternityComplexLot; return fraternityComplexLotCoord;
            case GraduateLot: currentAddress = gradComplexLot; return gradComplexLotCoord;
            case GrigsbyParking: currentAddress = grigsbyDrSt; return grigsbyDrStParkingCoord;
            case HarrisonAvenueLot: currentAddress = harrisonAveLot; return harrisonAveLotCoord;
            case HuntCircleLot: currentAddress = huntCircleLot; return huntCircleLotCoord;
            case JamesBlairStreetParking: currentAddress = jamesBlairDrSt; return jamesBlairDrStParkingCoord;
            case JamesBlairLot: currentAddress = jamesBlairLot; return jamesBlairLotCoord;
            case JimmyeLaycockParking: currentAddress = jimmyeLaycockCenter; return jimmyeLaycockCenterParkingCoord;
            case JonesLot: currentAddress = jonesLot; return jonesLotCoord;
            case KaplanArena: currentAddress = kaplanArenaLot; return kaplanArenaLotCoord;
            case KeckLabLot: currentAddress = keckLabLot; return keckLabLotCoord;
            case KeckLabRoadParking: currentAddress = keckLabRd; return keckLabRdParkingCoord;
            case KingHealthCenterLot: currentAddress = kingHealthCenterLot; return kingHealthCenterLotCoord;
            case LandrumParking: currentAddress = landrumSt; return landrumStParkingCoord;
            case LawSchoolLot: currentAddress = lawSchoolLot; return lawSchoolLotCoord;
            case LegacyCircleLot: currentAddress = legacyCircleLot; return legacyCircleLotCoord;
            case LudwellLot: currentAddress = ludwellLot; return ludwellLotCoord;
            case MaintenanceLot: currentAddress = maintenanceLot; return maintenanceLotCoord;
            case McClurgParking: currentAddress = mcclurgSt; return mcclurgStParkingCoord;
            case McCormackNagelsenLot: currentAddress = mccormackNagelsenTennisCenterLot; return mccormackNagelsenTennisCenterLotCoord;
            case McGlothlinStreetHallParkingLot: currentAddress = mcglothlinStHallLot; return mcglothlinStHallLotCoord;
            case OldDominionLot: currentAddress = oldDominionLot; return oldDominionLotCoord;
            case ParkingDeck: currentAddress = parkingDeck; return parkingDeckCoord;
            case PBKLot: currentAddress = phiBetaKappaCircleLot; return phiBetaKappaCircleLotCoord;
            case PlantLot: currentAddress = plantLot; return plantLotCoord;
            case PresbyterianLot: currentAddress = presbyterianLot; return presbyterianLotCoord;
            case RolfeRoadParking: currentAddress = rolfeRdSt; return rolfeRdStParkingCoord;
            case RoweHouseLot: currentAddress = roweHouseLot; return roweHouseLotCoord;
            case SadlerCenterLot: currentAddress = sadlerCenterLot; return sadlerCenterLotCoord;
            case SchoolofEducationLot: currentAddress = schoolOfEducationLot; return schoolOfEducationLotCoord;
            case SororityCourtLot: currentAddress = sororityCourtLot; return sororityCourtLotCoord;
            case StadiumDriveLot: currentAddress = stadiumDrLot; return stadiumDrLotCoord;
            case SwemFaculty: currentAddress = swemFacultyStaffLot; return swemFacultyStaffLotCoord;
            case SwemLot: currentAddress = swemLot; return swemLotCoord;
            case TribeSquareLot: currentAddress = tribeSquareLot; return tribeSquareLotCoord;
            case UkropWayParking: currentAddress = ukropWaySt; return ukropWayStParkingCoord;
            case VIMSRaleighLot: currentAddress = vimsRaleighLot; return vimsRaleighLotCoord;
            case VIMSTriangleLots: currentAddress = vimsTriangleLot; return vimsTriangleLotCoord;
            case WakeDriveParking: currentAddress = wakeDrSt; return wakeDrStParkingCoord;
            case WashingtonHallLot: currentAddress = washingtonHallLot; return washingtonHallLotCoord;
            case YatesDriveParking: currentAddress = yatesDrSt; return yatesDrStParkingCoord;
            case YatesLot: currentAddress = yatesLot; return yatesLotCoord;
            case ZipcarOldDominionLot: currentAddress = zipcarOldDominionLot; return zipcarOldDominionLotCoord;
            case ZipcarWakeDrive: currentAddress = zipcarWakeDr; return zipcarWakeDrCoord;
            default: return null;
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

    public hardcodedLocations getLocation(){return currentLocation;}

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
        BoundaryLot, JamestownParkingLot, RichmondLot, AdairLot, AdmissionLot,
        AlumniLot, ArmisteadLot, BoswellLot, BridgesLot, BrooksParking, BrooksParking2,
        BryanLot, CammLot, CollegeTerraceParking, CommonsLot, CommuterHouseLot, ComptonLot,
        CornerHouseLot, DawsonCircleLot, DillardLot, FraternityRearLot, GraduateLot,
        GrigsbyParking, HarrisonAvenueLot, HuntCircleLot, JamesBlairStreetParking, JamesBlairLot,
        JimmyeLaycockParking, JonesLot, KaplanArena, KeckLabLot, KeckLabRoadParking,
        KingHealthCenterLot, LandrumParking, LawSchoolLot, LegacyCircleLot, LudwellLot,
        MaintenanceLot, McClurgParking, McCormackNagelsenLot, McGlothlinStreetHallParkingLot,
       OldDominionLot, ParkingDeck, PBKLot, PlantLot, PresbyterianLot,
        RolfeRoadParking, RoweHouseLot, SadlerCenterLot, SchoolofEducationLot, SororityCourtLot,
        StadiumDriveLot, SwemFaculty, SwemLot, TribeSquareLot, UkropWayParking, VIMSRaleighLot,
        VIMSTriangleLots, WakeDriveParking, WashingtonHallLot, YatesDriveParking, YatesLot, ZipcarOldDominionLot,
        ZipcarWakeDrive
    }

    public String getCurrentDesinationAddress(){
        switch (currentLocation){
            case BoundaryLot: return boundaryLot;
            case JamestownParkingLot: return jamestownLot;
            case RichmondLot: return richmondLot;
            case AdairLot: return adairGravelLot;
            case AdmissionLot: return admissionLot;
            case AlumniLot: return alumniHouseLot;
            case ArmisteadLot: return armisteadLot;
            case BoswellLot: return boswellLot;
            case BridgesLot: return bridgesLot;
            case BrooksParking: return brooksSt;
            case BrooksParking2: return brooksSt2;
            case BryanLot: return bryanLot;
            case CammLot: return cammLot;
            case CollegeTerraceParking: return collegeTerraceSt;
            case CommonsLot: return commonsLot;
            case CommuterHouseLot: return commuterHouseLot;
            case ComptonLot: return comptonLot;
            case CornerHouseLot: return cornerHouseLot;
            case DawsonCircleLot: return dawsonCircleLot;
            case DillardLot: return dillardLot;
            case FraternityRearLot: return fraternityComplexLot;
            case GraduateLot: return gradComplexLot;
            case GrigsbyParking: return grigsbyDrSt;
            case HarrisonAvenueLot: return harrisonAveLot;
            case HuntCircleLot: return huntCircleLot;
            case JamesBlairStreetParking: return jamesBlairDrSt;
            case JamesBlairLot: return jamesBlairLot;
            case JimmyeLaycockParking: return jimmyeLaycockCenter;
            case JonesLot: return jonesLot;
            case KaplanArena: return kaplanArenaLot;
            case KeckLabLot: return keckLabLot;
            case KeckLabRoadParking: return keckLabRd;
            case KingHealthCenterLot: return kingHealthCenterLot;
            case LandrumParking: return landrumSt;
            case LawSchoolLot: return lawSchoolLot;
            case LegacyCircleLot: return legacyCircleLot;
            case LudwellLot: return ludwellLot;
            case MaintenanceLot: return maintenanceLot;
            case McClurgParking: return mcclurgSt;
            case McCormackNagelsenLot: return mccormackNagelsenTennisCenterLot;
            case McGlothlinStreetHallParkingLot: return mcglothlinStHallLot;
            case OldDominionLot: return oldDominionLot;
            case ParkingDeck: return parkingDeck;
            case PBKLot: return phiBetaKappaCircleLot;
            case PlantLot: return plantLot;
            case PresbyterianLot: return presbyterianLot;
            case RolfeRoadParking: return rolfeRdSt;
            case RoweHouseLot: return roweHouseLot;
            case SadlerCenterLot: return sadlerCenterLot;
            case SchoolofEducationLot: return schoolOfEducationLot;
            case SororityCourtLot: return sororityCourtLot;
            case StadiumDriveLot: return stadiumDrLot;
            case SwemFaculty: return swemFacultyStaffLot;
            case SwemLot: return swemLot;
            case TribeSquareLot: return tribeSquareLot;
            case UkropWayParking: return ukropWaySt;
            case VIMSRaleighLot: return vimsRaleighLot;
            case VIMSTriangleLots: return vimsTriangleLot;
            case WakeDriveParking: return wakeDrSt;
            case WashingtonHallLot: return washingtonHallLot;
            case YatesDriveParking: return yatesDrSt;
            case YatesLot: return yatesLot;
            case ZipcarOldDominionLot: return zipcarOldDominionLot;
            case ZipcarWakeDrive: return zipcarWakeDr;
            default: return "Not Set";
        }
    }
    public String getCurrentParkedAddress(){
        switch (currentLocation){
            case BoundaryLot: return boundaryLot;
            case JamestownParkingLot: return jamestownLot;
            case RichmondLot: return richmondLot;
            case AdairLot: return adairGravelLot;
            case AdmissionLot: return admissionLot;
            case AlumniLot: return alumniHouseLot;
            case ArmisteadLot: return armisteadLot;
            case BoswellLot: return boswellLot;
            case BridgesLot: return bridgesLot;
            case BrooksParking: return brooksSt;
            case BrooksParking2: return brooksSt2;
            case BryanLot: return bryanLot;
            case CammLot: return cammLot;
            case CollegeTerraceParking: return collegeTerraceSt;
            case CommonsLot: return commonsLot;
            case CommuterHouseLot: return commuterHouseLot;
            case ComptonLot: return comptonLot;
            case CornerHouseLot: return cornerHouseLot;
            case DawsonCircleLot: return dawsonCircleLot;
            case DillardLot: return dillardLot;
            case FraternityRearLot: return fraternityComplexLot;
            case GraduateLot: return gradComplexLot;
            case GrigsbyParking: return grigsbyDrSt;
            case HarrisonAvenueLot: return harrisonAveLot;
            case HuntCircleLot: return huntCircleLot;
            case JamesBlairStreetParking: return jamesBlairDrSt;
            case JamesBlairLot: return jamesBlairLot;
            case JimmyeLaycockParking: return jimmyeLaycockCenter;
            case JonesLot: return jonesLot;
            case KaplanArena: return kaplanArenaLot;
            case KeckLabLot: return keckLabLot;
            case KeckLabRoadParking: return keckLabRd;
            case KingHealthCenterLot: return kingHealthCenterLot;
            case LandrumParking: return landrumSt;
            case LawSchoolLot: return lawSchoolLot;
            case LegacyCircleLot: return legacyCircleLot;
            case LudwellLot: return ludwellLot;
            case MaintenanceLot: return maintenanceLot;
            case McClurgParking: return mcclurgSt;
            case McCormackNagelsenLot: return mccormackNagelsenTennisCenterLot;
            case McGlothlinStreetHallParkingLot: return mcglothlinStHallLot;
            case OldDominionLot: return oldDominionLot;
            case ParkingDeck: return parkingDeck;
            case PBKLot: return phiBetaKappaCircleLot;
            case PlantLot: return plantLot;
            case PresbyterianLot: return presbyterianLot;
            case RolfeRoadParking: return rolfeRdSt;
            case RoweHouseLot: return roweHouseLot;
            case SadlerCenterLot: return sadlerCenterLot;
            case SchoolofEducationLot: return schoolOfEducationLot;
            case SororityCourtLot: return sororityCourtLot;
            case StadiumDriveLot: return stadiumDrLot;
            case SwemFaculty: return swemFacultyStaffLot;
            case SwemLot: return swemLot;
            case TribeSquareLot: return tribeSquareLot;
            case UkropWayParking: return ukropWaySt;
            case VIMSRaleighLot: return vimsRaleighLot;
            case VIMSTriangleLots: return vimsTriangleLot;
            case WakeDriveParking: return wakeDrSt;
            case WashingtonHallLot: return washingtonHallLot;
            case YatesDriveParking: return yatesDrSt;
            case YatesLot: return yatesLot;
            case ZipcarOldDominionLot: return zipcarOldDominionLot;
            case ZipcarWakeDrive: return zipcarWakeDr;
            default: return "Not Set";
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
        return getCurrentLocation();
    }
}

