package id.bimbel.utils;

import id.bimbel.model.Pom;
import id.bimbel.model.PomResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Comp on 7/29/2017.
 */

public interface BimbelApi {
//    @FormUrlEncoded
//    @POST("register")
//    Call<User> register(@Field("name") String name, @Field("email") String email, @Field("password") String password);
//
//    @FormUrlEncoded
//    @POST("login")
//    Call<User> login(@Field("email") String email, @Field("password") String password);

    @GET("allpom")
    Call<PomResponse> getAllPom();

    @GET("pom/{pom}")
    Call<Pom> getPom(@Path("pom") String namePom);
}
