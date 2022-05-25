package com.podorozhnik.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.podorozhnik.R;
import com.podorozhnik.entities.Request;
import com.podorozhnik.fragments.DriverSearchFragment;

import java.util.List;

public class DriverSearchAdapter extends BaseAdapter {
    DriverSearchFragment activity3;
    List<Request> listRequest;
    LayoutInflater inflater;

    public DriverSearchAdapter(DriverSearchFragment activity3, List<Request> listRequest) {
        this.activity3 = activity3;
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
        if (listRequest.get(i).isDriver()){
            inflater = (LayoutInflater) activity3
                    .getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View itemView = inflater.inflate(R.layout.listview_item, null);
            itemView.setSaveEnabled(false);

            TextView txt_userLogin = (TextView) itemView.findViewById(R.id.txt_userLogin);
            TextView departurePoint = (TextView) itemView.findViewById(R.id.departurePoint);
            TextView destinationPoint = (TextView) itemView.findViewById(R.id.destinationPoint);
            TextView date = (TextView) itemView.findViewById(R.id.date);
            TextView time = (TextView) itemView.findViewById(R.id.time);

            txt_userLogin.setText(listRequest.get(i).getUserLogin());
            departurePoint.setText(listRequest.get(i).getDeparturePoint());
            destinationPoint.setText(listRequest.get(i).getDestinationPoint());
            date.setText(listRequest.get(i).getDate());
            time.setText(listRequest.get(i).getTime());

            return  itemView;
        }else {        inflater = (LayoutInflater) activity3
                .getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.nothing, null);


            return itemView;}

    }}