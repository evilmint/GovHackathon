package govhackathon.pl.mobileapp.activities;

import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;

import govhackathon.pl.mobileapp.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        VisibleRegion region = mMap.getProjection().getVisibleRegion();

        Handler mainHandler = new Handler(this.getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    // Add a marker in Sydney and move the camera
                    final LatLng position = new LatLng(Math.random()*150, Math.random()*150);

                    final BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.cast_album_art_placeholder);

                    // Get a handler that can be used to post to the main thread
                    Handler mainHandler = new Handler(MapsActivity.this.getMainLooper());

                    Runnable updateRunnable = new Runnable() {
                        @Override
                        public void run() {
                            addMarker(position, "Marker test", "Description test", icon);
                        }
                    };

                    mainHandler.post(updateRunnable);

                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };

        new Thread(myRunnable).start();
    }

    private void clearMarkers()
    {
        mMap.clear();
    }

    public void addMarker(LatLng position, String title, String description, BitmapDescriptor icon)
    {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(position)
                .icon(icon)
                .snippet(description)
                .title(title);

        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }
}
