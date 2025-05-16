package com.devkallouch.german;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.adsbase.StartAppSDK;

public class Home extends AppCompatActivity {
    CardView recCard,recCard1,recCard2,recCard3,recCard4;
    RelativeLayout banner;
 //  String data;
   String showAds;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        Firebase.setAndroidContext(this);


        banner = findViewById(R.id.banner);
        // Firebase Reference
        Firebase firebaseAds = new Firebase("https://german-4bc62-default-rtdb.firebaseio.com/ads_enabled");
        firebaseAds.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean adsEnabled = dataSnapshot.getValue(Boolean.class);

                if (adsEnabled != null && adsEnabled) {
                    // إذا كانت الإعلانات مفعّلة، تابع تنفيذ كود البانر
                    Firebase firebase = new Firebase("https://german-4bc62-default-rtdb.firebaseio.com/show_ads");
                    firebase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            showAds = snapshot.getValue(String.class);
                            if (showAds.equals("admob")) {
                                fireAds("https://german-4bc62-default-rtdb.firebaseio.com/admob/banner_ad_unit_id");
                            } else if (showAds.equals("startapp")) {
                                fireStartApp();
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                        }
                    });
                } else {
                    // إذا كانت الإعلانات معطّلة، لا تقم بإضافة أي بانر
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });


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

    public void fireAds(String adsUrl) {
        Firebase firebase = new Firebase(adsUrl);
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                AdView mAdView = new AdView(Home.this);
                mAdView.setAdUnitId(data);
                banner.addView(mAdView);
                mAdView.setAdSize(AdSize.BANNER);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    public void fireStartApp() {
        Firebase firebase = new Firebase("https://german-4bc62-default-rtdb.firebaseio.com/startapp/banner_app_id");
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String appId = dataSnapshot.getValue(String.class);
                StartAppSDK.init(Home.this, appId, false);
                Banner startAppBanner = new Banner(Home.this);
                banner.addView(startAppBanner);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }


}