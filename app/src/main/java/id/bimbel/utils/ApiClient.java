package id.bimbel.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
                .addInterceptor(logging)
                .build();

        Gson gson = new GsonBuilder()
               .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(AppConstans.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return mRetrofit;
    }
}
