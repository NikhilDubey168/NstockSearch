package com.example.nstocksearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface StockApi {
    @Headers({
            "X-RapidAPI-Key: fe210df9ebmsh95f046a39fcced2p1a059djsn93d7c2a59049",
            "X-RapidAPI-Host: apidojo-yahoo-finance-v1.p.rapidapi.com",
            "Accept: application/json"
    })
    @GET("stock/v2/get-summary")
    Call<StockResponse> getStockDetails(@Query("symbol") String stockSymbol);

}
