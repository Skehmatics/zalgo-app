package com.skehmatics.zalgo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Licenses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licenses);
    }

    public void tomautyGit(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://github.com/tomauty/ZalgoTextGenerator"));
        startActivity(i);
    }

    public void aasGit(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://github.com/romannurik/AndroidAssetStudio"));
        startActivity(i);
    }
}
