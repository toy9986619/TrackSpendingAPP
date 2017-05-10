package com.example.tsaujt.finalproject;

import android.content.ContentValues;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class AddActivity extends AppCompatActivity {

    private DBHelper DB;
    private EditText eMoney;
    private TextView tShow;
    private Button buttonBreakfast;
    private Button buttonLunch;
    private Button buttonDinner;
    private Button buttonSave;
    private Button buttonCancel;
    private EditText eExplanation;
    private EditText eTime;
    private int spendType=0;
    private int account=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        DB = DBHelper.getInstance(this);
        findViews();
        this.setTitle("新增花費");
    }

    public void findViews(){
        eMoney = (EditText) findViewById(R.id.money);
        tShow = (TextView) findViewById(R.id.show);
        buttonBreakfast = (Button) findViewById(R.id.buttonBreakfast);
        buttonLunch = (Button) findViewById(R.id.buttonLunch);
        buttonDinner = (Button) findViewById(R.id.buttonDinner);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        eExplanation = (EditText) findViewById(R.id.explanation);
        eTime = (EditText) findViewById(R.id.time);

        buttonBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spendType = 1;
            }
        });

        buttonLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spendType = 2;
            }
        });

        buttonDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spendType=3;
            }
        });
    }

    public void addRecord(View v){
        String time = eTime.getText().toString();
        String moneyString = eMoney.getText().toString();
        int money=0;
        if(!moneyString.isEmpty()){
            money = Integer.parseInt(moneyString);
        }

        String show ="NT$"+money;
        String explanation = eExplanation.getText().toString();

        ContentValues values = new ContentValues();
        values.put("type", spendType);
        values.put("money", money);
        values.put("explanation", explanation);
        values.put("time", time);
        values.put("account", account);
        long id = DB.getWritableDatabase().insert("record", null, values);
        Log.d("ADD", id+"");

    }



}
