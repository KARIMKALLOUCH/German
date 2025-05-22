package com.devkallouch.german.AdsManager;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.adsbase.StartAppSDK;


public class BannerAdsManager {

    private Context context;
    private ViewGroup bannerContainer;

    public BannerAdsManager(Context context, ViewGroup bannerContainer) {
        this.context = context;
        this.bannerContainer = bannerContainer;
    }

    public void loadBannerAds() {
        // جلب حالة تفعيل الإعلانات من المسار النسبي فقط
        DatabaseReference firebaseAds = FirebaseDatabase.getInstance().getReference("ads_enabled");

        firebaseAds.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean adsEnabled = dataSnapshot.getValue(Boolean.class);

                if (adsEnabled != null && adsEnabled) {
                    DatabaseReference showAdsRef = FirebaseDatabase.getInstance().getReference("show_ads");

                    showAdsRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            String showAds = snapshot.getValue(String.class);
                            if ("admob".equals(showAds)) {
                                fireAds();
                            } else if ("startapp".equals(showAds)) {
                                fireStartApp();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e("BannerAdsManager", "Error fetching show_ads", databaseError.toException());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("BannerAdsManager", "Error fetching ads_enabled", databaseError.toException());
            }
        });
    }

    private void fireAds() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("admob/banner_ad_unit_id");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String adUnitId = dataSnapshot.getValue(String.class);
                if (adUnitId != null && !adUnitId.isEmpty()) {
                    AdView mAdView = new AdView(context);
                    mAdView.setAdUnitId(adUnitId);
                    mAdView.setAdSize(AdSize.BANNER);
                    bannerContainer.removeAllViews();
                    bannerContainer.addView(mAdView);
                    AdRequest adRequest = new AdRequest.Builder().build();
                    mAdView.loadAd(adRequest);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("BannerAdsManager", "Error loading AdMob banner ad unit ID", databaseError.toException());
            }
        });
    }

    private void fireStartApp() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("startapp/banner_app_id");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String appId = String.valueOf(dataSnapshot.getValue());
                if (appId != null && !appId.isEmpty()) {
                    StartAppSDK.init(context, appId, false);
                    Banner startAppBanner = new Banner(context);
                    bannerContainer.removeAllViews();
                    bannerContainer.addView(startAppBanner);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("BannerAdsManager", "Error loading StartApp banner app ID", databaseError.toException());
            }
        });
    }
}
