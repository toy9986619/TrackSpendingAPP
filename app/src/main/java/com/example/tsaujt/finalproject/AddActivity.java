package com.example.tsaujt.finalproject;

//import android.app.FragmentManager;
import android.content.ContentValues;

import android.graphics.Typeface;
import android.support.annotation.IntegerRes;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class AddActivity extends AppCompatActivity {

    protected DBHelper DB;
    protected EditText eMoney;
    protected TextView tShow;
    protected Button buttonBreakfast;
    protected Button buttonLunch;
    protected Button buttonDinner;
    protected Button buttonSave;
    protected Button buttonCancel;
    protected EditText eExplanation;
    protected TextView tTime;
    public String time;
    private BlankFragment blankFragment;
    private FragmentManager mFragmentMgr;

    protected int spendType=0;
    protected int account=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        DB = DBHelper.getInstance(this);


        mFragmentMgr = getSupportFragmentManager();



        Bundle bundle = this.getIntent().getExtras();
        time = bundle.getString("time");
        findViews();
        this.setTitle("新增花費");

    }

    public void findViews() {
        eMoney = (EditText) findViewById(R.id.money);
        tShow = (TextView) findViewById(R.id.show);
        buttonBreakfast = (Button) findViewById(R.id.buttonBreakfast);
        buttonLunch = (Button) findViewById(R.id.buttonLunch);
        buttonDinner = (Button) findViewById(R.id.buttonDinner);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        eExplanation = (EditText) findViewById(R.id.explanation);
        tTime = (TextView) findViewById(R.id.time);


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
                spendType = 3;
            }
        });
        buttonSave.setOnClickListener(addRecord);

        tTime.setText(time);
        //tShow.setClickable(true);
        //tShow.setOnClickListener(countEvent);
    }

    public View.OnClickListener addRecord = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            String time = tTime.getText().toString();
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

            AddActivity.this.finish();
        }

    };
    /*
    private View.OnClickListener countEvent = new View.OnClickListener () {

        @Override
        public void onClick(View v) {
            blankFragment = new BlankFragment();
            mFragmentMgr.beginTransaction()
                    .replace(R.id.countmenu, blankFragment)
                    .commit();
        }
    };*/

    public void cancelButton(View v){
        AddActivity.this.finish();
    }



}
