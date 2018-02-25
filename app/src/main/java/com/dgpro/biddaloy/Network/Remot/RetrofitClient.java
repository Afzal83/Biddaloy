package com.dgpro.biddaloy.Network.Remot;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 *
 * Created by Babu on 1/5/2018.
 *
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static Retrofit anotherRetrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static Retrofit getAnotherClient(String baseUrl) {
        if (anotherRetrofit==null) {
            anotherRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return anotherRetrofit;
    }
}
