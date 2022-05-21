package com.podorozhnik.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.podorozhnik.R;
import com.podorozhnik.entities.Request;
import com.podorozhnik.entities.User;
import com.podorozhnik.fragments.SearchFragment;

import java.util.List;

public class SearchAdapter extends BaseAdapter {
    SearchFragment activity;
    List<Request> listRequest;
    LayoutInflater inflater;

    public SearchAdapter(SearchFragment activity, List<Request> listRequest) {
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
        inflater = (LayoutInflater) activity
                .getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_item, null);

        TextView txt_userLogin = (TextView) itemView.findViewById(R.id.txt_userLogin);
        TextView departurePoint = (TextView) itemView.findViewById(R.id.departurePoint);
        TextView destinationPoint = (TextView) itemView.findViewById(R.id.destinationPoint);
        TextView date = (TextView) itemView.findViewById(R.id.date);
        TextView time = (TextView) itemView.findViewById(R.id.time);
       // TextView txtDestinationPoint = (TextView) itemView.findViewById(R.id.txt_email);

        txt_userLogin.setText(listRequest.get(i).getUserLogin());
        departurePoint.setText(listRequest.get(i).getDeparturePoint());
        destinationPoint.setText(listRequest.get(i).getDestinationPoint());
        date.setText(listRequest.get(i).getDate());
        time.setText(listRequest.get(i).getTime());

        return  itemView;
    }}