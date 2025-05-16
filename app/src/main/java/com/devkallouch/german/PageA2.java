    package com.devkallouch.german;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.app.AppCompatDelegate;
    import androidx.cardview.widget.CardView;

    import android.content.Context;
    import android.content.Intent;
    import android.net.ConnectivityManager;
    import android.net.NetworkInfo;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.ImageView;
    import android.widget.RelativeLayout;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.firebase.client.DataSnapshot;
    import com.firebase.client.Firebase;
    import com.firebase.client.FirebaseError;
    import com.firebase.client.ValueEventListener;
    import com.google.android.gms.ads.AdError;
    import com.google.android.gms.ads.AdRequest;
    import com.google.android.gms.ads.AdSize;
    import com.google.android.gms.ads.AdView;
    import com.google.android.gms.ads.FullScreenContentCallback;
    import com.google.android.gms.ads.LoadAdError;
    import com.google.android.gms.ads.OnUserEarnedRewardListener;
    import com.google.android.gms.ads.rewarded.RewardItem;
    import com.google.android.gms.ads.rewarded.RewardedAd;
    import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
    import com.startapp.sdk.ads.banner.Banner;
    import com.startapp.sdk.adsbase.Ad;
    import com.startapp.sdk.adsbase.StartAppAd;
    import com.startapp.sdk.adsbase.StartAppSDK;
    import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
    import com.startapp.sdk.adsbase.adlisteners.VideoListener;


    public class PageA2 extends AppCompatActivity {
        CardView cardviewA2_1,cardviewA2_2,cardviewA2_3,cardviewA2_4,cardviewA2_5,cardviewA2_6,cardviewA2_7,cardviewA2_8,cardviewA2_9,cardviewA2_10;
        ImageView statusIcon, statusIcon1,statusIcon2,statusIcon3,statusIcon4, statusIcon5,statusIcon6,statusIcon7,statusIcon8,statusIcon9; // تعريف على مستوى الصف
        DatabaseHelperA2 dbHelperA2; // قاعدة البيانات

        ImageView[] statusIconsA2;
        int totalCards = 10; // إجمالي عدد الكروت
        ImageView back;
        TextView textname;
        private RewardedAd mRewardedAd2;

        RelativeLayout banner2;
        String data2;
        String showAds2;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            loadAdMobRewardedAd2(); // تأكد من تحميل الإعلان عند بدء الصفحة

            setContentView(R.layout.activity_page_a2);

            banner2 = findViewById(R.id.banner2);
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
                                showAds2 = snapshot.getValue(String.class);
                                if (showAds2.equals("admob")) {
                                    fireAds2("https://german-4bc62-default-rtdb.firebaseio.com/admob/banner_ad_unit_id");
                                } else if (showAds2.equals("startapp")) {
                                    fireStartApp2();
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

            cardviewA2_1 = findViewById(R.id.themaA2_1);
            cardviewA2_2 = findViewById(R.id.themaA2_2);
            cardviewA2_3 = findViewById(R.id.themaA2_3);
            cardviewA2_4 = findViewById(R.id.themaA2_4);
            cardviewA2_5 = findViewById(R.id.themaA2_5);
            cardviewA2_6 = findViewById(R.id.themaA2_6);
            cardviewA2_7 = findViewById(R.id.themaA2_7);
            cardviewA2_8 = findViewById(R.id.themaA2_8);
            cardviewA2_9 = findViewById(R.id.themaA2_9);
            cardviewA2_10 = findViewById(R.id.themaA2_10);

            // ربط الأيقونات بمصفوفة
            statusIconsA2 = new ImageView[totalCards];
            statusIconsA2[0] = findViewById(R.id.statusIconA2);
            statusIconsA2[1] = findViewById(R.id.statusIconA2_1);
            statusIconsA2[2] = findViewById(R.id.statusIconA2_2);
            statusIconsA2[3] = findViewById(R.id.statusIconA2_3);
            statusIconsA2[4] = findViewById(R.id.statusIconA2_4);
            statusIconsA2[5] = findViewById(R.id.statusIconA2_5);
            statusIconsA2[6] = findViewById(R.id.statusIconA2_6);
            statusIconsA2[7] = findViewById(R.id.statusIconA2_7);
            statusIconsA2[8] = findViewById(R.id.statusIconA2_8);
            statusIconsA2[9] = findViewById(R.id.statusIconA2_9);

            textname = findViewById(R.id.toolbar2_text);
            textname.setText("Niveau A2");

            back = findViewById(R.id.left3_icon);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PageA2.this,Home.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
            });

            // إنشاء كائن قاعدة البيانات
            dbHelperA2 = new DatabaseHelperA2(this);

            // تحديث الحالة عند التشغيل (اختياري)
            for (int i = 0; i < totalCards; i++) {
                int pageId = 1; // تعيين معرف الصفحة الحالي

                if (dbHelperA2.getCardStatus(i + 1)) {
                    statusIconsA2[i].setImageResource(R.drawable.baseline_done_all_24);
                }
            }




            cardviewA2_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isConnected()) {
                        // في حالة عدم وجود إنترنت، افتح النشاط مباشرةً
                        Intent intent = new Intent(PageA2.this, DetailActvity.class);
                        intent.putExtra("currentPage", "PageA2");
                        intent.putExtra("title", "Ein Wochenende auf dem Land");
                        intent.putExtra("image", R.drawable.ein_wochenende_auf_dem_land); // تمرير الصورة
                        intent.putExtra("audio", R.raw.ein_wochenende_auf_dem_land);  // تمرير ملف الصوت
                        intent.putExtra("description", getString(R.string.Ein_Wochenende_auf_dem_Land));
                        intent.putExtra("share_text",
                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                        "🔹 *Niveau:* A2\n" +
                                        "📌 *Titel:* Ein Wochenende auf dem Land\"\n\n" +
                                        "📝 *Text:*\n" + getString(R.string.Ein_Wochenende_auf_dem_Land) + "\n\n" +
                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                        startActivityForResult(intent, 1);                          return;
                    }
                    Firebase firebaseAds = new Firebase("https://german-4bc62-default-rtdb.firebaseio.com/ads_enabled");
                    firebaseAds.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Boolean adsEnabled = dataSnapshot.getValue(Boolean.class);

                            if (adsEnabled != null && adsEnabled) {
                                // في حالة عدم وجود إنترنت، افتح النشاط مباشرةً
                                Firebase firebase = new Firebase("https://german-4bc62-default-rtdb.firebaseio.com/show_ads");
                                firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String adProvider = dataSnapshot.getValue(String.class);

                                        if ("admob".equals(adProvider)) {
                                            if (mRewardedAd2 != null) {
                                                mRewardedAd2.show(PageA2.this, new OnUserEarnedRewardListener() {
                                                    @Override
                                                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                        loadAdMobRewardedAd2(); // إعادة تحميل إعلان AdMob
                                                    }
                                                });

                                                mRewardedAd2.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                    @Override
                                                    public void onAdDismissedFullScreenContent() {
                                                        mRewardedAd2 = null;
                                                        loadAdMobRewardedAd2();
                                                        Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                                        intent.putExtra("currentPage", "PageA2");
                                                        intent.putExtra("title", "Ein Wochenende auf dem Land");
                                                        intent.putExtra("image", R.drawable.ein_wochenende_auf_dem_land); // تمرير الصورة
                                                        intent.putExtra("audio", R.raw.ein_wochenende_auf_dem_land);  // تمرير ملف الصوت
                                                        intent.putExtra("description", getString(R.string.Ein_Wochenende_auf_dem_Land));
                                                        intent.putExtra("share_text",
                                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                        "🔹 *Niveau:* A2\n" +
                                                                        "📌 *Titel:* Ein Wochenende auf dem Land\"\n\n" +
                                                                        "📝 *Text:*\n" + getString(R.string.Ein_Wochenende_auf_dem_Land) + "\n\n" +
                                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                        startActivityForResult(intent, 1);
                                                    }

                                                    @Override
                                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                        mRewardedAd2 = null;
                                                        Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                                        intent.putExtra("currentPage", "PageA2");
                                                        intent.putExtra("title", "Ein Wochenende auf dem Land");
                                                        intent.putExtra("image", R.drawable.ein_wochenende_auf_dem_land); // تمرير الصورة
                                                        intent.putExtra("audio", R.raw.ein_wochenende_auf_dem_land);  // تمرير ملف الصوت
                                                        intent.putExtra("description", getString(R.string.Ein_Wochenende_auf_dem_Land));
                                                        intent.putExtra("share_text",
                                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                        "🔹 *Niveau:* A2\n" +
                                                                        "📌 *Titel:* Ein Wochenende auf dem Land\"\n\n" +
                                                                        "📝 *Text:*\n" + getString(R.string.Ein_Wochenende_auf_dem_Land) + "\n\n" +
                                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                        startActivityForResult(intent, 1);                                  }
                                                });

                                            } else {
                                                Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                                intent.putExtra("currentPage", "PageA2");
                                                intent.putExtra("title", "Ein Wochenende auf dem Land");
                                                intent.putExtra("image", R.drawable.ein_wochenende_auf_dem_land); // تمرير الصورة
                                                intent.putExtra("audio", R.raw.ein_wochenende_auf_dem_land);  // تمرير ملف الصوت
                                                intent.putExtra("description", getString(R.string.Ein_Wochenende_auf_dem_Land));
                                                intent.putExtra("share_text",
                                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                "🔹 *Niveau:* A2\n" +
                                                                "📌 *Titel:* Ein Wochenende auf dem Land\"\n\n" +
                                                                "📝 *Text:*\n" + getString(R.string.Ein_Wochenende_auf_dem_Land) + "\n\n" +
                                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                startActivityForResult(intent, 1);
                                            }

                                        } else if ("startapp".equals(adProvider)) {
                                            if (isConnected()) {  // التحقق من الاتصال بالإنترنت
                                                Firebase startAppFirebase = new Firebase("https://german-4bc62-default-rtdb.firebaseio.com/startapp/rewarded_app_id");
                                                startAppFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        String startAppAppId = dataSnapshot.getValue(String.class);
                                                        if (startAppAppId != null && !startAppAppId.isEmpty()) {
                                                            StartAppSDK.init(PageA2.this, startAppAppId, false);
                                                            StartAppAd startAppAd = new StartAppAd(PageA2.this);

                                                            startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                                                                @Override
                                                                public void onReceiveAd(Ad ad) {
                                                                    startAppAd.showAd();
                                                                }

                                                                @Override
                                                                public void onFailedToReceiveAd(Ad ad) {
                                                                    Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                                                    intent.putExtra("currentPage", "PageA2");
                                                                    intent.putExtra("title", "Ein Wochenende auf dem Land");
                                                                    intent.putExtra("image", R.drawable.ein_wochenende_auf_dem_land); // تمرير الصورة
                                                                    intent.putExtra("audio", R.raw.ein_wochenende_auf_dem_land);  // تمرير ملف الصوت
                                                                    intent.putExtra("description", getString(R.string.Ein_Wochenende_auf_dem_Land));
                                                                    intent.putExtra("share_text",
                                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                                    "🔹 *Niveau:* A2\n" +
                                                                                    "📌 *Titel:* Ein Wochenende auf dem Land\"\n\n" +
                                                                                    "📝 *Text:*\n" + getString(R.string.Ein_Wochenende_auf_dem_Land) + "\n\n" +
                                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                                    startActivityForResult(intent, 1);                                              }
                                                            });

                                                            startAppAd.setVideoListener(new VideoListener() {
                                                                @Override
                                                                public void onVideoCompleted() {
                                                                    Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                                                    intent.putExtra("currentPage", "PageA2");
                                                                    intent.putExtra("title", "Ein Wochenende auf dem Land");
                                                                    intent.putExtra("image", R.drawable.ein_wochenende_auf_dem_land); // تمرير الصورة
                                                                    intent.putExtra("audio", R.raw.ein_wochenende_auf_dem_land);  // تمرير ملف الصوت
                                                                    intent.putExtra("description", getString(R.string.Ein_Wochenende_auf_dem_Land));
                                                                    intent.putExtra("share_text",
                                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                                    "🔹 *Niveau:* A2\n" +
                                                                                    "📌 *Titel:* Ein Wochenende auf dem Land\"\n\n" +
                                                                                    "📝 *Text:*\n" + getString(R.string.Ein_Wochenende_auf_dem_Land) + "\n\n" +
                                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                                    startActivityForResult(intent, 1);                                                }
                                                            });

                                                        } else {
                                                            Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                                            intent.putExtra("currentPage", "PageA2");
                                                            intent.putExtra("title", "Ein Wochenende auf dem Land");
                                                            intent.putExtra("image", R.drawable.ein_wochenende_auf_dem_land); // تمرير الصورة
                                                            intent.putExtra("audio", R.raw.ein_wochenende_auf_dem_land);  // تمرير ملف الصوت
                                                            intent.putExtra("description", getString(R.string.Ein_Wochenende_auf_dem_Land));
                                                            intent.putExtra("share_text",
                                                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                            "🔹 *Niveau:* A2\n" +
                                                                            "📌 *Titel:* Ein Wochenende auf dem Land\"\n\n" +
                                                                            "📝 *Text:*\n" + getString(R.string.Ein_Wochenende_auf_dem_Land) + "\n\n" +
                                                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                            startActivityForResult(intent, 1);                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(FirebaseError firebaseError) {
                                                        Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                                        intent.putExtra("currentPage", "PageA2");
                                                        intent.putExtra("title", "Ein Wochenende auf dem Land");
                                                        intent.putExtra("image", R.drawable.ein_wochenende_auf_dem_land); // تمرير الصورة
                                                        intent.putExtra("audio", R.raw.ein_wochenende_auf_dem_land);  // تمرير ملف الصوت
                                                        intent.putExtra("description", getString(R.string.Ein_Wochenende_auf_dem_Land));
                                                        intent.putExtra("share_text",
                                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                        "🔹 *Niveau:* A2\n" +
                                                                        "📌 *Titel:* Ein Wochenende auf dem Land\"\n\n" +
                                                                        "📝 *Text:*\n" + getString(R.string.Ein_Wochenende_auf_dem_Land) + "\n\n" +
                                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                        startActivityForResult(intent, 1);                                    }
                                                });
                                            } else {
                                                // إذا لم يكن هناك اتصال بالإنترنت
                                                Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                                intent.putExtra("currentPage", "PageA2");
                                                intent.putExtra("title", "Ein Wochenende auf dem Land");
                                                intent.putExtra("image", R.drawable.ein_wochenende_auf_dem_land); // تمرير الصورة
                                                intent.putExtra("audio", R.raw.ein_wochenende_auf_dem_land);  // تمرير ملف الصوت
                                                intent.putExtra("description", getString(R.string.Ein_Wochenende_auf_dem_Land));
                                                intent.putExtra("share_text",
                                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                "🔹 *Niveau:* A2\n" +
                                                                "📌 *Titel:* Ein Wochenende auf dem Land\"\n\n" +
                                                                "📝 *Text:*\n" + getString(R.string.Ein_Wochenende_auf_dem_Land) + "\n\n" +
                                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                startActivityForResult(intent, 1);                           }
                                        } else {
                                            // فتح النشاط في حال لم يكن هناك إعلان
                                            Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                            intent.putExtra("currentPage", "PageA2");
                                            intent.putExtra("title", "Ein Wochenende auf dem Land");
                                            intent.putExtra("image", R.drawable.ein_wochenende_auf_dem_land); // تمرير الصورة
                                            intent.putExtra("audio", R.raw.ein_wochenende_auf_dem_land);  // تمرير ملف الصوت
                                            intent.putExtra("description", getString(R.string.Ein_Wochenende_auf_dem_Land));
                                            intent.putExtra("share_text",
                                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                            "🔹 *Niveau:* A2\n" +
                                                            "📌 *Titel:* Ein Wochenende auf dem Land\"\n\n" +
                                                            "📝 *Text:*\n" + getString(R.string.Ein_Wochenende_auf_dem_Land) + "\n\n" +
                                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                            startActivityForResult(intent, 1);                        }
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {
                                        Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                        intent.putExtra("currentPage", "PageA2");
                                        intent.putExtra("title", "Ein Wochenende auf dem Land");
                                        intent.putExtra("image", R.drawable.ein_wochenende_auf_dem_land); // تمرير الصورة
                                        intent.putExtra("audio", R.raw.ein_wochenende_auf_dem_land);  // تمرير ملف الصوت
                                        intent.putExtra("description", getString(R.string.Ein_Wochenende_auf_dem_Land));
                                        intent.putExtra("share_text",
                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                        "🔹 *Niveau:* A2\n" +
                                                        "📌 *Titel:* Ein Wochenende auf dem Land\"\n\n" +
                                                        "📝 *Text:*\n" + getString(R.string.Ein_Wochenende_auf_dem_Land) + "\n\n" +
                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                        startActivityForResult(intent, 1);                    }
                                });
                            } else {
                                // إذا كانت الإعلانات غير مفعلة، افتح النشاط بشكل طبيعي بدون إعلانات
                                Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                intent.putExtra("currentPage", "PageA2");
                                intent.putExtra("title", "Ein Wochenende auf dem Land");
                                intent.putExtra("image", R.drawable.ein_wochenende_auf_dem_land); // تمرير الصورة
                                intent.putExtra("audio", R.raw.ein_wochenende_auf_dem_land);  // تمرير ملف الصوت
                                intent.putExtra("description", getString(R.string.Ein_Wochenende_auf_dem_Land));
                                intent.putExtra("share_text",
                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                "🔹 *Niveau:* A2\n" +
                                                "📌 *Titel:* Ein Wochenende auf dem Land\"\n\n" +
                                                "📝 *Text:*\n" + getString(R.string.Ein_Wochenende_auf_dem_Land) + "\n\n" +
                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                startActivityForResult(intent, 1);
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            // افتح النشاط إذا حدث خطأ
                            Intent intent = new Intent(PageA2.this, DetailActvity.class);
                            intent.putExtra("currentPage", "PageA2");
                            intent.putExtra("title", "Ein Wochenende auf dem Land");
                            intent.putExtra("image", R.drawable.ein_wochenende_auf_dem_land); // تمرير الصورة
                            intent.putExtra("audio", R.raw.ein_wochenende_auf_dem_land);  // تمرير ملف الصوت
                            intent.putExtra("description", getString(R.string.Ein_Wochenende_auf_dem_Land));
                            intent.putExtra("share_text",
                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                            "🔹 *Niveau:* A2\n" +
                                            "📌 *Titel:* Ein Wochenende auf dem Land\"\n\n" +
                                            "📝 *Text:*\n" + getString(R.string.Ein_Wochenende_auf_dem_Land) + "\n\n" +
                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                            startActivityForResult(intent, 1);


                        }
                    });
                }
            });

            cardviewA2_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(PageA2.this, DetailActvity.class);
                        intent.putExtra("currentPage", "PageA2");
                        intent.putExtra("title", "Meine Hobbys");
                        intent.putExtra("image", R.drawable.meine_hobbys); // تمرير الصورة
                        intent.putExtra("audio", R.raw.meine_hobbys);  // تمرير ملف الصوت
                        intent.putExtra("description", getString(R.string.Meine_Hobbys));
                        intent.putExtra("share_text",
                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                        "🔹 *Niveau:* A2\n" +
                                        "📌 *Titel:* Meine Hobbys\n\n" +
                                        "📝 *Text:*\n" + getString(R.string.Meine_Hobbys) + "\n\n" +
                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                        startActivityForResult(intent, 2);
                }
            });

            cardviewA2_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PageA2.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageA2");

                    intent.putExtra("title", "Urlaub am Meer");
                    intent.putExtra("image", R.drawable.urlaub_am_meer); // تمرير الصورة
                    intent.putExtra("audio", R.raw.urlaub_am_meer);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Urlaub_am_Meer));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* A2\n" +
                                    "📌 *Titel:* Urlaub am Meer\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Urlaub_am_Meer) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 3);
                }
            });

            cardviewA2_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(PageA2.this, DetailActvity.class);
                        intent.putExtra("currentPage", "PageA2");

                        intent.putExtra("title", "Ein Besuch im Zoo");
                        intent.putExtra("image", R.drawable.ein_besuch_im_zoo); // تمرير الصورة
                        intent.putExtra("audio", R.raw.ein_besuch_im_zoo);  // تمرير ملف الصوت
                        intent.putExtra("description", getString(R.string.Ein_Besuch_im_Zoo));
                        intent.putExtra("share_text",
                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                        "🔹 *Niveau:* A2\n" +
                                        "📌 *Titel:* Ein Besuch im Zoo\n\n" +
                                        "📝 *Text:*\n" + getString(R.string.Ein_Besuch_im_Zoo) + "\n\n" +
                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                        startActivityForResult(intent, 4);
                }
            });

            cardviewA2_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isConnected()) {
                        // في حالة عدم وجود إنترنت، افتح النشاط مباشرةً
                        Intent intent = new Intent(PageA2.this, DetailActvity.class);
                        intent.putExtra("currentPage", "PageA2");

                        intent.putExtra("title", "Die neue Wohnung");
                        intent.putExtra("image", R.drawable.die_neue_wohnung); // تمرير الصورة
                        intent.putExtra("audio", R.raw.die_neue_wohnung);  // تمرير ملف الصوت
                        intent.putExtra("description", getString(R.string.Die_neue_Wohnung));
                        intent.putExtra("share_text",
                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                        "🔹 *Niveau:* A2\n" +
                                        "📌 *Titel:* Die neue Wohnung\n\n" +
                                        "📝 *Text:*\n" + getString(R.string.Die_neue_Wohnung) + "\n\n" +
                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                        startActivityForResult(intent, 5);                         return;
                    }
                    Firebase firebaseAds = new Firebase("https://german-4bc62-default-rtdb.firebaseio.com/ads_enabled");
                    firebaseAds.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Boolean adsEnabled = dataSnapshot.getValue(Boolean.class);

                            if (adsEnabled != null && adsEnabled) {
                                // في حالة عدم وجود إنترنت، افتح النشاط مباشرةً
                                Firebase firebase = new Firebase("https://german-4bc62-default-rtdb.firebaseio.com/show_ads");
                                firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String adProvider = dataSnapshot.getValue(String.class);

                                        if ("admob".equals(adProvider)) {
                                                if (mRewardedAd2 != null) {
                                                mRewardedAd2.show(PageA2.this, new OnUserEarnedRewardListener() {
                                                    @Override
                                                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                        loadAdMobRewardedAd2(); // إعادة تحميل إعلان AdMob
                                                    }
                                                });

                                                mRewardedAd2.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                    @Override
                                                    public void onAdDismissedFullScreenContent() {
                                                        mRewardedAd2 = null;
                                                        loadAdMobRewardedAd2();
                                                        Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                                        intent.putExtra("currentPage", "PageA2");

                                                        intent.putExtra("title", "Die neue Wohnung");
                                                        intent.putExtra("image", R.drawable.die_neue_wohnung); // تمرير الصورة
                                                        intent.putExtra("audio", R.raw.die_neue_wohnung);  // تمرير ملف الصوت
                                                        intent.putExtra("description", getString(R.string.Die_neue_Wohnung));
                                                        intent.putExtra("share_text",
                                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                        "🔹 *Niveau:* A2\n" +
                                                                        "📌 *Titel:* Die neue Wohnung\n\n" +
                                                                        "📝 *Text:*\n" + getString(R.string.Die_neue_Wohnung) + "\n\n" +
                                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                        startActivityForResult(intent, 5);
                                                    }

                                                    @Override
                                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                        mRewardedAd2 = null;
                                                        Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                                        intent.putExtra("currentPage", "PageA2");

                                                        intent.putExtra("title", "Die neue Wohnung");
                                                        intent.putExtra("image", R.drawable.die_neue_wohnung); // تمرير الصورة
                                                        intent.putExtra("audio", R.raw.die_neue_wohnung);  // تمرير ملف الصوت
                                                        intent.putExtra("description", getString(R.string.Die_neue_Wohnung));
                                                        intent.putExtra("share_text",
                                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                        "🔹 *Niveau:* A2\n" +
                                                                        "📌 *Titel:* Die neue Wohnung\n\n" +
                                                                        "📝 *Text:*\n" + getString(R.string.Die_neue_Wohnung) + "\n\n" +
                                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                        startActivityForResult(intent, 5);                                 }
                                                });

                                            } else {
                                                Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                                intent.putExtra("currentPage", "PageA2");

                                                intent.putExtra("title", "Die neue Wohnung");
                                                intent.putExtra("image", R.drawable.die_neue_wohnung); // تمرير الصورة
                                                intent.putExtra("audio", R.raw.die_neue_wohnung);  // تمرير ملف الصوت
                                                intent.putExtra("description", getString(R.string.Die_neue_Wohnung));
                                                intent.putExtra("share_text",
                                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                "🔹 *Niveau:* A2\n" +
                                                                "📌 *Titel:* Die neue Wohnung\n\n" +
                                                                "📝 *Text:*\n" + getString(R.string.Die_neue_Wohnung) + "\n\n" +
                                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                startActivityForResult(intent, 5);
                                            }

                                        } else if ("startapp".equals(adProvider)) {
                                            if (isConnected()) {  // التحقق من الاتصال بالإنترنت
                                                Firebase startAppFirebase = new Firebase("https://german-4bc62-default-rtdb.firebaseio.com/startapp/rewarded_app_id");
                                                startAppFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        String startAppAppId = dataSnapshot.getValue(String.class);
                                                        if (startAppAppId != null && !startAppAppId.isEmpty()) {
                                                            StartAppSDK.init(PageA2.this, startAppAppId, false);
                                                            StartAppAd startAppAd = new StartAppAd(PageA2.this);

                                                            startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                                                                @Override
                                                                public void onReceiveAd(Ad ad) {
                                                                    startAppAd.showAd();
                                                                }

                                                                @Override
                                                                public void onFailedToReceiveAd(Ad ad) {
                                                                    Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                                                    intent.putExtra("currentPage", "PageA2");

                                                                    intent.putExtra("title", "Die neue Wohnung");
                                                                    intent.putExtra("image", R.drawable.die_neue_wohnung); // تمرير الصورة
                                                                    intent.putExtra("audio", R.raw.die_neue_wohnung);  // تمرير ملف الصوت
                                                                    intent.putExtra("description", getString(R.string.Die_neue_Wohnung));
                                                                    intent.putExtra("share_text",
                                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                                    "🔹 *Niveau:* A2\n" +
                                                                                    "📌 *Titel:* Die neue Wohnung\n\n" +
                                                                                    "📝 *Text:*\n" + getString(R.string.Die_neue_Wohnung) + "\n\n" +
                                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                                    startActivityForResult(intent, 5);                                             }
                                                            });

                                                            startAppAd.setVideoListener(new VideoListener() {
                                                                @Override
                                                                public void onVideoCompleted() {
                                                                    Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                                                    intent.putExtra("currentPage", "PageA2");

                                                                    intent.putExtra("title", "Die neue Wohnung");
                                                                    intent.putExtra("image", R.drawable.die_neue_wohnung); // تمرير الصورة
                                                                    intent.putExtra("audio", R.raw.die_neue_wohnung);  // تمرير ملف الصوت
                                                                    intent.putExtra("description", getString(R.string.Die_neue_Wohnung));
                                                                    intent.putExtra("share_text",
                                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                                    "🔹 *Niveau:* A2\n" +
                                                                                    "📌 *Titel:* Die neue Wohnung\n\n" +
                                                                                    "📝 *Text:*\n" + getString(R.string.Die_neue_Wohnung) + "\n\n" +
                                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                                    startActivityForResult(intent, 5);                                               }
                                                            });

                                                        } else {
                                                            Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                                            intent.putExtra("currentPage", "PageA2");

                                                            intent.putExtra("title", "Die neue Wohnung");
                                                            intent.putExtra("image", R.drawable.die_neue_wohnung); // تمرير الصورة
                                                            intent.putExtra("audio", R.raw.die_neue_wohnung);  // تمرير ملف الصوت
                                                            intent.putExtra("description", getString(R.string.Die_neue_Wohnung));
                                                            intent.putExtra("share_text",
                                                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                            "🔹 *Niveau:* A2\n" +
                                                                            "📌 *Titel:* Die neue Wohnung\n\n" +
                                                                            "📝 *Text:*\n" + getString(R.string.Die_neue_Wohnung) + "\n\n" +
                                                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                            startActivityForResult(intent, 5);                                      }
                                                    }

                                                    @Override
                                                    public void onCancelled(FirebaseError firebaseError) {
                                                        Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                                        intent.putExtra("currentPage", "PageA2");

                                                        intent.putExtra("title", "Die neue Wohnung");
                                                        intent.putExtra("image", R.drawable.die_neue_wohnung); // تمرير الصورة
                                                        intent.putExtra("audio", R.raw.die_neue_wohnung);  // تمرير ملف الصوت
                                                        intent.putExtra("description", getString(R.string.Die_neue_Wohnung));
                                                        intent.putExtra("share_text",
                                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                        "🔹 *Niveau:* A2\n" +
                                                                        "📌 *Titel:* Die neue Wohnung\n\n" +
                                                                        "📝 *Text:*\n" + getString(R.string.Die_neue_Wohnung) + "\n\n" +
                                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                        startActivityForResult(intent, 5);                                  }
                                                });
                                            } else {
                                                // إذا لم يكن هناك اتصال بالإنترنت
                                                Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                                intent.putExtra("currentPage", "PageA2");

                                                intent.putExtra("title", "Die neue Wohnung");
                                                intent.putExtra("image", R.drawable.die_neue_wohnung); // تمرير الصورة
                                                intent.putExtra("audio", R.raw.die_neue_wohnung);  // تمرير ملف الصوت
                                                intent.putExtra("description", getString(R.string.Die_neue_Wohnung));
                                                intent.putExtra("share_text",
                                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                "🔹 *Niveau:* A2\n" +
                                                                "📌 *Titel:* Die neue Wohnung\n\n" +
                                                                "📝 *Text:*\n" + getString(R.string.Die_neue_Wohnung) + "\n\n" +
                                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                startActivityForResult(intent, 5);                          }
                                        } else {
                                            // فتح النشاط في حال لم يكن هناك إعلان
                                            Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                            intent.putExtra("currentPage", "PageA2");

                                            intent.putExtra("title", "Die neue Wohnung");
                                            intent.putExtra("image", R.drawable.die_neue_wohnung); // تمرير الصورة
                                            intent.putExtra("audio", R.raw.die_neue_wohnung);  // تمرير ملف الصوت
                                            intent.putExtra("description", getString(R.string.Die_neue_Wohnung));
                                            intent.putExtra("share_text",
                                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                            "🔹 *Niveau:* A2\n" +
                                                            "📌 *Titel:* Die neue Wohnung\n\n" +
                                                            "📝 *Text:*\n" + getString(R.string.Die_neue_Wohnung) + "\n\n" +
                                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                            startActivityForResult(intent, 5);                       }
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {
                                        Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                        intent.putExtra("currentPage", "PageA2");

                                        intent.putExtra("title", "Die neue Wohnung");
                                        intent.putExtra("image", R.drawable.die_neue_wohnung); // تمرير الصورة
                                        intent.putExtra("audio", R.raw.die_neue_wohnung);  // تمرير ملف الصوت
                                        intent.putExtra("description", getString(R.string.Die_neue_Wohnung));
                                        intent.putExtra("share_text",
                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                        "🔹 *Niveau:* A2\n" +
                                                        "📌 *Titel:* Die neue Wohnung\n\n" +
                                                        "📝 *Text:*\n" + getString(R.string.Die_neue_Wohnung) + "\n\n" +
                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                        startActivityForResult(intent, 5);                  }
                                });
                            } else {
                                // إذا كانت الإعلانات غير مفعلة، افتح النشاط بشكل طبيعي بدون إعلانات
                                Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                intent.putExtra("currentPage", "PageA2");

                                intent.putExtra("title", "Die neue Wohnung");
                                intent.putExtra("image", R.drawable.die_neue_wohnung); // تمرير الصورة
                                intent.putExtra("audio", R.raw.die_neue_wohnung);  // تمرير ملف الصوت
                                intent.putExtra("description", getString(R.string.Die_neue_Wohnung));
                                intent.putExtra("share_text",
                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                "🔹 *Niveau:* A2\n" +
                                                "📌 *Titel:* Die neue Wohnung\n\n" +
                                                "📝 *Text:*\n" + getString(R.string.Die_neue_Wohnung) + "\n\n" +
                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                startActivityForResult(intent, 5);
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            // افتح النشاط إذا حدث خطأ
                            Intent intent = new Intent(PageA2.this, DetailActvity.class);
                            intent.putExtra("currentPage", "PageA2");

                            intent.putExtra("title", "Die neue Wohnung");
                            intent.putExtra("image", R.drawable.die_neue_wohnung); // تمرير الصورة
                            intent.putExtra("audio", R.raw.die_neue_wohnung);  // تمرير ملف الصوت
                            intent.putExtra("description", getString(R.string.Die_neue_Wohnung));
                            intent.putExtra("share_text",
                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                            "🔹 *Niveau:* A2\n" +
                                            "📌 *Titel:* Die neue Wohnung\n\n" +
                                            "📝 *Text:*\n" + getString(R.string.Die_neue_Wohnung) + "\n\n" +
                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                            startActivityForResult(intent, 5);


                        }
                    });
                }
            });

            cardviewA2_6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(PageA2.this, DetailActvity.class);
                        intent.putExtra("currentPage", "PageA2");

                        intent.putExtra("title", "Ein Tag in der Schule");
                        intent.putExtra("image", R.drawable.ein_tag_in_der_schule); // تمرير الصورة
                        intent.putExtra("audio", R.raw.ein_tag_in_der_schule);  // تمرير ملف الصوت
                        intent.putExtra("description", getString(R.string.Ein_Tag_in_der_Schule));
                        intent.putExtra("share_text",
                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                        "🔹 *Niveau:* A2\n" +
                                        "📌 *Titel:* Ein Tag in der Schule\n\n" +
                                        "📝 *Text:*\n" + getString(R.string.Ein_Tag_in_der_Schule) + "\n\n" +
                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                        startActivityForResult(intent, 6);
                }
            });

            cardviewA2_7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(PageA2.this, DetailActvity.class);
                        intent.putExtra("currentPage", "PageA2");

                        intent.putExtra("title", "Das Lieblingsbuch");
                        intent.putExtra("image", R.drawable.das_lieblingsbuch); // تمرير الصورة
                        intent.putExtra("audio", R.raw.das_lieblingsbuch);  // تمرير ملف الصوت
                        intent.putExtra("description", getString(R.string.Das_Lieblingsbuch));
                        intent.putExtra("share_text",
                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                        "🔹 *Niveau:* A2\n" +
                                        "📌 *Titel:* Das Lieblingsbuch\n\n" +
                                        "📝 *Text:*\n" + getString(R.string.Das_Lieblingsbuch) + "\n\n" +
                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                        startActivityForResult(intent, 7);
                }
            });

            cardviewA2_8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(PageA2.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageA2");

                    intent.putExtra("title", "Ein Tag ohne Handy");
                    intent.putExtra("image", R.drawable.ein_tag_ohne_handy); // تمرير الصورة
                    intent.putExtra("audio", R.raw.ein_tag_ohne_handy);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Ein_Tag_ohne_Handy));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* A2\n" +
                                    "📌 *Titel:* Ein Tag ohne Handy\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Ein_Tag_ohne_Handy) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 8);
                }
            });

            cardviewA2_9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isConnected()) {
                        // في حالة عدم وجود إنترنت، افتح النشاط مباشرةً
                        Intent intent = new Intent(PageA2.this, DetailActvity.class);
                        intent.putExtra("currentPage", "PageA2");

                        intent.putExtra("title", "Der Stadtbummel");
                        intent.putExtra("image", R.drawable.der_stadtbummel); // تمرير الصورة
                        intent.putExtra("audio", R.raw.der_stadtbummel);  // تمرير ملف الصوت
                        intent.putExtra("description", getString(R.string.Der_Stadtbummel));
                        intent.putExtra("share_text",
                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                        "🔹 *Niveau:* A2\n" +
                                        "📌 *Titel:* Der Stadtbummel\n\n" +
                                        "📝 *Text:*\n" + getString(R.string.Der_Stadtbummel) + "\n\n" +
                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                        startActivityForResult(intent, 9);                  return;
                    }
                    Firebase firebaseAds = new Firebase("https://german-4bc62-default-rtdb.firebaseio.com/ads_enabled");
                    firebaseAds.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Boolean adsEnabled = dataSnapshot.getValue(Boolean.class);

                            if (adsEnabled != null && adsEnabled) {
                                // إذا كانت الإعلانات مفعلة، استمر في تحميل الإعلانات
                                Firebase firebase = new Firebase("https://german-4bc62-default-rtdb.firebaseio.com/show_ads");
                                firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String adProvider = dataSnapshot.getValue(String.class);

                                        if ("admob".equals(adProvider)) {
                                            if (mRewardedAd2 != null) {
                                                mRewardedAd2.show(PageA2.this, new OnUserEarnedRewardListener() {
                                                    @Override
                                                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                        loadAdMobRewardedAd2(); // إعادة تحميل إعلان AdMob
                                                    }
                                                });

                                                mRewardedAd2.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                    @Override
                                                    public void onAdDismissedFullScreenContent() {
                                                        mRewardedAd2 = null;
                                                        loadAdMobRewardedAd2();
                                                        Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                                        intent.putExtra("currentPage", "PageA2");

                                                        intent.putExtra("title", "Der Stadtbummel");
                                                        intent.putExtra("image", R.drawable.der_stadtbummel); // تمرير الصورة
                                                        intent.putExtra("audio", R.raw.der_stadtbummel);  // تمرير ملف الصوت
                                                        intent.putExtra("description", getString(R.string.Der_Stadtbummel));
                                                        intent.putExtra("share_text",
                                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                        "🔹 *Niveau:* A2\n" +
                                                                        "📌 *Titel:* Der Stadtbummel\n\n" +
                                                                        "📝 *Text:*\n" + getString(R.string.Der_Stadtbummel) + "\n\n" +
                                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                        startActivityForResult(intent, 9);
                                                    }

                                                    @Override
                                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                        mRewardedAd2 = null;
                                                        Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                                        intent.putExtra("currentPage", "PageA2");

                                                        intent.putExtra("title", "Der Stadtbummel");
                                                        intent.putExtra("image", R.drawable.der_stadtbummel); // تمرير الصورة
                                                        intent.putExtra("audio", R.raw.der_stadtbummel);  // تمرير ملف الصوت
                                                        intent.putExtra("description", getString(R.string.Der_Stadtbummel));
                                                        intent.putExtra("share_text",
                                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                        "🔹 *Niveau:* A2\n" +
                                                                        "📌 *Titel:* Der Stadtbummel\n\n" +
                                                                        "📝 *Text:*\n" + getString(R.string.Der_Stadtbummel) + "\n\n" +
                                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                        startActivityForResult(intent, 9);                                  }
                                                });

                                            } else {
                                                Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                                intent.putExtra("currentPage", "PageA2");

                                                intent.putExtra("title", "Der Stadtbummel");
                                                intent.putExtra("image", R.drawable.der_stadtbummel); // تمرير الصورة
                                                intent.putExtra("audio", R.raw.der_stadtbummel);  // تمرير ملف الصوت
                                                intent.putExtra("description", getString(R.string.Der_Stadtbummel));
                                                intent.putExtra("share_text",
                                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                "🔹 *Niveau:* A2\n" +
                                                                "📌 *Titel:* Der Stadtbummel\n\n" +
                                                                "📝 *Text:*\n" + getString(R.string.Der_Stadtbummel) + "\n\n" +
                                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                startActivityForResult(intent, 9);
                                            }

                                        } else if ("startapp".equals(adProvider)) {
                                            if (isConnected()) {  // التحقق من الاتصال بالإنترنت
                                                Firebase startAppFirebase = new Firebase("https://german-4bc62-default-rtdb.firebaseio.com/startapp/rewarded_app_id");
                                                startAppFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        String startAppAppId = dataSnapshot.getValue(String.class);
                                                        if (startAppAppId != null && !startAppAppId.isEmpty()) {
                                                            StartAppSDK.init(PageA2.this, startAppAppId, false);
                                                            StartAppAd startAppAd = new StartAppAd(PageA2.this);

                                                            startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                                                                @Override
                                                                public void onReceiveAd(Ad ad) {
                                                                    startAppAd.showAd();
                                                                }

                                                                @Override
                                                                public void onFailedToReceiveAd(Ad ad) {
                                                                    Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                                                    intent.putExtra("currentPage", "PageA2");

                                                                    intent.putExtra("title", "Der Stadtbummel");
                                                                    intent.putExtra("image", R.drawable.der_stadtbummel); // تمرير الصورة
                                                                    intent.putExtra("audio", R.raw.der_stadtbummel);  // تمرير ملف الصوت
                                                                    intent.putExtra("description", getString(R.string.Der_Stadtbummel));
                                                                    intent.putExtra("share_text",
                                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                                    "🔹 *Niveau:* A2\n" +
                                                                                    "📌 *Titel:* Der Stadtbummel\n\n" +
                                                                                    "📝 *Text:*\n" + getString(R.string.Der_Stadtbummel) + "\n\n" +
                                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                                    startActivityForResult(intent, 9);                                               }
                                                            });

                                                            startAppAd.setVideoListener(new VideoListener() {
                                                                @Override
                                                                public void onVideoCompleted() {
                                                                    Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                                                    intent.putExtra("currentPage", "PageA2");

                                                                    intent.putExtra("title", "Der Stadtbummel");
                                                                    intent.putExtra("image", R.drawable.der_stadtbummel); // تمرير الصورة
                                                                    intent.putExtra("audio", R.raw.der_stadtbummel);  // تمرير ملف الصوت
                                                                    intent.putExtra("description", getString(R.string.Der_Stadtbummel));
                                                                    intent.putExtra("share_text",
                                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                                    "🔹 *Niveau:* A2\n" +
                                                                                    "📌 *Titel:* Der Stadtbummel\n\n" +
                                                                                    "📝 *Text:*\n" + getString(R.string.Der_Stadtbummel) + "\n\n" +
                                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                                    startActivityForResult(intent, 9);                                               }
                                                            });

                                                        } else {
                                                            Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                                            intent.putExtra("currentPage", "PageA2");

                                                            intent.putExtra("title", "Der Stadtbummel");
                                                            intent.putExtra("image", R.drawable.der_stadtbummel); // تمرير الصورة
                                                            intent.putExtra("audio", R.raw.der_stadtbummel);  // تمرير ملف الصوت
                                                            intent.putExtra("description", getString(R.string.Der_Stadtbummel));
                                                            intent.putExtra("share_text",
                                                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                            "🔹 *Niveau:* A2\n" +
                                                                            "📌 *Titel:* Der Stadtbummel\n\n" +
                                                                            "📝 *Text:*\n" + getString(R.string.Der_Stadtbummel) + "\n\n" +
                                                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                            startActivityForResult(intent, 9);                                    }
                                                    }

                                                    @Override
                                                    public void onCancelled(FirebaseError firebaseError) {
                                                        Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                                        intent.putExtra("currentPage", "PageA2");

                                                        intent.putExtra("title", "Der Stadtbummel");
                                                        intent.putExtra("image", R.drawable.der_stadtbummel); // تمرير الصورة
                                                        intent.putExtra("audio", R.raw.der_stadtbummel);  // تمرير ملف الصوت
                                                        intent.putExtra("description", getString(R.string.Der_Stadtbummel));
                                                        intent.putExtra("share_text",
                                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                        "🔹 *Niveau:* A2\n" +
                                                                        "📌 *Titel:* Der Stadtbummel\n\n" +
                                                                        "📝 *Text:*\n" + getString(R.string.Der_Stadtbummel) + "\n\n" +
                                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                        startActivityForResult(intent, 9);                                  }
                                                });
                                            } else {
                                                // إذا لم يكن هناك اتصال بالإنترنت
                                                Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                                intent.putExtra("currentPage", "PageA2");

                                                intent.putExtra("title", "Der Stadtbummel");
                                                intent.putExtra("image", R.drawable.der_stadtbummel); // تمرير الصورة
                                                intent.putExtra("audio", R.raw.der_stadtbummel);  // تمرير ملف الصوت
                                                intent.putExtra("description", getString(R.string.Der_Stadtbummel));
                                                intent.putExtra("share_text",
                                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                "🔹 *Niveau:* A2\n" +
                                                                "📌 *Titel:* Der Stadtbummel\n\n" +
                                                                "📝 *Text:*\n" + getString(R.string.Der_Stadtbummel) + "\n\n" +
                                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                startActivityForResult(intent, 9);                           }
                                        } else {
                                            // فتح النشاط في حال لم يكن هناك إعلان
                                            Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                            intent.putExtra("currentPage", "PageA2");

                                            intent.putExtra("title", "Der Stadtbummel");
                                            intent.putExtra("image", R.drawable.der_stadtbummel); // تمرير الصورة
                                            intent.putExtra("audio", R.raw.der_stadtbummel);  // تمرير ملف الصوت
                                            intent.putExtra("description", getString(R.string.Der_Stadtbummel));
                                            intent.putExtra("share_text",
                                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                            "🔹 *Niveau:* A2\n" +
                                                            "📌 *Titel:* Der Stadtbummel\n\n" +
                                                            "📝 *Text:*\n" + getString(R.string.Der_Stadtbummel) + "\n\n" +
                                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                            startActivityForResult(intent, 9);                      }
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {
                                        Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                        intent.putExtra("currentPage", "PageA2");

                                        intent.putExtra("title", "Der Stadtbummel");
                                        intent.putExtra("image", R.drawable.der_stadtbummel); // تمرير الصورة
                                        intent.putExtra("audio", R.raw.der_stadtbummel);  // تمرير ملف الصوت
                                        intent.putExtra("description", getString(R.string.Der_Stadtbummel));
                                        intent.putExtra("share_text",
                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                        "🔹 *Niveau:* A2\n" +
                                                        "📌 *Titel:* Der Stadtbummel\n\n" +
                                                        "📝 *Text:*\n" + getString(R.string.Der_Stadtbummel) + "\n\n" +
                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                        startActivityForResult(intent, 9);                  }
                                });
                            } else {
                                // إذا كانت الإعلانات غير مفعلة، افتح النشاط بشكل طبيعي بدون إعلانات
                                Intent intent = new Intent(PageA2.this, DetailActvity.class);
                                intent.putExtra("currentPage", "PageA2");

                                intent.putExtra("title", "Der Stadtbummel");
                                intent.putExtra("image", R.drawable.der_stadtbummel); // تمرير الصورة
                                intent.putExtra("audio", R.raw.der_stadtbummel);  // تمرير ملف الصوت
                                intent.putExtra("description", getString(R.string.Der_Stadtbummel));
                                intent.putExtra("share_text",
                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                "🔹 *Niveau:* A2\n" +
                                                "📌 *Titel:* Der Stadtbummel\n\n" +
                                                "📝 *Text:*\n" + getString(R.string.Der_Stadtbummel) + "\n\n" +
                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                startActivityForResult(intent, 9);
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            // افتح النشاط إذا حدث خطأ
                            Intent intent = new Intent(PageA2.this, DetailActvity.class);
                            intent.putExtra("currentPage", "PageA2");

                            intent.putExtra("title", "Der Stadtbummel");
                            intent.putExtra("image", R.drawable.der_stadtbummel); // تمرير الصورة
                            intent.putExtra("audio", R.raw.der_stadtbummel);  // تمرير ملف الصوت
                            intent.putExtra("description", getString(R.string.Der_Stadtbummel));
                            intent.putExtra("share_text",
                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                            "🔹 *Niveau:* A2\n" +
                                            "📌 *Titel:* Der Stadtbummel\n\n" +
                                            "📝 *Text:*\n" + getString(R.string.Der_Stadtbummel) + "\n\n" +
                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                            startActivityForResult(intent, 9);


                        }
                    });
                }
            });

            cardviewA2_10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(PageA2.this, DetailActvity.class);
                        intent.putExtra("currentPage", "PageA2");

                        intent.putExtra("title", "Der Ausflug in den Wald");
                        intent.putExtra("image", R.drawable.der_ausflug_in_den_wald); // تمرير الصورة
                        intent.putExtra("audio", R.raw.der_ausflug_in_den_wald);  // تمرير ملف الصوت
                        intent.putExtra("description", getString(R.string.Der_Ausflug_in_den_Wald));
                        intent.putExtra("share_text",
                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                        "🔹 *Niveau:* A2\n" +
                                        "📌 *Titel:* Der Ausflug in den Wald\n\n" +
                                        "📝 *Text:*\n" + getString(R.string.Der_Ausflug_in_den_Wald) + "\n\n" +
                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                        startActivityForResult(intent, 10);
                    }

            });


        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK && data != null) {
                boolean isRead = data.getBooleanExtra("isRead1", false);

                if (isRead) {
                    switch (requestCode) {
                        case 1: // الكارت الأول
                            statusIconsA2[0].setImageResource(R.drawable.baseline_done_all_24);
                            dbHelperA2.updateCardStatus(1, true); // تحديث حالة القراءة في قاعدة البيانات
                            break;

                        case 2: // الكارت الثاني
                            statusIconsA2[1].setImageResource(R.drawable.baseline_done_all_24);
                            dbHelperA2.updateCardStatus(2, true); // تحديث حالة القراءة في قاعدة البيانات
                            break;
                        case 3:
                            statusIconsA2[2].setImageResource(R.drawable.baseline_done_all_24);
                            dbHelperA2.updateCardStatus(3,true);
                            break;
                        case 4:
                            statusIconsA2[3].setImageResource(R.drawable.baseline_done_all_24);
                            dbHelperA2.updateCardStatus(4,true);
                            break;
                        case 5: // الكارت الأول
                            statusIconsA2[4].setImageResource(R.drawable.baseline_done_all_24);
                            dbHelperA2.updateCardStatus(5, true); // تحديث حالة القراءة في قاعدة البيانات
                            break;

                        case 6: // الكارت الثاني
                            statusIconsA2[5].setImageResource(R.drawable.baseline_done_all_24);
                            dbHelperA2.updateCardStatus(6, true); // تحديث حالة القراءة في قاعدة البيانات
                            break;
                        case 7:
                            statusIconsA2[6].setImageResource(R.drawable.baseline_done_all_24);
                            dbHelperA2.updateCardStatus(7,true);
                            break;
                        case 8:
                            statusIconsA2[7].setImageResource(R.drawable.baseline_done_all_24);
                            dbHelperA2.updateCardStatus(8,true);
                            break;
                        case 9:
                            statusIconsA2[8].setImageResource(R.drawable.baseline_done_all_24);
                            dbHelperA2.updateCardStatus(9,true);
                            break;
                        case 10:
                            statusIconsA2[9].setImageResource(R.drawable.baseline_done_all_24);
                            dbHelperA2.updateCardStatus(10,true);
                            break;

                        default:
                            break;
                    }
                }
            }
        }

        @Override
        public void onBackPressed() {
            super.onBackPressed();

        }


        private void loadAdMobRewardedAd2() {
            Firebase firebase = new Firebase("https://german-4bc62-default-rtdb.firebaseio.com/admob/rewarded_ad_unit_id");

            firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String rewardedAdUnitId = dataSnapshot.getValue(String.class);

                    if (rewardedAdUnitId != null && !rewardedAdUnitId.isEmpty()) {
                        AdRequest adRequest = new AdRequest.Builder().build();

                        RewardedAd.load(PageA2.this, rewardedAdUnitId, adRequest, new RewardedAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                                mRewardedAd2 = rewardedAd;
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                mRewardedAd2 = null;
                       //         Toast.makeText(PageA2.this, "فشل في تحميل إعلان AdMob", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                  //      Toast.makeText(PageA2.this, "معرف إعلان AdMob غير متوفر", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
               //     Toast.makeText(PageA2.this, "فشل في جلب معرف إعلان AdMob", Toast.LENGTH_SHORT).show();
                }
            });
        }


        public void fireAds2(String adsUrl) {
            Firebase firebase = new Firebase(adsUrl);
            firebase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String data1 = dataSnapshot.getValue(String.class);
                    AdView mAdView = new AdView(PageA2.this);
                    mAdView.setAdUnitId(data1);
                    banner2.addView(mAdView);
                    mAdView.setAdSize(AdSize.BANNER);
                    AdRequest adRequest = new AdRequest.Builder().build();
                    mAdView.loadAd(adRequest);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });
        }

        public void fireStartApp2() {
            Firebase firebase = new Firebase("https://german-4bc62-default-rtdb.firebaseio.com/startapp/banner_app_id");
            firebase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String appId = dataSnapshot.getValue(String.class);
                    StartAppSDK.init(PageA2.this, appId, false);
                    Banner startAppBanner = new Banner(PageA2.this);
                    banner2.addView(startAppBanner);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });
        }
        public boolean isConnected() {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }


    }
