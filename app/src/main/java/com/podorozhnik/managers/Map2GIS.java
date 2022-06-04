package com.podorozhnik.managers;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.podorozhnik.enums.OperationResults;
import com.podorozhnik.fragments.MapFragment;

import java.io.InputStream;
import java.net.URL;

public class Map2GIS extends AsyncTask<String, Void, Drawable> {
    private MapFragment fragmentReference;
    private OperationResults operationResult;

    public Map2GIS(MapFragment fragmentReference){
        this.fragmentReference = fragmentReference;
    }

    protected Drawable doInBackground(String... urls) {
        Drawable receivedMap;
        try (InputStream inputStream = (InputStream) new URL(urls[0]).getContent()){
            receivedMap = Drawable.createFromStream(inputStream, "map");
        }
        catch (Exception exception) {
            receivedMap = null;
            if (urls[0].equals(""))
                operationResult = OperationResults.GPS_ERROR;
            else
                operationResult = OperationResults.MAP_ERROR;
            exception.printStackTrace();
        }

        return receivedMap;
    }

    protected void onPostExecute(Drawable receivedMap) {
        if (receivedMap != null)
            fragmentReference.updateMap(receivedMap);
        else
            fragmentReference.onResultReceived(operationResult);
    }
}
