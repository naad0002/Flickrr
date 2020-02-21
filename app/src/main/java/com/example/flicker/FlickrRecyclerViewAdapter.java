package com.example.flicker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.FlickrImageViewHolder> {
    private static final String TAG = "FlickrRecyclerViewAdapt";
    private List<Photo> mPhotosList;
    private Context mContext;

    public FlickrRecyclerViewAdapter(Context mContext, List<Photo> mPhotosList) {
        this.mContext = mContext;
        this.mPhotosList = mPhotosList;
    }



    @Override
    public void onBindViewHolder(@NonNull FlickrImageViewHolder holder, int position) {
        Photo photoitem=mPhotosList.get(position);
        Log.d(TAG, "onBindViewHolder: title "+photoitem.getmTitle() +"-->"+ position);
        Picasso.with(mContext).load(photoitem.getmImage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbmail);
        Log.d(TAG, "onBindViewHolder: author "+photoitem.getmAuthor() +"-->"+ position);
        holder.title.setText(photoitem.getmTitle());
    }

    @Override
    public FlickrImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      //Called by the layout manager when it needs a new view
        Log.d(TAG, "onCreateViewHolder: new view requested");
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.browse,parent,false);

        return new FlickrImageViewHolder(view);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: called");
        return ((mPhotosList !=null)&& (mPhotosList.size()!=0)? mPhotosList.size():0);
    }

    void loadNewData(List<Photo> newPhotos){
        mPhotosList=newPhotos;
        notifyDataSetChanged();
    }

    public  Photo getPhoto(int position ){
        return ((mPhotosList!=null)&&(mPhotosList.size() !=0)? mPhotosList.get(position):null);
    }
    static class  FlickrImageViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG ="FlickrImageViewHolder";
        ImageView thumbmail=null;
        TextView title=null;
        ImageView photoimage=null;
        TextView texttitle=null;
        TextView textauthor=null;
        TextView texttags=null;

        public FlickrImageViewHolder(View itemView){
            super(itemView);
            Log.d(TAG, "instance initializer: starts");
            Log.d(TAG, "FlickrImageViewHolder: "+itemView);
            this.thumbmail=(ImageView)itemView.findViewById(R.id.imageView2);
            this.title=(TextView)itemView.findViewById(R.id.textView3);

            this.textauthor=(TextView)itemView.findViewById(R.id.photo_author);
            this.texttags=(TextView)itemView.findViewById(R.id.text_tags);
            this.texttitle=(TextView)itemView.findViewById(R.id.text_title);
        }
    }

}
