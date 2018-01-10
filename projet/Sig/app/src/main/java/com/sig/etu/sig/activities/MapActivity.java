package com.sig.etu.sig.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sig.etu.sig.R;
import com.sig.etu.sig.modeles.Batiment;
import com.sig.etu.sig.modeles.Metier;
import com.sig.etu.sig.modeles.Personne;
import com.sig.etu.sig.modeles.TypeBatiment;
import com.sig.etu.sig.modeles.Ville;
import com.sig.etu.sig.util.ParserCsvLieux;
import com.sig.etu.sig.util.ParserCsvPersonnes;
import com.sig.etu.sig.util.ParserJson;

import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements LocationListener {
    MapView map;
    CompassOverlay mCompassOverlay;
    GeoPoint paris = new GeoPoint(48.866667,2.333333);
    GeoPoint geo = null;
    Location location = null;
    Road road = null;
    Polyline polyline = null;
    Polyline roadOverlay = null;

    public static final String EXTRA_NOM = "nom";
    public static final String EXTRA_DESCRIPTION = "description";
    public static final String EXTRA_LATITUDE = "latitude";
    public static final String EXTRA_LONGITUDE = "longitude";
    public static final String EXTRA_LIEUX = "lieux";
    public static final String EXTRA_PERSONNES = "personnes";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        final Context ctx = getApplicationContext();
        //
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_map);

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


        Intent intent = getIntent();
        String nom = intent.getStringExtra(MapActivity.EXTRA_NOM)+"";
        String description = intent.getStringExtra(MapActivity.EXTRA_DESCRIPTION)+"";
        String latitude = intent.getStringExtra(MapActivity.EXTRA_LATITUDE)+"";
        String longitude = intent.getStringExtra(MapActivity.EXTRA_LONGITUDE)+"";
        String lieux = intent.getStringExtra(MapActivity.EXTRA_LIEUX)+"";
        String personnes = intent.getStringExtra(MapActivity.EXTRA_PERSONNES)+"";

        //Add point on the map
        ArrayList<OverlayItem> items = new ArrayList<>();

        //Demande d'affichage d'un seul point.
        if(lieux.equals("")&&personnes.equals("")) {
            items.add(creerPointInteret(nom, description, Float.valueOf(latitude), Float.valueOf(longitude)));
        }else{
            if(!lieux.equals("")) {
                try {
                    JSONObject json = new JSONObject(lieux);
                    List<String[]> datas = ParserJson.parseLieuxFrom(json);
                    ArrayList<Batiment> batiments = new ArrayList<Batiment>();
                    ParserCsvLieux pcl = new ParserCsvLieux(',', batiments,
                            new ArrayList<Ville>(), new ArrayList<TypeBatiment>());
                    pcl.fromCsvData(datas);

                    for (Batiment b : batiments) {
                        items.add(creerPointInteret(b.getNom(),
                                b.getAdresse() + "\n"
                                        + b.getTelephone(), b.getLatitude(), b.getLongitude()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if(!personnes.equals("")){
                try {
                    JSONObject json = new JSONObject(personnes);
                    List<String[]> datas = ParserJson.parsePersonnesFrom(json);
                    ArrayList<Personne> pers = new ArrayList<Personne>();
                    ParserCsvPersonnes pcl = new ParserCsvPersonnes(',', new ArrayList<Batiment>(),
                            new ArrayList<Metier>(), pers);
                    pcl.fromCsvData(datas);

                    for (Personne p : pers) {
                        items.add(creerPointInteret(p.getNom(),
                                p.getAdresse(), p.getLatitude(), p.getLongitude()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
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

            final Button buttonAddPoint = (Button) findViewById(R.id.ButtonAddPoint);
            buttonAddPoint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO ajout d'un repère sur la carte
                }
            });
        }

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

                    private void affichageDialog(final int index, final OverlayItem item)
                    {
                        //on met en place le dialog
                        final Dialog dialog = new Dialog(MapActivity.this);

                        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

                        //On route un button pour que l'utilisateur puissent calculer son trajet en gps :

                        ImageButton map_popup_buttonGps = (ImageButton) dialog.findViewById(R.id.map_popup_buttonGps);
                        map_popup_buttonGps.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                               //Ici on lance le calcul itinéraire
                                dialog.cancel(); // On ferme le dialogue
                                gpsChemin(index,item);
                            }
                        });
                        map.getController().setCenter(item.getPoint()); // On centre dessus :)
                        dialog.show();
                    }

                    private void gpsChemin(final int index, final OverlayItem item)
                    {
                        Log.e("route", "Debut calcule route");
                        Toast.makeText(ctx, "Calcul de l'itineraire", Toast.LENGTH_LONG).show();

                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                RoadManager roadManager = new OSRMRoadManager(MapActivity.this);
                                ArrayList<GeoPoint> waypoints = new ArrayList<>();
                                waypoints.add(new GeoPoint(location.getLatitude(),location.getLongitude()));

                                waypoints.add(new GeoPoint(item.getPoint().getLatitude(), item.getPoint().getLongitude()));

                                road = roadManager.getRoad(waypoints);

                                if(roadOverlay != null)
                                {
                                    map.getOverlays().remove(roadOverlay);
                                    roadOverlay = null;
                                    Log.e("route", "suppression ancien trajet");
                                }

                                runOnUiThread(new Runnable()
                                {
                                    public void run()
                                    {
                                        if (road.mStatus != Road.STATUS_OK)
                                        {
                                            Toast.makeText(ctx, "Erreur", Toast.LENGTH_LONG).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(ctx, "distance="+road.mLength+" km", Toast.LENGTH_LONG).show();
                                            Toast.makeText(ctx, "durée="+(road.mDuration/3600d)+ " h", Toast.LENGTH_LONG).show();
                                            roadOverlay = RoadManager.buildRoadOverlay(road);
                                            map.getOverlays().add(roadOverlay);
                                        }

                                    }
                                });


                            }
                        };

                        new Thread(runnable).start();

                        Log.e("route", "Le calcul est en cours");
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {

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
