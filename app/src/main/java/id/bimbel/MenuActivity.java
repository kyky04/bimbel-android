package id.bimbel;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.btn_list_bimbel)
    RelativeLayout btnListBimbel;
    @BindView(R.id.btn_maps)
    RelativeLayout btnMaps;
    @BindView(R.id.btn_terdekat)
    RelativeLayout btnTerdekat;
    @BindView(R.id.btn_cari)
    RelativeLayout btnCari;
    @BindView(R.id.btn_tentang)
    RelativeLayout btnTentang;
    @BindView(R.id.btn_exit)
    RelativeLayout btnExit;
    @BindView(R.id.btn)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        btn = (Button) findViewById(R.id.btn);
        btnListBimbel = (RelativeLayout) findViewById(R.id.btn_list_bimbel);
        btnMaps = (RelativeLayout) findViewById(R.id.btn_maps);
        btnExit = (RelativeLayout) findViewById(R.id.btn_exit);
        btnTerdekat = (RelativeLayout) findViewById(R.id.btn_terdekat);
        btnCari = (RelativeLayout) findViewById(R.id.btn_cari);


        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Toast.makeText(MenuActivity.this, "Akses Di izinkan", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(MenuActivity.this, "Akses Tidak Di izinkan", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();

        btnListBimbel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, MainActivity.class));
            }
        });
        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, MapsActivity.class));
            }
        });
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("Anda yakin akan keluar dari aplikasi ini?")
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @OnClick({R.id.btn_list_bimbel, R.id.btn_maps, R.id.btn_terdekat, R.id.btn_cari, R.id.btn_tentang, R.id.btn_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_list_bimbel:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btn_maps:
                startActivity(new Intent(this, MapsActivity.class));
                break;
            case R.id.btn_terdekat:
                startActivity(new Intent(this, TerdekatActivity.class));
                break;
            case R.id.btn_cari:
                startActivity(new Intent(this, CariBimbelActivity.class));
                break;
            case R.id.btn_tentang:
//                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.btn_exit:
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setMessage("Anda yakin akan keluar dari aplikasi ini?")
                        .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {

    }
}
