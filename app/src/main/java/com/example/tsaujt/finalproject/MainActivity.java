package com.example.tsaujt.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public String time = "20160528";
    DBHelper DB;
    LinearLayout liner;
    //private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);


        //漂浮按鈕
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(
                      new Intent(MainActivity.this, AddActivity.class));
            }
        });

        this.setTitle(time);        //設定標題

        //set DB
        DB = DBHelper.getInstance(this);



        //Liner顯示
        liner = (LinearLayout) findViewById(R.id.LinerShow);


        //showItem(liner);

    }

    @Override
    protected void onResume(){
        super.onResume();

        //Liner顯示
        if(liner.getChildCount()!=0) {
            liner.removeAllViews();
        }

        //liner = (LinearLayout) findViewById(R.id.LinerShow);
        showItem(liner);


    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("ADD", Integer.toString(resultCode));
        //Log.d("ADD", Integer.toString(RESULT_OK));
        Log.d("ADD", Integer.toString(requestCode));

        if(resultCode == 1){ //確認是否從 popuptime 回傳
            if(requestCode == 1){ //確認所要執行的動作
                time=data.getExtras().getString("time");
                Log.d("ADD", time);
                this.setTitle(time);        //設定標題
            }
        }
    }




    /*public void showItem(ListView list){
        String db_query = "SELECT * FROM record INNER JOIN spendtype ON record.type = spendtype._id WHERE record.time = ?";
        Cursor c = DB.getReadableDatabase().rawQuery(db_query, new String[]{ time });

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.list_row,
                c,
                new String[] {"record._id", "record.time", "spendtime.typename", "record.money"},
                new int[] {R.id.item_id, R.id.item_time, R.id.item_type, R.id.item_money},
                0);
        list.setAdapter(adapter);


        //c.close();
    }*/

    public void showItem(LinearLayout liner){
        String db_query = "SELECT * FROM record INNER JOIN spendtype ON record.type = spendtype._id WHERE record.time = ?";
        Cursor c = DB.getReadableDatabase().rawQuery(db_query, new String[]{ time });
        String itemString="";
        TextView itemTV = new TextView(this);
        c.moveToFirst();
        while(!c.isAfterLast()){
            if(c.getColumnIndex("_id")!=0){
                itemString = c.getString(c.getColumnIndex("typename"))+" "+c.getString(c.getColumnIndex("money"));
                itemTV.setText(itemString);
                liner.addView(itemTV);
            }
            c.moveToNext();
        }


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

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener(){
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            Intent intent = new Intent(MainActivity.this, PopUpTimeChoose.class);
            startActivityForResult(intent, 1);

            return true;
        }
    };

    public void showDB(View view){
        startActivity(
                new Intent(MainActivity.this, ShowDBActivity.class));
    }
}
