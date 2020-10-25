package com.example.loginandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button hallo;
    TextClock jamdigital;
    String username;
    int login_time;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hallo = (Button) findViewById(R.id.btn_hallo);
        jamdigital = (TextClock) findViewById(R.id.jam);
        final String url = "hhttp://192.168.43.90/login/user/dataUser.php";

        showTextClock(jamdigital);
        SharedPreferences userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        username = userPreferences.getString("username", null);
        login_time = userPreferences.getInt("login_time", 0);
        hallo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String json = response.toString();
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            if(jsonObject.getString("status").equals("login")){
                                JSONObject dataUser = new JSONObject(jsonObject.getString("data"));
                                Toast.makeText(getApplicationContext(), "Hai" + username+ "Waktu Anda login" +login_time, Toast.LENGTH_SHORT).show();
                                finish();

                            }else{
                                Toast.makeText(getApplicationContext(), "Silahkan login terlebih dahulu", Toast.LENGTH_LONG).show();
                            }
                        }catch(Exception x){
                            x.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.printStackTrace();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        return params;
                    }
                };

                com.example.ngantrionline.Singleton.Singleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest);
            }
        });


    }

    public void showTextClock(View view) {
        jamdigital.setText(view.VISIBLE);
    }

}
