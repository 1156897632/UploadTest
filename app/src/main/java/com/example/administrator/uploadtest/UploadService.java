package com.example.administrator.uploadtest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by Administrator on 2017/8/15.
 */

public interface UploadService {

    @Multipart
    @POST("upload")
    Observable<ResponseBody> rxUpload(@Part("description") RequestBody body, @Part MultipartBody.Part file);

    @Multipart
    @POST("upload")
    Call<ResponseBody> callUpload(@Part("description") RequestBody body, @Part MultipartBody.Part file);
}
