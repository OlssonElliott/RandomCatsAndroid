package com.example.apitest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("images/search")
    Call<List<CatImage>> getCatImage();
}
