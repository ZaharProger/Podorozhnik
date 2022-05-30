package com.podorozhnik.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.podorozhnik.R;
import com.podorozhnik.entities.Request;
import com.podorozhnik.fragments.AllSearchFragment;

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

        inflater = (LayoutInflater) activity2
                .getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_item, null);
        itemView.setSaveEnabled(false);
        TextView txt_userLogin = itemView.findViewById(R.id.txt_userLogin);
        TextView departurePoint = itemView.findViewById(R.id.departurePoint);
        TextView destinationPoint = itemView.findViewById(R.id.destinationPoint);
        TextView date = itemView.findViewById(R.id.date);
        TextView time = itemView.findViewById(R.id.time);

            txt_userLogin.setText(listRequest.get(i).getUserLogin());
            departurePoint.setText(listRequest.get(i).getDeparturePoint());
            destinationPoint.setText(listRequest.get(i).getDestinationPoint());
            date.setText(listRequest.get(i).getDate());
            time.setText(listRequest.get(i).getTime());

        return  itemView;
    }}