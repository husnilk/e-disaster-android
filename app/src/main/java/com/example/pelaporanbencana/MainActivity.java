package com.example.pelaporanbencana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.ServerError;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
import com.example.pelaporanbencana.api.ApiClient;
import com.example.pelaporanbencana.api.ApiInterface;
import com.example.pelaporanbencana.model.Login.Data;
import com.example.pelaporanbencana.model.Login.LoginResponse;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button buttonLogin;
    EditText editTextEmail;
    EditText editTextPassword;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogin = findViewById(R.id.buttonLogin);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
//        sharedPreferencesHelper = new SharedPreferencesHelper(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //input data
                String Email = editTextEmail.getText().toString();
                String Pass = editTextPassword.getText().toString();

                //jika email kosong atau tidak pakai @
                if (Email.isEmpty()){
                    editTextEmail.setError("Email is required");
                    editTextEmail.requestFocus();
                    return;
                }else if(Email.contains("@") == false){
                    editTextEmail.setError("Your email is not corret");
                    editTextEmail.requestFocus();
                }

                //jika pass kosong
                if (Pass.isEmpty()){
                    editTextPassword.setError("Password is required");
                    editTextPassword.requestFocus();
                    return;
                }

                login(Email, Pass);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void login(String email, String pass) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> loginResponseCall = apiInterface.loginResponse(email, pass);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if(loginResponse != null && response.isSuccessful()){

                    Data data = loginResponse.getData();
                    String name_pref = data.getName();
                    String token_pref = data.getAccessToken();
                    String tokenType_pref = data.getTokenType();
                    String id_user = String.valueOf(data.getId());
                    String institution  = data.getInstitution();

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("id_user", id_user);
                    editor.putString("name", name_pref);
                    editor.putString("access_token", token_pref);
                    editor.putString("institution", institution);
                    editor.putString("token_type", tokenType_pref);
                    editor.apply();

//                    Toast.makeText(MainActivity.this, response.body().getMessage() + ", " + response.body().getData().getName(), Toast.LENGTH_SHORT).show();

                    StyleableToast.makeText(MainActivity.this, response.body().getMessage() + ", " + response.body().getData().getName(), Toast.LENGTH_SHORT, R.style.normal).show();

                    Log.d("retrofit", "response : " + response.toString());

                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();

                }else{
                    StyleableToast.makeText(MainActivity.this, "Email/Password Anda Salah", Toast.LENGTH_SHORT, R.style.error).show();
                    Log.d("retrofit", "error : " + response.toString());
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                StyleableToast.makeText(MainActivity.this, "Email/Password Anda Salah", Toast.LENGTH_SHORT, R.style.error).show();
            }
        });

    }

}