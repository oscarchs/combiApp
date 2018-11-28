package com.example.oscar.combinerito;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    Marker marker;
    LatLng Origin;
    LocationListener locationListener;
    Marker DestinyMarker;
    LatLng DestinyCoords;
    LatLng cmfb1 = new LatLng(-16.384235,-71.530413);
    LatLng umacollo = new LatLng(-16.402261, -71.551456);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //destiny = (Button) findViewById(R.id.destinybutton);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
        //GetBusStopService service = RetrofitInstance.getRetrofitInstance().create(GetBusStopService.class);


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                Origin = new LatLng(latitude, longitude);
                //marker = mMap.addMarker(new MarkerOptions().position(latLng));
                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 17));
                mMap.getUiSettings().setZoomControlsEnabled(true);
                if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);

                System.out.println("COORDENADAS SON =>   latitud: "+ latitude+ "    , longitud: "+longitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        GetBusStopService service = RetrofitInstance.getRetrofitInstance().create(GetBusStopService.class);

        Call<List<BusStop>> call = service.getBusStops();

        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<List<BusStop>>() {
            @Override
            public void onResponse(Call<List<BusStop>> call, Response<List<BusStop>> response) {
                for (BusStop element : response.body()) {
                    LatLng bus_coord = new LatLng(element.getLatitude(), element.getLongitude());
                    MarkerOptions marker = new MarkerOptions().position(bus_coord).title(element.getName());
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
                    mMap.addMarker(marker);
                }
            }

            @Override
            public void onFailure(Call<List<BusStop>> call, Throwable t) {
                Toast.makeText(MapsActivity.this, "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                mMap.clear();

                GetBusStopService service_a = RetrofitInstance.getRetrofitInstance().create(GetBusStopService.class);

                Call<List<BusStop>> call_2 = service_a.getBusStops();

                Log.wtf("URL Called", call_2.request().url() + "");

                call_2.enqueue(new Callback<List<BusStop>>() {
                    @Override
                    public void onResponse(Call<List<BusStop>> call_2, Response<List<BusStop>> response) {
                        for (BusStop element : response.body()) {
                            LatLng bus_coord = new LatLng(element.getLatitude(), element.getLongitude());
                            MarkerOptions marker = new MarkerOptions().position(bus_coord).title(element.getName());
                            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
                            mMap.addMarker(marker);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<BusStop>> call_2, Throwable t) {
                        Toast.makeText(MapsActivity.this, "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                DestinyCoords = point;
                DestinyMarker = mMap.addMarker(new MarkerOptions().position(point));
                Log.d("destiny_coords: ",DestinyCoords.toString());

                RoutePointService service = RetrofitInstance.getRetrofitInstance().create(RoutePointService.class);

                // ?lat1=-16.384235&long1=-71.530413&lat2=-16.402261&long2=-71.551456
                Map<String, String> data = new HashMap<>();
                data.put("lat1", String.valueOf(Origin.latitude));
                data.put("long1",String.valueOf(Origin.longitude));
                data.put("lat2", String.valueOf(DestinyCoords.latitude));
                data.put("long2", String.valueOf(DestinyCoords.longitude));

                Call<Point> call = service.getRoutePoint(data);

                Log.wtf("URL Called", call.request().url() + "");

                call.enqueue(new Callback<Point>() {
                    @Override
                    public void onResponse(Call<Point> call, Response<Point> response) {
                        Toast.makeText(MapsActivity.this, response.body().getRoutes_point(), Toast.LENGTH_LONG).show();
                        ArrayList points =null;
                        points = new ArrayList();
                        PolylineOptions lineOptions = null;
                        lineOptions = new PolylineOptions();
                        points = (ArrayList) decodePolyLines(response.body().getRoutes_point());

                        //    points.add(cmfb1);
                        //   points.add(umacollo);
                        lineOptions.addAll(points);
                        lineOptions.width(12);
                        lineOptions.color(Color.RED);
                        lineOptions.geodesic(true);
                        mMap.addPolyline(lineOptions);
                    }

                    @Override
                    public void onFailure(Call<Point> call, Throwable t) {
                        Toast.makeText(MapsActivity.this, "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;
        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters +"&key=AIzaSyD9IgLE8qRJn5he2BxthV2W8x-dwvadq8w";
        return url;
    }

    private String ParseDirections(LatLng latitude,LatLng longitude){
        String DirectionsPath = getDirectionsUrl(latitude,longitude);
        return DirectionsPath;

    }

    public static List<LatLng> decodePolyLines(String poly){
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<LatLng>();
        int lat = 0;
        int lng = 0;

        while (index < len){
            int b;
            int shift = 0;
            int result = 0;
            do{
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            }while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >>1));
            lng += dlng;

            decoded.add(new LatLng(
                    lat / 100000d,
                    lng / 100000d
            ));
        }
        return decoded;
    }

}