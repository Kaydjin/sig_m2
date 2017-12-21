package test.o2121076.myapplication;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import android.view.View;
import android.view.Window;

import android.widget.ImageButton;
import android.widget.TextView;


import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.PathOverlay;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;

import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LocationListener {
    MapView map;
    CompassOverlay mCompassOverlay;
    GeoPoint paris = new GeoPoint(48.866667,2.333333);
    Location location = null;
    Road road = null;
    Polyline polyline = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context ctx = getApplicationContext();
        //
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_main);

        //init
        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);

        map.setMultiTouchControls(true);
        map.setMinZoomLevel(7);
        final IMapController mapController = map.getController();
        mapController.setZoom(13); //small road

        DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
        /***/


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
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

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

            ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(map);
            myScaleBarOverlay.setAlignRight(true);
            map.getOverlays().add(myScaleBarOverlay);

            // ajout d'une minimap
           /* MinimapOverlay mMinimapOverlay = new MinimapOverlay(ctx, map.getTileRequestCompleteHandler());
            mMinimapOverlay.setWidth(dm.widthPixels / 5);
            mMinimapOverlay.setHeight(dm.heightPixels / 5);
            mMinimapOverlay.setZoomDifference(5);
            map.getOverlays().add(mMinimapOverlay);*/

            final GeoPoint startPoint;
            if(location == null)
            {
                //cas ou le gps fonctionne mal on positionne sur Paris
                startPoint = new GeoPoint(paris);
                TextView error_gps = (TextView) findViewById(R.id.text_error);
                error_gps.setVisibility(TextView.VISIBLE);
            }

            else {
                startPoint = new GeoPoint(location); //centre le début sur la position de l'utilisateur

                TextView error_gps = (TextView) findViewById(R.id.text_error);
                error_gps.setVisibility(TextView.INVISIBLE);
            }
            mapController.setCenter(startPoint);

            /*Button qui re centre la map sur notre position*/
            ImageButton focusPosition = (ImageButton) findViewById(R.id.ButtonFocusPosition);
            focusPosition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mapController.setCenter(startPoint);
                }
            });
        }

        /***/
        /**TODO
         * -Distance entre nous et le point d'interet (faire lors d'un onItemLongPress on peut afficher la distance)
         * -Ajout a la volée (button en bas à droite pour activer) tu cliques sur la map, ajout d'un point et demande les informations sous forme d'un Dialog
         * **/

    }

    //Creer un point d'interet sur la carte
    public OverlayItem creerPointInteret(String titre, String description, double latitude, double longitude)
    {
        return new OverlayItem(titre,description,new GeoPoint(latitude,longitude));
    }

    //affiche la liste des point d'interets avec leur description losqu'on clique dessus
    public void affichePointInteret(final Context ctx, ArrayList<OverlayItem> items )
    {
        map.getOverlays().add(new ItemizedOverlayWithFocus<>(ctx, items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {

                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {

                        affichageDialog(index,item);
                        return true;
                    }

                    public void affichageDialog(final int index, final OverlayItem item)
                    {
                        //on met en place le dialog
                        Dialog dialog = new Dialog(MainActivity.this);

                        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.setContentView(R.layout.custom_dialog);

                        dialog.setCancelable(true);

                        TextView map_popup_header = (TextView) dialog.findViewById(R.id.map_popup_header);
                        map_popup_header.setText(item.getTitle());

                        TextView map_popup_body = (TextView) dialog.findViewById(R.id.map_popup_body);
                        map_popup_body.setText(item.getSnippet());

                        TextView map_popup_distance = (TextView) dialog.findViewById(R.id.map_popup_distance);
                        float[] distance = new float[1];
                        Location.distanceBetween(location.getLatitude(), location.getLongitude(), item.getPoint().getLatitude(), item.getPoint().getLongitude(), distance);
                        map_popup_distance.setText(distance[0]*0.001 + " km");

                        map.getController().setCenter(item.getPoint()); // On centre dessus :)
                        dialog.show();
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {

                    //Je fais la route LOL c'est optionnel :))))))
                        /*
                        if(road != null)
                            map.getOverlays().remove(road);

                        Log.e("route", "Debut trace route");

                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                RoadManager roadManager = new OSRMRoadManager(MainActivity.this);
                                ArrayList<GeoPoint> waypoints = new ArrayList<>();
                                waypoints.add(new GeoPoint(location.getLatitude(),location.getLongitude()));

                                waypoints.add(new GeoPoint(item.getPoint().getLatitude(), item.getPoint().getLongitude()));

                                road = roadManager.getRoad(waypoints);
                                Polyline roadOverlay = RoadManager.buildRoadOverlay(road);
                                map.getOverlays().add(roadOverlay);
                            }
                        };

                        new Thread(runnable).start();

                        Log.e("route", "je trace mon chemin");
                        */
                        if(polyline != null)
                        {
                            Log.e("Polyline", "suppression ancienne ligne");
                            map.getOverlays().remove(polyline);
                            polyline = null;
                            polyline = new Polyline();
                        }
                        else
                        {
                            polyline = new Polyline();
                        }
                        ArrayList<GeoPoint> waypoints = new ArrayList<>();
                        waypoints.add(new GeoPoint(location.getLatitude(),location.getLongitude()));
                        waypoints.add(new GeoPoint(item.getPoint().getLatitude(), item.getPoint().getLongitude()));
                        polyline.setPoints(waypoints);
                        map.getOverlays().add(polyline);

                        return true;
                    }
                })
        );
    }

    public void onResume(){
        super.onResume();
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
    }

    @Override
    public void onLocationChanged(final Location location) {

        if(location == null)
        {
            TextView error_gps = (TextView) findViewById(R.id.text_error);
            error_gps.setVisibility(TextView.VISIBLE);

            ImageButton focusPosition = (ImageButton) findViewById(R.id.ButtonFocusPosition);
            focusPosition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    map.getController().setCenter(new GeoPoint(paris));
                }
            });

        }

        else {
            TextView error_gps = (TextView) findViewById(R.id.text_error);
            error_gps.setVisibility(TextView.INVISIBLE);
            ImageButton focusPosition = (ImageButton) findViewById(R.id.ButtonFocusPosition);
            focusPosition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    map.getController().setCenter(new GeoPoint(location));
                }
            });
        }
    }

    @Override
    public void onStatusChanged(String s, int status, Bundle bundle) {
        // OUT_OF_SERVICE  Constant Value: 0 (0x00000000)
        // int TEMPORARILY_UNAVAILABLE   Constant Value: 1 (0x00000001)
        if(status == 0 || status == 1)
        {
            TextView error_gps = (TextView) findViewById(R.id.text_error);
            error_gps.setVisibility(TextView.VISIBLE);
        }
        else //2 == AVAIBLE
        {
            TextView error_gps = (TextView) findViewById(R.id.text_error);
            error_gps.setVisibility(TextView.INVISIBLE);
        }

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
