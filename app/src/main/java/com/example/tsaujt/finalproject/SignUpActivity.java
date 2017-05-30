package com.example.tsaujt.finalproject;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText eEmail;
    private EditText ePassword;
    private EditText ePasswordAgain;
    private EditText eUsername;
    OkHttpClient client = new OkHttpClient();
    private static Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setTitle("註冊");
        findViews();
    }

    private void findViews() {
        eEmail = (EditText) findViewById(R.id.signup_email);
        ePassword = (EditText) findViewById(R.id.signup_passwd);
        ePasswordAgain = (EditText) findViewById(R.id.signup_passwdagain);
        eUsername = (EditText) findViewById(R.id.signup_username);
    }

    public void signUp(View view){

        String email = eEmail.getText().toString();
        String password = ePassword.getText().toString();
        String passwordAgain = ePasswordAgain.getText().toString();
        String username = eUsername.getText().toString();

        boolean vaild=false;

        if(!isEmailValid(email)){
            makeTextAndShow(SignUpActivity.this, "email格式不正確，請重新輸入", Toast.LENGTH_SHORT);
        }
        else if (!password.equals(passwordAgain)){
            makeTextAndShow(SignUpActivity.this, "密碼不相同，請重新輸入", Toast.LENGTH_SHORT);
        }
        else{
            vaild = true;
        }

        if(vaild){
            sendSignUp(email, password, username);
        }



    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    public void signCancel(View view){
        SignUpActivity.this.finish();
    }

    public void sendSignUp(String email, String password, String username){
        FormBody loginFM = new FormBody.Builder()
                .add("username", username)
                .add("email", email)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("http://49.159.104.66:808/appsignup")
                .post(loginFM)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                switch(response.code()) {
                    case 200 :
                        String str = response.body().string();
                        if(str.equals("done")){
                            makeTextAndShow(SignUpActivity.this, "註冊成功!", Toast.LENGTH_SHORT);
                            SignUpActivity.this.finish();
                        }else{
                            makeTextAndShow(SignUpActivity.this, "註冊失敗!", Toast.LENGTH_SHORT);
                        }

                        break;

                    case 403 :
                        makeTextAndShow(SignUpActivity.this, "http 403 error", Toast.LENGTH_SHORT);
                        break;
                    case 404 :
                        makeTextAndShow(SignUpActivity.this, "http 404 error", Toast.LENGTH_SHORT);
                        break;

                    case 500 :
                        makeTextAndShow(SignUpActivity.this, "http 500 error", Toast.LENGTH_SHORT);
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

    private void makeTextAndShow(final Context context, final String text, final int duration) {
        SignUpActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                if (toast == null) {
                    //如果還沒有用過makeText方法，才使用
                    toast = android.widget.Toast.makeText(context, text, duration);
                } else {
                    toast.setText(text);
                    toast.setDuration(duration);
                }
                toast.show();
            }
        });

    }
}
