package com.example.tsaujt.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;


/**
 * Created by toy9986619 on 2017/5/11.
 */

public class PopUpTimeChoose extends Activity {
    private DatePicker datePicker;
    private static final int RESULT_OK = 1;
    private Button okButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_time);
        datePicker = (DatePicker) findViewById(R.id.datePicker);

        okButton = (Button) findViewById(R.id.popup_okButton);
        cancelButton = (Button) findViewById(R.id.popup_cancelButton);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int heigth = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(heigth*.6));
    }

    public void popupOkButton(View v){

        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        String time;

        int year = datePicker.getYear();
        int month = datePicker.getMonth()+1;
        int day = datePicker.getDayOfMonth();

        if(month<10){
            time = Integer.toString(year)+"0"+Integer.toString(month)+Integer.toString(day);
        }
        else{
            time = Integer.toString(year)+Integer.toString(month)+Integer.toString(day);
        }

        bundle.putString("time", time);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);

        PopUpTimeChoose.this.finish();
    }

    public void popupCancelButton(View v){
        PopUpTimeChoose.this.finish();
    }


}
