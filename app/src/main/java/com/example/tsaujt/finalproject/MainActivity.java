package com.example.tsaujt.finalproject;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public String time = "20160528";
    DBHelper DB;
    LinearLayout liner;
    //private Toolbar toolbar;
    private SharedPreferences settings;
    private static final String data = "DATA";
    TextView userText;
    MenuItem loginItem;
    MenuItem logoutItem;
    NavigationView navigationView;

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

        //滑動
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        findViews();
    }
    public void findViews(){
        View headerView = navigationView.getHeaderView(0);
        Menu menuNav = navigationView.getMenu();
        userText = (TextView) headerView.findViewById(R.id.text_user);
        loginItem = menuNav.findItem(R.id.nav_login);
        logoutItem = menuNav.findItem(R.id.nav_logout);
    }

    @Override
    protected void onResume(){
        super.onResume();

        //Liner顯示
        if(liner.getChildCount()!=0) {
            liner.removeAllViews();
        }

        showItem(liner);

        //Nav調整
        String user = readUser();

        if(user!=null){
            userText.setText(user);
            loginItem.setVisible(false);
            logoutItem.setVisible(true);
        }else{
            userText.setText("請登入");
            loginItem.setVisible(true);
            logoutItem.setVisible(false);
        }



    }

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

    public void showItem(LinearLayout liner){
        String db_query = "SELECT record.*, spendtype.typename, spendtype._id AS sid FROM record INNER JOIN spendtype ON record.type = spendtype._id WHERE record.time = ?";
        Cursor c = DB.getReadableDatabase().rawQuery(db_query, new String[]{ time });
        String itemString="";

        c.moveToFirst();
        while(!c.isAfterLast()){

            TextView itemTV = new TextView(this);
            itemString =c.getString(c.getColumnIndex("_id"))+" "+ c.getString(c.getColumnIndex("typename"))+" "+c.getString(c.getColumnIndex("money"));
            itemTV.setText(itemString);
            itemTV.setId(Utils.generateViewId());
            itemTV.setClickable(true);
            itemTV.setOnClickListener(editEvent);
            LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(200, 200);
            itemTV.setLayoutParams(layoutParams);
            itemTV.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            liner.addView(itemTV);

            c.moveToNext();
        }

        c.close();


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

    private View.OnClickListener editEvent = new View.OnClickListener () {

        @Override
        public void onClick(View v) {
            Intent editIntent = new Intent(MainActivity.this, ShowRecordActivity.class);
            Bundle recordBundle = new Bundle();
            int recordIndex = liner.indexOfChild(v);

            recordBundle.putString("time", time);
            recordBundle.putInt("recordIndex", recordIndex);

            editIntent.putExtras(recordBundle);
            startActivity(editIntent);
        }
    };

    public void showDB(View view){
        startActivity(
                new Intent(MainActivity.this, ShowDBActivity.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            startActivity(
                    new Intent(MainActivity.this, LoginActivity.class));
        }
        else if (id == R.id.nav_logout){
            userLogout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String readUser(){
        settings = getSharedPreferences(data,0);
        String username = settings.getString("user",null);

        return username;
    }

    public void userLogout(){
        settings = getSharedPreferences(data,0);
        settings.edit().remove("user").apply();
        userText.setText("請登入");
        loginItem.setVisible(true);
        logoutItem.setVisible(false);

    }
}
