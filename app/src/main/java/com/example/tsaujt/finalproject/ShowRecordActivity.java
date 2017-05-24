package com.example.tsaujt.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ShowRecordActivity extends AppCompatActivity {
    DBHelper DB = DBHelper.getInstance(this);
    private TextView showMoney;
    private TextView showSpentType;
    private TextView showTime;
    int money;
    int spendType;
    String spendName;
    String explanation;
    int recordId;
    int recordIndex;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.showRecord_Toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);

        findViews();
        Bundle bundle = this.getIntent().getExtras();
        time = bundle.getString("time");
        recordIndex = bundle.getInt("recordIndex");

        //Log.d("CHECK", Integer.toString(recordId));

    }

    @Override
    protected void onResume(){
        super.onResume();

        showItem();


    }

    public void showItem(){
        Log.d("CHECK", Integer.toString(recordIndex));
        String db_query = "SELECT record.*, spendtype.typename, spendtype._id AS sid FROM record INNER JOIN spendtype ON record.type = spendtype._id WHERE record.time = ?";
        Cursor c = DB.getReadableDatabase().rawQuery(db_query, new String[]{ time });

        c.moveToPosition(recordIndex);
        recordId=c.getInt(c.getColumnIndex("_id"));
        money = c.getInt(c.getColumnIndex("money"));
        spendType=c.getInt(c.getColumnIndex("type"));
        spendName = c.getString(c.getColumnIndex("typename"));
        explanation = c.getString(c.getColumnIndex("explanation"));

        showMoney.setText("NT$"+money);
        showSpentType.setText(spendName);
        showTime.setText(time);

        c.close();
    }

    public void findViews(){
        showMoney = (TextView) findViewById(R.id.show_money);
        showSpentType = (TextView) findViewById(R.id.show_spendtype);
        showTime = (TextView) findViewById(R.id.show_Time);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener(){
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            Intent intent = new Intent(ShowRecordActivity.this, EditActivity.class);
            Bundle bundle = ShowRecordActivity.this.getIntent().getExtras();
            bundle.putString("time", time);
            bundle.putInt("recordId", recordId);
            bundle.putInt("money", money);
            bundle.putInt("spendType", spendType);
            bundle.putString("explanation", explanation);
            Log.d("CHECK", Integer.toString(recordId));

            intent.putExtras(bundle);
            startActivity(intent);

            return true;
        }
    };


}
