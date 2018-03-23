package id.bimbel.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.bimbel.MapsActivity;
import id.bimbel.R;
import id.bimbel.model.LocationItem;


/**
 * Created by Comp on 2/11/2018.
 */

public class KursusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<LocationItem> locationItems;
    Double myLocLat, myLocLong;

    public KursusAdapter( Context ctx,List<LocationItem> locationItems, Double myLocLat, Double myLocLong) {
        this.locationItems = locationItems;
        this.myLocLat = myLocLat;
        this.myLocLong = myLocLong;
        this.ctx = ctx;
    }

    private OnLoadMoreListener onLoadMoreListener;

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public KursusAdapter(Context ctx, List<LocationItem> pesertaList) {
        this.locationItems = pesertaList;
        this.ctx = ctx;
    }

    public KursusAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
       TextView tvNamaBimbel;
       TextView tvAlamatBimbel;
       TextView tvKecamatan;
       TextView tvDistance;
       LinearLayout lay;
        public OriginalViewHolder(View v) {
            super(v);
            tvNamaBimbel = (TextView)itemView.findViewById(R.id.tv_nama_bimbel);
            tvAlamatBimbel = (TextView)itemView.findViewById(R.id.tv_alamat_bimbel);
            tvKecamatan = (TextView)itemView.findViewById(R.id.tv_kecamatan);
            tvDistance = (TextView)itemView.findViewById(R.id.tv_distance);
            lay = (LinearLayout)itemView.findViewById(R.id.lay);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        LocationItem itemPeserta = locationItems.get(position);
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            view.lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(ctx, MapsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("pom", locationItems.get(position));
                    i.putExtra("long",myLocLong);
                    i.putExtra("lat",myLocLat);
                    i.putExtras(bundle);
                    ctx.startActivity(i);
                }
            });

            ((OriginalViewHolder) holder).tvNamaBimbel.setText(locationItems.get(position).getNamaLembaga());
            ((OriginalViewHolder) holder).tvAlamatBimbel.setText(locationItems.get(position).getAlamat());
//            ((OriginalViewHolder) holder).tvKecamatan.setText(locationItems.get(position).getKecamatan());
            ((OriginalViewHolder) holder).tvDistance.setText(String.valueOf((int)getDistance(myLocLat, myLocLong,locationItems.get(position).getLatitude(), locationItems.get(position).getLongitude()) / 1000) + " Km");



        }
    }

    @Override
    public int getItemCount() {
        return locationItems.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore(int current_page);
    }

    public static float getDistance(double startLati, double startLongi, double goalLati, double goalLongi) {
        float[] resultArray = new float[99];
        Location.distanceBetween(startLati, startLongi, goalLati, goalLongi, resultArray);
        return resultArray[0];
    }

}
