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

import java.util.ArrayList;
import java.util.List;

import id.bimbel.adapter.BimbelAdapter;
import id.bimbel.model.BimbelResponse;
import id.bimbel.model.DataItem;
import id.bimbel.model.LocationItem;
import id.bimbel.service.BimbelApi;
import id.bimbel.utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private Marker myMarker;


    List<DataItem> list;
    BimbelAdapter adapter;

    Double myLat, myLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        myLong = getIntent().getDoubleExtra("long", 0);
        myLat = getIntent().getDoubleExtra("lat", 0);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        list = new ArrayList<>();
        getAllKursus();
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


        mMap.setOnInfoWindowClickListener(this);


    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(myMarker)) {
            marker.showInfoWindow();
            Toast.makeText(this, marker.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (marker.equals(myMarker)) {
//            Intent i = new Intent(this, DetailBimbelActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("pom", pom);
//            i.putExtras(bundle);
//            startActivity(i);
        }
    }

    void getAllKursus() {
        Retrofit retrofit = ApiClient.newInstance();
        BimbelApi service = retrofit.create(BimbelApi.class);
        service.getAllBimbel().enqueue(new Callback<BimbelResponse>() {
            @Override
            public void onResponse(Call<BimbelResponse> call, Response<BimbelResponse> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        list.add(response.body().getData().get(i));
                    }

                    for (int i = 0; i < list.size(); i++) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(list.get(i).getLatitude(), list.get(i).getLongitude())).snippet(list.get(i).getAlamat()).title(list.get(i).getNama()));
                    }

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(list.get(1).getLatitude(), list.get(1).getLongitude()), 10));

                }
            }

            @Override
            public void onFailure(Call<BimbelResponse> call, Throwable t) {

            }
        });
    }
}
