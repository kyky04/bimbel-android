package id.bimbel;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.bimbel.adapter.KursusAdapter;
import id.bimbel.adapter.PomAdapter;
import id.bimbel.model.LocationItem;
import id.bimbel.model.Pom;
import id.bimbel.model.PomResponse;
import id.bimbel.utils.ApiClient;
import id.bimbel.utils.GPSTracker;
import id.bimbel.utils.BimbelApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    List<Pom> listPom;

    Dialog dialog;
    GPSTracker gpsTracker;

    List<LocationItem> locationItems;
    @BindView(R.id.recyclerPom)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        gpsTracker = new GPSTracker(this);
        if (gpsTracker.canGetLocation()) {

            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }

        locationItems = new ArrayList<>();
        loadJSONFromAsset();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerPom);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

//        sortLocations(locationItems,gpsTracker.getLatitude(),gpsTracker.getLongitude());
        recyclerView.setAdapter(new KursusAdapter(this, locationItems, gpsTracker.getLatitude(), gpsTracker.getLongitude()));

        listPom = new ArrayList<>();


//        getAllPom();
    }

    private void getAllPom() {
        openDialog();
        Retrofit mRetrofit = ApiClient.newInstance();
        BimbelApi allPom = mRetrofit.create(BimbelApi.class);

        allPom.getAllPom().enqueue(new Callback<PomResponse>() {
            @Override
            public void onResponse(Call<PomResponse> call, Response<PomResponse> response) {
                closeDialog();
                listPom.clear();
                if (response.code() == 200) {
                    for (Pom p : response.body().getPom()) {
                        Pom pom = new Pom();
                        pom.setId(p.getId());
                        pom.setName(p.getName());
                        pom.setLatitude(p.getLatitude());
                        pom.setLongitude(p.getLongitude());
                        pom.setUrl(p.getUrl());
                        listPom.add(pom);
                    }


                    PomAdapter adapter = new PomAdapter(listPom, MainActivity.this, gpsTracker.getLatitude(), gpsTracker.getLongitude());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<PomResponse> call, Throwable t) {
                closeDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.allpom:
                Intent i = new Intent(this, MapsAllBimbelActivity.class);
                i.putExtra("list", (ArrayList) listPom);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void closeDialog() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void openDialog() {
        dialog = ProgressDialog.show(this, "Fetching Data Pom Mini", "Please wait...", false, false);
    }

    public static float getDistance(double startLati, double startLongi, double goalLati, double goalLongi) {
        float[] resultArray = new float[99];
        Location.distanceBetween(startLati, startLongi, goalLati, goalLongi, resultArray);
        return resultArray[0];
    }

    public static List<LocationItem> sortLocations(List<LocationItem> locations, final double myLatitude, final double myLongitude) {
        Comparator comp = new Comparator<LocationItem>() {
            @Override
            public int compare(LocationItem o, LocationItem o2) {
                float[] result1 = new float[3];
                Location.distanceBetween(myLatitude, myLongitude, o.getLatitude(), o.getLongitude(), result1);
                Float distance1 = result1[0];

                float[] result2 = new float[3];
                Location.distanceBetween(myLatitude, myLongitude, o2.getLatitude(), o.getLongitude(), result2);
                Float distance2 = result2[0];

                return distance1.compareTo(distance2);
            }
        };
        Collections.sort(locations, comp);
        return locations;
    }

    public ArrayList<LocationItem> loadJSONFromAsset() {
        ArrayList<LocationItem> locList = new ArrayList<>();
        String json = null;
        try {
            InputStream is = getAssets().open("locations.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray m_jArry = obj.getJSONArray("locations");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                LocationItem location = new LocationItem();
                location.setId(jo_inside.getString("id"));
                location.setNamaLembaga(jo_inside.getString("nama_lembaga"));
                location.setAlamat(jo_inside.getString("alamat"));
                location.setKecamatan(jo_inside.getString("kecamatan"));
                location.setJenisKursus(jo_inside.getString("jenis_kursus"));
                location.setLatitude(jo_inside.getDouble("latitude"));
                location.setLongitude(jo_inside.getDouble("longitude"));

                Log.d("LOCATION", location.toString());
                //Add your values in your `ArrayList` as below:
                locationItems.add(location);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return locList;
    }
}
