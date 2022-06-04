package com.podorozhnik.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.podorozhnik.R;
import com.podorozhnik.entities.Request;
import com.podorozhnik.fragments.AllSearchFragment;

import java.util.ArrayList;
import java.util.List;

public class AllSearchAdapter extends BaseAdapter {
    AllSearchFragment activity2;
    List<Request> listRequest;
    LayoutInflater inflater;

    public AllSearchAdapter(AllSearchFragment activity2, List<Request> listRequest) {
        this.activity2 = activity2;
        this.listRequest = listRequest;
    }

    @Override
    public int getCount() {
        return listRequest.size();
    }

    @Override
    public Object getItem(int i) {
        return listRequest.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ArrayList<Integer> icons = new ArrayList<>();
        icons.add(R.drawable.ic_find);
        icons.add(R.drawable.ic_create);

        inflater = (LayoutInflater) activity2
                .getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_item, null);
        itemView.setSaveEnabled(false);

        ImageView ic= itemView.findViewById(R.id.ic) ;
        ImageView city= itemView.findViewById(R.id.city) ;
        TextView departurePoint = itemView.findViewById(R.id.departurePoint);
        TextView destinationPoint = itemView.findViewById(R.id.destinationPoint);
        TextView date = itemView.findViewById(R.id.date);
        TextView time = itemView.findViewById(R.id.time);

        if (!listRequest.get(i).isDriver()){
            ic.setImageResource(R.drawable.ic_find);
        }
        else {
            ic.setImageResource(R.drawable.ic_create);
        }
        city.setImageResource(R.drawable.ic_city);
        departurePoint.setText(listRequest.get(i).getDeparturePoint().getName());
        destinationPoint.setText(listRequest.get(i).getDestinationPoint().getName());
        date.setText(listRequest.get(i).getDate());
        time.setText(listRequest.get(i).getTime());

        return  itemView;
    }
}