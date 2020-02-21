package com.example.flicker;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum DownloadStatus {IDLE ,PROCESSING,NOT_INITIALISED,FAILED_OR_EMPTY ,OK }


class GetRawData extends AsyncTask<String,Void,String> {

    private static final String TAG = "GetRawData";
    private DownloadStatus mDownloadStatus;
    private  final  OnDownloadComplete  mCallback;


    interface OnDownloadComplete{
        void onDownloadComplete(String data,DownloadStatus status);
    }

    public GetRawData(OnDownloadComplete callback){
        this.mDownloadStatus=DownloadStatus.IDLE;
    mCallback=callback;
    }


    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: parameter"+s);
        if(mCallback!=null){
            mCallback.onDownloadComplete(s,mDownloadStatus);
        }
        Log.d(TAG, "onPostExecute:  ends");
    }

    @Override
    protected String doInBackground(String... strings) {

    if(strings==null){
        mDownloadStatus=DownloadStatus.NOT_INITIALISED;
    }
        HttpURLConnection connection=null;
        BufferedReader reader=null;
    try{
        mDownloadStatus=DownloadStatus.PROCESSING;
        URL url=new URL(strings[0]);
        connection=(HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int response=connection.getResponseCode();
       reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));

//        InputStream i=connection.getInputStream();
//InputStreamReader inp=new InputStreamReader(i);
//BufferedReader reader1=new BufferedReader(inp);

        StringBuilder result=new StringBuilder();
      // String line=reader.readLine();
     //   while(null!=reader.readLine()){
       for(String line=reader.readLine();line !=null;line=reader.readLine()){
            result.append(line+"\n");
        }
        mDownloadStatus=DownloadStatus.OK;
return result.toString();


    }
    catch ( MalformedURLException e){
        Log.e(TAG, "doInBackground:Invalid URL "+ e.getMessage());
    }catch (IOException i){
        Log.e(TAG, "doInBackground: IOException"+ i.getMessage());
    }catch (SecurityException s){
        Log.e(TAG, "doInBackground: Security Exception"+s.getMessage());
    }finally {
        if(connection!=null){
            connection.disconnect();
        }
        if(reader!=null){
            try{
                reader.close();

            }catch (IOException e){
                Log.e(TAG, "doInBackground: IOException "+e.getMessage() );
            }

        }
    }

mDownloadStatus=DownloadStatus.FAILED_OR_EMPTY;

        return null;
    }
}
