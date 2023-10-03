package com.example.pelaporanbencana;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelaporanbencana.adapter.PictureRecViewAdapter;
import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.databinding.ActivityCameraBinding;
import com.example.pelaporanbencana.model.PicDisastersShowall.DataItem;
import com.example.pelaporanbencana.model.PicDisastersShowall.PicDisastersShowAllResponse;
import com.example.pelaporanbencana.model.PicturesDisasters;
import com.example.pelaporanbencana.model.PicturesDisastersStore.PicturesDisastersStoreResponse;

import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import io.github.muddz.styleabletoast.StyleableToast;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;

public class CameraActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 22;
    private static final int STORAGE_PERMISSION_CODE = 2342;
    private static final int REQUEST_CODE = 10;
    RecyclerView recViewCamera;
    PictureRecViewAdapter pictureRecViewAdapter;
    ImageButton imgBtnPictures;
    ImageView imgViewPictures;
    Button btnTambahPictures;
    ProgressDialog pd;
    private Bitmap bitmap;
    ApiInterface apiInterface;
    private Uri filePath;
    private String part_image;

    ActivityCameraBinding binding;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCameraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        clickListeners();

        SharedPreferences pref = getApplicationContext().getSharedPreferences(
                "MyPref", Context.MODE_PRIVATE
        );
        String accessToken = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences(
                "MyPref1", Context.MODE_PRIVATE
        );
        String id_disasters = pref1.getString("id_disasters", "");

        pd = new ProgressDialog(this);
        pd.setMessage("loading...");

//        requestStoragePermission();

        imgViewPictures = findViewById(R.id.imgViewPictures);
        imgBtnPictures = findViewById(R.id.imgBtnPictures);
        btnTambahPictures = findViewById(R.id.btnTambahPictures);
        pictureRecViewAdapter = new PictureRecViewAdapter();
        recViewCamera = findViewById(R.id.recViewCamera);
        recViewCamera.setAdapter(pictureRecViewAdapter);
        recViewCamera.setLayoutManager(new GridLayoutManager(this, 1));
