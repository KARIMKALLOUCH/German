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


public class PageB1 extends AppCompatActivity {
    CardView cardviewB1_1,cardviewB1_2,cardviewB1_3,cardviewB1_4,cardviewB1_5,cardviewB1_6,cardviewB1_7,cardviewB1_8,cardviewB1_9,cardviewB1_10;
    DatabaseHelperB1 dbHelperB1; // قاعدة البيانات

    ImageView[] statusIconsB1;
    int totalCards = 10; // إجمالي عدد الكروت
    ImageView back;
    TextView textname;
    private RewardedAd mRewardedAd3;

    RelativeLayout banner3;
    String showAds3;
    long lastAdTime ;
    long currentTime ;
    long adCooldown = 2 * 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        loadAdMobRewardedAd3(); // تأكد من تحميل الإعلان عند بدء الصفحة
        setContentView(R.layout.activity_page_b1);

        banner3 = findViewById(R.id.banner3);
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
                            showAds3 = snapshot.getValue(String.class);
                            if (showAds3.equals("admob")) {
                                fireAds3("https://german-4bc62-default-rtdb.firebaseio.com/admob/banner_ad_unit_id");
                            } else if (showAds3.equals("startapp")) {
                                fireStartApp3();
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

        cardviewB1_1 = findViewById(R.id.themaB1_1);
        cardviewB1_2 = findViewById(R.id.themaB1_2);
        cardviewB1_3 = findViewById(R.id.themaB1_3);
        cardviewB1_4 = findViewById(R.id.themaB1_4);
        cardviewB1_5 = findViewById(R.id.themaB1_5);
        cardviewB1_6 = findViewById(R.id.themaB1_6);
        cardviewB1_7 = findViewById(R.id.themaB1_7);
        cardviewB1_8 = findViewById(R.id.themaB1_8);
        cardviewB1_9 = findViewById(R.id.themaB1_9);
        cardviewB1_10 = findViewById(R.id.themaB1_10);

        // ربط الأيقونات بمصفوفة
        statusIconsB1 = new ImageView[totalCards];
        statusIconsB1[0] = findViewById(R.id.statusIconB1);
        statusIconsB1[1] = findViewById(R.id.statusIconB1_1);
        statusIconsB1[2] = findViewById(R.id.statusIconB1_2);
        statusIconsB1[3] = findViewById(R.id.statusIconB1_3);
        statusIconsB1[4] = findViewById(R.id.statusIconB1_4);
        statusIconsB1[5] = findViewById(R.id.statusIconB1_5);
        statusIconsB1[6] = findViewById(R.id.statusIconB1_6);
        statusIconsB1[7] = findViewById(R.id.statusIconB1_7);
        statusIconsB1[8] = findViewById(R.id.statusIconB1_8);
        statusIconsB1[9] = findViewById(R.id.statusIconB1_9);

        textname = findViewById(R.id.toolbar2_text);
        textname.setText("Niveau B1");

        back = findViewById(R.id.left3_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageB1.this,Home.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        // إنشاء كائن قاعدة البيانات
        dbHelperB1 = new DatabaseHelperB1(this);

        // تحديث الحالة عند التشغيل (اختياري)
        for (int i = 0; i < totalCards; i++) {
            int pageId = 1; // تعيين معرف الصفحة الحالي

            if (dbHelperB1.getCardStatus(i + 1)) {
                statusIconsB1[i].setImageResource(R.drawable.baseline_done_all_24);
            }
        }



        cardviewB1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(PageB1.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageB1");
                    intent.putExtra("title", "Freundschaft und Vertrauen");
                    intent.putExtra("image", R.drawable.freundschaft_und_vertrauen); // تمرير الصورة
                    intent.putExtra("audio", R.raw.freundschaft_und_vertrauen);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Freundschaft_und_Vertrauen));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* B1\n" +
                                    "📌 *Titel:* Freundschaft und Vertrauen\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Freundschaft_und_Vertrauen) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");

