package test.o2121076.myapplication;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;

import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LocationListener{
    MapView map;
    CompassOverlay mCompassOverlay;
    GeoPoint paris = new GeoPoint(48.866667,2.333333);
    GeoPoint coord_new_point = null;
    Location location = null;
    Road road = null;
    Polyline polyline = null;
    Polyline roadOverlay = null;
    final CharSequence textButton_AjoutRepere_Ajouter = " ON";
    final CharSequence textButton_AjoutRepere_Annuler = " OFF ";

    MapEventsOverlay overlayEvents = null;

    //Permet l'ajout de point sur la carte
    ArrayList<OverlayItem> items = new ArrayList<>();
    //Point ajouter par l'utilisateur
    ArrayList<OverlayItem> personnel = new ArrayList<>();


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
        map.setUseDataConnection(true);
        map.setTileSource(TileSourceFactory.MAPNIK);

        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        map.setMinZoomLevel(7);

        final IMapController mapController = map.getController();
        mapController.setZoom(13); //small road

        DisplayMetrics dm = ctx.getResources().getDisplayMetrics(); //avoir les dimensions
        /***/



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
            buttonAddPoint.setText(textButton_AjoutRepere_Ajouter);
            buttonAddPoint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO ajout d'un repère sur la carte
                    if( buttonAddPoint.getText().toString().equals(textButton_AjoutRepere_Ajouter.toString()))
                    {
                        //Ici on s'occupe de la partie l'ajout d'un point sur la carte
                        buttonAddPoint.setText(textButton_AjoutRepere_Annuler);
                        //on affiche un petit toast pour lui dire : "click sur la map pour ajouter le point"
                        MapEventsReceiver mReceive = new MapEventsReceiver() {
                            @Override
                            public boolean singleTapConfirmedHelper(GeoPoint p) {
                                //Toast.makeText(getBaseContext(),p.getLatitude() + " - "+p.getLongitude(),Toast.LENGTH_LONG).show();
                                coord_new_point = p;
                                Intent intent = new Intent(ctx, FormulaireActivity.class);
                                startActivityForResult(intent, 0);

                                return false;
                            }

                            @Override
                            public boolean longPressHelper(GeoPoint p) {
                                return false;
                            }
                        };


                        overlayEvents = new MapEventsOverlay(mReceive);
                        map.getOverlays().add(overlayEvents);

                    }
                    else
                    {
                        buttonAddPoint.setText(textButton_AjoutRepere_Ajouter);
                        if(overlayEvents != null)
                        {
                            map.getOverlays().remove(overlayEvents);
                            overlayEvents = null;
                        }

                    }

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
                        final Dialog dialog = new Dialog(MainActivity.this);

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
                        if(location != null)
                            Location.distanceBetween(location.getLatitude(), location.getLongitude(), item.getPoint().getLatitude(), item.getPoint().getLongitude(), distance);
                        else
                            Location.distanceBetween(paris.getLatitude(), paris.getLongitude(), item.getPoint().getLatitude(), item.getPoint().getLongitude(), distance);

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
                                RoadManager roadManager = new OSRMRoadManager(MainActivity.this);
                                ArrayList<GeoPoint> waypoints = new ArrayList<>();
                                if(location != null)
                                    waypoints.add(new GeoPoint(location.getLatitude(),location.getLongitude()));
                                else
                                    waypoints.add(new GeoPoint(paris.getLatitude(),paris.getLongitude()));

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

    //On récupere de la partie de FormulaireActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Retour 0 pour le formulaire
        if (requestCode == 0) {
            // Cas ou on va ajouter
            if (resultCode == RESULT_OK) {
                String nom = data.getStringExtra("Nom");
                String adresse = data.getStringExtra("Adresse");
                String codePostal = data.getStringExtra("CodePostale");
                String telephone = data.getStringExtra("Telephone");
                //TODO  String type = data.getStringExtra("Type");
                String ville = data.getStringExtra("Ville");

                personnel.add(creerPointInteret(nom,
                        ville + "\n" +
                        adresse + " " +
                        codePostal + "\n" +
                        telephone
                        , coord_new_point.getLatitude(),coord_new_point.getLongitude()));
                affichePointInteret(getApplicationContext(),items);
            }
            //Sinon on ne fait rien
        }
    }

}
