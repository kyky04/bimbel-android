package id.bimbel.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import id.bimbel.service.AppContans;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Comp on 7/29/2017.
 */

public class ApiClient {
    private static Retrofit mRetrofit;

    public static Retrofit newInstance() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();



        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(AppContans.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return mRetrofit;
    }
}
