package id.bimbel.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.bimbel.MapsActivity;
import id.bimbel.MapsDetailActivity;
import id.bimbel.R;
import id.bimbel.model.Bimbel;
import id.bimbel.model.DataItem;
import id.bimbel.utils.GPSTracker;

/**
 * Created by Comp on 9/8/2017.
 */

public class BimbelAdapter extends RecyclerView.Adapter<BimbelAdapter.MyViewHolder> {
    List<DataItem> bimbel;
    Context context;
    GPSTracker gpsTracker;

    public BimbelAdapter(Context context, List<DataItem> bimbel) {
        this.bimbel = bimbel;
        this.context = context;
        gpsTracker = new GPSTracker(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvNamaBimbel.setText(bimbel.get(position).getNama());
        holder.tvKecamatan.setText(bimbel.get(position).getKategori());
        holder.tvAlamatBimbel.setText(bimbel.get(position).getAlamat());
        holder.tvDistance.setText(String.valueOf(getDistance(gpsTracker.getLatitude(), gpsTracker.getLongitude(), bimbel.get(position).getLatitude(), bimbel.get(position).getLongitude()) / 1000) + " Km");
    }

    @Override
    public int getItemCount() {
        return bimbel.size();
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

            tvNamaBimbel = (TextView) itemView.findViewById(R.id.tv_nama_bimbel);
            tvAlamatBimbel = (TextView) itemView.findViewById(R.id.tv_alamat_bimbel);
            tvKecamatan = (TextView) itemView.findViewById(R.id.tv_kecamatan);
            tvDistance = (TextView) itemView.findViewById(R.id.tv_distance);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, MapsDetailActivity.class);
            i.putExtra("long",bimbel.get(getAdapterPosition()).getLongitude());
            i.putExtra("lat",bimbel.get(getAdapterPosition()).getLatitude());
            i.putExtra("nama",bimbel.get(getAdapterPosition()).getNama());
            i.putExtra("alamat",bimbel.get(getAdapterPosition()).getAlamat());
            context.startActivity(i);

        }
    }

    public static float getDistance(double startLati, double startLongi, double goalLati, double goalLongi) {
        float[] resultArray = new float[99];
        Location.distanceBetween(startLati, startLongi, goalLati, goalLongi, resultArray);
        return resultArray[0];
    }

    public void setFilter(List<DataItem> listItem) {
        bimbel = new ArrayList<>();
        bimbel.addAll(listItem);
        notifyDataSetChanged();
    }


}
