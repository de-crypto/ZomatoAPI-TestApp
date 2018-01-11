package com.example.decrypto.gyandhan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadImage(View view){
        Intent i1 = new Intent(this,LoadImage.class);
        //i1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i1);
        this.finish();
        //overridePendingTransition(R.anim.slide_out,R.anim.slide_in);
    }

    public void loadJSON(View view){
        Intent i2 = new Intent(this,Loadjson.class);
        startActivity(i2);
        this.finish();
    }
}
