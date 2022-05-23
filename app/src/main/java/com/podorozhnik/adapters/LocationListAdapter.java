package com.podorozhnik.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.podorozhnik.R;
import com.podorozhnik.entities.Location;
import com.podorozhnik.fragments.LocationFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.LocationListViewHolder> {
    private ArrayList<Location> recyclerViewItems;
    private LocationFragment fragmentReference;

    public LocationListAdapter(LocationFragment fragmentReference){
        recyclerViewItems = new ArrayList<>();
        this.fragmentReference = fragmentReference;
    }

    public LocationListAdapter(LocationFragment fragmentReference, ArrayList<Location> itemsList){
        recyclerViewItems = itemsList;
        this.fragmentReference = fragmentReference;
    }

    @NonNull
    @Override
    public LocationListAdapter.LocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_list_item, parent, false);

        return new LocationListAdapter.LocationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationListAdapter.LocationListViewHolder holder, int position) {
        Location item = recyclerViewItems.get(position);

        holder.locationName.setText(item.getName());
        holder.locationName.setOnClickListener(view -> {
            if (view.getId() == R.id.locationName)
                fragmentReference.onLocationSelected(item.getName());
        });

        holder.searchImage.setOnClickListener(view -> {
            if (view.getId() == R.id.searchImage)
                fragmentReference.onLocationSelected(item.getName());
        });
    }

    @Override
    public int getItemCount() {
        return recyclerViewItems.size();
    }

    public Location getItemByIndex(int index){
        return recyclerViewItems.get(index);
    }

    public void setData(List<Location> newData){
        recyclerViewItems.clear();
        recyclerViewItems.addAll(newData);

        notifyDataSetChanged();
    }

    static class LocationListViewHolder extends RecyclerView.ViewHolder{
        private TextView locationName;
        private ImageView searchImage;

        LocationListViewHolder(@NonNull View itemView) {
            super(itemView);

            this.locationName = itemView.findViewById(R.id.locationName);
            this.searchImage = itemView.findViewById(R.id.searchImage);
        }
    }
}
