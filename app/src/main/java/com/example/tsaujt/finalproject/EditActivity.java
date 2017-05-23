package com.example.tsaujt.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AddActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Bundle bundle = this.getIntent().getExtras();
        this.setTitle("編輯花費");
        DB = DBHelper.getInstance(this);
        findViews();

        buttonSave.setOnClickListener(saveRecord);
        //int money = bundle.getInt("money");
        //Log.d("ADD", Integer.toString(bundle.getInt("money")));
        tShow.setText("NT$"+bundle.getInt("money"));
        eTime.setText(bundle.getString("time"), TextView.BufferType.EDITABLE);
    }

    @Override
    public void cancelButton(View v){
        EditActivity.this.finish();
    }

    public View.OnClickListener saveRecord = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        }
    };
}
