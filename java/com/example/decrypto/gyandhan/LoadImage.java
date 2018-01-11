package com.example.decrypto.gyandhan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import java.io.InputStream;
import android.widget.Toast;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.net.URL;

public class LoadImage extends AppCompatActivity {

    String MY_URL_STRING = "https://thumbs.dreamstime.com/z/hello-world-11059717.jpg";
    String MY_URL_STRING_1 = "https://goo.gl/images/8n4hu2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);

        setTitle("Loaded Image");
        //Displaying Back Button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        final ImageView imageView = (ImageView) findViewById(R.id.img_glide);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);

        /*
        I have done the Image loading task by using two different methods :
            1) Using Glide library
            2) Creating my own custom AsyncTask
        */

        /*Glide.with(this)
                .load("https://thumbs.dreamstime.com/z/hello-world-11059717.jpg")
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(imageView.getContext(),"Image could not be Loaded",Toast.LENGTH_LONG ).show();
                        GotoMainActivity();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);*/
        new DownloadImageTask((ImageView) findViewById(R.id.img_glide), (ProgressBar) findViewById(R.id.progress))
                .execute(MY_URL_STRING);
    }

    public void GotoMainActivity(){
        Intent j = new Intent(this, MainActivity.class);
        j.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(j);
        this.finish();
       // overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            GotoMainActivity();
            return true;
        }
        return false;
    }

    //Creating AsyncTask for downloading image
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        ProgressBar pbar;
        public DownloadImageTask(ImageView bmImage, ProgressBar pbar) {
            this.bmImage = bmImage;
            this.pbar = pbar;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if(result!=null) {
                Toast.makeText(bmImage.getContext(),"URL : "+MY_URL_STRING_1,Toast.LENGTH_LONG).show();
                bmImage.setImageBitmap(result);
                pbar.setVisibility(View.GONE);
            }
            else{
                Toast.makeText(bmImage.getContext(),"Image could not be Loaded",Toast.LENGTH_LONG ).show();
                GotoMainActivity();
            }
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                GotoMainActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
