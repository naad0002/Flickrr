package com.example.flicker;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class GetFlickrjsonData extends AsyncTask<String,Void,List<Photo>> implements GetRawData.OnDownloadComplete {

    private static final String TAG = "GetFlickrjsonData";

    private List<Photo>mPhotoList =null;

    private String mBaseURL;
    private String mLanguage;
    private boolean mMatchAll;

    private final OnDataAvailable mCallBack;

    interface OnDataAvailable{
        void onDataAvailable(List<Photo> data ,DownloadStatus status);
    }

    public GetFlickrjsonData(  OnDataAvailable CallBack ,String BaseURL,String Language, boolean MatchAll ) {

        this.mBaseURL = BaseURL;
        this.mLanguage = Language;
        this.mMatchAll = MatchAll;
        this.mCallBack = CallBack;
    }

    @Override
    protected void onPostExecute(List<Photo> photos) {
        super.onPostExecute(photos);
    }

    @Override
    protected List<Photo> doInBackground(String... params) {
        Log.d(TAG, "doInBackground: starts");
        String destinationUri=createUri(params[0],mLanguage,mMatchAll);
        new GetRawData(this).doInBackground(destinationUri);
        Log.d(TAG, "doInBackground: ends ");
        return mPhotoList;
    }

    void executeOnSameThread(String searchCriteria){
        Log.d(TAG, "executeOnSameThread: starts");
        String destinationUri=createUri(searchCriteria,mLanguage,mMatchAll);

        GetRawData getRawData =new GetRawData(this);
        getRawData.execute(destinationUri);
        Log.d(TAG, "executeOnSameThread ends");

    }

    private String createUri(String searchCriteria,String lang ,boolean mMatchAll){
        Log.d(TAG, "createUri: starts");

//        Uri uri=Uri.parse(mBaseURL);
//        Uri.Builder builder =uri.buildUpon();
//        builder=builder.appendQueryParameter("tags",searchCriteria);
//        builder=builder.appendQueryParameter("tagmode",mMatchAll?"All":"ANY");
//        builder=builder.appendQueryParameter("lang",lang);
//        builder=builder.appendQueryParameter("format","json");
//        builder=builder.appendQueryParameter("nojsoncallback","1");
//uri=builder.build();

//        Uri uri=Uri.parse(mBaseURL)
//                .buildUpon().appendQueryParameter("tags",searchCriteria)
//                .appendQueryParameter("tagmode",mMatchAll?"All":"ANY")
//                .appendQueryParameter("lang",lang)
//                .appendQueryParameter("format","json")
//                .appendQueryParameter("nojsoncallback","1")
//                .build();
//
        return  Uri.parse(mBaseURL).buildUpon()
                .appendQueryParameter("tags",searchCriteria)
                .appendQueryParameter("tagmode",mMatchAll?"All":"ANY")
                .appendQueryParameter("lang",lang)
                .appendQueryParameter("format","json")
                .appendQueryParameter("nojsoncallback","1")
                .build().toString();
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete: Status"+status);
        if(status==DownloadStatus.OK){
            mPhotoList=new ArrayList<>();
            try{
                JSONObject jsonData=new JSONObject(data);
                JSONArray itemArray=jsonData.getJSONArray("items");

                for(int i=0;i<itemArray.length();i++){
                    JSONObject jsonPhoto=itemArray.getJSONObject(i);
                    String title=jsonPhoto.getString("title");
                    String author=jsonPhoto.getString("author");
                    String authorId=jsonPhoto.getString("author_id");
                    String tags=jsonPhoto.getString("tags");

                    JSONObject jsonMedia=jsonPhoto.getJSONObject("media");
                    String photoUrl=jsonMedia.getString("m");
                    String link=photoUrl.replaceFirst("_m.","_b.");

                    Photo photoObject =new Photo(title,author,authorId,link,tags,photoUrl);

                    mPhotoList.add(photoObject);
                    Log.d(TAG, "onDownloadComplete: "+photoObject.toString());


                }
            }catch (JSONException jsone){
                jsone.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error processing json data"+ jsone.getMessage() );
                status=DownloadStatus.FAILED_OR_EMPTY;
            }
        }
        if(mCallBack !=null){
            // now inform the caller that processing is done - possibly returning null if there
            //was an error
            mCallBack.onDataAvailable(mPhotoList,status);
        }
    }
}
