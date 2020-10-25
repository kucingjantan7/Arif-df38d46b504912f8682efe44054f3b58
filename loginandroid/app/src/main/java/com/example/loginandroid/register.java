package com.example.loginandroid;

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
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ngantrionline.Singleton.Singleton;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


public class register extends AppCompatActivity {


    EditText tusername;
    EditText tpassword;
    EditText tpassword2;
    Button bregis;

    public void onClick(View v){
        Intent intent = null;
        switch (v.getId()){
            case R.id.link_login:
                intent = new Intent(this, login.class );
                break;
        }
        if (null!=intent) startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        bregis = (Button) findViewById(R.id.btn_register);
        tusername = (EditText) findViewById(R.id.username);
        tpassword = (EditText) findViewById(R.id.password);
        tpassword2 = (EditText) findViewById(R.id.REPpassword);

        final String url = "http://192.168.43.90/login/user/tambahUser.php";

        bregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 String pass1 = tpassword.getText().toString();
                 String pass2 = tpassword2.getText().toString();

                  if (!pass1.equals(pass2)){
                      Toast.makeText(getApplicationContext(), "Password dan Konfirmasi Password Berbeda", Toast.LENGTH_SHORT).show();
                      startActivity(new Intent(register.this, login.class));
                      finish();
                  }
                StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        String json = response.toString();

                        try{
                            JSONObject data = new JSONObject(response);

                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }){
                    @SuppressLint("NewApi")
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", tusername.getText().toString());
                        params.put("password", tpassword.getText().toString());
                     //   params.put("password", tpassword.getText().toString());
                        return params;
                    }
                };

                Singleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest);

            }

        });

    }


}

