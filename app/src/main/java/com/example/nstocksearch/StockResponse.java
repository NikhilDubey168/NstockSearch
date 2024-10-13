package com.example.nstocksearch;

import com.google.gson.annotations.SerializedName;

public class StockResponse {
    @SerializedName("quoteResponse")
    private QuoteResponse quoteResponse;

    public QuoteResponse getQuoteResponse() {
        return quoteResponse;
    }

    public void setQuoteResponse(QuoteResponse quoteResponse) {
        this.quoteResponse = quoteResponse;
    }

    public static class QuoteResponse {
        @SerializedName("result")
        private Stock[] result;

        public Stock[] getResult() {
            return result;
        }

        public void setResult(Stock[] result) {
            this.result = result;
        }
    }

    public static class Stock {
        @SerializedName("regularMarketPrice")
        private double regularMarketPrice;

        public double getRegularMarketPrice() {
            return regularMarketPrice;
        }

        public void setRegularMarketPrice(double regularMarketPrice) {
            this.regularMarketPrice = regularMarketPrice;
        }
    }
}
