package id.bimbel;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import id.bimbel.model.LocationItem;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener,GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private Marker myMarker;
    LocationItem pom;

    Double myLat,myLong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        pom= (LocationItem) getIntent().getSerializableExtra("pom");
        myLong=  getIntent().getDoubleExtra("long",0);
        myLat=  getIntent().getDoubleExtra("lat",0);


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
        LatLng target = new LatLng(pom.getLatitude(),pom.getLongitude());
        myMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(pom.getLatitude(),pom.getLongitude())).snippet(pom.getKecamatan()).title(pom.getNamaLembaga()));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(-6.8110100, 107.1491040)).snippet("Garut").title("Jln. Soeta"));
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(target).zoom(15).build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.setOnInfoWindowClickListener(this);

        PolylineOptions lineOptions = null;

        mMap.addPolyline(new PolylineOptions().add(new LatLng(myLat,myLong),new LatLng(-7.208182,107.898541)).width(5).color(Color.BLUE).geodesic(true));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(marker.equals(myMarker)){
            marker.showInfoWindow();
            Toast.makeText(this, marker.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if(marker.equals(myMarker)){
            Intent i = new Intent(this,DetailBimbelActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("pom",pom);
            i.putExtras(bundle);
            startActivity(i);
        }
    }
}
