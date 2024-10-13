package com.example.nstocksearch;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class MainActivity extends AppCompatActivity {

    private EditText editTextText2;
    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editTextText2 = findViewById(R.id.editTextText2);
        Button button = findViewById(R.id.button);
        textView2 = findViewById(R.id.textView2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stockSymbol = editTextText2.getText().toString();
                fetchStockDetails(stockSymbol);
            }
        });
    }

    private void fetchStockDetails(String stockSymbol) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apidojo-yahoo-finance-v1.p.rapidapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        StockApi stockApi = retrofit.create(StockApi.class);
        Call<StockResponse> call = stockApi.getStockDetails(stockSymbol);
        call.enqueue(new Callback<StockResponse>() {
            @Override
            public void onResponse(Call<StockResponse> call, Response<StockResponse> response) {
                if (response.isSuccessful()) {
                    StockResponse stockResponse = response.body();
                    if (stockResponse != null && stockResponse.getQuoteResponse() != null) {
                        try {
                            if (stockResponse.getQuoteResponse().getResult().length > 0) {
                                double price = stockResponse.getQuoteResponse().getResult()[0].getRegularMarketPrice();
                                runOnUiThread(() -> textView2.setText("Price: " + price));
                            } else {
                                runOnUiThread(() -> textView2.setText("No data available"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(() -> textView2.setText("Error parsing response: " + e.getMessage()));
                        }
                    } else {
                        runOnUiThread(() -> textView2.setText("Error: QuoteResponse or Result is null"));
                    }
                } else {
                    try {
                        String errorResponse = response.errorBody().string();
                        Log.e("StockResponse", "Error Body: " + errorResponse);
                        runOnUiThread(() -> textView2.setText("Response Error: " + errorResponse));
                    } catch (Exception e) {
                        runOnUiThread(() -> textView2.setText("Error parsing error body."));
                    }
                }
            }

            @Override
            public void onFailure(Call<StockResponse> call, Throwable t) {
                runOnUiThread(() -> textView2.setText("Failure: " + t.getMessage()));
            }
        });
    }





}
