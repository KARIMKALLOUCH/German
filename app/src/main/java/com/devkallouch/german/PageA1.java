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

public class PageA1 extends AppCompatActivity {
    CardView cardviewA1_1,cardviewA1_2,cardviewA1_3,cardviewA1_4,cardviewA1_5,cardviewA1_6,cardviewA1_7,cardviewA1_8,cardviewA1_9,cardviewA1_10;
    ImageView statusIcon, statusIcon1,statusIcon2,statusIcon3,statusIcon4, statusIcon5,statusIcon6,statusIcon7,statusIcon8,statusIcon9; // تعريف على مستوى الصف
    DatabaseHelper dbHelper; // قاعدة البيانات

    private RewardedAd mRewardedAd1;
    String showAds;

    ImageView[] statusIcons;
    int totalCards = 10; // إجمالي عدد الكروت
    ImageView back;
    TextView textname;
    RelativeLayout banner1;
    String data1;
    long lastAdTime ;
    long currentTime ;
    long adCooldown = 2 * 60 * 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        loadAdMobRewardedAd(); // تأكد من تحميل الإعلان عند بدء الصفحة

        setContentView(R.layout.activity_page_a1);


        banner1 = findViewById(R.id.banner1);
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


        cardviewA1_1 = findViewById(R.id.themaA1_1);
        cardviewA1_2 = findViewById(R.id.themaA1_2);
        cardviewA1_3 = findViewById(R.id.themaA1_3);
        cardviewA1_4 = findViewById(R.id.themaA1_4);
        cardviewA1_5 = findViewById(R.id.themaA1_5);
        cardviewA1_6 = findViewById(R.id.themaA1_6);
        cardviewA1_7 = findViewById(R.id.themaA1_7);
        cardviewA1_8 = findViewById(R.id.themaA1_8);
        cardviewA1_9 = findViewById(R.id.themaA1_9);
        cardviewA1_10 = findViewById(R.id.themaA1_10);

        // ربط الأيقونات بمصفوفة
        statusIcons = new ImageView[totalCards];
        statusIcons[0] = findViewById(R.id.statusIcon);
        statusIcons[1] = findViewById(R.id.statusIcon1);
        statusIcons[2] = findViewById(R.id.statusIcon2);
        statusIcons[3] = findViewById(R.id.statusIcon3);
        statusIcons[4] = findViewById(R.id.statusIcon4);
        statusIcons[5] = findViewById(R.id.statusIcon5);
        statusIcons[6] = findViewById(R.id.statusIcon6);
        statusIcons[7] = findViewById(R.id.statusIcon7);
        statusIcons[8] = findViewById(R.id.statusIcon8);
        statusIcons[9] = findViewById(R.id.statusIcon9);

        textname = findViewById(R.id.toolbar2_text);
        textname.setText("Niveau A1");

