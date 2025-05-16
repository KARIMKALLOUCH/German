package com.devkallouch.german;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.adsbase.StartAppSDK;

public class DetailActvity extends AppCompatActivity {
    ImageView back;
    ImageView share;
    ImageView play;
    ImageView imageView;
    MediaPlayer mediaPlayer;
    Boolean playing = true;
    SeekBar seekBar, seekbBarSpeed;
    Runnable runnable;
    Handler handler;
    RelativeLayout banner5;
    String showAds5;

    private Toolbar toolbar;
    private TextView centeredTitle;
    int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_actvity);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        banner5 = findViewById(R.id.banner5);

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
                            showAds5 = snapshot.getValue(String.class);
                            if (showAds5.equals("admob")) {
                                fireAds5("https://german-4bc62-default-rtdb.firebaseio.com/admob/banner_ad_unit_id");
                            } else if (showAds5.equals("startapp")) {
                                fireStartApp5();
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

        // ربط العناصر من واجهة المستخدم
        play = findViewById(R.id.btn_play);
        seekBar = findViewById(R.id.seekBar);
        handler = new Handler();
        back = findViewById(R.id.left1_icon);
        share = findViewById(R.id.right1_icon);


        TextView title = findViewById(R.id.toolbar1_text);
        TextView description = findViewById(R.id.description);
        imageView = findViewById(R.id.header_image);
        seekbBarSpeed = findViewById(R.id.speedSeekBar);
        // الحصول على البيانات المرسلة من النشاط السابق
        Intent intent = getIntent();
        String currentPage = intent.getStringExtra("currentPage");

        String receivedTitle = intent.getStringExtra("title");
        int receivedImage = intent.getIntExtra("image", R.drawable.loyy); // صورة افتراضية في حالة عدم وجود صورة
        int receivedAudio = intent.getIntExtra("audio", R.raw.das_haustier); // ملف صوتي افتراضي
        String receivedDescription = intent.getStringExtra("description");
        String receivedShareText = intent.getStringExtra("share_text");

        // تعيين البيانات إلى الواجهة
        title.setText(receivedTitle);
        description.setText(receivedDescription);
        imageView.setImageResource(receivedImage);

        //////////////////////////////

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        setupCollapsingToolbar();
        setupCenteredTitle(receivedTitle);
        setupAppBarListener();

        ////////////////////////////
        // إعداد زر الرجوع
      /*  back.setOnClickListener(v -> {
            Intent backIntent = new Intent(DetailActvity.this, PageA1.class);
            startActivity(backIntent);
            finish();
        });*/
        back.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            // تحديد المفتاح المناسب بناءً على الصفحة الحالية
         //   String isReadKey = "PageA1".equals(currentPage) ? "isRead" : "isRead1" ;
            String isReadKey;
            if("PageA1".equals(currentPage)){
                isReadKey= "isRead";
            }else if("PageA2".equals(currentPage)){
                isReadKey= "isRead1";
            }else if("PageB1".equals(currentPage)){
                isReadKey= "isRead2";
            }else if("PageB2".equals(currentPage)){
                isReadKey = "isRead3";
            }else {
                isReadKey = "defaulePage";
            }
// إرسال النتيجة إلى الصفحة السابقة
            Intent resultIntent = new Intent();
            resultIntent.putExtra(isReadKey, true); // أرسل القيمة المناسبة بناءً على الصفحة
            setResult(RESULT_OK, resultIntent); // تعيين النتيجة
            finish(); // إنهاء النشاط





        });

        // إعداد زر المشاركة
        share.setOnClickListener(v -> {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, receivedShareText);
            startActivity(Intent.createChooser(shareIntent, "شارك باستخدام"));
        });

        // إعداد مشغل الصوت
        play.setOnClickListener(view -> {
            if (playing) {
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), receivedAudio);
                    // إضافة مستمع لاكتشاف نهاية الصوت
                    mediaPlayer.setOnCompletionListener(mp -> {
                        play.setImageResource(R.drawable.baseline_play_circle_24); // تغيير الأيقونة إلى تشغيل
                        playing = true; // تحديث حالة التشغيل
                        mediaPlayer.seekTo(0); // إعادة المؤشر إلى البداية
                    });
                    mediaPlayer.start();
                    updateSeekBar();
                } else if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(currentPosition);
                    mediaPlayer.start();
                    updateSeekBar();
                }
                play.setImageResource(R.drawable.baseline_pause_circle_24);
                playing = false;
            } else {
                if (mediaPlayer != null) {
                    mediaPlayer.pause();
                    currentPosition = mediaPlayer.getCurrentPosition();
                    play.setImageResource(R.drawable.baseline_play_circle_24);
                    playing = true;
                }
            }
        });

        // إعداد شريط تقدم الصوت
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b && mediaPlayer != null) {
                    mediaPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekbBarSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // تحديد سرعة التشغيل (حيث أن القيمة بين 0 و 200)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        float speed = 0.5f + (progress / 100f); // تحويل النسبة إلى سرعة مناسبة بين 0.5x و 2x
                        mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(speed));
                    }
                } else {
                    // التعامل مع النسخ القديمة هنا
                    // يمكن تقديم سرعة ثابتة أو التعامل مع الصوت بطرق أخرى
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

    }


    public void updateSeekBar() {
        if (mediaPlayer != null) {
            int current = mediaPlayer.getCurrentPosition();
            seekBar.setProgress(current);
            int duration = mediaPlayer.getDuration();
            seekBar.setMax(duration);

            runnable = () -> updateSeekBar();
            handler.postDelayed(runnable, 1000);
        }
    }

    @Override
    public void onBackPressed() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        Intent intent = getIntent();
        String currentPage = intent.getStringExtra("currentPage"); // استرجاع القيمة هنا أيضًا
