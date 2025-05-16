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


public class PageB2 extends AppCompatActivity {
    CardView cardviewB2_1,cardviewB2_2,cardviewB2_3,cardviewB2_4,cardviewB2_5,cardviewB2_6,cardviewB2_7,cardviewB2_8,cardviewB2_9,cardviewB2_10;
    DatabaseHelperB2 dbHelperB2; // قاعدة البيانات

    ImageView[] statusIconsB2;
    int totalCards = 10; // إجمالي عدد الكروت
    ImageView back;
    TextView textname;
    private RewardedAd mRewardedAd4;

    RelativeLayout banner4;
    String showAds4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        loadAdMobRewardedAd4(); // تأكد من تحميل الإعلان عند بدء الصفحة
        setContentView(R.layout.activity_page_b2);


        banner4 = findViewById(R.id.banner3);
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
                            showAds4 = snapshot.getValue(String.class);
                            if (showAds4.equals("admob")) {
                                fireAds4("https://german-4bc62-default-rtdb.firebaseio.com/admob/banner_ad_unit_id");
                            } else if (showAds4.equals("startapp")) {
                                fireStartApp4();
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

        cardviewB2_1 = findViewById(R.id.themaB2_1);
        cardviewB2_2 = findViewById(R.id.themaB2_2);
        cardviewB2_3 = findViewById(R.id.themaB2_3);
        cardviewB2_4 = findViewById(R.id.themaB2_4);
        cardviewB2_5 = findViewById(R.id.themaB2_5);
        cardviewB2_6 = findViewById(R.id.themaB2_6);
        cardviewB2_7 = findViewById(R.id.themaB2_7);
        cardviewB2_8 = findViewById(R.id.themaB2_8);
        cardviewB2_9 = findViewById(R.id.themaB2_9);
        cardviewB2_10 = findViewById(R.id.themaB2_10);

        // ربط الأيقونات بمصفوفة
        statusIconsB2 = new ImageView[totalCards];
        statusIconsB2[0] = findViewById(R.id.statusIconB2);
        statusIconsB2[1] = findViewById(R.id.statusIconB2_1);
        statusIconsB2[2] = findViewById(R.id.statusIconB2_2);
        statusIconsB2[3] = findViewById(R.id.statusIconB2_3);
        statusIconsB2[4] = findViewById(R.id.statusIconB2_4);
        statusIconsB2[5] = findViewById(R.id.statusIconB2_5);
        statusIconsB2[6] = findViewById(R.id.statusIconB2_6);
        statusIconsB2[7] = findViewById(R.id.statusIconB2_7);
        statusIconsB2[8] = findViewById(R.id.statusIconB2_8);
        statusIconsB2[9] = findViewById(R.id.statusIconB2_9);

        textname = findViewById(R.id.toolbar2_text);
        textname.setText("Niveau B2");

        back = findViewById(R.id.left3_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageB2.this,Home.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        // إنشاء كائن قاعدة البيانات
        dbHelperB2 = new DatabaseHelperB2(this);

        // تحديث الحالة عند التشغيل (اختياري)
        for (int i = 0; i < totalCards; i++) {
            int pageId = 1; // تعيين معرف الصفحة الحالي

            if (dbHelperB2.getCardStatus(i + 1)) {
                statusIconsB2[i].setImageResource(R.drawable.baseline_done_all_24);
            }
        }

        cardviewB2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(PageB2.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageB2");
                    intent.putExtra("title", "Digitale Transformation im Arbeitsmarkt");
                    intent.putExtra("image", R.drawable.digitale_transformation_im_arbeitsmarkt); // تمرير الصورة
                    intent.putExtra("audio", R.raw.digitale_transformation_im_arbeitsmarkt);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Digitale_Transformation_im_Arbeitsmarkt));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* B2\n" +
                                    "📌 *Titel:* Digitale Transformation im Arbeitsmarkt\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Digitale_Transformation_im_Arbeitsmarkt) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 1);
            }
        });


        cardviewB2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnected()) {
                    // في حالة عدم وجود إنترنت، افتح النشاط مباشرةً
                    Intent intent = new Intent(PageB2.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageB2");

                    intent.putExtra("title", "Nachhaltigkeit und Umweltbewusstsein");
                    intent.putExtra("image", R.drawable.nachhaltigkeit_und_umweltbewusstsein); // تمرير الصورة
                    intent.putExtra("audio", R.raw.nachhaltigkeit_und_umweltbewusstsein);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* B2\n" +
                                    "📌 *Titel:* Nachhaltigkeit und Umweltbewusstsein\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 2);                    return;
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
                                        if (mRewardedAd4 != null) {
                                            mRewardedAd4.show(PageB2.this, new OnUserEarnedRewardListener() {
                                                @Override
                                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                    loadAdMobRewardedAd4(); // إعادة تحميل إعلان AdMob
                                                }
                                            });

                                            mRewardedAd4.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                @Override
                                                public void onAdDismissedFullScreenContent() {
                                                    mRewardedAd4 = null;
                                                    loadAdMobRewardedAd4();
                                                    Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageB2");

                                                    intent.putExtra("title", "Nachhaltigkeit und Umweltbewusstsein");
                                                    intent.putExtra("image", R.drawable.nachhaltigkeit_und_umweltbewusstsein); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.nachhaltigkeit_und_umweltbewusstsein);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* B2\n" +
                                                                    "📌 *Titel:* Nachhaltigkeit und Umweltbewusstsein\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                    startActivityForResult(intent, 2);
                                                }

                                                @Override
                                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                    mRewardedAd4 = null;
                                                    Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageB2");

                                                    intent.putExtra("title", "Nachhaltigkeit und Umweltbewusstsein");
                                                    intent.putExtra("image", R.drawable.nachhaltigkeit_und_umweltbewusstsein); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.nachhaltigkeit_und_umweltbewusstsein);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* B2\n" +
                                                                    "📌 *Titel:* Nachhaltigkeit und Umweltbewusstsein\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                    startActivityForResult(intent, 2);                            }
                                            });

                                        } else {
                                            Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                            intent.putExtra("currentPage", "PageB2");

                                            intent.putExtra("title", "Nachhaltigkeit und Umweltbewusstsein");
                                            intent.putExtra("image", R.drawable.nachhaltigkeit_und_umweltbewusstsein); // تمرير الصورة
                                            intent.putExtra("audio", R.raw.nachhaltigkeit_und_umweltbewusstsein);  // تمرير ملف الصوت
                                            intent.putExtra("description", getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein));
                                            intent.putExtra("share_text",
                                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                            "🔹 *Niveau:* B2\n" +
                                                            "📌 *Titel:* Nachhaltigkeit und Umweltbewusstsein\n\n" +
                                                            "📝 *Text:*\n" + getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein) + "\n\n" +
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
                                                        StartAppSDK.init(PageB2.this, startAppAppId, false);
                                                        StartAppAd startAppAd = new StartAppAd(PageB2.this);

                                                        startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                                                            @Override
                                                            public void onReceiveAd(Ad ad) {
                                                                startAppAd.showAd();
                                                            }

                                                            @Override
                                                            public void onFailedToReceiveAd(Ad ad) {
                                                                Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                                                intent.putExtra("currentPage", "PageB2");

                                                                intent.putExtra("title", "Nachhaltigkeit und Umweltbewusstsein");
                                                                intent.putExtra("image", R.drawable.nachhaltigkeit_und_umweltbewusstsein); // تمرير الصورة
                                                                intent.putExtra("audio", R.raw.nachhaltigkeit_und_umweltbewusstsein);  // تمرير ملف الصوت
                                                                intent.putExtra("description", getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein));
                                                                intent.putExtra("share_text",
                                                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                                "🔹 *Niveau:* B2\n" +
                                                                                "📌 *Titel:* Nachhaltigkeit und Umweltbewusstsein\n\n" +
                                                                                "📝 *Text:*\n" + getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein) + "\n\n" +
                                                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                                startActivityForResult(intent, 2);                                          }
                                                        });

                                                        startAppAd.setVideoListener(new VideoListener() {
                                                            @Override
                                                            public void onVideoCompleted() {
                                                                Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                                                intent.putExtra("currentPage", "PageB2");

                                                                intent.putExtra("title", "Nachhaltigkeit und Umweltbewusstsein");
                                                                intent.putExtra("image", R.drawable.nachhaltigkeit_und_umweltbewusstsein); // تمرير الصورة
                                                                intent.putExtra("audio", R.raw.nachhaltigkeit_und_umweltbewusstsein);  // تمرير ملف الصوت
                                                                intent.putExtra("description", getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein));
                                                                intent.putExtra("share_text",
                                                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                                "🔹 *Niveau:* B2\n" +
                                                                                "📌 *Titel:* Nachhaltigkeit und Umweltbewusstsein\n\n" +
                                                                                "📝 *Text:*\n" + getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein) + "\n\n" +
                                                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                                startActivityForResult(intent, 2);                                        }
                                                        });

                                                    } else {
                                                        Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                                        intent.putExtra("currentPage", "PageB2");

                                                        intent.putExtra("title", "Nachhaltigkeit und Umweltbewusstsein");
                                                        intent.putExtra("image", R.drawable.nachhaltigkeit_und_umweltbewusstsein); // تمرير الصورة
                                                        intent.putExtra("audio", R.raw.nachhaltigkeit_und_umweltbewusstsein);  // تمرير ملف الصوت
                                                        intent.putExtra("description", getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein));
                                                        intent.putExtra("share_text",
                                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                        "🔹 *Niveau:* B2\n" +
                                                                        "📌 *Titel:* Nachhaltigkeit und Umweltbewusstsein\n\n" +
                                                                        "📝 *Text:*\n" + getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein) + "\n\n" +
                                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                        startActivityForResult(intent, 2);                                 }
                                                }

                                                @Override
                                                public void onCancelled(FirebaseError firebaseError) {
                                                    Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageB2");

                                                    intent.putExtra("title", "Nachhaltigkeit und Umweltbewusstsein");
                                                    intent.putExtra("image", R.drawable.nachhaltigkeit_und_umweltbewusstsein); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.nachhaltigkeit_und_umweltbewusstsein);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* B2\n" +
                                                                    "📌 *Titel:* Nachhaltigkeit und Umweltbewusstsein\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                    startActivityForResult(intent, 2);                             }
                                            });
                                        } else {
                                            // إذا لم يكن هناك اتصال بالإنترنت
                                            Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                            intent.putExtra("currentPage", "PageB2");

                                            intent.putExtra("title", "Nachhaltigkeit und Umweltbewusstsein");
                                            intent.putExtra("image", R.drawable.nachhaltigkeit_und_umweltbewusstsein); // تمرير الصورة
                                            intent.putExtra("audio", R.raw.nachhaltigkeit_und_umweltbewusstsein);  // تمرير ملف الصوت
                                            intent.putExtra("description", getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein));
                                            intent.putExtra("share_text",
                                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                            "🔹 *Niveau:* B2\n" +
                                                            "📌 *Titel:* Nachhaltigkeit und Umweltbewusstsein\n\n" +
                                                            "📝 *Text:*\n" + getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein) + "\n\n" +
                                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                            startActivityForResult(intent, 2);                   }
                                    } else {
                                        // فتح النشاط في حال لم يكن هناك إعلان
                                        Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                        intent.putExtra("currentPage", "PageB2");

                                        intent.putExtra("title", "Nachhaltigkeit und Umweltbewusstsein");
                                        intent.putExtra("image", R.drawable.nachhaltigkeit_und_umweltbewusstsein); // تمرير الصورة
                                        intent.putExtra("audio", R.raw.nachhaltigkeit_und_umweltbewusstsein);  // تمرير ملف الصوت
                                        intent.putExtra("description", getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein));
                                        intent.putExtra("share_text",
                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                        "🔹 *Niveau:* B2\n" +
                                                        "📌 *Titel:* Nachhaltigkeit und Umweltbewusstsein\n\n" +
                                                        "📝 *Text:*\n" + getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein) + "\n\n" +
                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                        startActivityForResult(intent, 2);                  }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {
                                    Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                    intent.putExtra("currentPage", "PageB2");

                                    intent.putExtra("title", "Nachhaltigkeit und Umweltbewusstsein");
                                    intent.putExtra("image", R.drawable.nachhaltigkeit_und_umweltbewusstsein); // تمرير الصورة
                                    intent.putExtra("audio", R.raw.nachhaltigkeit_und_umweltbewusstsein);  // تمرير ملف الصوت
                                    intent.putExtra("description", getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein));
                                    intent.putExtra("share_text",
                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                    "🔹 *Niveau:* B2\n" +
                                                    "📌 *Titel:* Nachhaltigkeit und Umweltbewusstsein\n\n" +
                                                    "📝 *Text:*\n" + getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein) + "\n\n" +
                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                    startActivityForResult(intent, 2);            }
                            });
                        } else {
                            // إذا كانت الإعلانات غير مفعلة، افتح النشاط بشكل طبيعي بدون إعلانات
                            Intent intent = new Intent(PageB2.this, DetailActvity.class);
                            intent.putExtra("currentPage", "PageB2");

                            intent.putExtra("title", "Nachhaltigkeit und Umweltbewusstsein");
                            intent.putExtra("image", R.drawable.nachhaltigkeit_und_umweltbewusstsein); // تمرير الصورة
                            intent.putExtra("audio", R.raw.nachhaltigkeit_und_umweltbewusstsein);  // تمرير ملف الصوت
                            intent.putExtra("description", getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein));
                            intent.putExtra("share_text",
                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                            "🔹 *Niveau:* B2\n" +
                                            "📌 *Titel:* Nachhaltigkeit und Umweltbewusstsein\n\n" +
                                            "📝 *Text:*\n" + getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein) + "\n\n" +
                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                            startActivityForResult(intent, 2);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        // افتح النشاط إذا حدث خطأ
                        Intent intent = new Intent(PageB2.this, DetailActvity.class);
                        intent.putExtra("currentPage", "PageB2");

                        intent.putExtra("title", "Nachhaltigkeit und Umweltbewusstsein");
                        intent.putExtra("image", R.drawable.nachhaltigkeit_und_umweltbewusstsein); // تمرير الصورة
                        intent.putExtra("audio", R.raw.nachhaltigkeit_und_umweltbewusstsein);  // تمرير ملف الصوت
                        intent.putExtra("description", getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein));
                        intent.putExtra("share_text",
                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                        "🔹 *Niveau:* B2\n" +
                                        "📌 *Titel:* Nachhaltigkeit und Umweltbewusstsein\n\n" +
                                        "📝 *Text:*\n" + getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein) + "\n\n" +
                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                        startActivityForResult(intent, 2);


                    }
                });
            }
        });


        cardviewB2_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(PageB2.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageB2");

                    intent.putExtra("title", "Technologischer Wandel und seine Folgen");
                    intent.putExtra("image", R.drawable.technologische_entwicklungen_und_ihre_auswirkungen); // تمرير الصورة
                    intent.putExtra("audio", R.raw.technologische_entwicklungen_und_ihre_auswirkungen);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Technologische_Entwicklungen_und_ihre_Auswirkungen));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* B2\n" +
                                    "📌 *Titel:* Technologischer Wandel und seine Folgen\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Technologische_Entwicklungen_und_ihre_Auswirkungen) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deuts" +
                                    "ch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 3);
            }
        });


        cardviewB2_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(PageB2.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageB2");
                    intent.putExtra("title", "Gleichberechtigung und Chancen");
                    intent.putExtra("image", R.drawable.chancengleichheit_und_soziale_gerechtigkeit); // تمرير الصورة
                    intent.putExtra("audio", R.raw.chancengleichheit_und_soziale_gerechtigkeit);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Chancengleichheit_und_soziale_Gerechtigkeit));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* B2\n" +
                                    "📌 *Titel:* Gleichberechtigung und Chancen\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Chancengleichheit_und_soziale_Gerechtigkeit) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 4);
            }
        });


        cardviewB2_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnected()) {
                    // في حالة عدم وجود إنترنت، افتح النشاط مباشرةً
                    Intent intent = new Intent(PageB2.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageB2");

                    intent.putExtra("title", "KI: Chancen und Herausforderungen");
                    intent.putExtra("image", R.drawable.kunstliche_intelligenz_und_ihre_herausforderungen); // تمرير الصورة
                    intent.putExtra("audio", R.raw.kunstliche_intelligenz_und_ihre_herausforderungen);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* B2\n" +
                                    "📌 *Titel:* Herausforderungen der Künstlichen Intelligenz\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");

                    startActivityForResult(intent, 5);                     return;
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
                                        if (mRewardedAd4 != null) {
                                            mRewardedAd4.show(PageB2.this, new OnUserEarnedRewardListener() {
                                                @Override
                                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                                    loadAdMobRewardedAd4(); // إعادة تحميل إعلان AdMob
                                                }
                                            });

                                            mRewardedAd4.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                @Override
                                                public void onAdDismissedFullScreenContent() {
                                                    mRewardedAd4 = null;
                                                    loadAdMobRewardedAd4();
                                                    Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageB2");

                                                    intent.putExtra("title", "KI: Chancen und Herausforderungen");
                                                    intent.putExtra("image", R.drawable.kunstliche_intelligenz_und_ihre_herausforderungen); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.kunstliche_intelligenz_und_ihre_herausforderungen);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* B2\n" +
                                                                    "📌 *Titel:* Herausforderungen der Künstlichen Intelligenz\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");

                                                    startActivityForResult(intent, 5);
                                                }

                                                @Override
                                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                                    mRewardedAd4 = null;
                                                    Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageB2");

                                                    intent.putExtra("title", "KI: Chancen und Herausforderungen");
                                                    intent.putExtra("image", R.drawable.kunstliche_intelligenz_und_ihre_herausforderungen); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.kunstliche_intelligenz_und_ihre_herausforderungen);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* B2\n" +
                                                                    "📌 *Titel:* Herausforderungen der Künstlichen Intelligenz\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");

                                                    startActivityForResult(intent, 5);                            }
                                            });

                                        } else {
                                            Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                            intent.putExtra("currentPage", "PageB2");

                                            intent.putExtra("title", "KI: Chancen und Herausforderungen");
                                            intent.putExtra("image", R.drawable.kunstliche_intelligenz_und_ihre_herausforderungen); // تمرير الصورة
                                            intent.putExtra("audio", R.raw.kunstliche_intelligenz_und_ihre_herausforderungen);  // تمرير ملف الصوت
                                            intent.putExtra("description", getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen));
                                            intent.putExtra("share_text",
                                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                            "🔹 *Niveau:* B2\n" +
                                                            "📌 *Titel:* Herausforderungen der Künstlichen Intelligenz\n\n" +
                                                            "📝 *Text:*\n" + getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen) + "\n\n" +
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
                                                        StartAppSDK.init(PageB2.this, startAppAppId, false);
                                                        StartAppAd startAppAd = new StartAppAd(PageB2.this);

                                                        startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                                                            @Override
                                                            public void onReceiveAd(Ad ad) {
                                                                startAppAd.showAd();
                                                            }

                                                            @Override
                                                            public void onFailedToReceiveAd(Ad ad) {
                                                                Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                                                intent.putExtra("currentPage", "PageB2");

                                                                intent.putExtra("title", "KI: Chancen und Herausforderungen");
                                                                intent.putExtra("image", R.drawable.kunstliche_intelligenz_und_ihre_herausforderungen); // تمرير الصورة
                                                                intent.putExtra("audio", R.raw.kunstliche_intelligenz_und_ihre_herausforderungen);  // تمرير ملف الصوت
                                                                intent.putExtra("description", getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen));
                                                                intent.putExtra("share_text",
                                                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                                "🔹 *Niveau:* B2\n" +
                                                                                "📌 *Titel:* Herausforderungen der Künstlichen Intelligenz\n\n" +
                                                                                "📝 *Text:*\n" + getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen) + "\n\n" +
                                                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");

                                                                startActivityForResult(intent, 5);                                        }
                                                        });

                                                        startAppAd.setVideoListener(new VideoListener() {
                                                            @Override
                                                            public void onVideoCompleted() {
                                                                Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                                                intent.putExtra("currentPage", "PageB2");

                                                                intent.putExtra("title", "KI: Chancen und Herausforderungen");
                                                                intent.putExtra("image", R.drawable.kunstliche_intelligenz_und_ihre_herausforderungen); // تمرير الصورة
                                                                intent.putExtra("audio", R.raw.kunstliche_intelligenz_und_ihre_herausforderungen);  // تمرير ملف الصوت
                                                                intent.putExtra("description", getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen));
                                                                intent.putExtra("share_text",
                                                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                                "🔹 *Niveau:* B2\n" +
                                                                                "📌 *Titel:* Herausforderungen der Künstlichen Intelligenz\n\n" +
                                                                                "📝 *Text:*\n" + getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen) + "\n\n" +
                                                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");

                                                                startActivityForResult(intent, 5);                                          }
                                                        });

                                                    } else {
                                                        Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                                        intent.putExtra("currentPage", "PageB2");

                                                        intent.putExtra("title", "KI: Chancen und Herausforderungen");
                                                        intent.putExtra("image", R.drawable.kunstliche_intelligenz_und_ihre_herausforderungen); // تمرير الصورة
                                                        intent.putExtra("audio", R.raw.kunstliche_intelligenz_und_ihre_herausforderungen);  // تمرير ملف الصوت
                                                        intent.putExtra("description", getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen));
                                                        intent.putExtra("share_text",
                                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                        "🔹 *Niveau:* B2\n" +
                                                                        "📌 *Titel:* Herausforderungen der Künstlichen Intelligenz\n\n" +
                                                                        "📝 *Text:*\n" + getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen) + "\n\n" +
                                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");

                                                        startActivityForResult(intent, 5);                                    }
                                                }

                                                @Override
                                                public void onCancelled(FirebaseError firebaseError) {
                                                    Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageB2");

                                                    intent.putExtra("title", "KI: Chancen und Herausforderungen");
                                                    intent.putExtra("image", R.drawable.kunstliche_intelligenz_und_ihre_herausforderungen); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.kunstliche_intelligenz_und_ihre_herausforderungen);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* B2\n" +
                                                                    "📌 *Titel:* Herausforderungen der Künstlichen Intelligenz\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");

                                                    startActivityForResult(intent, 5);                               }
                                            });
                                        } else {
                                            // إذا لم يكن هناك اتصال بالإنترنت
                                            Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                            intent.putExtra("currentPage", "PageB2");

                                            intent.putExtra("title", "KI: Chancen und Herausforderungen");
                                            intent.putExtra("image", R.drawable.kunstliche_intelligenz_und_ihre_herausforderungen); // تمرير الصورة
                                            intent.putExtra("audio", R.raw.kunstliche_intelligenz_und_ihre_herausforderungen);  // تمرير ملف الصوت
                                            intent.putExtra("description", getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen));
                                            intent.putExtra("share_text",
                                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                            "🔹 *Niveau:* B2\n" +
                                                            "📌 *Titel:* Herausforderungen der Künstlichen Intelligenz\n\n" +
                                                            "📝 *Text:*\n" + getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen) + "\n\n" +
                                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");

                                            startActivityForResult(intent, 5);                     }
                                    } else {
                                        // فتح النشاط في حال لم يكن هناك إعلان
                                        Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                        intent.putExtra("currentPage", "PageB2");

                                        intent.putExtra("title", "KI: Chancen und Herausforderungen");
                                        intent.putExtra("image", R.drawable.kunstliche_intelligenz_und_ihre_herausforderungen); // تمرير الصورة
                                        intent.putExtra("audio", R.raw.kunstliche_intelligenz_und_ihre_herausforderungen);  // تمرير ملف الصوت
                                        intent.putExtra("description", getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen));
                                        intent.putExtra("share_text",
                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                        "🔹 *Niveau:* B2\n" +
                                                        "📌 *Titel:* Herausforderungen der Künstlichen Intelligenz\n\n" +
                                                        "📝 *Text:*\n" + getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen) + "\n\n" +
                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");

                                        startActivityForResult(intent, 5);                 }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {
                                    Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                    intent.putExtra("currentPage", "PageB2");

                                    intent.putExtra("title", "KI: Chancen und Herausforderungen");
                                    intent.putExtra("image", R.drawable.kunstliche_intelligenz_und_ihre_herausforderungen); // تمرير الصورة
                                    intent.putExtra("audio", R.raw.kunstliche_intelligenz_und_ihre_herausforderungen);  // تمرير ملف الصوت
                                    intent.putExtra("description", getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen));
                                    intent.putExtra("share_text",
                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                    "🔹 *Niveau:* B2\n" +
                                                    "📌 *Titel:* Herausforderungen der Künstlichen Intelligenz\n\n" +
                                                    "📝 *Text:*\n" + getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen) + "\n\n" +
                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");

                                    startActivityForResult(intent, 5);             }
                            });
                        } else {
                            // إذا كانت الإعلانات غير مفعلة، افتح النشاط بشكل طبيعي بدون إعلانات
                            Intent intent = new Intent(PageB2.this, DetailActvity.class);
                            intent.putExtra("currentPage", "PageB2");

                            intent.putExtra("title", "KI: Chancen und Herausforderungen");
                            intent.putExtra("image", R.drawable.kunstliche_intelligenz_und_ihre_herausforderungen); // تمرير الصورة
                            intent.putExtra("audio", R.raw.kunstliche_intelligenz_und_ihre_herausforderungen);  // تمرير ملف الصوت
                            intent.putExtra("description", getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen));
                            intent.putExtra("share_text",
                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                            "🔹 *Niveau:* B2\n" +
                                            "📌 *Titel:* Herausforderungen der Künstlichen Intelligenz\n\n" +
                                            "📝 *Text:*\n" + getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen) + "\n\n" +
                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");

                            startActivityForResult(intent, 5);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        // افتح النشاط إذا حدث خطأ
                        Intent intent = new Intent(PageB2.this, DetailActvity.class);
                        intent.putExtra("currentPage", "PageB2");

                        intent.putExtra("title", "KI: Chancen und Herausforderungen");
                        intent.putExtra("image", R.drawable.kunstliche_intelligenz_und_ihre_herausforderungen); // تمرير الصورة
                        intent.putExtra("audio", R.raw.kunstliche_intelligenz_und_ihre_herausforderungen);  // تمرير ملف الصوت
                        intent.putExtra("description", getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen));
                        intent.putExtra("share_text",
                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                        "🔹 *Niveau:* B2\n" +
                                        "📌 *Titel:* Herausforderungen der Künstlichen Intelligenz\n\n" +
                                        "📝 *Text:*\n" + getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen) + "\n\n" +
                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");

                        startActivityForResult(intent, 5);


                    }
                });
            }
        });


        cardviewB2_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(PageB2.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageB2");

                    intent.putExtra("title", "Ernährungsgewohnheiten und Gesundheit");
                    intent.putExtra("image", R.drawable.ernahrungsgewohnheiten_und_gesundheit); // تمرير الصورة
                    intent.putExtra("audio", R.raw.ernahrungsgewohnheiten_und_gesundheit);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Ernährungsgewohnheiten_und_Gesundheit));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* B2\n" +
                                    "📌 *Titel:* Ernährungsgewohnheiten und Gesundheit\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Ernährungsgewohnheiten_und_Gesundheit) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 6);
            }
        });

        cardviewB2_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(PageB2.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageB2");

                    intent.putExtra("title", "Bildung als Schlüssel zum Erfolg");
                    intent.putExtra("image", R.drawable.bildung_als_schlussel_zum_erfolg); // تمرير الصورة
                    intent.putExtra("audio", R.raw.bildung_als_schlussel_zum_erfolg);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Bildung_als_Schlüssel_zum_Erfolg));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* B2\n" +
                                    "📌 *Titel:* Bildung als Schlüssel zum Erfolg\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Bildung_als_Schlüssel_zum_Erfolg) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 7);
            }
        });


        cardviewB2_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnected()) {
                    // في حالة عدم وجود إنترنت، افتح النشاط مباشرةً
                    Intent intent = new Intent(PageB2.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageB2");

                    intent.putExtra("title", "Mobilität und Zukunft der Städte");
                    intent.putExtra("image", R.drawable.mobilitat_und_zukunft_der_stadte); // تمرير الصورة
                    intent.putExtra("audio", R.raw.mobilitat_und_zukunft_der_stadte);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Mobilität_und_Zukunft_der_Städte));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* B2\n" +
                                    "📌 *Titel:* Mobilität und Zukunft der Städte\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Mobilität_und_Zukunft_der_Städte) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 8);                      return;
                }
                Firebase firebase = new Firebase("https://german-4bc62-default-rtdb.firebaseio.com/show_ads");
                firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String adProvider = dataSnapshot.getValue(String.class);

                        if ("admob".equals(adProvider)) {
                            if (mRewardedAd4 != null) {
                                mRewardedAd4.show(PageB2.this, new OnUserEarnedRewardListener() {
                                    @Override
                                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                        loadAdMobRewardedAd4(); // إعادة تحميل إعلان AdMob
                                    }
                                });

                                mRewardedAd4.setFullScreenContentCallback(new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        mRewardedAd4 = null;
                                        loadAdMobRewardedAd4();
                                        Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                        intent.putExtra("currentPage", "PageB2");

                                        intent.putExtra("title", "Mobilität und Zukunft der Städte");
                                        intent.putExtra("image", R.drawable.mobilitat_und_zukunft_der_stadte); // تمرير الصورة
                                        intent.putExtra("audio", R.raw.mobilitat_und_zukunft_der_stadte);  // تمرير ملف الصوت
                                        intent.putExtra("description", getString(R.string.Mobilität_und_Zukunft_der_Städte));
                                        intent.putExtra("share_text",
                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                        "🔹 *Niveau:* B2\n" +
                                                        "📌 *Titel:* Mobilität und Zukunft der Städte\n\n" +
                                                        "📝 *Text:*\n" + getString(R.string.Mobilität_und_Zukunft_der_Städte) + "\n\n" +
                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                        startActivityForResult(intent, 8);
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        mRewardedAd4 = null;
                                        Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                        intent.putExtra("currentPage", "PageB2");

                                        intent.putExtra("title", "Mobilität und Zukunft der Städte");
                                        intent.putExtra("image", R.drawable.mobilitat_und_zukunft_der_stadte); // تمرير الصورة
                                        intent.putExtra("audio", R.raw.mobilitat_und_zukunft_der_stadte);  // تمرير ملف الصوت
                                        intent.putExtra("description", getString(R.string.Mobilität_und_Zukunft_der_Städte));
                                        intent.putExtra("share_text",
                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                        "🔹 *Niveau:* B2\n" +
                                                        "📌 *Titel:* Mobilität und Zukunft der Städte\n\n" +
                                                        "📝 *Text:*\n" + getString(R.string.Mobilität_und_Zukunft_der_Städte) + "\n\n" +
                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                        startActivityForResult(intent, 8);                            }
                                });

                            } else {
                                Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                intent.putExtra("currentPage", "PageB2");

                                intent.putExtra("title", "Mobilität und Zukunft der Städte");
                                intent.putExtra("image", R.drawable.mobilitat_und_zukunft_der_stadte); // تمرير الصورة
                                intent.putExtra("audio", R.raw.mobilitat_und_zukunft_der_stadte);  // تمرير ملف الصوت
                                intent.putExtra("description", getString(R.string.Mobilität_und_Zukunft_der_Städte));
                                intent.putExtra("share_text",
                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                "🔹 *Niveau:* B2\n" +
                                                "📌 *Titel:* Mobilität und Zukunft der Städte\n\n" +
                                                "📝 *Text:*\n" + getString(R.string.Mobilität_und_Zukunft_der_Städte) + "\n\n" +
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
                                            StartAppSDK.init(PageB2.this, startAppAppId, false);
                                            StartAppAd startAppAd = new StartAppAd(PageB2.this);

                                            startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
                                                @Override
                                                public void onReceiveAd(Ad ad) {
                                                    startAppAd.showAd();
                                                }

                                                @Override
                                                public void onFailedToReceiveAd(Ad ad) {
                                                    Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageB2");

                                                    intent.putExtra("title", "Mobilität und Zukunft der Städte");
                                                    intent.putExtra("image", R.drawable.mobilitat_und_zukunft_der_stadte); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.mobilitat_und_zukunft_der_stadte);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Mobilität_und_Zukunft_der_Städte));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* B2\n" +
                                                                    "📌 *Titel:* Mobilität und Zukunft der Städte\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Mobilität_und_Zukunft_der_Städte) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                    startActivityForResult(intent, 8);                                          }
                                            });

                                            startAppAd.setVideoListener(new VideoListener() {
                                                @Override
                                                public void onVideoCompleted() {
                                                    Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                                    intent.putExtra("currentPage", "PageB2");

                                                    intent.putExtra("title", "Mobilität und Zukunft der Städte");
                                                    intent.putExtra("image", R.drawable.mobilitat_und_zukunft_der_stadte); // تمرير الصورة
                                                    intent.putExtra("audio", R.raw.mobilitat_und_zukunft_der_stadte);  // تمرير ملف الصوت
                                                    intent.putExtra("description", getString(R.string.Mobilität_und_Zukunft_der_Städte));
                                                    intent.putExtra("share_text",
                                                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                                    "🔹 *Niveau:* B2\n" +
                                                                    "📌 *Titel:* Mobilität und Zukunft der Städte\n\n" +
                                                                    "📝 *Text:*\n" + getString(R.string.Mobilität_und_Zukunft_der_Städte) + "\n\n" +
                                                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                                    startActivityForResult(intent, 8);                                           }
                                            });

                                        } else {
                                            Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                            intent.putExtra("currentPage", "PageB2");

                                            intent.putExtra("title", "Mobilität und Zukunft der Städte");
                                            intent.putExtra("image", R.drawable.mobilitat_und_zukunft_der_stadte); // تمرير الصورة
                                            intent.putExtra("audio", R.raw.mobilitat_und_zukunft_der_stadte);  // تمرير ملف الصوت
                                            intent.putExtra("description", getString(R.string.Mobilität_und_Zukunft_der_Städte));
                                            intent.putExtra("share_text",
                                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                            "🔹 *Niveau:* B2\n" +
                                                            "📌 *Titel:* Mobilität und Zukunft der Städte\n\n" +
                                                            "📝 *Text:*\n" + getString(R.string.Mobilität_und_Zukunft_der_Städte) + "\n\n" +
                                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                            startActivityForResult(intent, 8);                                   }
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {
                                        Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                        intent.putExtra("currentPage", "PageB2");

                                        intent.putExtra("title", "Mobilität und Zukunft der Städte");
                                        intent.putExtra("image", R.drawable.mobilitat_und_zukunft_der_stadte); // تمرير الصورة
                                        intent.putExtra("audio", R.raw.mobilitat_und_zukunft_der_stadte);  // تمرير ملف الصوت
                                        intent.putExtra("description", getString(R.string.Mobilität_und_Zukunft_der_Städte));
                                        intent.putExtra("share_text",
                                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                        "🔹 *Niveau:* B2\n" +
                                                        "📌 *Titel:* Mobilität und Zukunft der Städte\n\n" +
                                                        "📝 *Text:*\n" + getString(R.string.Mobilität_und_Zukunft_der_Städte) + "\n\n" +
                                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                        startActivityForResult(intent, 8);                              }
                                });
                            } else {
                                // إذا لم يكن هناك اتصال بالإنترنت
                                Intent intent = new Intent(PageB2.this, DetailActvity.class);
                                intent.putExtra("currentPage", "PageB2");

                                intent.putExtra("title", "Mobilität und Zukunft der Städte");
                                intent.putExtra("image", R.drawable.mobilitat_und_zukunft_der_stadte); // تمرير الصورة
                                intent.putExtra("audio", R.raw.mobilitat_und_zukunft_der_stadte);  // تمرير ملف الصوت
                                intent.putExtra("description", getString(R.string.Mobilität_und_Zukunft_der_Städte));
                                intent.putExtra("share_text",
                                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                                "🔹 *Niveau:* B2\n" +
                                                "📌 *Titel:* Mobilität und Zukunft der Städte\n\n" +
                                                "📝 *Text:*\n" + getString(R.string.Mobilität_und_Zukunft_der_Städte) + "\n\n" +
                                                "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                                startActivityForResult(intent, 8);                  }
                        } else {
                            // فتح النشاط في حال لم يكن هناك إعلان
                            Intent intent = new Intent(PageB2.this, DetailActvity.class);
                            intent.putExtra("currentPage", "PageB2");

                            intent.putExtra("title", "Mobilität und Zukunft der Städte");
                            intent.putExtra("image", R.drawable.mobilitat_und_zukunft_der_stadte); // تمرير الصورة
                            intent.putExtra("audio", R.raw.mobilitat_und_zukunft_der_stadte);  // تمرير ملف الصوت
                            intent.putExtra("description", getString(R.string.Mobilität_und_Zukunft_der_Städte));
                            intent.putExtra("share_text",
                                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                            "🔹 *Niveau:* B2\n" +
                                            "📌 *Titel:* Mobilität und Zukunft der Städte\n\n" +
                                            "📝 *Text:*\n" + getString(R.string.Mobilität_und_Zukunft_der_Städte) + "\n\n" +
                                            "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                            startActivityForResult(intent, 8);                }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        Intent intent = new Intent(PageB2.this, DetailActvity.class);
                        intent.putExtra("currentPage", "PageB2");

                        intent.putExtra("title", "Mobilität und Zukunft der Städte");
                        intent.putExtra("image", R.drawable.mobilitat_und_zukunft_der_stadte); // تمرير الصورة
                        intent.putExtra("audio", R.raw.mobilitat_und_zukunft_der_stadte);  // تمرير ملف الصوت
                        intent.putExtra("description", getString(R.string.Mobilität_und_Zukunft_der_Städte));
                        intent.putExtra("share_text",
                                "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                        "🔹 *Niveau:* B2\n" +
                                        "📌 *Titel:* Mobilität und Zukunft der Städte\n\n" +
                                        "📝 *Text:*\n" + getString(R.string.Mobilität_und_Zukunft_der_Städte) + "\n\n" +
                                        "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                        startActivityForResult(intent, 8);            }
                });
            }
        });

        cardviewB2_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(PageB2.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageB2");

                    intent.putExtra("title", "Soziale Verantwortung von Unternehmen");
                    intent.putExtra("image", R.drawable.soziale_verantwortung_von_unternehmen); // تمرير الصورة
                    intent.putExtra("audio", R.raw.soziale_verantwortung_von_unternehmen);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Soziale_Verantwortung_von_Unternehmen));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* B2\n" +
                                    "📌 *Titel:* Soziale Verantwortung von Unternehmen\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Soziale_Verantwortung_von_Unternehmen) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 9);
            }
        });

        cardviewB2_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(PageB2.this, DetailActvity.class);
                    intent.putExtra("currentPage", "PageB2");

                    intent.putExtra("title", "Herausforderungen der Integration");
                    intent.putExtra("image", R.drawable.integration_von_migranten_in_die_gesellschaft); // تمرير الصورة
                    intent.putExtra("audio", R.raw.integration_von_migranten_in_die_gesellschaft);  // تمرير ملف الصوت
                    intent.putExtra("description", getString(R.string.Integration_von_Migranten_in_die_Gesellschaft));
                    intent.putExtra("share_text",
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* B2\n" +
                                    "📌 *Titel:* Herausforderungen der Integration\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Integration_von_Migranten_in_die_Gesellschaft) + "\n\n" +
                                    "🎧 Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪");
                    startActivityForResult(intent, 10);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            boolean isRead = data.getBooleanExtra("isRead3", false);

            if (isRead) {
                switch (requestCode) {
                    case 1: // الكارت الأول
                        statusIconsB2[0].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelperB2.updateCardStatus(1, true); // تحديث حالة القراءة في قاعدة البيانات
                        break;

                    case 2: // الكارت الثاني
                        statusIconsB2[1].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelperB2.updateCardStatus(2, true); // تحديث حالة القراءة في قاعدة البيانات
                        break;
                    case 3:
                        statusIconsB2[2].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelperB2.updateCardStatus(3,true);
                        break;
                    case 4:
                        statusIconsB2[3].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelperB2.updateCardStatus(4,true);
                        break;
                    case 5: // الكارت الأول
                        statusIconsB2[4].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelperB2.updateCardStatus(5, true); // تحديث حالة القراءة في قاعدة البيانات
                        break;

                    case 6: // الكارت الثاني
                        statusIconsB2[5].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelperB2.updateCardStatus(6, true); // تحديث حالة القراءة في قاعدة البيانات
                        break;
                    case 7:
                        statusIconsB2[6].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelperB2.updateCardStatus(7,true);
                        break;
                    case 8:
                        statusIconsB2[7].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelperB2.updateCardStatus(8,true);
                        break;
                    case 9:
                        statusIconsB2[8].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelperB2.updateCardStatus(9,true);
                        break;
                    case 10:
                        statusIconsB2[9].setImageResource(R.drawable.baseline_done_all_24);
                        dbHelperB2.updateCardStatus(10,true);
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
    private void loadAdMobRewardedAd4() {
        Firebase firebase = new Firebase("https://german-4bc62-default-rtdb.firebaseio.com/admob/rewarded_ad_unit_id");

        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String rewardedAdUnitId = dataSnapshot.getValue(String.class);

                if (rewardedAdUnitId != null && !rewardedAdUnitId.isEmpty()) {
                    AdRequest adRequest = new AdRequest.Builder().build();

                    RewardedAd.load(PageB2.this, rewardedAdUnitId, adRequest, new RewardedAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                            mRewardedAd4 = rewardedAd;
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            mRewardedAd4 = null;
                         //   Toast.makeText(PageB2.this, "فشل في تحميل إعلان AdMob", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                 //   Toast.makeText(PageB2.this, "معرف إعلان AdMob غير متوفر", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
              //  Toast.makeText(PageB2.this, "فشل في جلب معرف إعلان AdMob", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void fireAds4(String adsUrl) {
        Firebase firebase = new Firebase(adsUrl);
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data1 = dataSnapshot.getValue(String.class);
                AdView mAdView = new AdView(PageB2.this);
                mAdView.setAdUnitId(data1);
                banner4.addView(mAdView);
                mAdView.setAdSize(AdSize.BANNER);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    public void fireStartApp4() {
        Firebase firebase = new Firebase("https://german-4bc62-default-rtdb.firebaseio.com/startapp/banner_app_id");
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String appId = dataSnapshot.getValue(String.class);
                StartAppSDK.init(PageB2.this, appId, false);
                Banner startAppBanner = new Banner(PageB2.this);
                banner4.addView(startAppBanner);
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