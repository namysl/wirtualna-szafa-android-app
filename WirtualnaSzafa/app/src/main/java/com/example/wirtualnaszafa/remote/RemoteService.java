package com.example.wirtualnaszafa.remote;

import android.net.Uri;

import com.example.wirtualnaszafa.model.Suite;
import com.example.wirtualnaszafa.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RemoteService {

    private static final String BASE_URL = "http://adrinna-pilawa-314-a-2022-04.int.heag.live/api/";
    private final RemoteServiceInterface client;

    public RemoteService() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // Add the interceptor to OkHttpClient
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);
//            builder.networkInterceptors().add(interceptor)
        OkHttpClient okHttpClient = builder.build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = (new Retrofit.Builder())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        client = retrofit.create(RemoteServiceInterface.class);
    }

    public Observable<String> login(String email, String password) {
        return client.login(email, password, "Android Smartphone")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> register(String email, String password, String name) {
        return client.register(email, password, "Android Smartphone", name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<User> getUser(String token) {
        return client.getUser("Bearer " + token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Suite>> getSuites(String token) {
        return client.getSuites("Bearer " + token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Suite> getSuite(String token, int id) {
        return client.getSuite("Bearer " + token, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Suite> newSuite(String token, String name, String description, Uri imageUri) {
        File file = new File(imageUri.getPath());

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(file, MediaType.parse("image/*")));

        return client.newSuite("Bearer " + token, name, description, filePart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private interface RemoteServiceInterface{
        @Headers("Accept: application/json")
        @POST("user/login")
        Observable<String> login(@Query("email") String email, @Query("password")String password, @Query("device_name")String deviceName);

        @Headers("Accept: application/json")
        @POST("user/register")
        Observable<String> register(@Query("email") String email, @Query("password")String password, @Query("device_name")String deviceName, @Query("name")String name);

        @Headers("Accept: application/json")
        @GET("user")
        Observable<User> getUser(@Header("Authorization") String token);

        @Headers("Accept: application/json")
        @GET("suites")
        Observable<List<Suite>> getSuites(@Header("Authorization") String token);

        @Headers("Accept: application/json")
        @GET("suite/{id}")
        Observable<Suite> getSuite(@Header("Authorization") String token, @Path("id") int id);

        @Multipart
        @Headers("Accept: application/json")
        @POST("suite/new")
        Observable<Suite> newSuite(@Header("Authorization") String token, @Query("name") String name, @Query("description")String description, @Part MultipartBody.Part image);
    }
}