// تحديد المفتاح المناسب بناءً على الصفحة الحالية
    //    String isReadKey = "PageA1".equals(currentPage) ? "isRead" : "isRead1";
        String isReadKey;
        if("PageA1".equals(currentPage)){
            isReadKey= "isRead";
        }else if("PageA2".equals(currentPage)){
            isReadKey= "isRead1";
        }else if("PageB1".equals(currentPage)){
            isReadKey= "isRead2";
        }else if("PageB2".equals(currentPage)){
            isReadKey = "isRead3";
        }else {
            isReadKey = "defaulePage";
        }
// إرسال النتيجة إلى الصفحة السابقة
        Intent resultIntent = new Intent();
        resultIntent.putExtra(isReadKey, true); // أرسل القيمة المناسبة بناءً على الصفحة
        setResult(RESULT_OK, resultIntent); // تعيين النتيجة
        finish(); // إنهاء النشاط

    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            currentPosition = mediaPlayer.getCurrentPosition();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null && !playing) {
            mediaPlayer.seekTo(currentPosition);
            mediaPlayer.start();
            updateSeekBar();
        }
    }
    public void fireAds5(String adsUrl) {
        Firebase firebase = new Firebase(adsUrl);
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data1 = dataSnapshot.getValue(String.class);
                AdView mAdView = new AdView(DetailActvity.this);
                mAdView.setAdUnitId(data1);
                banner5.addView(mAdView);
                mAdView.setAdSize(AdSize.BANNER);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    public void fireStartApp5() {
        Firebase firebase = new Firebase("https://german-4bc62-default-rtdb.firebaseio.com/startapp/banner_app_id");
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String appId = dataSnapshot.getValue(String.class);
                StartAppSDK.setTestAdsEnabled(true);

                StartAppSDK.init(DetailActvity.this, appId, false);
                Banner startAppBanner = new Banner(DetailActvity.this);
                banner5.addView(startAppBanner);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }
    private void setupCollapsingToolbar() {
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("");
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
    }

    private void setupCenteredTitle(String titleText) {
        centeredTitle = new TextView(this);
        centeredTitle.setText(titleText);
        centeredTitle.setTextSize(18);
        centeredTitle.setTextColor(getResources().getColor(android.R.color.white));
        centeredTitle.setGravity(Gravity.CENTER);
        centeredTitle.setPadding(16, 16, 54, 16);
        centeredTitle.setVisibility(View.GONE);

        Typeface customFont = ResourcesCompat.getFont(this, R.font.anton);
        centeredTitle.setTypeface(customFont);



        int heightInPx = (int) (50 * getResources().getDisplayMetrics().density);

        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(
                Toolbar.LayoutParams.MATCH_PARENT,
                heightInPx
        );
        layoutParams.gravity = Gravity.CENTER;
        centeredTitle.setLayoutParams(layoutParams);

        toolbar.addView(centeredTitle);
    }

    private void setupAppBarListener() {
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isTitleVisible = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }

                if (scrollRange + verticalOffset == 0) {
                    centeredTitle.setVisibility(View.VISIBLE);
                    toolbar.setBackgroundColor(Color.parseColor("#b5734c"));
                    isTitleVisible = true;
                } else if (isTitleVisible) {
                    centeredTitle.setVisibility(View.GONE);
                    toolbar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    isTitleVisible = false;
                }
            }
        });
    }

}
