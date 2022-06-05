package com.podorozhnik.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.android.volley.RequestQueue;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.podorozhnik.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Random;

public class Meme extends Fragment implements View.OnClickListener {
    ProgressBar loading;
    ImageView memeImage;
    private Button next;
    private Button sh;
    private static Random memePositionGenerator = new Random();
    private String memeUrl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.activity_meme, container, false);

        loading = fragmentView.findViewById(R.id.progress);
        memeImage = fragmentView.findViewById(R.id.image);

        next = fragmentView.findViewById(R.id.next);
        next. setOnClickListener(this);

        sh = fragmentView.findViewById(R.id.share);
        sh. setOnClickListener(this);

        super.onCreate(savedInstanceState);

        getMeme();
        return fragmentView;
    }

    private void getMeme(){
        loading.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        String url = "https://meme-api.herokuapp.com/gimme";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray memesArray = response.getJSONArray("preview");
                        memeUrl = memesArray.getString(memePositionGenerator.nextInt(memesArray.length()));
                    }
                    catch (JSONException exception) {
                        memeUrl = "";
                    }

                    Glide.with(Meme.this).load(memeUrl).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            loading.setVisibility(View.VISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                            loading.setVisibility(View.GONE);
                            return false;
                        }

                    }).into(memeImage);
                },
                error -> {}
        );
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onClick (View view) {
        if (view.getId() == R.id.next){
            getMeme();
        }else if (view.getId() == R.id.share){
            shareMeme(view);
        }
    }

    public void shareMeme(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra("Check meme using", memeUrl);
        Intent chooser = Intent.createChooser(intent,"Поделиться с помощью...");
        startActivity(chooser);
    }


}