                    startActivityForResult(intent, 1);

            }
        });

        cardviewB1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnected()) {
                    // في حالة عدم وجود إنترنت، افتح النشاط مباشرةً
                    Intent intent = new Intent(PageB1.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageB1");

                    intent.putExtra("title", "Meine erste Reise ins Ausland");
                    intent.putExtra("image", R.drawable.meine_erste_reise_ins_ausland); // تمرير الصورة
                    intent.putExtra("audio", R.raw.meine_erste_reise_ins_ausland);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Meine_erste_Reise_ins_Ausland));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* B1\n" +
                                    "📌 *Titel:* Meine_erste_Reise_ins_Ausland\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Meine_erste_Reise_ins_Ausland) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 2);                      return;
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
                                        if (mRewardedAd3 != null) {
                                            mRewardedAd3.show(PageB1.this, new OnUserEarnedRewardListener() {
                                                @Override
                                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                    loadAdMobRewardedAd3(); // إعادة تحميل إعلان AdMob
                                                }
                                            });

                                            mRewardedAd3.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                @Override
                                                public void onAdDismissedFullScreenContent() {
                                                    mRewardedAd3 = null;
                                                    loadAdMobRewardedAd3();
                                                    Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageB1");

                                                    intent.putExtra("title", "Meine erste Reise ins Ausland");
                                                    intent.putExtra("image", R.drawable.meine_erste_reise_ins_ausland); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.meine_erste_reise_ins_ausland);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Meine_erste_Reise_ins_Ausland));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* B1\n" +
                                                                    "📌 *Titel:* Meine_erste_Reise_ins_Ausland\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Meine_erste_Reise_ins_Ausland) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                    startActivityForResult(intent, 2);
                                                }

                                                @Override
                                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                    mRewardedAd3 = null;
                                                    Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageB1");

                                                    intent.putExtra("title", "Meine erste Reise ins Ausland");
                                                    intent.putExtra("image", R.drawable.meine_erste_reise_ins_ausland); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.meine_erste_reise_ins_ausland);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Meine_erste_Reise_ins_Ausland));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* B1\n" +
                                                                    "📌 *Titel:* Meine_erste_Reise_ins_Ausland\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Meine_erste_Reise_ins_Ausland) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                    startActivityForResult(intent, 2);                               }
                                            });

                                        } else {
                                            Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                            intent.putExtra("currentPage", "PageB1");

                                            intent.putExtra("title", "Meine erste Reise ins Ausland");
                                            intent.putExtra("image", R.drawable.meine_erste_reise_ins_ausland); // تمرير الصورة
                                            intent.putExtra("audio", R.raw.meine_erste_reise_ins_ausland);  // تمرير ملف الصوت
                                            intent.putExtra("description", getString(R.string.Meine_erste_Reise_ins_Ausland));
                                            intent.putExtra("share_text",
                                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                            "🔹 *Niveau:* B1\n" +
                                                            "📌 *Titel:* Meine_erste_Reise_ins_Ausland\n\n" +
                                                            "📝 *Text:*\n" + getString(R.string.Meine_erste_Reise_ins_Ausland) + "\n\n" +
                                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                            startActivityForResult(intent, 2);
                                        }

                                    } else if ("startapp".equals(adProvider)) {
                                        if (isConnected()) {  // التحقق من الاتصال بالإنترنت
                                            Firebase startAppFirebase = new Firebase("https://german-4bc62-default-rtdb.firebaseio.com/startapp/rewarded_app_id");
                                            startAppFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    String startAppAppId = dataSnapshot.getValue(String.class);
                                                    if (startAppAppId != null && !startAppAppId.isEmpty()) {
                                                        StartAppSDK.init(PageB1.this, startAppAppId, false);
                                                        StartAppAd startAppAd = new StartAppAd(PageB1.this);

                                                        startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                                                            @Override
                                                            public void onReceiveAd(Ad ad) {
                                                                startAppAd.showAd();
                                                            }

                                                            @Override
                                                            public void onFailedToReceiveAd(Ad ad) {
                                                                Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                                                intent.putExtra("currentPage", "PageB1");

                                                                intent.putExtra("title", "Meine erste Reise ins Ausland");
                                                                intent.putExtra("image", R.drawable.meine_erste_reise_ins_ausland); // تمرير الصورة
                                                                intent.putExtra("audio", R.raw.meine_erste_reise_ins_ausland);  // تمرير ملف الصوت
                                                                intent.putExtra("description", getString(R.string.Meine_erste_Reise_ins_Ausland));
                                                                intent.putExtra("share_text",
                                                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                                "🔹 *Niveau:* B1\n" +
                                                                                "📌 *Titel:* Meine_erste_Reise_ins_Ausland\n\n" +
                                                                                "📝 *Text:*\n" + getString(R.string.Meine_erste_Reise_ins_Ausland) + "\n\n" +
                                                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                                startActivityForResult(intent, 2);                                          }
                                                        });

                                                        startAppAd.setVideoListener(new VideoListener() {
                                                            @Override
                                                            public void onVideoCompleted() {
                                                                Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                                                intent.putExtra("currentPage", "PageB1");

                                                                intent.putExtra("title", "Meine erste Reise ins Ausland");
                                                                intent.putExtra("image", R.drawable.meine_erste_reise_ins_ausland); // تمرير الصورة
                                                                intent.putExtra("audio", R.raw.meine_erste_reise_ins_ausland);  // تمرير ملف الصوت
                                                                intent.putExtra("description", getString(R.string.Meine_erste_Reise_ins_Ausland));
                                                                intent.putExtra("share_text",
                                                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                                "🔹 *Niveau:* B1\n" +
                                                                                "📌 *Titel:* Meine_erste_Reise_ins_Ausland\n\n" +
                                                                                "📝 *Text:*\n" + getString(R.string.Meine_erste_Reise_ins_Ausland) + "\n\n" +
                                                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                                startActivityForResult(intent, 2);                                            }
                                                        });

                                                    } else {
                                                        Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                                        intent.putExtra("currentPage", "PageB1");

                                                        intent.putExtra("title", "Meine erste Reise ins Ausland");
                                                        intent.putExtra("image", R.drawable.meine_erste_reise_ins_ausland); // تمرير الصورة
                                                        intent.putExtra("audio", R.raw.meine_erste_reise_ins_ausland);  // تمرير ملف الصوت
                                                        intent.putExtra("description", getString(R.string.Meine_erste_Reise_ins_Ausland));
                                                        intent.putExtra("share_text",
                                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                        "🔹 *Niveau:* B1\n" +
                                                                        "📌 *Titel:* Meine_erste_Reise_ins_Ausland\n\n" +
                                                                        "📝 *Text:*\n" + getString(R.string.Meine_erste_Reise_ins_Ausland) + "\n\n" +
                                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                        startActivityForResult(intent, 2);                                      }
                                                }

                                                @Override
                                                public void onCancelled(FirebaseError firebaseError) {
                                                    Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageB1");

                                                    intent.putExtra("title", "Meine erste Reise ins Ausland");
                                                    intent.putExtra("image", R.drawable.meine_erste_reise_ins_ausland); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.meine_erste_reise_ins_ausland);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Meine_erste_Reise_ins_Ausland));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* B1\n" +
                                                                    "📌 *Titel:* Meine_erste_Reise_ins_Ausland\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Meine_erste_Reise_ins_Ausland) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                    startActivityForResult(intent, 2);                                  }
                                            });
                                        } else {
                                            // إذا لم يكن هناك اتصال بالإنترنت
                                            Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                            intent.putExtra("currentPage", "PageB1");

                                            intent.putExtra("title", "Meine erste Reise ins Ausland");
                                            intent.putExtra("image", R.drawable.meine_erste_reise_ins_ausland); // تمرير الصورة
                                            intent.putExtra("audio", R.raw.meine_erste_reise_ins_ausland);  // تمرير ملف الصوت
                                            intent.putExtra("description", getString(R.string.Meine_erste_Reise_ins_Ausland));
                                            intent.putExtra("share_text",
                                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                            "🔹 *Niveau:* B1\n" +
                                                            "📌 *Titel:* Meine_erste_Reise_ins_Ausland\n\n" +
                                                            "📝 *Text:*\n" + getString(R.string.Meine_erste_Reise_ins_Ausland) + "\n\n" +
                                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                            startActivityForResult(intent, 2);                      }
                                    } else {
                                        // فتح النشاط في حال لم يكن هناك إعلان
                                        Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                        intent.putExtra("currentPage", "PageB1");

                                        intent.putExtra("title", "Meine erste Reise ins Ausland");
                                        intent.putExtra("image", R.drawable.meine_erste_reise_ins_ausland); // تمرير الصورة
                                        intent.putExtra("audio", R.raw.meine_erste_reise_ins_ausland);  // تمرير ملف الصوت
                                        intent.putExtra("description", getString(R.string.Meine_erste_Reise_ins_Ausland));
                                        intent.putExtra("share_text",
                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                        "🔹 *Niveau:* B1\n" +
                                                        "📌 *Titel:* Meine_erste_Reise_ins_Ausland\n\n" +
                                                        "📝 *Text:*\n" + getString(R.string.Meine_erste_Reise_ins_Ausland) + "\n\n" +
                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                        startActivityForResult(intent, 2);                   }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {
                                    Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                    intent.putExtra("currentPage", "PageB1");

                                    intent.putExtra("title", "Meine erste Reise ins Ausland");
                                    intent.putExtra("image", R.drawable.meine_erste_reise_ins_ausland); // تمرير الصورة
                                    intent.putExtra("audio", R.raw.meine_erste_reise_ins_ausland);  // تمرير ملف الصوت
                                    intent.putExtra("description", getString(R.string.Meine_erste_Reise_ins_Ausland));
                                    intent.putExtra("share_text",
                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                    "🔹 *Niveau:* B1\n" +
                                                    "📌 *Titel:* Meine_erste_Reise_ins_Ausland\n\n" +
                                                    "📝 *Text:*\n" + getString(R.string.Meine_erste_Reise_ins_Ausland) + "\n\n" +
                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                    startActivityForResult(intent, 2);                }
                            });
                        } else {
                            // إذا كانت الإعلانات غير مفعلة، افتح النشاط بشكل طبيعي بدون إعلانات
                            Intent intent = new Intent(PageB1.this, DetailActvity.class);
                            intent.putExtra("currentPage", "PageB1");

                            intent.putExtra("title", "Meine erste Reise ins Ausland");
                            intent.putExtra("image", R.drawable.meine_erste_reise_ins_ausland); // تمرير الصورة
                            intent.putExtra("audio", R.raw.meine_erste_reise_ins_ausland);  // تمرير ملف الصوت
                            intent.putExtra("description", getString(R.string.Meine_erste_Reise_ins_Ausland));
                            intent.putExtra("share_text",
                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                            "🔹 *Niveau:* B1\n" +
                                            "📌 *Titel:* Meine_erste_Reise_ins_Ausland\n\n" +
                                            "📝 *Text:*\n" + getString(R.string.Meine_erste_Reise_ins_Ausland) + "\n\n" +
                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                            startActivityForResult(intent, 2);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        // افتح النشاط إذا حدث خطأ
                        Intent intent = new Intent(PageB1.this, DetailActvity.class);
                        intent.putExtra("currentPage", "PageB1");

                        intent.putExtra("title", "Meine erste Reise ins Ausland");
                        intent.putExtra("image", R.drawable.meine_erste_reise_ins_ausland); // تمرير الصورة
                        intent.putExtra("audio", R.raw.meine_erste_reise_ins_ausland);  // تمرير ملف الصوت
                        intent.putExtra("description", getString(R.string.Meine_erste_Reise_ins_Ausland));
                        intent.putExtra("share_text",
                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                        "🔹 *Niveau:* B1\n" +
                                        "📌 *Titel:* Meine_erste_Reise_ins_Ausland\n\n" +
                                        "📝 *Text:*\n" + getString(R.string.Meine_erste_Reise_ins_Ausland) + "\n\n" +
                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                        startActivityForResult(intent, 2);


                    }
                });
            }
        });

        cardviewB1_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(PageB1.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageB1");

                    intent.putExtra("title", "Sport und ein gesunder Lebensstil");
                    intent.putExtra("image", R.drawable.sport_und_ein_gesunder_lebensstil); // تمرير الصورة
                    intent.putExtra("audio", R.raw.sport_und_ein_gesunder_lebensstil);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Sport_und_gesunder_Lebensstil));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* B1\n" +
                                    "📌 *Titel:* Sport und ein gesunder Lebensstil\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Sport_und_gesunder_Lebensstil) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 3);
            }
        });

        cardviewB1_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(PageB1.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageB1");

                    intent.putExtra("title", "Der Umweltschutz und seine Bedeutung");
                    intent.putExtra("image", R.drawable.der_umweltschutz_und_seine_bedeutung); // تمرير الصورة
                    intent.putExtra("audio", R.raw.der_umweltschutz_und_seine_bedeutung);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Der_Umweltschutz_und_seine_Bedeutung));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* B1\n" +
                                    "📌 *Titel:* Der Umweltschutz und seine Bedeutung\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Der_Umweltschutz_und_seine_Bedeutung) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 4);   }

        });

        cardviewB1_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnected()) {
                    // في حالة عدم وجود إنترنت، افتح النشاط مباشرةً
                    Intent intent = new Intent(PageB1.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageB1");

                    intent.putExtra("title", "Das Leben ohne moderne Technologie");
                    intent.putExtra("image", R.drawable.das_leben_ohne_moderne_technologie); // تمرير الصورة
                    intent.putExtra("audio", R.raw.das_leben_ohne_moderne_technologie);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Das_Leben_ohne_moderne_Technologie));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* B1\n" +
                                    "📌 *Titel:* Das Leben ohne moderne Technologie\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Das_Leben_ohne_moderne_Technologie) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 5);                        return;
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
                                        if (mRewardedAd3 != null) {
                                            mRewardedAd3.show(PageB1.this, new OnUserEarnedRewardListener() {
                                                @Override
                                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                    loadAdMobRewardedAd3(); // إعادة تحميل إعلان AdMob
                                                }
                                            });

                                            mRewardedAd3.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                @Override
                                                public void onAdDismissedFullScreenContent() {
                                                    mRewardedAd3 = null;
                                                    loadAdMobRewardedAd3();
                                                    Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageB1");

                                                    intent.putExtra("title", "Das Leben ohne moderne Technologie");
                                                    intent.putExtra("image", R.drawable.das_leben_ohne_moderne_technologie); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.das_leben_ohne_moderne_technologie);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Das_Leben_ohne_moderne_Technologie));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* B1\n" +
                                                                    "📌 *Titel:* Das Leben ohne moderne Technologie\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Das_Leben_ohne_moderne_Technologie) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                    startActivityForResult(intent, 5);
                                                }

                                                @Override
                                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                    mRewardedAd3 = null;
                                                    Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageB1");

                                                    intent.putExtra("title", "Das Leben ohne moderne Technologie");
                                                    intent.putExtra("image", R.drawable.das_leben_ohne_moderne_technologie); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.das_leben_ohne_moderne_technologie);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Das_Leben_ohne_moderne_Technologie));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* B1\n" +
                                                                    "📌 *Titel:* Das Leben ohne moderne Technologie\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Das_Leben_ohne_moderne_Technologie) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                    startActivityForResult(intent, 5);                              }
                                            });

                                        } else {
                                            Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                            intent.putExtra("currentPage", "PageB1");

                                            intent.putExtra("title", "Das Leben ohne moderne Technologie");
                                            intent.putExtra("image", R.drawable.das_leben_ohne_moderne_technologie); // تمرير الصورة
                                            intent.putExtra("audio", R.raw.das_leben_ohne_moderne_technologie);  // تمرير ملف الصوت
                                            intent.putExtra("description", getString(R.string.Das_Leben_ohne_moderne_Technologie));
                                            intent.putExtra("share_text",
                                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                            "🔹 *Niveau:* B1\n" +
                                                            "📌 *Titel:* Das Leben ohne moderne Technologie\n\n" +
                                                            "📝 *Text:*\n" + getString(R.string.Das_Leben_ohne_moderne_Technologie) + "\n\n" +
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
                                                        StartAppSDK.init(PageB1.this, startAppAppId, false);
                                                        StartAppAd startAppAd = new StartAppAd(PageB1.this);

                                                        startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                                                            @Override
                                                            public void onReceiveAd(Ad ad) {
                                                                startAppAd.showAd();
                                                            }

                                                            @Override
                                                            public void onFailedToReceiveAd(Ad ad) {
                                                                Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                                                intent.putExtra("currentPage", "PageB1");

                                                                intent.putExtra("title", "Das Leben ohne moderne Technologie");
                                                                intent.putExtra("image", R.drawable.das_leben_ohne_moderne_technologie); // تمرير الصورة
                                                                intent.putExtra("audio", R.raw.das_leben_ohne_moderne_technologie);  // تمرير ملف الصوت
                                                                intent.putExtra("description", getString(R.string.Das_Leben_ohne_moderne_Technologie));
                                                                intent.putExtra("share_text",
                                                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                                "🔹 *Niveau:* B1\n" +
                                                                                "📌 *Titel:* Das Leben ohne moderne Technologie\n\n" +
                                                                                "📝 *Text:*\n" + getString(R.string.Das_Leben_ohne_moderne_Technologie) + "\n\n" +
                                                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                                startActivityForResult(intent, 5);                                          }
                                                        });

                                                        startAppAd.setVideoListener(new VideoListener() {
                                                            @Override
                                                            public void onVideoCompleted() {
                                                                Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                                                intent.putExtra("currentPage", "PageB1");

                                                                intent.putExtra("title", "Das Leben ohne moderne Technologie");
                                                                intent.putExtra("image", R.drawable.das_leben_ohne_moderne_technologie); // تمرير الصورة
                                                                intent.putExtra("audio", R.raw.das_leben_ohne_moderne_technologie);  // تمرير ملف الصوت
                                                                intent.putExtra("description", getString(R.string.Das_Leben_ohne_moderne_Technologie));
                                                                intent.putExtra("share_text",
                                                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                                "🔹 *Niveau:* B1\n" +
                                                                                "📌 *Titel:* Das Leben ohne moderne Technologie\n\n" +
                                                                                "📝 *Text:*\n" + getString(R.string.Das_Leben_ohne_moderne_Technologie) + "\n\n" +
                                                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                                startActivityForResult(intent, 5);                                             }
                                                        });

                                                    } else {
                                                        Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                                        intent.putExtra("currentPage", "PageB1");

                                                        intent.putExtra("title", "Das Leben ohne moderne Technologie");
                                                        intent.putExtra("image", R.drawable.das_leben_ohne_moderne_technologie); // تمرير الصورة
                                                        intent.putExtra("audio", R.raw.das_leben_ohne_moderne_technologie);  // تمرير ملف الصوت
                                                        intent.putExtra("description", getString(R.string.Das_Leben_ohne_moderne_Technologie));
                                                        intent.putExtra("share_text",
                                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                        "🔹 *Niveau:* B1\n" +
                                                                        "📌 *Titel:* Das Leben ohne moderne Technologie\n\n" +
                                                                        "📝 *Text:*\n" + getString(R.string.Das_Leben_ohne_moderne_Technologie) + "\n\n" +
                                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                        startActivityForResult(intent, 5);                                      }
                                                }

                                                @Override
                                                public void onCancelled(FirebaseError firebaseError) {
                                                    Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageB1");

                                                    intent.putExtra("title", "Das Leben ohne moderne Technologie");
                                                    intent.putExtra("image", R.drawable.das_leben_ohne_moderne_technologie); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.das_leben_ohne_moderne_technologie);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Das_Leben_ohne_moderne_Technologie));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* B1\n" +
                                                                    "📌 *Titel:* Das Leben ohne moderne Technologie\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Das_Leben_ohne_moderne_Technologie) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                    startActivityForResult(intent, 5);                                 }
                                            });
                                        } else {
                                            // إذا لم يكن هناك اتصال بالإنترنت
                                            Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                            intent.putExtra("currentPage", "PageB1");

                                            intent.putExtra("title", "Das Leben ohne moderne Technologie");
                                            intent.putExtra("image", R.drawable.das_leben_ohne_moderne_technologie); // تمرير الصورة
                                            intent.putExtra("audio", R.raw.das_leben_ohne_moderne_technologie);  // تمرير ملف الصوت
                                            intent.putExtra("description", getString(R.string.Das_Leben_ohne_moderne_Technologie));
                                            intent.putExtra("share_text",
                                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                            "🔹 *Niveau:* B1\n" +
                                                            "📌 *Titel:* Das Leben ohne moderne Technologie\n\n" +
                                                            "📝 *Text:*\n" + getString(R.string.Das_Leben_ohne_moderne_Technologie) + "\n\n" +
                                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                            startActivityForResult(intent, 5);                      }
                                    } else {
                                        // فتح النشاط في حال لم يكن هناك إعلان
                                        Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                        intent.putExtra("currentPage", "PageB1");

                                        intent.putExtra("title", "Das Leben ohne moderne Technologie");
                                        intent.putExtra("image", R.drawable.das_leben_ohne_moderne_technologie); // تمرير الصورة
                                        intent.putExtra("audio", R.raw.das_leben_ohne_moderne_technologie);  // تمرير ملف الصوت
                                        intent.putExtra("description", getString(R.string.Das_Leben_ohne_moderne_Technologie));
                                        intent.putExtra("share_text",
                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                        "🔹 *Niveau:* B1\n" +
                                                        "📌 *Titel:* Das Leben ohne moderne Technologie\n\n" +
                                                        "📝 *Text:*\n" + getString(R.string.Das_Leben_ohne_moderne_Technologie) + "\n\n" +
                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                        startActivityForResult(intent, 5);                    }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {
                                    Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                    intent.putExtra("currentPage", "PageB1");

                                    intent.putExtra("title", "Das Leben ohne moderne Technologie");
                                    intent.putExtra("image", R.drawable.das_leben_ohne_moderne_technologie); // تمرير الصورة
                                    intent.putExtra("audio", R.raw.das_leben_ohne_moderne_technologie);  // تمرير ملف الصوت
                                    intent.putExtra("description", getString(R.string.Das_Leben_ohne_moderne_Technologie));
                                    intent.putExtra("share_text",
                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                    "🔹 *Niveau:* B1\n" +
                                                    "📌 *Titel:* Das Leben ohne moderne Technologie\n\n" +
                                                    "📝 *Text:*\n" + getString(R.string.Das_Leben_ohne_moderne_Technologie) + "\n\n" +
                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                    startActivityForResult(intent, 5);               }
                            });
                        } else {
                            // إذا كانت الإعلانات غير مفعلة، افتح النشاط بشكل طبيعي بدون إعلانات
                            Intent intent = new Intent(PageB1.this, DetailActvity.class);
                            intent.putExtra("currentPage", "PageB1");

                            intent.putExtra("title", "Das Leben ohne moderne Technologie");
                            intent.putExtra("image", R.drawable.das_leben_ohne_moderne_technologie); // تمرير الصورة
                            intent.putExtra("audio", R.raw.das_leben_ohne_moderne_technologie);  // تمرير ملف الصوت
                            intent.putExtra("description", getString(R.string.Das_Leben_ohne_moderne_Technologie));
                            intent.putExtra("share_text",
                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                            "🔹 *Niveau:* B1\n" +
                                            "📌 *Titel:* Das Leben ohne moderne Technologie\n\n" +
                                            "📝 *Text:*\n" + getString(R.string.Das_Leben_ohne_moderne_Technologie) + "\n\n" +
                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                            startActivityForResult(intent, 5);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        // افتح النشاط إذا حدث خطأ
                        Intent intent = new Intent(PageB1.this, DetailActvity.class);
                        intent.putExtra("currentPage", "PageB1");

                        intent.putExtra("title", "Das Leben ohne moderne Technologie");
                        intent.putExtra("image", R.drawable.das_leben_ohne_moderne_technologie); // تمرير الصورة
                        intent.putExtra("audio", R.raw.das_leben_ohne_moderne_technologie);  // تمرير ملف الصوت
                        intent.putExtra("description", getString(R.string.Das_Leben_ohne_moderne_Technologie));
                        intent.putExtra("share_text",
                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                        "🔹 *Niveau:* B1\n" +
                                        "📌 *Titel:* Das Leben ohne moderne Technologie\n\n" +
                                        "📝 *Text:*\n" + getString(R.string.Das_Leben_ohne_moderne_Technologie) + "\n\n" +
                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                        startActivityForResult(intent, 5);


                    }
                });
            }
        });

        cardviewB1_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(PageB1.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageB1");

                    intent.putExtra("title", "Wichtige Entscheidungen im Leben");
                    intent.putExtra("image", R.drawable.wichtige_entscheidungen_im_leben); // تمرير الصورة
                    intent.putExtra("audio", R.raw.wichtige_entscheidungen_im_leben);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Wichtige_Entscheidungen_im_Leben));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* B1\n" +
                                    "📌 *Titel:* Wichtige Entscheidungen im Leben\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Wichtige_Entscheidungen_im_Leben) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 6);
            }
        });

        cardviewB1_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(PageB1.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageB1");

                    intent.putExtra("title", "Die ideale Schule");
                    intent.putExtra("image", R.drawable.die_ideale_schule); // تمرير الصورة
                    intent.putExtra("audio", R.raw.die_ideale_schule);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Die_ideale_Schule));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* B1\n" +
                                    "📌 *Titel:* Die ideale Schule\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Die_ideale_Schule) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 7);

            }
        });

        cardviewB1_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnected()) {
                    // في حالة عدم وجود إنترنت، افتح النشاط مباشرةً
                    Intent intent = new Intent(PageB1.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageB1");

                    intent.putExtra("title", "Stadt oder Land – Wo lebt es sich besser?");
                    intent.putExtra("image", R.drawable.leben_in_der_stadt_vs_leben_auf_dem_land); // تمرير الصورة
                    intent.putExtra("audio", R.raw.leben_in_der_stadt_vs_leben_auf_dem_land);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* B1\n" +
                                    "📌 *Titel:* Stadt oder Land – Wo lebt es sich besser?\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 8);                    return;
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
                                        if (mRewardedAd3 != null) {
                                            mRewardedAd3.show(PageB1.this, new OnUserEarnedRewardListener() {
                                                @Override
                                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                    loadAdMobRewardedAd3(); // إعادة تحميل إعلان AdMob
                                                }
                                            });

                                            mRewardedAd3.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                @Override
                                                public void onAdDismissedFullScreenContent() {
                                                    mRewardedAd3 = null;
                                                    loadAdMobRewardedAd3();
                                                    Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageB1");

                                                    intent.putExtra("title", "Stadt oder Land – Wo lebt es sich besser?");
                                                    intent.putExtra("image", R.drawable.leben_in_der_stadt_vs_leben_auf_dem_land); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.leben_in_der_stadt_vs_leben_auf_dem_land);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* B1\n" +
                                                                    "📌 *Titel:* Stadt oder Land – Wo lebt es sich besser?\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                    startActivityForResult(intent, 8);
                                                }

                                                @Override
                                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                    mRewardedAd3 = null;
                                                    Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageB1");

                                                    intent.putExtra("title", "Stadt oder Land – Wo lebt es sich besser?");
                                                    intent.putExtra("image", R.drawable.leben_in_der_stadt_vs_leben_auf_dem_land); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.leben_in_der_stadt_vs_leben_auf_dem_land);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* B1\n" +
                                                                    "📌 *Titel:* Stadt oder Land – Wo lebt es sich besser?\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                    startActivityForResult(intent, 8);                             }
                                            });

                                        } else {
                                            Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                            intent.putExtra("currentPage", "PageB1");

                                            intent.putExtra("title", "Stadt oder Land – Wo lebt es sich besser?");
                                            intent.putExtra("image", R.drawable.leben_in_der_stadt_vs_leben_auf_dem_land); // تمرير الصورة
                                            intent.putExtra("audio", R.raw.leben_in_der_stadt_vs_leben_auf_dem_land);  // تمرير ملف الصوت
                                            intent.putExtra("description", getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land));
                                            intent.putExtra("share_text",
                                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                            "🔹 *Niveau:* B1\n" +
                                                            "📌 *Titel:* Stadt oder Land – Wo lebt es sich besser?\n\n" +
                                                            "📝 *Text:*\n" + getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land) + "\n\n" +
                                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                            startActivityForResult(intent, 8);
                                        }

                                    } else if ("startapp".equals(adProvider)) {
                                        if (isConnected()) {  // التحقق من الاتصال بالإنترنت
                                            Firebase startAppFirebase = new Firebase("https://german-4bc62-default-rtdb.firebaseio.com/startapp/rewarded_app_id");
                                            startAppFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    String startAppAppId = dataSnapshot.getValue(String.class);
                                                    if (startAppAppId != null && !startAppAppId.isEmpty()) {
                                                        StartAppSDK.init(PageB1.this, startAppAppId, false);
                                                        StartAppAd startAppAd = new StartAppAd(PageB1.this);

                                                        startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                                                            @Override
                                                            public void onReceiveAd(Ad ad) {
                                                                startAppAd.showAd();
                                                            }

                                                            @Override
                                                            public void onFailedToReceiveAd(Ad ad) {
                                                                Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                                                intent.putExtra("currentPage", "PageB1");

                                                                intent.putExtra("title", "Stadt oder Land – Wo lebt es sich besser?");
                                                                intent.putExtra("image", R.drawable.leben_in_der_stadt_vs_leben_auf_dem_land); // تمرير الصورة
                                                                intent.putExtra("audio", R.raw.leben_in_der_stadt_vs_leben_auf_dem_land);  // تمرير ملف الصوت
                                                                intent.putExtra("description", getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land));
                                                                intent.putExtra("share_text",
                                                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                                "🔹 *Niveau:* B1\n" +
                                                                                "📌 *Titel:* Stadt oder Land – Wo lebt es sich besser?\n\n" +
                                                                                "📝 *Text:*\n" + getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land) + "\n\n" +
                                                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                                startActivityForResult(intent, 8);                                         }
                                                        });

                                                        startAppAd.setVideoListener(new VideoListener() {
                                                            @Override
                                                            public void onVideoCompleted() {
                                                                Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                                                intent.putExtra("currentPage", "PageB1");

                                                                intent.putExtra("title", "Stadt oder Land – Wo lebt es sich besser?");
                                                                intent.putExtra("image", R.drawable.leben_in_der_stadt_vs_leben_auf_dem_land); // تمرير الصورة
                                                                intent.putExtra("audio", R.raw.leben_in_der_stadt_vs_leben_auf_dem_land);  // تمرير ملف الصوت
                                                                intent.putExtra("description", getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land));
                                                                intent.putExtra("share_text",
                                                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                                "🔹 *Niveau:* B1\n" +
                                                                                "📌 *Titel:* Stadt oder Land – Wo lebt es sich besser?\n\n" +
                                                                                "📝 *Text:*\n" + getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land) + "\n\n" +
                                                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                                startActivityForResult(intent, 8);                                            }
                                                        });

                                                    } else {
                                                        Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                                        intent.putExtra("currentPage", "PageB1");

                                                        intent.putExtra("title", "Stadt oder Land – Wo lebt es sich besser?");
                                                        intent.putExtra("image", R.drawable.leben_in_der_stadt_vs_leben_auf_dem_land); // تمرير الصورة
                                                        intent.putExtra("audio", R.raw.leben_in_der_stadt_vs_leben_auf_dem_land);  // تمرير ملف الصوت
                                                        intent.putExtra("description", getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land));
                                                        intent.putExtra("share_text",
                                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                        "🔹 *Niveau:* B1\n" +
                                                                        "📌 *Titel:* Stadt oder Land – Wo lebt es sich besser?\n\n" +
                                                                        "📝 *Text:*\n" + getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land) + "\n\n" +
                                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                        startActivityForResult(intent, 8);                                     }
                                                }

                                                @Override
                                                public void onCancelled(FirebaseError firebaseError) {
                                                    Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageB1");

                                                    intent.putExtra("title", "Stadt oder Land – Wo lebt es sich besser?");
                                                    intent.putExtra("image", R.drawable.leben_in_der_stadt_vs_leben_auf_dem_land); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.leben_in_der_stadt_vs_leben_auf_dem_land);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* B1\n" +
                                                                    "📌 *Titel:* Stadt oder Land – Wo lebt es sich besser?\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                    startActivityForResult(intent, 8);                               }
                                            });
                                        } else {
                                            // إذا لم يكن هناك اتصال بالإنترنت
                                            Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                            intent.putExtra("currentPage", "PageB1");

                                            intent.putExtra("title", "Stadt oder Land – Wo lebt es sich besser?");
                                            intent.putExtra("image", R.drawable.leben_in_der_stadt_vs_leben_auf_dem_land); // تمرير الصورة
                                            intent.putExtra("audio", R.raw.leben_in_der_stadt_vs_leben_auf_dem_land);  // تمرير ملف الصوت
                                            intent.putExtra("description", getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land));
                                            intent.putExtra("share_text",
                                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                            "🔹 *Niveau:* B1\n" +
                                                            "📌 *Titel:* Stadt oder Land – Wo lebt es sich besser?\n\n" +
                                                            "📝 *Text:*\n" + getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land) + "\n\n" +
                                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                            startActivityForResult(intent, 8);                     }
                                    } else {
                                        // فتح النشاط في حال لم يكن هناك إعلان
                                        Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                        intent.putExtra("currentPage", "PageB1");

                                        intent.putExtra("title", "Stadt oder Land – Wo lebt es sich besser?");
                                        intent.putExtra("image", R.drawable.leben_in_der_stadt_vs_leben_auf_dem_land); // تمرير الصورة
                                        intent.putExtra("audio", R.raw.leben_in_der_stadt_vs_leben_auf_dem_land);  // تمرير ملف الصوت
                                        intent.putExtra("description", getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land));
                                        intent.putExtra("share_text",
                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                        "🔹 *Niveau:* B1\n" +
                                                        "📌 *Titel:* Stadt oder Land – Wo lebt es sich besser?\n\n" +
                                                        "📝 *Text:*\n" + getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land) + "\n\n" +
                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                        startActivityForResult(intent, 8);                 }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {
                                    Intent intent = new Intent(PageB1.this, DetailActvity.class);
                                    intent.putExtra("currentPage", "PageB1");

                                    intent.putExtra("title", "Stadt oder Land – Wo lebt es sich besser?");
                                    intent.putExtra("image", R.drawable.leben_in_der_stadt_vs_leben_auf_dem_land); // تمرير الصورة
                                    intent.putExtra("audio", R.raw.leben_in_der_stadt_vs_leben_auf_dem_land);  // تمرير ملف الصوت
                                    intent.putExtra("description", getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land));
                                    intent.putExtra("share_text",
                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                    "🔹 *Niveau:* B1\n" +
                                                    "📌 *Titel:* Stadt oder Land – Wo lebt es sich besser?\n\n" +
                                                    "📝 *Text:*\n" + getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land) + "\n\n" +
                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                    startActivityForResult(intent, 8);                }
                            });
                        } else {
                            // إذا كانت الإعلانات غير مفعلة، افتح النشاط بشكل طبيعي بدون إعلانات
                            Intent intent = new Intent(PageB1.this, DetailActvity.class);
                            intent.putExtra("currentPage", "PageB1");

                            intent.putExtra("title", "Stadt oder Land – Wo lebt es sich besser?");
                            intent.putExtra("image", R.drawable.leben_in_der_stadt_vs_leben_auf_dem_land); // تمرير الصورة
                            intent.putExtra("audio", R.raw.leben_in_der_stadt_vs_leben_auf_dem_land);  // تمرير ملف الصوت
                            intent.putExtra("description", getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land));
                            intent.putExtra("share_text",
                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                            "🔹 *Niveau:* B1\n" +
                                            "📌 *Titel:* Stadt oder Land – Wo lebt es sich besser?\n\n" +
                                            "📝 *Text:*\n" + getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land) + "\n\n" +
                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                            startActivityForResult(intent, 8);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        // افتح النشاط إذا حدث خطأ
                        Intent intent = new Intent(PageB1.this, DetailActvity.class);
                        intent.putExtra("currentPage", "PageB1");

                        intent.putExtra("title", "Stadt oder Land – Wo lebt es sich besser?");
                        intent.putExtra("image", R.drawable.leben_in_der_stadt_vs_leben_auf_dem_land); // تمرير الصورة
                        intent.putExtra("audio", R.raw.leben_in_der_stadt_vs_leben_auf_dem_land);  // تمرير ملف الصوت
                        intent.putExtra("description", getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land));
                        intent.putExtra("share_text",
                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                        "🔹 *Niveau:* B1\n" +
                                        "📌 *Titel:* Stadt oder Land – Wo lebt es sich besser?\n\n" +
                                        "📝 *Text:*\n" + getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land) + "\n\n" +
                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                        startActivityForResult(intent, 8);


                    }
                });
            }
        });

        cardviewB1_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(PageB1.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageB1");

                    intent.putExtra("title", "Erfolg und Motivation im Berufsleben");
                    intent.putExtra("image", R.drawable.erfolg_und_motivation_im_berufsleben); // تمرير الصورة
                    intent.putExtra("audio", R.raw.erfolg_und_motivation_im_berufsleben);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Erfolg_und_Motivation_im_Berufsleben));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* B1\n" +
                                    "📌 *Titel:* Erfolg und Motivation im Berufsleben\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Erfolg_und_Motivation_im_Berufsleben) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 9);
                }

        });

        cardviewB1_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(PageB1.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageB1");

                    intent.putExtra("title", "Familie und ihre Rolle im Alltag");
                    intent.putExtra("image", R.drawable.familie_und_ihre_rolle_im_alltag); // تمرير الصورة
                    intent.putExtra("audio", R.raw.familie_und_ihre_rolle_im_alltag);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Familie_und_Rolle_im_Alltag));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* B1\n" +
                                    "📌 *Titel:* Familie und ihre Rolle im Alltag\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Familie_und_Rolle_im_Alltag) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 10);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            boolean isRead = data.getBooleanExtra("isRead2", false);

            if (isRead) {
                switch (requestCode) {
                    case 1: // الكارت الأول
                        statusIconsB1[0].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelperB1.updateCardStatus(1, true); // تحديث حالة القراءة في قاعدة البيانات
                        break;

                    case 2: // الكارت الثاني
                        statusIconsB1[1].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelperB1.updateCardStatus(2, true); // تحديث حالة القراءة في قاعدة البيانات
                        break;
                    case 3:
                        statusIconsB1[2].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelperB1.updateCardStatus(3,true);
                        break;
                    case 4:
                        statusIconsB1[3].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelperB1.updateCardStatus(4,true);
                        break;
                    case 5: // الكارت الأول
                        statusIconsB1[4].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelperB1.updateCardStatus(5, true); // تحديث حالة القراءة في قاعدة البيانات
                        break;

                    case 6: // الكارت الثاني
                        statusIconsB1[5].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelperB1.updateCardStatus(6, true); // تحديث حالة القراءة في قاعدة البيانات
                        break;
                    case 7:
                        statusIconsB1[6].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelperB1.updateCardStatus(7,true);
                        break;
                    case 8:
                        statusIconsB1[7].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelperB1.updateCardStatus(8,true);
                        break;
                    case 9:
                        statusIconsB1[8].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelperB1.updateCardStatus(9,true);
                        break;
                    case 10:
                        statusIconsB1[9].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelperB1.updateCardStatus(10,true);
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
    private void loadAdMobRewardedAd3() {
        Firebase firebase = new Firebase("https://german-4bc62-default-rtdb.firebaseio.com/admob/rewarded_ad_unit_id");

        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String rewardedAdUnitId = dataSnapshot.getValue(String.class);

                if (rewardedAdUnitId != null && !rewardedAdUnitId.isEmpty()) {
                    AdRequest adRequest = new AdRequest.Builder().build();

                    RewardedAd.load(PageB1.this, rewardedAdUnitId, adRequest, new RewardedAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                            mRewardedAd3 = rewardedAd;
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            mRewardedAd3 = null;
                         //   Toast.makeText(PageB1.this, "فشل في تحميل إعلان AdMob", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
               //     Toast.makeText(PageB1.this, "معرف إعلان AdMob غير متوفر", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
              //  Toast.makeText(PageB1.this, "فشل في جلب معرف إعلان AdMob", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void fireAds3(String adsUrl) {
        Firebase firebase = new Firebase(adsUrl);
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data1 = dataSnapshot.getValue(String.class);
                AdView mAdView = new AdView(PageB1.this);
                mAdView.setAdUnitId(data1);
                banner3.addView(mAdView);
                mAdView.setAdSize(AdSize.BANNER);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    public void fireStartApp3() {
        Firebase firebase = new Firebase("https://german-4bc62-default-rtdb.firebaseio.com/startapp/banner_app_id");
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String appId = dataSnapshot.getValue(String.class);
                StartAppSDK.init(PageB1.this, appId, false);
                Banner startAppBanner = new Banner(PageB1.this);
                banner3.addView(startAppBanner);
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