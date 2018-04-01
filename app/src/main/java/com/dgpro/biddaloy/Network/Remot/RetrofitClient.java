package com.dgpro.biddaloy.Network.Remot;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 *
 * Created by Babu on 1/5/2018.
 *
 */

public class RetrofitClient {

    private static Retrofit anotherRetrofit = null;

    public static Retrofit getClient(String baseUrl) {
        Retrofit retrofit ;
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
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
