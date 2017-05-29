package com.example.tsaujt.finalproject;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient();
    EditText eEmail;
    EditText ePasswd;
    private SharedPreferences settings;
    private static final String data = "DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("登入");
        findViews();

    }

    private void findViews() {
        eEmail = (EditText) findViewById(R.id.login_email);
        ePasswd = (EditText) findViewById(R.id.login_passwd);
    }

    public void loginButton(View view) {

        String email = eEmail.getText().toString();
        String password = ePasswd.getText().toString();

        FormBody loginFM = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();



        Request request = new Request.Builder()
                .url("http://49.159.104.66:808/applogin")
                .post(loginFM)
                .build();


        //Response response = client.newCall(request).execute();
        //Log.d("status", response.toString());

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                switch(response.code()) {
                    case 200 :
                        String json = response.body().string();
                        Log.d("OKHTTP", json);
                        //解析JSON
                        parseJSON(json);
                        LoginActivity.this.finish();
                        break;

                    case 403 :
                        break;
                    case 404 :
                        break;

                    case 500 :
                        break;
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {
                //告知使用者連線失敗
                e.printStackTrace();
            }
        });

    }

    public void parseJSON(String json){
        try{
            JSONObject loginJson = new JSONObject(json);
            int status = loginJson.getInt("status");

            if(status==1){
                String user = loginJson.getString("user");
                saveData(user);
            }

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void saveData(String user){
        settings = getSharedPreferences(data,0);
        settings.edit()
                .putString("user", user).apply();
    }




}
