package com.example.flicker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements GetFlickrjsonData.OnDataAvailable
        , RecyclerItemClickListener.OnRecyclerClickListener
{
    private static final String TAG = "MainActivity";
private  FlickrRecyclerViewAdapter mFlickrRecyclerViewAdapter;
private SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        GetRawData grd=new GetRawData(this);
//        Log.d(TAG, "onCreate: "+grd.execute("https://www.flickr.com/services/feeds/photos_public.gne?&format=json&nojsoncallback=1"));

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,recyclerView,this));

        mFlickrRecyclerViewAdapter=new FlickrRecyclerViewAdapter(this,new ArrayList<Photo>());
        recyclerView.setAdapter(mFlickrRecyclerViewAdapter);
        Log.d(TAG, "onCreate: ends");

//        TextView searchfield2=(TextView)findViewById(R.id.search_field2);
//        searchfield2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               String s=searchfield.getText().toString();
//
//
//            }
//        });



    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: starts");
        super.onResume();
search=(SearchView)findViewById(R.id.search);
        String s=search.getQuery().toString();
        Log.d(TAG, "onResume: value==="+s);
    //    search(searchView.getQuery().toString());
        GetFlickrjsonData getFlickrjsonData=new GetFlickrjsonData(this,"https://www.flickr.com/services/feeds/photos_public.gne","en-us",true);
        getFlickrjsonData.executeOnSameThread(s);// Searching the text
       // getFlickrjsonData.execute("johwick");
        Log.d(TAG, "onResume ends");


     // Search.setOnClickListener(new View.OnClickListener() {

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
startActivity(new Intent(MainActivity.this,WEB.class));
        WebView    web=(WebView)findViewById(R.id.web);
            web.getSettings().setJavaScriptEnabled(true);

            web.loadUrl("https://www.google.com");
        }
        else if(id==R.id.search12){
            search=(SearchView)findViewById(R.id.search);
            String s=search.getQuery().toString();
            GetFlickrjsonData getFlickrjsonData=new GetFlickrjsonData(this,"https://www.flickr.com/services/feeds/photos_public.gne","en-us",true);
            getFlickrjsonData.executeOnSameThread(s);

            }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public  void onDataAvailable(List<Photo> data, DownloadStatus status){
        Log.d(TAG, "onDataAvailable: starts");
        if(status== DownloadStatus.OK){
            mFlickrRecyclerViewAdapter.loadNewData(data);
        }
        else{
            Log.e(TAG, "OnDataAvailable: failed with status"+status);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, "onItemClick: starts");
        Toast.makeText(MainActivity.this,"Normal tap"+position,Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,PhotoDetailActivity.class);
        intent.putExtra(PHOTO_TRANSFER,mFlickrRecyclerViewAdapter.getPhoto(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Log.d(TAG, "onItemClick: starts");
        Toast.makeText(MainActivity.this,"Long tap"+position,Toast.LENGTH_SHORT).show();



    }
}
