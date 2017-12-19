package test.o2121076.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LocationListener {

    MyLocationNewOverlay mLocationOverlay;
    CompassOverlay mCompassOverlay;
    MinimapOverlay mMinimapOverlay;
    MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context ctx = getApplicationContext();
        //important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_main);

        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        map.setMinZoomLevel(8);

        IMapController mapController = map.getController();
        mapController.setZoom(13); //small road


        //add compass
        this.mCompassOverlay = new CompassOverlay(ctx, new InternalCompassOrientationProvider(ctx), map);
        this.mCompassOverlay.enableCompass();
        map.getOverlays().add(this.mCompassOverlay);


        //GeoPoint startPoint = new GeoPoint(46.159, -1.153); // a la rochelle
        //mapController.setCenter(startPoint);

        //Add point on the map
        creationPointInteret("1",49.438,2.097,"test", "test");

        /*ArrayList<OverlayItem> items = new ArrayList<>();
        GeoPoint beauvais = new GeoPoint(49.438,2.097);
        OverlayItem o = new OverlayItem("1", "Beauvais", beauvais);

        items.add(o);


        map.getOverlays().add(new ItemizedOverlayWithFocus<OverlayItem>(this.getApplicationContext(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {

                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        return true; // We 'handled' this event.
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return false;
                    }
                })
        );*/

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else
        {
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10,this);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            //add localisation
            this.mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(ctx), map);
            mLocationOverlay.onLocationChanged(location,new GpsMyLocationProvider(ctx));
            this.mLocationOverlay.enableMyLocation();
            map.getOverlays().add(this.mLocationOverlay);

            //centre le début sur la position de l'utilisateur
            GeoPoint startPoint = new GeoPoint(location);
            mapController.setCenter(startPoint);;
        }





    }


    public void creationPointInteret(String id, double latitude, double longitude, String nom, String description)
    {
        MapItemizedOverlay itemJuridique = new MapItemizedOverlay(
                null, this);

        GeoPoint point = new GeoPoint(latitude, longitude);
        OverlayItem overlayitem2 = new OverlayItem(id,nom,description, point);

        itemJuridique.addOverlay(overlayitem2);

        map.getOverlays().add(itemJuridique);
    }

    public void onResume(){
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat =  (location.getLatitude());
        double lng = (location.getLongitude());
        GeoPoint point = new GeoPoint(lat, lng);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