//        imgBtnPictures.setOnClickListener(this);
//        btnTambahPictures.setOnClickListener(this);
//        tvPath =findViewById(R.id.tvPath);
//        tvPath1 = findViewById(R.id.tvPath1);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<PicDisastersShowAllResponse> call = apiInterface.getPicDisaster("Bearer " + accessToken, id_disasters);
        call.enqueue(new Callback<PicDisastersShowAllResponse>() {
            @Override
            public void onResponse(Call<PicDisastersShowAllResponse> call, Response<PicDisastersShowAllResponse> response) {
                PicDisastersShowAllResponse response1 = response.body();
                ArrayList<PicturesDisasters> listData = new ArrayList<>();

                if (response1 != null && response1.isSuccess()){
                    List<DataItem> itemList = response1.getData();

                    for (DataItem item : itemList){
                        PicturesDisasters picturesDisasters = new PicturesDisasters(
                                item.getIdPictures(),
                                item.getIdDisasters(),
                                item.getPictures()
                        );
                        listData.add(picturesDisasters);
                    }
                    Log.d("retrofit", "success" + response.toString());

                }else {
                    StyleableToast.makeText(CameraActivity.this, "Gagal dapat Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error" + response.toString());
                }
                pictureRecViewAdapter.setPicturesDisasters(listData);
            }

            @Override
            public void onFailure(Call<PicDisastersShowAllResponse> call, Throwable t) {
                StyleableToast.makeText(CameraActivity.this, "Gagal", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });
    }

    private void clickListeners() {
        binding.imgBtnPictures.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }else{
                ActivityCompat.requestPermissions(CameraActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        });

        final int min = 20;
        final int max = 80;
        final int random = new Random().nextInt((max - min) + 1) + min;
        String randomId = String.valueOf(random);

        binding.btnTambahPictures.setOnClickListener(view -> {
            addPictures(randomId);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri uri = data.getData();
            Context context = CameraActivity.this;
            path = RealPathUtil.getRealPath(context, uri);

            Bitmap bitmap = BitmapFactory.decodeFile(path);
            binding.imgViewPictures.setImageBitmap(bitmap);
        }
    }

    public void addPictures(String id_pictures){

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String access_token = pref.getString("access_token", "");

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
        String id_disasters = pref1.getString("id_disasters", "");

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://disasterta.herokuapp.com/api/").addConverterFactory(GsonConverterFactory.create()).build();

        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("pictures", file.getName(), requestFile);

        RequestBody id_picture = RequestBody.create(MediaType.parse("multipart/form-data"), id_pictures);
        RequestBody id_disaster = RequestBody.create(MediaType.parse("multipart/form-data"), id_disasters);

        apiInterface = retrofit.create(ApiInterface.class);
        Call<PicturesDisastersStoreResponse> call = apiInterface.setPicDisaster("Bearer " + access_token,
                id_picture, id_disaster, body);
        call.enqueue(new Callback<PicturesDisastersStoreResponse>() {
            @Override
            public void onResponse(Call<PicturesDisastersStoreResponse> call, Response<PicturesDisastersStoreResponse> response) {
                if (response.isSuccessful()){
                    StyleableToast.makeText(CameraActivity.this, "Berhasil Menambahkan Data", Toast.LENGTH_SHORT, R.style.success).show();
                }else{
                    StyleableToast.makeText(CameraActivity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error : " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<PicturesDisastersStoreResponse> call, Throwable t) {
                StyleableToast.makeText(CameraActivity.this, "Gagal", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });

    }
    //    private void requestStoragePermission(){
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == STORAGE_PERMISSION_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Permission not Granted", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private void showFileChooser(){
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK){
//            if (requestCode == REQUEST_CODE){
//                filePath = data.getData();
//                String[] imageprojection = {MediaStore.Images.Media.DATA};
//                Cursor cursor = getContentResolver().query(filePath, imageprojection, null, null, null);
//                tvPath.setText(String.valueOf(filePath));
//                if (cursor != null){
//                    cursor.moveToFirst();
//                    int indexImage = cursor.getColumnIndex(imageprojection[0]);
//                    part_image = cursor.getString(indexImage);
//
//                    if (part_image != null){
//                        File image = new File(part_image);
////                        imgViewPictures.setImageBitmap(BitmapFactory.decodeFile(image.getAbsolutePath()));
////                        tvPath.setText(image.getAbsolutePath());
//                        try {
//                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                            imgViewPictures.setImageBitmap(bitmap);
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//
//
//            }
//        }
//    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
//            filePath = data.getData();
////            String path = getPath(filePath);
//            tvPath.setText(String.valueOf(filePath));
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                imgViewPictures.setImageBitmap(bitmap);
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private String getPath(Uri uri){
//        String[] imageprojection = {MediaStore.Images.Media.DATA};
//        Cursor cursor = getContentResolver().query(uri, imageprojection, null, null, null);
//        cursor.moveToFirst();
//        String document_id = cursor.getString(0);
//
//
//        document_id = document_id.substring(document_id.lastIndexOf(":")+1);
//        cursor.close();
//
//        cursor = getContentResolver().query(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                null,
//                MediaStore.Images.Media._ID + " = ? ", new String[]{document_id},
//                null
//        );
//        cursor.moveToFirst();
//        int indexImage = cursor.getColumnIndex(imageprojection[0]);
//        String path = cursor.getString(indexImage);
//        cursor.close();
//        return path;
//    }
//
//    private void uploadImage(){
//        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
//        String access_token = pref.getString("access_token", "");
//
//        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
//        String id_disasters = pref1.getString("id_disasters", "");
////        String path = getPath(filePath);
//
//        String uploadid = UUID.randomUUID().toString();
//
//        File file = new File(path);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
//        MultipartBody.Part parts = MultipartBody.Part.createFormData("newimage", file.getName(), requestBody);
//
//        RequestBody requestImage = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//
//        RequestBody idDisasters = RequestBody.create(MediaType.parse("text/plain"), id_disasters);
//        RequestBody uploadId = RequestBody.create(MediaType.parse("text/plain"), uploadid);
//
//        apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<PicturesDisastersStoreResponse> call = apiInterface.setPicDisaster("Bearer " + access_token,
//                uploadId, idDisasters, parts);
//        call.enqueue(new Callback<PicturesDisastersStoreResponse>() {
//            @Override
//            public void onResponse(Call<PicturesDisastersStoreResponse> call, Response<PicturesDisastersStoreResponse> response) {
//                if (response.isSuccessful()){
//                    StyleableToast.makeText(CameraActivity.this, "Berhasil Menambahkan Data", Toast.LENGTH_SHORT, R.style.success).show();
//                }else{
//                    StyleableToast.makeText(CameraActivity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PicturesDisastersStoreResponse> call, Throwable t) {
//                StyleableToast.makeText(CameraActivity.this, "Gagal", Toast.LENGTH_SHORT, R.style.error).show();
//            }
//        });
//
//    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.imgBtnPictures:
//                showFileChooser();
//                break;
//            case R.id.btnTambahPictures:
//                uploadImage();
//                break;
//        }
//    }

//    private void uploadImage() {
//        pd.show();
//        String pathh = tvPath.getText().toString();
//        File imageFile = new File(pathh);
//        String a = pathh + "/" + imageFile.getName();
//        tvPath1.setText(a);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), imageFile);
//        MultipartBody.Part parts = MultipartBody.Part.createFormData("pictures", imageFile.getName(), requestBody);
//
//        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
//        String access_token = pref.getString("access_token", "");
//
//        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", Context.MODE_PRIVATE);
//        String id_disasters = pref1.getString("id_disasters", "");
////        String path = getPath(filePath);
////
//        String uploadid = UUID.randomUUID().toString();
//
//        File file = new File(part_image);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
//        MultipartBody.Part parts = MultipartBody.Part.createFormData("newimage", file.getName(), requestBody);

//        RequestBody requestImage = RequestBody.create(MediaType.parse("multipart/form-data"), file);

//        RequestBody idDisasters = RequestBody.create(MediaType.parse("text/plain"), id_disasters);
//        RequestBody uploadId = RequestBody.create(MediaType.parse("text/plain"), uploadid);
//
//        apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<PicturesDisastersStoreResponse> call = apiInterface.setPicDisaster("Bearer " + access_token,
//                uploadId, idDisasters, parts);
//        call.enqueue(new Callback<PicturesDisastersStoreResponse>() {
//            @Override
//            public void onResponse(Call<PicturesDisastersStoreResponse> call, Response<PicturesDisastersStoreResponse> response) {
//                if (response.isSuccessful()){
//                    StyleableToast.makeText(CameraActivity.this, "Berhasil Menambahkan Data", Toast.LENGTH_SHORT, R.style.success).show();
//                }else{
//                    StyleableToast.makeText(CameraActivity.this, "Gagal Menambahkan Data", Toast.LENGTH_SHORT, R.style.error).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PicturesDisastersStoreResponse> call, Throwable t) {
//                StyleableToast.makeText(CameraActivity.this, "Gagal", Toast.LENGTH_SHORT, R.style.error).show();
//            }
//        });
//    }


}