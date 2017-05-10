package com.example.tsaujt.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {

    public String date = "20160528";
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //漂浮按鈕
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(
                      new Intent(MainActivity.this, AddActivity.class));
            }
        });

        this.setTitle(date);        //設定標題

        //set DB
        DB = DBHelper.getInstance(this);

        //List顯示
        ListView list = (ListView) findViewById(R.id.list);

        //showItem(list);
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

    public void showItem(ListView list){
        /*String db_query = "SELECT * FROM record INNER JOIN spendtype ON record.type = spendtype._id WHERE record.time = ?";
        Cursor c = DB.getReadableDatabase().rawQuery(db_query, new String[]{ date });

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.list_row,
                c,
                new String[] {"record._id", "record.time", "spedntime.typename", "record.money"},
                new int[] {R.id.item_id, R.id.item_time, R.id.item_type, R.id.item_money},
                0);
        list.setAdapter(adapter);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
