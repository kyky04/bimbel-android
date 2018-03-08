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

import id.bimbel.MapsActivity;
import id.bimbel.R;
import id.bimbel.model.Pom;

/**
 * Created by Comp on 9/8/2017.
 */

public class PomAdapter extends RecyclerView.Adapter<PomAdapter.MyViewHolder> {
    List<Pom> pomList;
    Context context;
    Double myLocLat,myLocLong;

    public PomAdapter(List<Pom> pomList, Context context, Double myLocLat, Double myLocLong) {
        this.pomList = pomList;
        this.context = context;
        this.myLocLat = myLocLat;
        this.myLocLong = myLocLong;
    }

    @Override
    public PomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PomAdapter.MyViewHolder holder, int position) {
        holder.tvPom.setText(pomList.get(position).getName());
        holder.tvPomDistance.setText(String.valueOf(getDistance(myLocLat,myLocLong,pomList.get(position).getLatitude(),pomList.get(position).getLongitude())/1000)+" Km");
//        Glide.with(context).load(bimbel.get(position).getUrl()).into(holder.imgPom);
        Picasso.with(context).load(pomList.get(position).getUrl()).resize(80,80).into(holder.imgPom);
    }

    @Override
    public int getItemCount() {
        return pomList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvPom;
        TextView tvPomDistance;
        ImageView imgPom;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, MapsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("pom",pomList.get(getAdapterPosition()));
            i.putExtras(bundle);
            context.startActivity(i);

        }
    }
    public static float getDistance(double startLati, double startLongi, double goalLati, double goalLongi){
        float[] resultArray = new float[99];
        Location.distanceBetween(startLati, startLongi, goalLati, goalLongi, resultArray);
        return resultArray[0];
    }

}
