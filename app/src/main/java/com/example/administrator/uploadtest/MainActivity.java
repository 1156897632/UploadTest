package com.example.administrator.uploadtest;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "TAG";

    String baseurl = "";
    Button btn_startUpload;
    Button btn_clearFiles;
    File file;
    String path;
    FileManage manage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_startUpload = (Button) findViewById(R.id.btn_startUpload);
        btn_clearFiles = (Button) findViewById(R.id.btn_clearFiles);
        btn_startUpload.setOnClickListener(this);
        btn_clearFiles.setOnClickListener(this);
        init();
    }

    private void init(){
        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        String file_name = "shumei.txt";
        String file_path = path + "/" + file_name;
        file = new File(file_path);
    }

    private void doDivideFile() throws Exception {
        manage = new FileManage();
        manage.clearFiles();
        manage.createnewFile(path);
        manage.divideFile(file);
    }

    private void doUpload(){
        Retrofit retrofit = new Retrofit
                .Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseurl)
                .build();
        RequestBody requestfile = RequestBody.create(MediaType.parse("multipart/form-data"),file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("image",file.getName(),requestfile);

        String descriptionString = "hello,this is file description";

        RequestBody description = RequestBody.create(MediaType.parse("mulitpart/form-data"),descriptionString);

        UploadService service = retrofit.create(UploadService.class);

        Call<ResponseBody> call = service.callUpload(description,body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Log.d("TAG","Success");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("TAG","Fail");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_startUpload:
//                doUpload();
                try {
                    doDivideFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_clearFiles:
                Log.d(TAG, "onClick: delete");
                manage.deleteFile();
                break;
            default:

        }
    }
}
