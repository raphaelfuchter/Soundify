package com.rf17.soundify.app.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.rf17.soundifyapp.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void onClickDeveloper(View view) {


    }

    public void onClickEmail(View view) {
        Intent i = new Intent(Intent.ACTION_SENDTO);
        i.setData(Uri.parse("mailto:raphael.fuchter@gmail.com"));
        startActivity(Intent.createChooser(i, "Enviar email"));
    }

    public void onClickGitHub(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://github.com/RF177/Soundify"));
        startActivity(intent);
    }

    public void onClickAndroidArsenal(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://android-arsenal.com/details/1/4595"));
        startActivity(intent);
    }

}
