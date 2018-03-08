package id.bimbel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import id.bimbel.model.Pom;
import id.bimbel.utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailFromAllActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pom);


        final String namePom = getIntent().getStringExtra("pom");

        final TextView tvDetail = (TextView)findViewById(R.id.tvDetailPom);
        final ImageView imgDetailPom = (ImageView)findViewById(R.id.imgDetailPom);


        Retrofit mRetrofit = ApiClient.newInstance();
//        BimbelApi pomService = mRetrofit.create(BimbelApi.class);
//        pomService.getPom(namePom).enqueue(new Callback<Pom>() {
//            @Override
//            public void onResponse(Call<Pom> call, Response<Pom> response) {
//                if(response.code() == 200){
//                    tvDetail.setText("Alamat : "+response.body().getName());
//                    Glide.with(DetailFromAllActivity.this).load(response.body().getUrl()).into(imgDetailPom);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Pom> call, Throwable t) {
//
//            }
//        });
    }
}
