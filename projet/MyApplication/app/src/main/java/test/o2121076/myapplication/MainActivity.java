package test.o2121076.myapplication;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
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
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LocationListener {
    MapView map;

    CompassOverlay mCompassOverlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context ctx = getApplicationContext();
        //important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_main);

        //init
        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);

        map.setMultiTouchControls(true);
        map.setMinZoomLevel(8);
        IMapController mapController = map.getController();
        mapController.setZoom(13); //small road

        DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
        /***/

        //GeoPoint startPoint = new GeoPoint(46.159, -1.153); // a la rochelle
        //mapController.setCenter(startPoint);

        //Add point on the map
        ArrayList<OverlayItem> items = new ArrayList<>();
       /* OverlayItem o = new OverlayItem("Beauvais - Titre", "Beauvais - Description", new GeoPoint(49.438,2.097));
        OverlayItem o2 = new OverlayItem("Rochelle - Titre", "Rochelle - Description", new GeoPoint(46.159, -1.153));
*/
        items.add(creerPointInteret("Beauvais - Titre", "Beauvais - Description", 49.438,2.097));
        items.add(creerPointInteret("Rochelle - Titre", "Rochelle - Description", 46.159, -1.153));

        affichePointInteret(ctx,items);



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
            MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(ctx), map);
            mLocationOverlay.onLocationChanged(location,new GpsMyLocationProvider(ctx));
            mLocationOverlay.enableMyLocation();
            map.getOverlays().add(mLocationOverlay);

            //add compass
            mCompassOverlay = new CompassOverlay(ctx, new InternalCompassOrientationProvider(ctx), map);
            mCompassOverlay.enableCompass();
            Log.e("enableCompass OK?", mCompassOverlay.isCompassEnabled() + "  ");
            map.getOverlays().add(this.mCompassOverlay);

            // ajout d'une minimap
           /* MinimapOverlay mMinimapOverlay = new MinimapOverlay(ctx, map.getTileRequestCompleteHandler());
            mMinimapOverlay.setWidth(dm.widthPixels / 5);
            mMinimapOverlay.setHeight(dm.heightPixels / 5);
            mMinimapOverlay.setZoomDifference(5);
            map.getOverlays().add(mMinimapOverlay);*/

            //centre le début sur la position de l'utilisateur
            GeoPoint startPoint = new GeoPoint(location);
            mapController.setCenter(startPoint);
        }
    }

    //Creer un point d'interet sur la carte
    public OverlayItem creerPointInteret(String titre, String description, double latitude, double longitude)
    {
        return new OverlayItem(titre,description,new GeoPoint(latitude,longitude));
    }

    //affiche la liste des point d'interets avec leur description losqu'on clique dessus
    public void affichePointInteret(Context ctx, ArrayList<OverlayItem> items )
    {
        map.getOverlays().add(new ItemizedOverlayWithFocus<>(ctx, items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {

                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {

                        //set up dialog
                        Dialog dialog = new Dialog(MainActivity.this);

                        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.setContentView(R.layout.custom_dialog);

                        dialog.setCancelable(true);
                        //there are a lot of settings, for dialog, check them all out!

                        //set up text
                        TextView map_popup_header = (TextView) dialog.findViewById(R.id.map_popup_header);
                        map_popup_header.setText(item.getTitle());

                        TextView map_popup_body = (TextView) dialog.findViewById(R.id.map_popup_body);
                        map_popup_body.setText(item.getSnippet());

                        //now that the dialog is set up, it's time to show it
                        map.getController().setCenter(item.getPoint()); // On centre dessus :)
                        dialog.show();

                        return true; // We 'handled' this event.
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return false;
                    }
                })
        );
    }

    public void onResume(){
        super.onResume();
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
