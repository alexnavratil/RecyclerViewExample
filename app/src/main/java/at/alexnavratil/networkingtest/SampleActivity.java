package at.alexnavratil.networkingtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class SampleActivity extends Activity {
    public static Context context;
    public static Activity activity;
    private RecyclerView mItemView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView mDrawerView;
    private RecyclerView.Adapter mDrawerAdapter;
    private RecyclerView.LayoutManager mDrawerLayoutManager;

    private ListView mDrawerListView;
    private ArrayAdapter<String> mDrawerArrayAdapter;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        SampleActivity.context = this;
        SampleActivity.activity = this;

        mDrawerView = (RecyclerView) findViewById(R.id.drawer_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerView = (RecyclerView) findViewById(R.id.drawer_view);
        mDrawerLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ListsAdapter();
        mDrawerView.setLayoutManager(mDrawerLayoutManager);
        mDrawerView.setAdapter(mAdapter);

        this.getActionBar().setDisplayHomeAsUpEnabled(true);
        this.getActionBar().setHomeButtonEnabled(true);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        mSwipeRefreshLayout.setColorSchemeColors(Color.GRAY, Color.parseColor("#33b5e5"), Color.parseColor("#99cc00"), Color.parseColor("#ffbb33"));

        mItemView = (RecyclerView) findViewById(R.id.itemView);

        mLayoutManager = new LinearLayoutManager(this);
        mItemView.setLayoutManager(mLayoutManager);

        loadData();
    }

    private void loadData(){
        mSwipeRefreshLayout.setRefreshing(true);
        Ion.with(SampleActivity.context, "http://alexnavratil.ddns.net/getItems.php")
                //.setMultipartFile("file", new File("/sdcard/test.zip"))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject jsonObject) {
                        if (e == null) {
                            int status = jsonObject.get("status").getAsInt();
                            if (status == 200) {
                                List<Item> dataList = new ArrayList<Item>();
                                JsonArray item = jsonObject.get("item").getAsJsonArray();
                                Iterator iterator = item.iterator();
                                while (iterator.hasNext()) {
                                    JsonObject current = (JsonObject) iterator.next();
                                    dataList.add(new Item(current.get("id").getAsInt(), current.get("num_files").getAsInt(), current.get("checked").getAsBoolean(), current.get("text").getAsString(), current.get("has_comment").getAsBoolean(), current.get("has_reminder").getAsBoolean()));
                                }
                                Log.d("SampleActivity", dataList.toString());
                                mAdapter = new ItemAdapter(dataList);
                                mItemView.setAdapter(mAdapter);
                            } else {
                                Toast.makeText(SampleActivity.context.getApplicationContext(), "Ein API-Fehler ist aufgetreten! Code: " + status, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            //Exception aufgetreten!
                            AlertDialog.Builder dialog = new AlertDialog.Builder(SampleActivity.context);
                            dialog.setTitle("Error");
                            dialog.setMessage(e.toString());
                            dialog.show();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sample, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        /*int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }
}
