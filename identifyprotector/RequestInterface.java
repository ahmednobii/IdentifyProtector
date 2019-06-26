package com.identifyprotector.identifyprotector;

import com.identifyprotector.identifyprotector.models.ServerRequest;
import com.identifyprotector.identifyprotector.models.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {

    @POST("identityprotector/")
    Call<ServerResponse> operation(@Body ServerRequest request);

}
