package id.bimbel.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.bimbel.MapsActivity;
import id.bimbel.R;
import id.bimbel.model.LocationItem;

/**
 * Created by Comp on 9/8/2017.
 */

public class BimbelAdapter extends RecyclerView.Adapter<BimbelAdapter.MyViewHolder> {
    List<LocationItem> pomList;
    Context context;
    Double myLocLat, myLocLong;

    public BimbelAdapter(List<LocationItem> pomList, Context context, Double myLocLat, Double myLocLong) {
        this.pomList = pomList;
        this.context = context;
        this.myLocLat = myLocLat;
        this.myLocLong = myLocLong;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return pomList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_nama_bimbel)
        TextView tvNamaBimbel;
        @BindView(R.id.tv_alamat_bimbel)
        TextView tvAlamatBimbel;
        @BindView(R.id.tv_kecamatan)
        TextView tvKecamatan;
        @BindView(R.id.tv_distance)
        TextView tvDistance;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, MapsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("pom", pomList.get(getAdapterPosition()));
            i.putExtras(bundle);
            context.startActivity(i);

        }
    }

    public static float getDistance(double startLati, double startLongi, double goalLati, double goalLongi) {
        float[] resultArray = new float[99];
        Location.distanceBetween(startLati, startLongi, goalLati, goalLongi, resultArray);
        return resultArray[0];
    }



}
