package com.example.nino.toptendownloader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private String mFileContents;
    private RecyclerView rvItems;
    private RecyclerView.LayoutManager rvLayoutmanager;
    private RecyclerView.Adapter rvAdapter;
    private Button btnParse;
    public FeedContainer feed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rvItems = (RecyclerView) findViewById(R.id.itemListView);
        rvLayoutmanager = new LinearLayoutManager(this);
        rvItems.setLayoutManager(rvLayoutmanager);

        rvItems.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).margin(16,16).build());

        btnParse = (Button) findViewById(R.id.btnParse);
        rvItems.setHasFixedSize(true);

        DownloadData dd = new DownloadData();
        dd.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=10/json");

        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                feed = gson.fromJson(mFileContents, FeedContainer.class);
                rvAdapter = new FeedContainer.EntryAdapter(feed.getFeed().getEntries());
                rvItems.setAdapter(rvAdapter);
            }
        });








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

    private class DownloadData extends AsyncTask<String, Void, String> {

        private final OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {
            mFileContents = downloadXMLFile(params[0]);
            if (mFileContents == null) {
                Log.d("DownloadData", "Error Downloading");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //rvAdapter = new
//            for (Entry entry : feed.feed.entries) { //(Iterator<Entry> i = feed.entries.iterator():  i.hasN){
//                Log.d("DownloadData", "entry is " + entry.toString());
//
//            }


        }

        private String downloadXMLFile(String urlPath) {
            try {

                Request.Builder rb = new Request.Builder().url(urlPath);
                Request req = rb.build();
                Response resp = client.newCall(req).execute();
                Log.d("DownloadData", "Response Code: " + resp.code());
                String ret = resp.body().string();
                resp.close();

                return ret;

            } catch (IOException e) {
                Log.d("DownloadData", "IO Exception reading data: " + e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

    }




}
