package com.example.tsaujt.finalproject;

import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class ShowDBActivity extends AppCompatActivity {

    ListView list_record, list_spend;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_db);

        DB = DBHelper.getInstance(this);

        //List顯示
        list_record = (ListView) findViewById(R.id.list_record);
        list_spend = (ListView) findViewById(R.id.list_spend);

        showRecord(list_record);
        showSpend(list_spend);
    }

    @Override
    protected void onResume(){
        super.onResume();

        //List顯示
        //ListView list = (ListView) findViewById(R.id.list);

        //showItem(list);
        //showAll(list);

        //Liner顯示
        if(list_record==null){
            list_record = (ListView) findViewById(R.id.list_record);
            showRecord(list_record);
        }

        if(list_spend==null){
            list_spend = (ListView) findViewById(R.id.list_spend);
            showSpend(list_spend);

        }

    }

    public void showRecord(ListView list){
        Cursor c = DB.getReadableDatabase().query(
                "record", null, null, null, null, null, null);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.list_row,
                c,
                new String[] {"_id", "time", "type", "money"},
                new int[] {R.id.item_id, R.id.item_time, R.id.item_type, R.id.item_money},
                0);

        list.setAdapter(adapter);

    }
    public void showSpend(ListView list){
        Cursor c = DB.getReadableDatabase().query(
                "spendtype", null, null, null, null, null, null);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_expandable_list_item_2,
                c,
                new String[] {"_id", "typename"},
                new int[] {android.R.id.text1, android.R.id.text2},
                0);

        list.setAdapter(adapter);

    }

}
