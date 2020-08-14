package com.cnakhn.faradarscompletion.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cnakhn.faradarscompletion.R;
import com.cnakhn.faradarscompletion.Utils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    private GoogleMap googleMap;
    private Location userLocation, destinationLocation;
    private Marker userMarker, destinationMarker;
    private TextView tvDistance;
    private static final int REQ_LOCATION_PERM_CODE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        initViews();

    }

    private void initViews() {
        Utils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorGreyDark));
        Toolbar toolbar = findViewById(R.id.tool_bar_maps);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        tvDistance = findViewById(R.id.tv_distance);
    }

    private void setupLocationManager() {
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (manager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQ_LOCATION_PERM_CODE);

                return;
            }
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                onLocationChanged(location);
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, this);
            }
        }

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                destinationLocation = new Location(LocationManager.GPS_PROVIDER);
                destinationLocation.setLatitude(latLng.latitude);
                destinationLocation.setLongitude(latLng.longitude);
                if (destinationMarker != null) {
                    destinationMarker.remove();
                } else {
                    tvDistance.setVisibility(View.VISIBLE);
                }
                tvDistance.setText(String.format("%s %s %s", getResources().getString(R.string.map_distance_label), userLocation.distanceTo(destinationLocation) / 100, getResources().getString(R.string.map_distance_metric_label)));
                destinationMarker = googleMap.addMarker(new MarkerOptions().position(latLng).title("Your Destination").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_destination)));
            }
        });
        setupLocationManager();
    }

    @Override
    public void onLocationChanged(Location location) {
        if ((location != null) && (googleMap != null)) {
            userLocation = location;
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            if (userMarker != null) {
                userMarker.remove();
            }
            userMarker = googleMap.addMarker(new MarkerOptions().position(latLng).title("Current Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_position)));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 20);
            googleMap.animateCamera(cameraUpdate);
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_LOCATION_PERM_CODE) {
            if (grantResults[0] == 0 && grantResults[1] == 0) {
                setupLocationManager();
            }
        }
    }
}