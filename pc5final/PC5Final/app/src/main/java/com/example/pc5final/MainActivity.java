package com.example.pc5final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;
    protected LatLng start=null;
    protected LatLng end=null;
    LatLng Fi[] = new LatLng[5];
    private final int TIEMPO = 10000;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        // Ubicar la actividad actual
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

//        Fi[0] = new LatLng(-12.0519504,-77.0286722);
//        Fi[1] = new LatLng(-12.0506177,-77.0303941);
//        Fi[2] = new LatLng(-12.0497964,-77.0259846);
//        Fi[3] = new LatLng(-12.0484026,-77.0391791);
//        Fi[4] = new LatLng(-12.0635041,-77.0378363);
//        -12.01782,-77.0549594
//        -12.0219597,-77.0548297

        Fi[0] = new LatLng(-12.0172283,-77.0562608);
        Fi[1] = new LatLng(-12.0222104,-77.0592219);
        Fi[2] = new LatLng(-12.0221054,-77.0534176);
        Fi[3] = new LatLng(-12.0222167,-77.0624624);
        Fi[4] = new LatLng(-12.0219597,-77.0548297);

//        Inkafarma
//        -12.0172283,-77.0562608
//        -12.0222104,-77.0592219
//        -12.0221054,-77.0534176
//        -12.0222167,-77.0624624
//        -12.0219597,-77.0548297

//        Fi[0] = new LatLng(-12.0440123,-76.9700501);
//        Fi[1] = new LatLng(-12.0383921,-76.9655691);
//        Fi[2] = new LatLng(-12.0530397,-76.9638875);
//        Fi[3] = new LatLng(-12.0452746,-76.9745826);
//        Fi[4] = new LatLng(-12.056981,-76.971236);

//        miubicacion();
        ejecutarTarea();
    }

    public void ejecutarTarea() {
        handler.postDelayed(new Runnable() {
            public void run() {
                miubicacion();
                handler.postDelayed(this, TIEMPO);
            }
        }, 1000);
    }

    public void miubicacion() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(@NonNull GoogleMap googleMap) {
                                googleMap.clear();
                                for (int i = 0; i < 5; i++) {
                                    MarkerOptions markerOptions = new MarkerOptions().position(Fi[i]).title("F"+i);
                                    googleMap.addMarker(markerOptions);
                                }
                                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                start = latLng;
                                // Crear marker
                                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Aqui Estoy").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                // Posicion de vision
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                                // Agregar el marker en el mapa
                                googleMap.addMarker(markerOptions);
                                double mindist = Double.POSITIVE_INFINITY;
                                for (int i = 0; i < 5; i++) {
                                    double dist = haversine(start, Fi[i]);
                                    if (dist < mindist){
                                        mindist = dist;
                                        end = Fi[i];
                                    }
                                }
                                Toast.makeText(MainActivity.this, end.toString(), Toast.LENGTH_LONG).show();
                                MarkerOptions minMarkerOptions = new MarkerOptions().position(end).title("Mas cercana").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                                googleMap.addMarker(minMarkerOptions);
                            }
                        });
                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 44);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                miubicacion();
                ejecutarTarea();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public double haversine(LatLng start, LatLng end){
        int r = 6371000;
        double c = Math.PI/180;

        double d = 2*r*Math.asin(Math.sqrt(Math.pow(Math.sin(c*(end.latitude-start.latitude)/2),2) + Math.cos(c*start.latitude)*Math.cos(c*end.latitude)*Math.pow(Math.sin(c*(end.longitude-start.longitude)/2),2)));
        return d;
    }
}