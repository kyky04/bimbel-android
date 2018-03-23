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
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.bimbel.adapter.BimbelAdapter;
import id.bimbel.model.BimbelResponse;
import id.bimbel.model.DataItem;
import id.bimbel.model.Edge;
import id.bimbel.model.LocationItem;
import id.bimbel.model.Node;
import id.bimbel.service.BimbelApi;
import id.bimbel.utils.ApiClient;
import id.bimbel.utils.GPSTracker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TerdekatActivity extends AppCompatActivity {

    Dialog dialog;
    GPSTracker gpsTracker;

    List<LocationItem> locationItems;
    @BindView(R.id.recyclerBimbel)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    List<DataItem> list;
    BimbelAdapter adapter;

    double latitude, longitude;

    Node source,goal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terdekat);
        ButterKnife.bind(this);
        gpsTracker = new GPSTracker(this);
        if (gpsTracker.canGetLocation()) {
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
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

        recyclerView = (RecyclerView) findViewById(R.id.recyclerBimbel);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

//        sortLocations(locationItems,gpsTracker.getLatitude(),gpsTracker.getLongitude());
        list = new ArrayList<>();

        getAllKursus();

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
                        list.get(i).setDistance(getDistance(gpsTracker.getLatitude(),gpsTracker.getLongitude(),response.body().getData().get(i).getLatitude(),response.body().getData().get(i).getLongitude()));
                    }

                    Collections.sort(list, new Comparator<DataItem>() {
                        @Override
                        public int compare(DataItem c1, DataItem c2) {


                            return Float.compare(c1.getDistance(), c2.getDistance());
                        }
                    });
                    adapter = new BimbelAdapter(TerdekatActivity.this, list);
                    recyclerView.setAdapter(adapter);

                    AstarSearch(source,goal);


                }
            }

            @Override
            public void onFailure(Call<BimbelResponse> call, Throwable t) {

            }
        });
    }

    public static float getDistance(double startLati, double startLongi, double goalLati, double goalLongi) {
        float[] resultArray = new float[99];
        Location.distanceBetween(startLati, startLongi, goalLati, goalLongi, resultArray);
        return resultArray[0];
    }



    private void closeDialog() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void openDialog() {
        dialog = ProgressDialog.show(this, "Fetching Data ", "Please wait...", false, false);
    }

    public static List<DataItem> sortLocations(List<DataItem> locations, final double myLatitude, final double myLongitude) {
        Comparator comp = new Comparator<DataItem>() {
            @Override
            public int compare(DataItem o, DataItem o2) {
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

    public void AstarSearch(Node source, Node goal) { Set<Node> explored = new HashSet<Node>();
        PriorityQueue<Node> queue = new PriorityQueue<Node>(20, new Comparator<Node>() {

            public int compare(Node i, Node j) {
                if (i.f_scores > j.f_scores) {
                    return 1;
                } else if (i.f_scores < j.f_scores) {
                    return -1;
                } else {
                    return 0;
                }
            }

        }
        );

//cost awal set 0 source.g_scores = 0;

        queue.add(source);

        boolean found = false;

        while ((!queue.isEmpty()) && (!found)) {
//node yang punya nilai f_score rendah Node current = queue.poll(); explored.add(current);

//ketemu goal
            if (source.value.equals(goal.value)) {
                found = true;
            }

//mengeck setiap jalur pada node saat ini for (Edge e : current.adjacencies) {

            for (Edge e:source.adjacencies) {
                Node child = e.target;
                double cost = e.cost;
                double temp_g_scores = source.g_scores + cost;
                double temp_f_scores = temp_g_scores + child.h_scores;
                if ((explored.contains(child)) && (temp_f_scores >= child.f_scores)) {
                    continue;
                }

/*jika jalur belum di lewati atau nilai f_score-nya lebih rendah */
                else if ((!queue.contains(child)) || (temp_f_scores < child.f_scores)) {

                    child.parent = source; child.g_scores = temp_g_scores; child.f_scores = temp_f_scores;

                    if (queue.contains(child)) {
                        queue.remove(child);
                    }
                    queue.add(child);
                }
                printPath(goal);

            }

/*jika jalur sudah di lewati dan f_score-nya lebih besar maka, skip*/

        }
    }
    public List<Node> printPath(Node target) { List<Node> path = new ArrayList<Node>();
        for (Node node = target; node != null; node = node.parent) {
            path.add(node);
        } Collections.reverse(path); return path;
    }


}