        back = findViewById(R.id.left3_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageA1.this, Home.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        // إنشاء كائن قاعدة البيانات
        dbHelper = new DatabaseHelper(this);

        // تحديث الحالة عند التشغيل (اختياري)
        for (int i = 0; i < totalCards; i++) {
            if (dbHelper.getCardStatus(i + 1)) {
                statusIcons[i].setImageResource(R.drawable.baseline_done_all_24);
            }
        }

        cardviewA1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // في حالة عدم وجود إنترنت، افتح النشاط مباشرةً
                Intent intent = new Intent(PageA1.this, DetailActvity.class);
                intent.putExtra("currentPage", "PageA1");
                intent.putExtra("title", "Mein Tag");
                intent.putExtra("image", R.drawable.mein_tag);
                intent.putExtra("audio", R.raw.mein_tag);
                intent.putExtra("description", getString(R.string.Mein_Tag));
                intent.putExtra("share_text",
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* A1\n" +
                                "📌 *Titel:* Mein Tag\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Mein_Tag) + "\n\n" +
                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                startActivityForResult(intent, 1);
            }


    });


        cardviewA1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnected()) {
                    // في حالة عدم وجود إنترنت، افتح النشاط مباشرةً
                    Intent intent = new Intent(PageA1.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageA1");
                    intent.putExtra("title", "Einkaufen im Supermarkt");
                    intent.putExtra("image", R.drawable.einkaufen_im_supermarkt); // تمرير الصورة
                    intent.putExtra("audio", R.raw.einkaufen_im_supermarkt);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Einkaufen_im_Supermarkt));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* A1\n" +
                                    "📌 *Titel:* Einkaufen im Supermarkt\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Einkaufen_im_Supermarkt) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 2);                    return;
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
                                        if (mRewardedAd1 != null) {
                                            mRewardedAd1.show(PageA1.this, new OnUserEarnedRewardListener() {
                                                @Override
                                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                    loadAdMobRewardedAd(); // إعادة تحميل إعلان AdMob
                                                }
                                            });

                                            mRewardedAd1.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                @Override
                                                public void onAdDismissedFullScreenContent() {
                                                    mRewardedAd1 = null;
                                                    loadAdMobRewardedAd();
                                                    Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageA1");
                                                    intent.putExtra("title", "Einkaufen im Supermarkt");
                                                    intent.putExtra("image", R.drawable.einkaufen_im_supermarkt); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.einkaufen_im_supermarkt);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Einkaufen_im_Supermarkt));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* A1\n" +
                                                                    "📌 *Titel:* Einkaufen im Supermarkt\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Einkaufen_im_Supermarkt) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                    startActivityForResult(intent, 2);
                                                }

                                                @Override
                                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                    mRewardedAd1 = null;
                                                    Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageA1");
                                                    intent.putExtra("title", "Einkaufen im Supermarkt");
                                                    intent.putExtra("image", R.drawable.einkaufen_im_supermarkt); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.einkaufen_im_supermarkt);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Einkaufen_im_Supermarkt));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* A1\n" +
                                                                    "📌 *Titel:* Einkaufen im Supermarkt\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Einkaufen_im_Supermarkt) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                    startActivityForResult(intent, 2);                                   }
                                            });

                                        } else {
                                            Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                            intent.putExtra("currentPage", "PageA1");
                                            intent.putExtra("title", "Einkaufen im Supermarkt");
                                            intent.putExtra("image", R.drawable.einkaufen_im_supermarkt); // تمرير الصورة
                                            intent.putExtra("audio", R.raw.einkaufen_im_supermarkt);  // تمرير ملف الصوت
                                            intent.putExtra("description", getString(R.string.Einkaufen_im_Supermarkt));
                                            intent.putExtra("share_text",
                                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                            "🔹 *Niveau:* A1\n" +
                                                            "📌 *Titel:* Einkaufen im Supermarkt\n\n" +
                                                            "📝 *Text:*\n" + getString(R.string.Einkaufen_im_Supermarkt) + "\n\n" +
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
                                                        StartAppSDK.init(PageA1.this, startAppAppId, false);
                                                        StartAppAd startAppAd = new StartAppAd(PageA1.this);

                                                        startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                                                            @Override
                                                            public void onReceiveAd(Ad ad) {
                                                                startAppAd.showAd();
                                                            }

                                                            @Override
                                                            public void onFailedToReceiveAd(Ad ad) {
                                                                Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                                                intent.putExtra("currentPage", "PageA1");
                                                                intent.putExtra("title", "Einkaufen im Supermarkt");
                                                                intent.putExtra("image", R.drawable.einkaufen_im_supermarkt); // تمرير الصورة
                                                                intent.putExtra("audio", R.raw.einkaufen_im_supermarkt);  // تمرير ملف الصوت
                                                                intent.putExtra("description", getString(R.string.Einkaufen_im_Supermarkt));
                                                                intent.putExtra("share_text",
                                                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                                "🔹 *Niveau:* A1\n" +
                                                                                "📌 *Titel:* Einkaufen im Supermarkt\n\n" +
                                                                                "📝 *Text:*\n" + getString(R.string.Einkaufen_im_Supermarkt) + "\n\n" +
                                                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                                startActivityForResult(intent, 2);                                               }
                                                        });

                                                        startAppAd.setVideoListener(new VideoListener() {
                                                            @Override
                                                            public void onVideoCompleted() {
                                                                Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                                                intent.putExtra("currentPage", "PageA1");
                                                                intent.putExtra("title", "Einkaufen im Supermarkt");
                                                                intent.putExtra("image", R.drawable.einkaufen_im_supermarkt); // تمرير الصورة
                                                                intent.putExtra("audio", R.raw.einkaufen_im_supermarkt);  // تمرير ملف الصوت
                                                                intent.putExtra("description", getString(R.string.Einkaufen_im_Supermarkt));
                                                                intent.putExtra("share_text",
                                                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                                "🔹 *Niveau:* A1\n" +
                                                                                "📌 *Titel:* Einkaufen im Supermarkt\n\n" +
                                                                                "📝 *Text:*\n" + getString(R.string.Einkaufen_im_Supermarkt) + "\n\n" +
                                                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                                startActivityForResult(intent, 2);                                                }
                                                        });

                                                    } else {
                                                        Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                                        intent.putExtra("currentPage", "PageA1");
                                                        intent.putExtra("title", "Einkaufen im Supermarkt");
                                                        intent.putExtra("image", R.drawable.einkaufen_im_supermarkt); // تمرير الصورة
                                                        intent.putExtra("audio", R.raw.einkaufen_im_supermarkt);  // تمرير ملف الصوت
                                                        intent.putExtra("description", getString(R.string.Einkaufen_im_Supermarkt));
                                                        intent.putExtra("share_text",
                                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                        "🔹 *Niveau:* A1\n" +
                                                                        "📌 *Titel:* Einkaufen im Supermarkt\n\n" +
                                                                        "📝 *Text:*\n" + getString(R.string.Einkaufen_im_Supermarkt) + "\n\n" +
                                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                        startActivityForResult(intent, 2);                                      }
                                                }

                                                @Override
                                                public void onCancelled(FirebaseError firebaseError) {
                                                    Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageA1");
                                                    intent.putExtra("title", "Einkaufen im Supermarkt");
                                                    intent.putExtra("image", R.drawable.einkaufen_im_supermarkt); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.einkaufen_im_supermarkt);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Einkaufen_im_Supermarkt));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* A1\n" +
                                                                    "📌 *Titel:* Einkaufen im Supermarkt\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Einkaufen_im_Supermarkt) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                    startActivityForResult(intent, 2);                                    }
                                            });
                                        } else {
                                            // إذا لم يكن هناك اتصال بالإنترنت
                                            Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                            intent.putExtra("currentPage", "PageA1");
                                            intent.putExtra("title", "Einkaufen im Supermarkt");
                                            intent.putExtra("image", R.drawable.einkaufen_im_supermarkt); // تمرير الصورة
                                            intent.putExtra("audio", R.raw.einkaufen_im_supermarkt);  // تمرير ملف الصوت
                                            intent.putExtra("description", getString(R.string.Einkaufen_im_Supermarkt));
                                            intent.putExtra("share_text",
                                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                            "🔹 *Niveau:* A1\n" +
                                                            "📌 *Titel:* Einkaufen im Supermarkt\n\n" +
                                                            "📝 *Text:*\n" + getString(R.string.Einkaufen_im_Supermarkt) + "\n\n" +
                                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                            startActivityForResult(intent, 2);                          }
                                    } else {
                                        // فتح النشاط في حال لم يكن هناك إعلان
                                        Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                        intent.putExtra("currentPage", "PageA1");
                                        intent.putExtra("title", "Einkaufen im Supermarkt");
                                        intent.putExtra("image", R.drawable.einkaufen_im_supermarkt); // تمرير الصورة
                                        intent.putExtra("audio", R.raw.einkaufen_im_supermarkt);  // تمرير ملف الصوت
                                        intent.putExtra("description", getString(R.string.Einkaufen_im_Supermarkt));
                                        intent.putExtra("share_text",
                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                        "🔹 *Niveau:* A1\n" +
                                                        "📌 *Titel:* Einkaufen im Supermarkt\n\n" +
                                                        "📝 *Text:*\n" + getString(R.string.Einkaufen_im_Supermarkt) + "\n\n" +
                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                        startActivityForResult(intent, 2);                      }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {
                                    Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                    intent.putExtra("currentPage", "PageA1");
                                    intent.putExtra("title", "Einkaufen im Supermarkt");
                                    intent.putExtra("image", R.drawable.einkaufen_im_supermarkt); // تمرير الصورة
                                    intent.putExtra("audio", R.raw.einkaufen_im_supermarkt);  // تمرير ملف الصوت
                                    intent.putExtra("description", getString(R.string.Einkaufen_im_Supermarkt));
                                    intent.putExtra("share_text",
                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                    "🔹 *Niveau:* A1\n" +
                                                    "📌 *Titel:* Einkaufen im Supermarkt\n\n" +
                                                    "📝 *Text:*\n" + getString(R.string.Einkaufen_im_Supermarkt) + "\n\n" +
                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                    startActivityForResult(intent, 2);                    }
                            });
                        } else {
                            // إذا كانت الإعلانات غير مفعلة، افتح النشاط بشكل طبيعي بدون إعلانات
                            Intent intent = new Intent(PageA1.this, DetailActvity.class);
                            intent.putExtra("currentPage", "PageA1");
                            intent.putExtra("title", "Einkaufen im Supermarkt");
                            intent.putExtra("image", R.drawable.einkaufen_im_supermarkt); // تمرير الصورة
                            intent.putExtra("audio", R.raw.einkaufen_im_supermarkt);  // تمرير ملف الصوت
                            intent.putExtra("description", getString(R.string.Einkaufen_im_Supermarkt));
                            intent.putExtra("share_text",
                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                            "🔹 *Niveau:* A1\n" +
                                            "📌 *Titel:* Einkaufen im Supermarkt\n\n" +
                                            "📝 *Text:*\n" + getString(R.string.Einkaufen_im_Supermarkt) + "\n\n" +
                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                            startActivityForResult(intent, 2);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        // افتح النشاط إذا حدث خطأ
                        Intent intent = new Intent(PageA1.this, DetailActvity.class);
                        intent.putExtra("currentPage", "PageA1");
                        intent.putExtra("title", "Einkaufen im Supermarkt");
                        intent.putExtra("image", R.drawable.einkaufen_im_supermarkt); // تمرير الصورة
                        intent.putExtra("audio", R.raw.einkaufen_im_supermarkt);  // تمرير ملف الصوت
                        intent.putExtra("description", getString(R.string.Einkaufen_im_Supermarkt));
                        intent.putExtra("share_text",
                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                        "🔹 *Niveau:* A1\n" +
                                        "📌 *Titel:* Einkaufen im Supermarkt\n\n" +
                                        "📝 *Text:*\n" + getString(R.string.Einkaufen_im_Supermarkt) + "\n\n" +
                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                        startActivityForResult(intent, 2);


                    }
                });
            }
        });


        cardviewA1_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(PageA1.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageA1");
                    intent.putExtra("title", "Das Wetter heute");
                    intent.putExtra("image", R.drawable.das_wetter_heute); // تمرير الصورة
                    intent.putExtra("audio", R.raw.das_wetter_heute);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Das_Wetter_heute));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* A1\n" +
                                    "📌 *Titel:* Das Wetter heute\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Das_Wetter_heute) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 3);

            }
        });


        cardviewA1_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(PageA1.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageA1");
                    intent.putExtra("title", "Mein Lieblingsessen");
                    intent.putExtra("image", R.drawable.mein_lieblingsessen); // تمرير الصورة
                    intent.putExtra("audio", R.raw.mein_lieblingsessen);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Mein_Lieblingsessen));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* A1\n" +
                                    "📌 *Titel:* Mein Lieblingsessen\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Mein_Lieblingsessen) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 4);
            }
        });


        cardviewA1_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnected()) {
                    // في حالة عدم وجود إنترنت، افتح النشاط مباشرةً
                    Intent intent = new Intent(PageA1.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageA1");
                    intent.putExtra("title", "Im Park ");
                    intent.putExtra("image", R.drawable.im_park); // تمرير الصورة
                    intent.putExtra("audio", R.raw.im_park);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Im_Park));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* A1\n" +
                                    "📌 *Titel:* Im Park\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Im_Park) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 5);                  return;
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
                                        if (mRewardedAd1 != null) {
                                            mRewardedAd1.show(PageA1.this, new OnUserEarnedRewardListener() {
                                                @Override
                                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                    loadAdMobRewardedAd(); // إعادة تحميل إعلان AdMob
                                                }
                                            });

                                            mRewardedAd1.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                @Override
                                                public void onAdDismissedFullScreenContent() {
                                                    mRewardedAd1 = null;
                                                    loadAdMobRewardedAd();
                                                    Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageA1");
                                                    intent.putExtra("title", "Im Park ");
                                                    intent.putExtra("image", R.drawable.im_park); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.im_park);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Im_Park));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* A1\n" +
                                                                    "📌 *Titel:* Im Park\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Im_Park) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                    startActivityForResult(intent, 5);
                                                }

                                                @Override
                                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                    mRewardedAd1 = null;
                                                    Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageA1");
                                                    intent.putExtra("title", "Im Park ");
                                                    intent.putExtra("image", R.drawable.im_park); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.im_park);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Im_Park));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* A1\n" +
                                                                    "📌 *Titel:* Im Park\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Im_Park) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                    startActivityForResult(intent, 5);                                 }
                                            });

                                        } else {
                                            Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                            intent.putExtra("currentPage", "PageA1");
                                            intent.putExtra("title", "Im Park ");
                                            intent.putExtra("image", R.drawable.im_park); // تمرير الصورة
                                            intent.putExtra("audio", R.raw.im_park);  // تمرير ملف الصوت
                                            intent.putExtra("description", getString(R.string.Im_Park));
                                            intent.putExtra("share_text",
                                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                            "🔹 *Niveau:* A1\n" +
                                                            "📌 *Titel:* Im Park\n\n" +
                                                            "📝 *Text:*\n" + getString(R.string.Im_Park) + "\n\n" +
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
                                                        StartAppSDK.init(PageA1.this, startAppAppId, false);
                                                        StartAppAd startAppAd = new StartAppAd(PageA1.this);

                                                        startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                                                            @Override
                                                            public void onReceiveAd(Ad ad) {
                                                                startAppAd.showAd();
                                                            }

                                                            @Override
                                                            public void onFailedToReceiveAd(Ad ad) {
                                                                Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                                                intent.putExtra("currentPage", "PageA1");
                                                                intent.putExtra("title", "Im Park ");
                                                                intent.putExtra("image", R.drawable.im_park); // تمرير الصورة
                                                                intent.putExtra("audio", R.raw.im_park);  // تمرير ملف الصوت
                                                                intent.putExtra("description", getString(R.string.Im_Park));
                                                                intent.putExtra("share_text",
                                                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                                "🔹 *Niveau:* A1\n" +
                                                                                "📌 *Titel:* Im Park\n\n" +
                                                                                "📝 *Text:*\n" + getString(R.string.Im_Park) + "\n\n" +
                                                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                                startActivityForResult(intent, 5);                                               }
                                                        });

                                                        startAppAd.setVideoListener(new VideoListener() {
                                                            @Override
                                                            public void onVideoCompleted() {
                                                                Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                                                intent.putExtra("currentPage", "PageA1");
                                                                intent.putExtra("title", "Im Park ");
                                                                intent.putExtra("image", R.drawable.im_park); // تمرير الصورة
                                                                intent.putExtra("audio", R.raw.im_park);  // تمرير ملف الصوت
                                                                intent.putExtra("description", getString(R.string.Im_Park));
                                                                intent.putExtra("share_text",
                                                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                                "🔹 *Niveau:* A1\n" +
                                                                                "📌 *Titel:* Im Park\n\n" +
                                                                                "📝 *Text:*\n" + getString(R.string.Im_Park) + "\n\n" +
                                                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                                startActivityForResult(intent, 5);                                               }
                                                        });

                                                    } else {
                                                        Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                                        intent.putExtra("currentPage", "PageA1");
                                                        intent.putExtra("title", "Im Park ");
                                                        intent.putExtra("image", R.drawable.im_park); // تمرير الصورة
                                                        intent.putExtra("audio", R.raw.im_park);  // تمرير ملف الصوت
                                                        intent.putExtra("description", getString(R.string.Im_Park));
                                                        intent.putExtra("share_text",
                                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                        "🔹 *Niveau:* A1\n" +
                                                                        "📌 *Titel:* Im Park\n\n" +
                                                                        "📝 *Text:*\n" + getString(R.string.Im_Park) + "\n\n" +
                                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                        startActivityForResult(intent, 5);                                     }
                                                }

                                                @Override
                                                public void onCancelled(FirebaseError firebaseError) {
                                                    Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageA1");
                                                    intent.putExtra("title", "Im Park ");
                                                    intent.putExtra("image", R.drawable.im_park); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.im_park);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Im_Park));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* A1\n" +
                                                                    "📌 *Titel:* Im Park\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Im_Park) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                    startActivityForResult(intent, 5);                                  }
                                            });
                                        } else {
                                            // إذا لم يكن هناك اتصال بالإنترنت
                                            Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                            intent.putExtra("currentPage", "PageA1");
                                            intent.putExtra("title", "Im Park ");
                                            intent.putExtra("image", R.drawable.im_park); // تمرير الصورة
                                            intent.putExtra("audio", R.raw.im_park);  // تمرير ملف الصوت
                                            intent.putExtra("description", getString(R.string.Im_Park));
                                            intent.putExtra("share_text",
                                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                            "🔹 *Niveau:* A1\n" +
                                                            "📌 *Titel:* Im Park\n\n" +
                                                            "📝 *Text:*\n" + getString(R.string.Im_Park) + "\n\n" +
                                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                            startActivityForResult(intent, 5);                           }
                                    } else {
                                        // فتح النشاط في حال لم يكن هناك إعلان
                                        Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                        intent.putExtra("currentPage", "PageA1");
                                        intent.putExtra("title", "Im Park ");
                                        intent.putExtra("image", R.drawable.im_park); // تمرير الصورة
                                        intent.putExtra("audio", R.raw.im_park);  // تمرير ملف الصوت
                                        intent.putExtra("description", getString(R.string.Im_Park));
                                        intent.putExtra("share_text",
                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                        "🔹 *Niveau:* A1\n" +
                                                        "📌 *Titel:* Im Park\n\n" +
                                                        "📝 *Text:*\n" + getString(R.string.Im_Park) + "\n\n" +
                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                        startActivityForResult(intent, 5);                       }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {
                                    Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                    intent.putExtra("currentPage", "PageA1");
                                    intent.putExtra("title", "Im Park ");
                                    intent.putExtra("image", R.drawable.im_park); // تمرير الصورة
                                    intent.putExtra("audio", R.raw.im_park);  // تمرير ملف الصوت
                                    intent.putExtra("description", getString(R.string.Im_Park));
                                    intent.putExtra("share_text",
                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                    "🔹 *Niveau:* A1\n" +
                                                    "📌 *Titel:* Im Park\n\n" +
                                                    "📝 *Text:*\n" + getString(R.string.Im_Park) + "\n\n" +
                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                    startActivityForResult(intent, 5);                  }
                            });
                        } else {
                            // إذا كانت الإعلانات غير مفعلة، افتح النشاط بشكل طبيعي بدون إعلانات
                            Intent intent = new Intent(PageA1.this, DetailActvity.class);
                            intent.putExtra("currentPage", "PageA1");
                            intent.putExtra("title", "Im Park ");
                            intent.putExtra("image", R.drawable.im_park); // تمرير الصورة
                            intent.putExtra("audio", R.raw.im_park);  // تمرير ملف الصوت
                            intent.putExtra("description", getString(R.string.Im_Park));
                            intent.putExtra("share_text",
                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                            "🔹 *Niveau:* A1\n" +
                                            "📌 *Titel:* Im Park\n\n" +
                                            "📝 *Text:*\n" + getString(R.string.Im_Park) + "\n\n" +
                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                            startActivityForResult(intent, 5);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        // افتح النشاط إذا حدث خطأ
                        Intent intent = new Intent(PageA1.this, DetailActvity.class);
                        intent.putExtra("currentPage", "PageA1");
                        intent.putExtra("title", "Im Park ");
                        intent.putExtra("image", R.drawable.im_park); // تمرير الصورة
                        intent.putExtra("audio", R.raw.im_park);  // تمرير ملف الصوت
                        intent.putExtra("description", getString(R.string.Im_Park));
                        intent.putExtra("share_text",
                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                        "🔹 *Niveau:* A1\n" +
                                        "📌 *Titel:* Im Park\n\n" +
                                        "📝 *Text:*\n" + getString(R.string.Im_Park) + "\n\n" +
                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                        startActivityForResult(intent, 5);


                    }
                });
            }
        });


        cardviewA1_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(PageA1.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageA1");
                    intent.putExtra("title", "Meine Familie ");
                    intent.putExtra("image", R.drawable.meine_familie); // تمرير الصورة
                    intent.putExtra("audio", R.raw.meine_familie);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Meine_Familie));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* A1\n" +
                                    "📌 *Titel:* Meine Familie\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Meine_Familie) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");

                    startActivityForResult(intent, 6);
            }
        });


        cardviewA1_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(PageA1.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageA1");
                    intent.putExtra("title", "Mein Haus");
                    intent.putExtra("image", R.drawable.mein_haus); // تمرير الصورة
                    intent.putExtra("audio", R.raw.mein_haus);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Mein_Haus));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* A1\n" +
                                    "📌 *Titel:* Mein Haus\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Mein_Haus) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 7);

            }
        });


        cardviewA1_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnected()) {
                    // في حالة عدم وجود إنترنت، افتح النشاط مباشرةً
                    Intent intent = new Intent(PageA1.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageA1");
                    intent.putExtra("title", "Das Haustier");
                    intent.putExtra("image", R.drawable.das_haustier); // تمرير الصورة
                    intent.putExtra("audio", R.raw.das_haustier);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Das_Haustier));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* A1\n" +
                                    "📌 *Titel:* Das Haustier\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Das_Haustier) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 8);                   return;
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
                                        if (mRewardedAd1 != null) {
                                            mRewardedAd1.show(PageA1.this, new OnUserEarnedRewardListener() {
                                                @Override
                                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                    loadAdMobRewardedAd(); // إعادة تحميل إعلان AdMob
                                                }
                                            });

                                            mRewardedAd1.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                @Override
                                                public void onAdDismissedFullScreenContent() {
                                                    mRewardedAd1 = null;
                                                    loadAdMobRewardedAd();
                                                    Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageA1");
                                                    intent.putExtra("title", "Das Haustier");
                                                    intent.putExtra("image", R.drawable.das_haustier); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.das_haustier);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Das_Haustier));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* A1\n" +
                                                                    "📌 *Titel:* Das Haustier\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Das_Haustier) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                    startActivityForResult(intent, 8);
                                                }

                                                @Override
                                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                    mRewardedAd1 = null;
                                                    Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageA1");
                                                    intent.putExtra("title", "Das Haustier");
                                                    intent.putExtra("image", R.drawable.das_haustier); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.das_haustier);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Das_Haustier));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* A1\n" +
                                                                    "📌 *Titel:* Das Haustier\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Das_Haustier) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                    startActivityForResult(intent, 8);                                 }
                                            });

                                        } else {
                                            Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                            intent.putExtra("currentPage", "PageA1");
                                            intent.putExtra("title", "Das Haustier");
                                            intent.putExtra("image", R.drawable.das_haustier); // تمرير الصورة
                                            intent.putExtra("audio", R.raw.das_haustier);  // تمرير ملف الصوت
                                            intent.putExtra("description", getString(R.string.Das_Haustier));
                                            intent.putExtra("share_text",
                                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                            "🔹 *Niveau:* A1\n" +
                                                            "📌 *Titel:* Das Haustier\n\n" +
                                                            "📝 *Text:*\n" + getString(R.string.Das_Haustier) + "\n\n" +
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
                                                        StartAppSDK.init(PageA1.this, startAppAppId, false);
                                                        StartAppAd startAppAd = new StartAppAd(PageA1.this);

                                                        startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                                                            @Override
                                                            public void onReceiveAd(Ad ad) {
                                                                startAppAd.showAd();
                                                            }

                                                            @Override
                                                            public void onFailedToReceiveAd(Ad ad) {
                                                                Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                                                intent.putExtra("currentPage", "PageA1");
                                                                intent.putExtra("title", "Das Haustier");
                                                                intent.putExtra("image", R.drawable.das_haustier); // تمرير الصورة
                                                                intent.putExtra("audio", R.raw.das_haustier);  // تمرير ملف الصوت
                                                                intent.putExtra("description", getString(R.string.Das_Haustier));
                                                                intent.putExtra("share_text",
                                                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                                "🔹 *Niveau:* A1\n" +
                                                                                "📌 *Titel:* Das Haustier\n\n" +
                                                                                "📝 *Text:*\n" + getString(R.string.Das_Haustier) + "\n\n" +
                                                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                                startActivityForResult(intent, 8);                                              }
                                                        });

                                                        startAppAd.setVideoListener(new VideoListener() {
                                                            @Override
                                                            public void onVideoCompleted() {
                                                                Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                                                intent.putExtra("currentPage", "PageA1");
                                                                intent.putExtra("title", "Das Haustier");
                                                                intent.putExtra("image", R.drawable.das_haustier); // تمرير الصورة
                                                                intent.putExtra("audio", R.raw.das_haustier);  // تمرير ملف الصوت
                                                                intent.putExtra("description", getString(R.string.Das_Haustier));
                                                                intent.putExtra("share_text",
                                                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                                "🔹 *Niveau:* A1\n" +
                                                                                "📌 *Titel:* Das Haustier\n\n" +
                                                                                "📝 *Text:*\n" + getString(R.string.Das_Haustier) + "\n\n" +
                                                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                                startActivityForResult(intent, 8);                                               }
                                                        });

                                                    } else {
                                                        Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                                        intent.putExtra("currentPage", "PageA1");
                                                        intent.putExtra("title", "Das Haustier");
                                                        intent.putExtra("image", R.drawable.das_haustier); // تمرير الصورة
                                                        intent.putExtra("audio", R.raw.das_haustier);  // تمرير ملف الصوت
                                                        intent.putExtra("description", getString(R.string.Das_Haustier));
                                                        intent.putExtra("share_text",
                                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                        "🔹 *Niveau:* A1\n" +
                                                                        "📌 *Titel:* Das Haustier\n\n" +
                                                                        "📝 *Text:*\n" + getString(R.string.Das_Haustier) + "\n\n" +
                                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                        startActivityForResult(intent, 8);                                     }
                                                }

                                                @Override
                                                public void onCancelled(FirebaseError firebaseError) {
                                                    Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageA1");
                                                    intent.putExtra("title", "Das Haustier");
                                                    intent.putExtra("image", R.drawable.das_haustier); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.das_haustier);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Das_Haustier));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* A1\n" +
                                                                    "📌 *Titel:* Das Haustier\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Das_Haustier) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                    startActivityForResult(intent, 8);                                 }
                                            });
                                        } else {
                                            // إذا لم يكن هناك اتصال بالإنترنت
                                            Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                            intent.putExtra("currentPage", "PageA1");
                                            intent.putExtra("title", "Das Haustier");
                                            intent.putExtra("image", R.drawable.das_haustier); // تمرير الصورة
                                            intent.putExtra("audio", R.raw.das_haustier);  // تمرير ملف الصوت
                                            intent.putExtra("description", getString(R.string.Das_Haustier));
                                            intent.putExtra("share_text",
                                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                            "🔹 *Niveau:* A1\n" +
                                                            "📌 *Titel:* Das Haustier\n\n" +
                                                            "📝 *Text:*\n" + getString(R.string.Das_Haustier) + "\n\n" +
                                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                            startActivityForResult(intent, 8);                           }
                                    } else {
                                        // فتح النشاط في حال لم يكن هناك إعلان
                                        Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                        intent.putExtra("currentPage", "PageA1");
                                        intent.putExtra("title", "Das Haustier");
                                        intent.putExtra("image", R.drawable.das_haustier); // تمرير الصورة
                                        intent.putExtra("audio", R.raw.das_haustier);  // تمرير ملف الصوت
                                        intent.putExtra("description", getString(R.string.Das_Haustier));
                                        intent.putExtra("share_text",
                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                        "🔹 *Niveau:* A1\n" +
                                                        "📌 *Titel:* Das Haustier\n\n" +
                                                        "📝 *Text:*\n" + getString(R.string.Das_Haustier) + "\n\n" +
                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                        startActivityForResult(intent, 8);                      }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {
                                    Intent intent = new Intent(PageA1.this, DetailActvity.class);
                                    intent.putExtra("currentPage", "PageA1");
                                    intent.putExtra("title", "Das Haustier");
                                    intent.putExtra("image", R.drawable.das_haustier); // تمرير الصورة
                                    intent.putExtra("audio", R.raw.das_haustier);  // تمرير ملف الصوت
                                    intent.putExtra("description", getString(R.string.Das_Haustier));
                                    intent.putExtra("share_text",
                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                    "🔹 *Niveau:* A1\n" +
                                                    "📌 *Titel:* Das Haustier\n\n" +
                                                    "📝 *Text:*\n" + getString(R.string.Das_Haustier) + "\n\n" +
                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                    startActivityForResult(intent, 8);                  }
                            });
                        } else {
                            // إذا كانت الإعلانات غير مفعلة، افتح النشاط بشكل طبيعي بدون إعلانات
                            Intent intent = new Intent(PageA1.this, DetailActvity.class);
                            intent.putExtra("currentPage", "PageA1");
                            intent.putExtra("title", "Das Haustier");
                            intent.putExtra("image", R.drawable.das_haustier); // تمرير الصورة
                            intent.putExtra("audio", R.raw.das_haustier);  // تمرير ملف الصوت
                            intent.putExtra("description", getString(R.string.Das_Haustier));
                            intent.putExtra("share_text",
                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                            "🔹 *Niveau:* A1\n" +
                                            "📌 *Titel:* Das Haustier\n\n" +
                                            "📝 *Text:*\n" + getString(R.string.Das_Haustier) + "\n\n" +
                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                            startActivityForResult(intent, 8);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        // افتح النشاط إذا حدث خطأ
                        Intent intent = new Intent(PageA1.this, DetailActvity.class);
                        intent.putExtra("currentPage", "PageA1");
                        intent.putExtra("title", "Das Haustier");
                        intent.putExtra("image", R.drawable.das_haustier); // تمرير الصورة
                        intent.putExtra("audio", R.raw.das_haustier);  // تمرير ملف الصوت
                        intent.putExtra("description", getString(R.string.Das_Haustier));
                        intent.putExtra("share_text",
                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                        "🔹 *Niveau:* A1\n" +
                                        "📌 *Titel:* Das Haustier\n\n" +
                                        "📝 *Text:*\n" + getString(R.string.Das_Haustier) + "\n\n" +
                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                        startActivityForResult(intent, 8);


                    }
                });
            }
        });


        cardviewA1_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    // في حالة عدم وجود إنترنت، افتح النشاط مباشرةً
                Intent intent = new Intent(PageA1.this, DetailActvity.class);
                intent.putExtra("currentPage", "PageA1");
                intent.putExtra("title", "Im Restaurant ");
                intent.putExtra("image", R.drawable.im_restaurant); // تمرير الصورة
                intent.putExtra("audio", R.raw.im_restaurant);  // تمرير ملف الصوت
                intent.putExtra("description", getString(R.string.Im_Restaurant));
                intent.putExtra("share_text",
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* A1\n" +
                                "📌 *Titel:* Im Restaurant\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Im_Restaurant) + "\n\n" +
                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                startActivityForResult(intent, 9);
            }

        });


        cardviewA1_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(PageA1.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageA1");
                    intent.putExtra("title", "Der neue Freund");
                    intent.putExtra("image", R.drawable.der_neue_freund); // تمرير الصورة
                    intent.putExtra("audio", R.raw.der_neue_freund);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Der_neue_Freund));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* A1\n" +
                                    "📌 *Titel:* Der neue Freund\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Der_neue_Freund) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 10);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            boolean isRead = data.getBooleanExtra("isRead", false);

            if (isRead) {
                switch (requestCode) {
                    case 1: // الكارت الأول
                        statusIcons[0].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelper.updateCardStatus(1, true); // تحديث حالة القراءة في قاعدة البيانات
                        break;

                    case 2: // الكارت الثاني
                        statusIcons[1].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelper.updateCardStatus(2, true); // تحديث حالة القراءة في قاعدة البيانات
                        break;
                    case 3:
                        statusIcons[2].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelper.updateCardStatus(3,true);
                        break;
                    case 4:
                        statusIcons[3].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelper.updateCardStatus(4,true);
                        break;
                    case 5: // الكارت الأول
                        statusIcons[4].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelper.updateCardStatus(5, true); // تحديث حالة القراءة في قاعدة البيانات
                        break;

                    case 6: // الكارت الثاني
                        statusIcons[5].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelper.updateCardStatus(6, true); // تحديث حالة القراءة في قاعدة البيانات
                        break;
                    case 7:
                        statusIcons[6].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelper.updateCardStatus(7,true);
                        break;
                    case 8:
                        statusIcons[7].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelper.updateCardStatus(8,true);
                        break;
                    case 9:
                        statusIcons[8].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelper.updateCardStatus(9,true);
                        break;
                    case 10:
                        statusIcons[9].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelper.updateCardStatus(10,true);
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

    // دالة لتحميل إعلان AdMob المكافئ
    private void loadAdMobRewardedAd() {
        Firebase firebase = new Firebase("https://german-4bc62-default-rtdb.firebaseio.com/admob/rewarded_ad_unit_id");

        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String rewardedAdUnitId = dataSnapshot.getValue(String.class);

                if (rewardedAdUnitId != null && !rewardedAdUnitId.isEmpty()) {
                    AdRequest adRequest = new AdRequest.Builder().build();

                    RewardedAd.load(PageA1.this, rewardedAdUnitId, adRequest, new RewardedAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                            mRewardedAd1 = rewardedAd;
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            mRewardedAd1 = null;
                   //         Toast.makeText(PageA1.this, "فشل في تحميل إعلان AdMob", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
               //     Toast.makeText(PageA1.this, "معرف إعلان AdMob غير متوفر", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            //    Toast.makeText(PageA1.this, "فشل في جلب معرف إعلان AdMob", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void fireAds(String adsUrl) {
        Firebase firebase = new Firebase(adsUrl);
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data1 = dataSnapshot.getValue(String.class);
                AdView mAdView = new AdView(PageA1.this);
                mAdView.setAdUnitId(data1);
                banner1.addView(mAdView);
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
                StartAppSDK.init(PageA1.this, appId, false);
                Banner startAppBanner = new Banner(PageA1.this);
                banner1.addView(startAppBanner);
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