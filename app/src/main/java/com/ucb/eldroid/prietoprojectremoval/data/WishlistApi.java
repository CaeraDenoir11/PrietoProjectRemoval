package com.ucb.eldroid.prietoprojectremoval.data;

import com.ucb.eldroid.prietoprojectremoval.data.model.WishlistItem;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface WishlistApi {
    @GET("/items")
    Call<List<WishlistItem>> getItems();

    @POST("/items")
    Call<WishlistItem> addItem(@Body WishlistItem item);
}

