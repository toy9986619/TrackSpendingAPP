package com.example.tsaujt.finalproject;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AddActivity {
    int recordId;

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
        tTime.setText(bundle.getString("time"), TextView.BufferType.EDITABLE);
        spendType=bundle.getInt("spendType");
        recordId=bundle.getInt("recordId");
        Log.d("CHECK Edit", Integer.toString(recordId));
    }

    @Override
    public void cancelButton(View v){
        EditActivity.this.finish();
    }

    public View.OnClickListener saveRecord = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String time = tTime.getText().toString();
            String moneyString = eMoney.getText().toString();
            int money=0;
            if(!moneyString.isEmpty()){
                money = Integer.parseInt(moneyString);
            }

            String explanation = eExplanation.getText().toString();

            ContentValues values = new ContentValues();
            values.put("type", spendType);
            values.put("money", money);
            values.put("explanation", explanation);
            values.put("time", time);
            values.put("account", account);
            long id = DB.getWritableDatabase().update("record", values, "_id=" + recordId, null);
            Log.d("ADD", id+"");

            EditActivity.this.finish();
        }
    };
}
