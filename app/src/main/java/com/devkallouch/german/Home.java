package com.devkallouch.german;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.devkallouch.german.AdsManager.BannerAdsManager;


public class Home extends AppCompatActivity {
    CardView recCard,recCard1,recCard2,recCard3,recCard4;
 //  String data;


    private ViewGroup banner;
    private BannerAdsManager bannerAdsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);


        banner = findViewById(R.id.banner);

        bannerAdsManager = new BannerAdsManager(this,banner);
        bannerAdsManager.loadBannerAds();


        recCard = findViewById(R.id.recCard);
        recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,PageA1.class);
                startActivity(intent);
            }
        });
        recCard1 = findViewById(R.id.recCard1);
        recCard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,PageA2.class);
                startActivity(intent);
            }
        });
        recCard2 = findViewById(R.id.recCard2);
        recCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(Home.this,PageB1.class);
                startActivity(intent);
            }
        });
        recCard3 = findViewById(R.id.recCard3);
        recCard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new  Intent(Home.this, PageB2.class);
                startActivity(intent);

            }
        });
        recCard4 = findViewById(R.id.recCard4);
        recCard4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this, "Bald verfügbar – Bleib gespannt!", Toast.LENGTH_LONG).show();
            }
        });


    }




}