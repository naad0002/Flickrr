package com.example.flicker;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG="RecyclerItemClickListener";

    interface OnRecyclerClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view ,int position);
    }
    private final OnRecyclerClickListener mListener;
    private final GestureDetectorCompat mgestureDetector;

    public RecyclerItemClickListener(Context context,final RecyclerView recyclerView,OnRecyclerClickListener listener){
        mListener=listener;
        mgestureDetector=new GestureDetectorCompat(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(TAG, "onSingleTapUp: starts");
                View childView=recyclerView.findChildViewUnder(e.getX(),e.getY());
                if(childView!=null && mListener!=null){
                    Log.d(TAG, "onSingleTapUp: calling listener onItemClick");
              mListener.onItemClick(childView,recyclerView.getChildAdapterPosition(childView));
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(TAG, "onLongPress: starts");
                View childView =recyclerView.findChildViewUnder(e.getX(),e.getY());
                if(childView!=null && mListener!=null){
                    Log.d(TAG, "onLongPress: calling listener .onItemLongClick");
                    mListener.onItemClick(childView,recyclerView.getChildAdapterPosition(childView));

                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent: starts");

       if((mgestureDetector!=null)){
           boolean result=mgestureDetector.onTouchEvent(e);
           Log.d(TAG, "onInterceptTouchEvent(): +result");
           return result;
       }else {
           Log.d(TAG, "onInterceptTouchEvent: returned  false");
       return false;
       }

    }
}
