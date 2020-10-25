package com.example.loginandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ngantrionline.Singleton.Singleton;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

    EditText tusername;
    EditText tpassword;
    Button bbtn_login;
    SharedPreferences userPreferences;

    public void onClick(View v){
        Intent intent = null;
        switch (v.getId()){
            case R.id.register:
                intent = new Intent(this, register.class );
                break;
        }
        if (null!=intent) startActivity(intent);
    }


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        tusername = (EditText) findViewById(R.id.username);
        tpassword = (EditText) findViewById(R.id.password);
        bbtn_login = (Button) findViewById(R.id.btn_login);
        final String url = "http://192.168.43.90/login/user/dataUser.php";

        SharedPreferences userPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);

        if(userPreferences.getString("status", null) == "login"){
            startActivity(new Intent(login.this, MainActivity.class));
            finish();
        }

        bbtn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String username = username.getText().toString();
//                String pass = tpassword.getText().toString();
//
//                if (username.equals("mulyono") && pass.equals("ayam")){
//                    Toast.makeText(getApplicationContext(), "Login Sukses", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(formloginumum.this, MainActivity.class));
//                    finish();
//                }
                StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Kembalian Login", response);
                        String json = response.toString();

                        try {
                            JSONObject jsonObject = new JSONObject(json);

                            if(jsonObject.getString("status").equals("sukses")){
                                JSONObject dataUser = new JSONObject(jsonObject.getString("data"));
                                Log.d("id", String.valueOf(dataUser.getInt("id")));

                                SharedPreferences sharedPreferences = PreferenceManager
                                        .getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("status", "login");
                                editor.putInt("id", dataUser.getInt("id"));
                                editor.putInt("username", dataUser.getInt("username"));
                                editor.putInt("login_time", dataUser.getInt("login_time"));
                                editor.apply();
                                startActivity(new Intent(login.this, MainActivity.class));
                                finish();

                            }else{
                                Toast.makeText(getApplicationContext(), "Username atau password salah", Toast.LENGTH_LONG).show();
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
                        params.put("username", tusername.getText().toString());
                        params.put("password", tpassword.getText().toString());
                        return params;
                    }
                };

                Singleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest);
            }
        });

    }
}

