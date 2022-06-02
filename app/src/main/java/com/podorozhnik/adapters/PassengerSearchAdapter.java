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
import com.podorozhnik.fragments.PassengerSearchFragment;

import java.util.List;

public class PassengerSearchAdapter extends BaseAdapter {
    PassengerSearchFragment activity;
    List<Request> listRequest;
    LayoutInflater inflater;

    public PassengerSearchAdapter(PassengerSearchFragment activity, List<Request> listRequest) {
        this.activity = activity;
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
        if (!listRequest.get(i).isDriver()){
        inflater = (LayoutInflater) activity
                .getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_item, null);
            itemView.setSaveEnabled(false);

        TextView departurePoint = (TextView) itemView.findViewById(R.id.departurePoint);
        TextView destinationPoint = (TextView) itemView.findViewById(R.id.destinationPoint);
        TextView date = (TextView) itemView.findViewById(R.id.date);
        TextView time = (TextView) itemView.findViewById(R.id.time);
        ImageView city=(ImageView) itemView.findViewById(R.id.city) ;

            city.setImageResource(R.drawable.ic_city);
        departurePoint.setText(listRequest.get(i).getDeparturePoint());
        destinationPoint.setText(listRequest.get(i).getDestinationPoint());
        date.setText(listRequest.get(i).getDate());
        time.setText(listRequest.get(i).getTime());
            return  itemView;
}else {        inflater = (LayoutInflater) activity
                .getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.nothing, null);


            return itemView;}

    }}