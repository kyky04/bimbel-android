package id.bimbel;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

import id.bimbel.model.Pom;

public class MapsAllBimbelActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private Marker myMarker;
    Pom pom;
    ArrayList<Pom> listPom;
    HashMap<String,String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        pom = (Pom) getIntent().getSerializableExtra("pom");
        listPom = (ArrayList<Pom>) getIntent().getSerializableExtra("list");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        mMap.setOnMarkerClickListener(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-6.8188660, 107.1472870);
        for (int i = 0; i < listPom.size(); i++) {
            myMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(listPom.get(i).getLatitude(), listPom.get(i).getLongitude()   )).title(listPom.get(i).getName()));
        }
//        mMap.addMarker(new MarkerOptions().position(new LatLng(-6.8110100, 107.1491040)).snippet("Garut").title("Jln. Soeta"));
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                sydney).zoom(14).build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return true;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
//            Intent i = new Intent(this, DetailFromAllActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("pom", marker.getTitle());
//            i.putExtras(bundle);
//        Log.d("pomId",marker.getId());
//            startActivity(i);

    }
}
