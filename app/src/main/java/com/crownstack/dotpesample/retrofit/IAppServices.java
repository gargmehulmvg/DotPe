package com.crownstack.dotpesample.retrofit;

import com.crownstack.dotpesample.model.response.ItemResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IAppServices {

    @GET("getItems/2018")
    Call<ItemResponse> getItemsServerCall();

}
