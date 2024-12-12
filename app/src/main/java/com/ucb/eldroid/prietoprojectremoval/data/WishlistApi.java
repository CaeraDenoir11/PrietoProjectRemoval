package com.ucb.eldroid.prietoprojectremoval.data;

import com.ucb.eldroid.prietoprojectremoval.data.model.WishlistItem;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

public interface WishlistApi {
    @GET("/items")
    Call<List<WishlistItem>> getItems();

    @POST("/items")
    Call<WishlistItem> addItem(@Body WishlistItem item);

    @DELETE("/items/{id}")
    Call<Void> deleteItem(@Path("id") String id); // Add this

    @PUT("/items/{id}")
    Call<WishlistItem> editItem(@Path("id") String id, @Body WishlistItem item);

}

