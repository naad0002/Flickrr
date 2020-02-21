package com.example.flicker;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotoDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
       activateToolbar(true);

        Intent intent =getIntent();
        Photo photo=(Photo) intent.getSerializableExtra(PHOTO_TRANSFER);
        if(photo!=null){
            TextView photoTitle=(TextView)findViewById(R.id.text_title);
            photoTitle.setText("Title: "+photo.getmTitle());

            TextView photoTags=(TextView)findViewById(R.id.text_tags);
            photoTags.setText("Tags: "+photo.getmTags());

            TextView photoAuthor=(TextView)findViewById(R.id.photo_author);
            photoAuthor.setText("Author: "+photo.getmAuthor());

            ImageView photoImage=(ImageView)findViewById(R.id.photo_image);
            Picasso.with(this).load(photo.getmLink())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(photoImage);
        }
    }

}
