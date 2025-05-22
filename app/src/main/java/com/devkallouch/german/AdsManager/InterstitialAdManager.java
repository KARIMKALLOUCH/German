package com.devkallouch.german.AdsManager;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

public class InterstitialAdManager {

    private static final String TAG = "InterstitialAdManager";

    private final Activity activity;
    private InterstitialAd mInterstitialAd;
    private boolean isInterstitialLoading = false;

    public InterstitialAdManager(Activity activity) {
        this.activity = activity;
        loadInterstitialAd();
    }

    private void loadInterstitialAd() {
        DatabaseReference enabledRef = FirebaseDatabase.getInstance().getReference("ads_enabled");

        enabledRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot enabledSnapshot) {
                Boolean adsEnabled = enabledSnapshot.getValue(Boolean.class);
                if (adsEnabled != null && adsEnabled) {
                    DatabaseReference providerRef = FirebaseDatabase.getInstance().getReference("show_ads");
                    providerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot providerSnapshot) {
                            String provider = providerSnapshot.getValue(String.class);
                            if ("admob".equals(provider)) {
                                loadAdMobInterstitial();
                            } else if ("startapp".equals(provider)) {
                                loadStartAppInterstitial();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e(TAG, "Failed to read ad provider", error.toException());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Failed to read ads_enabled", error.toException());
            }
        });
    }

    private void loadAdMobInterstitial() {
        if (isInterstitialLoading) return;
        isInterstitialLoading = true;

        DatabaseReference admobIdRef = FirebaseDatabase.getInstance().getReference("admob/german_Interstitial");
        admobIdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String adUnitId = snapshot.getValue(String.class);
                if (adUnitId != null && !adUnitId.isEmpty()) {
                    AdRequest adRequest = new AdRequest.Builder().build();
                    InterstitialAd.load(activity, adUnitId, adRequest, new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            mInterstitialAd = interstitialAd;
                            isInterstitialLoading = false;
                            Log.d(TAG, "AdMob Interstitial loaded");
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            mInterstitialAd = null;
                            isInterstitialLoading = false;
                            Log.e(TAG, "AdMob failed to load: " + loadAdError.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Failed to read AdMob interstitial ID", error.toException());
                isInterstitialLoading = false;
            }
        });
    }

    private void loadStartAppInterstitial() {
        DatabaseReference startAppIdRef = FirebaseDatabase.getInstance().getReference("startapp/interstitial_ad_id");

        startAppIdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String appId = String.valueOf(snapshot.getValue());
                if (appId != null && !appId.isEmpty()) {
                    StartAppSDK.init(activity, appId, false);
                    Log.d(TAG, "StartApp Interstitial initialized");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Failed to read StartApp interstitial app ID", error.toException());
            }
        });
    }

    public void showInterstitialAd(Runnable onAdDismissed) {
        if (mInterstitialAd != null) {
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    mInterstitialAd = null;
                    loadInterstitialAd();
                    onAdDismissed.run();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    mInterstitialAd = null;
                    loadInterstitialAd();
                    onAdDismissed.run();
                }
            });

            mInterstitialAd.show(activity);
        } else {
            // جرب عرض StartApp إذا كان مفعل
            StartAppAd.showAd(activity);
            Log.d(TAG, "AdMob Interstitial not ready. Tried showing StartApp.");
            onAdDismissed.run();
        }
    }
}
