package caiocleaoca.in_loco_media_app;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;
    private LatLng marcador;
    public static String endereco[];
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

        // EVENTS
        Button butt = ( Button ) findViewById(R.id.eunaosei);
        final Context context = this;

        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CitiesActivity.class);
                intent.putExtra("endereco", endereco);
                startActivity(intent);

            }
        });

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latilong) {
                Log.i("Script", "setOnMapClickListener()");

                if (marker != null) {
                    Log.i("Script", "entraNoIfMapClickListener()");
                    marker.remove();
                }

                marcador = new LatLng(latilong.latitude, latilong.longitude);
                customAddMarker(new LatLng(latilong.latitude, latilong.longitude), "2: Marcador Alterado", "Marcador Reposicionado");

                // Pegando enderecos
                Geocoder gc = new Geocoder ( MapsActivity.this );
                List<Address> adresslist = null;

                JSONObject jObj = new JSONObject();

                try {
                    adresslist = gc.getFromLocation(marcador.latitude, marcador.longitude, 15);
                    endereco = new String[15];
                    for ( int i = 0; i < 14; i ++ ) {
                        endereco[i] = "Cidade: " + adresslist.get(1).getLocality();
                    }
                    Toast.makeText(MapsActivity.this, "Local: " + endereco[3], Toast.LENGTH_SHORT).show();


                } catch (IOException e) {
                    Log.i("Script", "entraNoIfMapClickListener()Exception");
                    e.printStackTrace();
                }


            }
        });

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.i("Script: ", "3: Marker: " + marker.getTitle());
                return false;
            }
        });

        googleMap.setOnInfoWindowClickListener( new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick ( Marker marker ) {
                Log.i("Script", "4: Marker: " + marker.getTitle());
            }
        });

    }

    // Funcao que cria marcadores.
    public void customAddMarker ( LatLng latilong, String title, String snippet ) {
        // Cria as opcoes para o marker e seta elas.
        MarkerOptions options = new MarkerOptions();
        options.position(latilong).title(title).snippet(snippet).draggable(true);

        // objeto marker criado e instaciado aqui.
        marker = mMap.addMarker(options);

    }

}
