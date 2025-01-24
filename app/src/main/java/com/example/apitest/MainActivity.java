package com.example.apitest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ImageView apipicture;
    private ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: Started");

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        apipicture = findViewById(R.id.apipicture);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thecatapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
        getCatImage();
    }


    public void getCatImage() {
        Call<List<CatImage>> call = service.getCatImage();
        call.enqueue(new Callback<List<CatImage>>() {
            @Override
            public void onResponse(Call<List<CatImage>> call, Response<List<CatImage>> response) {
                List<CatImage> catImages = response.body();
                for (CatImage cat : catImages) {
                    Log.d(TAG, "Cat URL: " + cat.getUrl());
                    Picasso.get()
                            .load(cat.getUrl())
                            .into(apipicture);
                }
            }


            @Override
            public void onFailure(Call<List<CatImage>> call, Throwable throwable) {
                Log.e(TAG, "onFailure", throwable);
            }
        });
    }

    public void pressButton(View v){
        // Samma knappmetod som tidigare, anropar bara getCatImage()
        getCatImage();
    }

}
