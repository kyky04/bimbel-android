package id.bimbel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import id.bimbel.model.Pom;

public class DetailBimbelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pom);
        Pom pom= (Pom) getIntent().getSerializableExtra("pom");

        TextView tvDetail = (TextView)findViewById(R.id.tvDetailPom);
        ImageView imgDetailPom = (ImageView)findViewById(R.id.imgDetailPom);

        tvDetail.setText("Alamat : "+pom.getName());
        Glide.with(this).load(pom.getUrl()).into(imgDetailPom);
    }
}
