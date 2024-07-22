package com.example.yourheathhero;
import com.bumptech.glide.Glide;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class HotDealAdapter extends RecyclerView.Adapter<HotDealAdapter.HotDealViewHolder> {
    private List<HotDeal> hotDealList;
    private Context context;

    public HotDealAdapter(Context context, List<HotDeal> hotDealList) {
        this.context = context;
        this.hotDealList = hotDealList;
    }

    @NonNull
    @Override
    public HotDealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner, parent, false);
        return new HotDealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotDealViewHolder holder, int position) {
        HotDeal hotDeal = hotDealList.get(position);
        Glide.with(context).load(hotDeal.getImageUrl()).into(holder.hotDealImage);
    }

    @Override
    public int getItemCount() {
        return hotDealList.size();
    }

    public static class HotDealViewHolder extends RecyclerView.ViewHolder {
        ImageView hotDealImage;

        public HotDealViewHolder(@NonNull View itemView) {
            super(itemView);
            hotDealImage = itemView.findViewById(R.id.hotdelas);
        }
    }
}
