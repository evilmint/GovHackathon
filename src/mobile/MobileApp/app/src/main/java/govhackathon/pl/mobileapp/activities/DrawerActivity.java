package govhackathon.pl.mobileapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import govhackathon.pl.mobileapp.ApiClient;
import govhackathon.pl.mobileapp.ApiData;
import govhackathon.pl.mobileapp.R;

public class DrawerActivity extends AppCompatActivity implements LocationListener, NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private EditText searchEditText = null;

    private Map<Marker, ApiDataMarker> allMarkersMap = new HashMap<>();
    private LocationManager locationManager = null;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;
    private GoogleApiClient mGoogleApiClient;

    private HashSet<Integer> markerSet = new HashSet<>();

    private volatile boolean locationIsDirty = true;
    private DrawerLayout drawer;

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (mGoogleApiClient != null) {
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setFastestInterval(0);
            locationRequest.setInterval(0).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(DrawerActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
            }

            if (this.mMap != null && this.mMap.getProjection() != null) {
                DrawerActivity.this.updateMarkerThread.setVisibleMapRegion(mMap.getProjection().getVisibleRegion());
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public class AreaSearchThread extends Thread {
        private VisibleRegion visibleMapRegion = null;

        public AreaSearchThread(Runnable runnable) {
            super(runnable);
        }

        public VisibleRegion getVisibleMapRegion() {
            return visibleMapRegion;
        }

        public void setVisibleMapRegion(VisibleRegion visibleMapRegion) {
            this.visibleMapRegion = visibleMapRegion;
        }
    }

    private AreaSearchThread updateMarkerThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        this.searchEditText = (EditText) drawer.findViewById(R.id.search_content);

/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ((EditText) drawer.findViewById(R.id.search_content)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            drawer.closeDrawer(GravityCompat.START);
                            
                            return true;
                        }

                        return false;
                    }
                });

                drawer.openDrawer(GravityCompat.START);
                drawer.findViewById(R.id.search_content).requestFocus();
            }
        });
        */

        // checkForGpsLocation();
    }

    private void checkForGpsLocation() {
        mGoogleApiClient  = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(this, SearchHousingActivity.class);
            startActivityForResult(intent, 100);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLocationChanged(Location location)
    {
        mLastLocation = location;

        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        this.addCurrentLocationMarker(location.getLatitude(), location.getLongitude());

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            final float busResult = data.getFloatExtra("busStop", 0.0f) * 2.0f;
            final float tramResult = data.getFloatExtra("tramStop", 0.0f) * 2.0f;
            final float partyabilityResult = data.getFloatExtra("partyability", 0.0f) * 2.0f;
            final float schoolResult = data.getFloatExtra("busStop", 0.0f) * 2.0f;
            final float relicResult = data.getFloatExtra("relic", 0.0f) * 2.0f;

            // http://govhak.azurewebsites.net/houses/search?searchParams=preschool:10;middleschool:5

            // Clear all markers from the map
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String url = "http://govhak.azurewebsites.net/houses/search?searchParams=preschool:" + Float.toString(schoolResult) +
                                ";middleschool:" + Float.toString(schoolResult) + ";primaryschool:" + Float.toString(schoolResult) + ";relic:" + Float.toString(relicResult) + ";" +
                                "tramStop:" + Float.toString(tramResult) + ";busStop:" + Float.toString(busResult);

                        final ApiData apiData = ApiClient.retrieveHousingDataFromUrl(url);

                        // Get a handler that can be used to post to the main thread
                        Handler mainHandler = new Handler(DrawerActivity.this.getMainLooper());
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                DrawerActivity.this.addMarkersFromApiData(apiData);
                            }
                        });


                    } catch (IOException | JSONException e1) {
                        e1.printStackTrace();
                    }


                }
            }).start();
        }
    }

    private void addCurrentLocationMarker(final double latitude, final double longitude) {
        Handler mainHandler = new Handler(DrawerActivity.this.getMainLooper());

        // Clear all markers from the map
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                LatLng latLng = new LatLng(latitude, longitude);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                markerOptions.position(latLng);


                mCurrLocationMarker = mMap.addMarker(markerOptions);
                mCurrLocationMarker.setDraggable(true);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /*
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DrawerActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            //mMap.setMyLocationEnabled(true);
        }
*/
        // Update map visible region
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                DrawerActivity.this.updateMarkerThread.setVisibleMapRegion(mMap.getProjection().getVisibleRegion());

                locationIsDirty = true;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (DrawerActivity.this.mCurrLocationMarker == null) {
                    DrawerActivity.this.addCurrentLocationMarker(latLng.latitude, latLng.longitude);
                } else {
                    DrawerActivity.this.mCurrLocationMarker.setPosition(latLng);
                }



                final String urlAddress = String.format(Locale.US, "http://govhak.azurewebsites.net/point?latitude=%1$f&longitude=%2$f",
                        latLng.latitude, latLng.longitude
                );

                final LatLng fLatLng = latLng;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String data = null;

                        try {
                            data = ApiClient.retrieveApiString(urlAddress);

                            JSONObject mainObject = new JSONObject(data);

                            Iterator keyIterator = mainObject.keys();

                            ArrayList<ContainerObject> containers = new ArrayList<ContainerObject>();

                            while (keyIterator.hasNext()) {
                                String key = (String)keyIterator.next();

                                ContainerObject containerObject = new ContainerObject();

                                Log.d("test", "key is " + key);

                                containerObject.key = key;
                                containerObject.jsonObject = mainObject.getJSONObject(key);

                                containers.add(containerObject);
                            }

                            DrawerActivity.this.setContainerWidgets(containers);

                            //DrawerActivity.this.addMarkersFromApiData(data);
                            //DrawerActivity.this.addCurrentLocationMarker(fLatLng.latitude, fLatLng.longitude);

                            DrawerActivity.this.openDrawer();
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        this.updateMarkerThread = new AreaSearchThread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (DrawerActivity.this.locationIsDirty) {
                        VisibleRegion visibleRegion = DrawerActivity.this.updateMarkerThread.getVisibleMapRegion();

                        try {
                            String urlAddress = String.format(Locale.US, "http://govhak.azurewebsites.net/markers/getMarkersFromBoundingBox?topRightLatitude=%1$f&topRightLongitude=%2$f&bottomLeftLatitude=%3$f&bottomLeftLongitude=%4$f",
                                    visibleRegion.latLngBounds.northeast.latitude, visibleRegion.latLngBounds.northeast.longitude,
                                    visibleRegion.latLngBounds.southwest.latitude, visibleRegion.latLngBounds.southwest.longitude
                            );

                            ApiData data = ApiClient.retrieveDataFromUrl(urlAddress);

                            DrawerActivity.this.addMarkersFromApiData(data);
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        DrawerActivity.this.updateMarkerThread.setVisibleMapRegion(mMap.getProjection().getVisibleRegion());

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                ApiDataMarker apiDataMarker = null;

                // Get extra information about the marker and pass it to the intent
                if (DrawerActivity.this.allMarkersMap.containsKey(marker)) {
                    apiDataMarker = DrawerActivity.this.allMarkersMap.get(marker);
                }

                Intent intent =  new Intent(DrawerActivity.this, DetailViewActivity.class);

                DetailViewActivity.apiDataMarker = apiDataMarker;

                startActivity(intent);
            }
        });

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View view = getLayoutInflater().inflate(R.layout.marker_view, null);

                TextView titleView = (TextView)view.findViewById(R.id.title);
                TextView descriptionView = (TextView)view.findViewById(R.id.description);

                String title = "<b>" + marker.getTitle() + "</b> ";

                titleView.setText(Html.fromHtml(title));

                if (marker.getSnippet() != null) {
                    descriptionView.setText(Html.fromHtml(marker.getSnippet()));
                }

                return view;
            }
        });


        // DrawerActivity.this.updateMarkerThread.start();
    }

    private void setContainerWidgets(final ArrayList<ContainerObject> containers) {
        // Get a handler that can be used to post to the main thread
        Handler mainHandler = new Handler(DrawerActivity.this.getMainLooper());

        // Clear all markers from the map
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                ListView listview = (ListView) findViewById(R.id.containerListView);
                listview.setAdapter(new ListViewContainerAdapter(DrawerActivity.this, containers));
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ListViewContainerAdapter adapter = (ListViewContainerAdapter)adapterView.getAdapter();

                        ContainerObject containerObject = adapter.containers.get(i);

                        String key = containerObject.key;

                        DrawerActivity.this.closeDrawer();
                        DrawerActivity.this.showMarkersByType(key);


                    }
                });
            }
        });
    }

    private void showMarkersByType(String type) {
        VisibleRegion visibleRegion = DrawerActivity.this.updateMarkerThread.getVisibleMapRegion();

        final String urlAddress = String.format(Locale.US, "http://govhak.azurewebsites.net/markers/getMarkersFromBoundingBox?topRightLatitude=%1$f&topRightLongitude=%2$f&bottomLeftLatitude=%3$f&bottomLeftLongitude=%4$f&type=" + type,
                visibleRegion.latLngBounds.northeast.latitude, visibleRegion.latLngBounds.northeast.longitude,
                visibleRegion.latLngBounds.southwest.latitude, visibleRegion.latLngBounds.southwest.longitude
        );

        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ApiData data = null;
                    try {
                        data = ApiClient.retrieveBoundingMarkerDataFromUrl(urlAddress);
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }

                    DrawerActivity.this.addMarkersFromApiData(data);


                }
            }).start();

        }
        catch (Exception e) {
            Log.d("test", e.getMessage());
        }
    }

    private void openDrawer() {
        // Get a handler that can be used to post to the main thread
        Handler mainHandler = new Handler(DrawerActivity.this.getMainLooper());

        // Clear all markers from the map
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });
    }

    private void closeDrawer() {
        // Get a handler that can be used to post to the main thread
        Handler mainHandler = new Handler(DrawerActivity.this.getMainLooper());

        // Clear all markers from the map
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
    }

    private void addMarkersFromApiData(ApiData data) {
        // Get a handler that can be used to post to the main thread
        Handler mainHandler = new Handler(DrawerActivity.this.getMainLooper());

        // Clear all markers from the map
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                DrawerActivity.this.mMap.clear();
                DrawerActivity.this.allMarkersMap.clear();

                if (DrawerActivity.this.mCurrLocationMarker != null) {
                    if (DrawerActivity.this.mCurrLocationMarker.getPosition() != null) {
                        DrawerActivity.this.addCurrentLocationMarker(
                                DrawerActivity.this.mCurrLocationMarker.getPosition().latitude,
                                DrawerActivity.this.mCurrLocationMarker.getPosition().longitude
                        );
                    }

                }
            }
        });

        final LatLngBounds.Builder builder = new LatLngBounds.Builder();

        // Emplace new markers on the map
        for (final ApiDataMarker marker : data.markers) {
            Log.d("test", "include marker point");

            builder.include(new LatLng(marker.getLat(), marker.getLon()));

            final BitmapDescriptor icon = DrawerActivity.getIconByMarkerType(marker.getType());

            Runnable updateRunnable = new Runnable() {
                @Override
                public void run() {
                    addMarker(new LatLng(marker.getLat(), marker.getLon()), marker.getName(), marker.getDescription(), icon, marker);
                }
            };

            mainHandler.post(updateRunnable);
        }

        if (data.markers.size() > 0) {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    LatLngBounds bounds = builder.build();

                    int padding = 150; // offset from edges of the map in pixels
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

                    mMap.animateCamera(cu);
                }
            });
        }

    }

    private static BitmapDescriptor getIconByMarkerType(String type) {
        BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(R.drawable.cast_album_art_placeholder);

        return descriptor;
    }

    private void clearMarkers()
    {
        mMap.clear();
    }

    public void addMarker(LatLng position, String title, String description, BitmapDescriptor icon, ApiDataMarker apiDataMarker)
    {
        if (markerSet.contains(position.hashCode()))
            return;

        MarkerOptions markerOptions = new MarkerOptions()
                .position(position)
                .icon(BitmapDescriptorFactory.defaultMarker(apiDataMarker.getHue()))
                .title(title);

        if (description != null && !description.equals("null")) {
            markerOptions = markerOptions.snippet(description);
        }

        Marker marker = mMap.addMarker(markerOptions);

        DrawerActivity.this.allMarkersMap.put(marker, apiDataMarker);
    }
}
