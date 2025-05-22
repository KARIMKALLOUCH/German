package com.devkallouch.german;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.devkallouch.german.AdsManager.BannerAdsManager;
import java.util.ArrayList;
import java.util.List;

public class PageA1 extends AppCompatActivity {
    DatabaseHelper dbHelper; // قاعدة البيانات
    ImageView back;
    TextView textname;

    private RecyclerView recyclerView;
    private List<CardItem> cardItems;

    private CardAdapter adapter;
    private ViewGroup banner1;  // مثلا LinearLayout أو FrameLayout حاوي مكان الإعلان
    private BannerAdsManager bannerAdsManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);


        setContentView(R.layout.activity_page_a1);
        /////////////////banner ///////////
        banner1 = findViewById(R.id.banner1);

        bannerAdsManager = new BannerAdsManager(this, banner1);
        bannerAdsManager.loadBannerAds();

        /////////////////////
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


        // تهيئة RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));


        // إعداد قائمة العناصر
         cardItems = loadCardItem();
        adapter = new CardAdapter(cardItems, this);
        recyclerView.setAdapter(adapter);
        ///////////


    }
    private List<CardItem> loadCardItem() {
        List<CardItem> cardItems = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            boolean isRead = dbHelper.getCardStatus(i);
            int icon = isRead ? R.drawable.baseline_done_all_24 : R.drawable.baseline_remove_done_24;

            if (i == 1) {
                cardItems.add(new CardItem(
                        "themaA1_1",
                        "Mein Tag",
                        R.drawable.mein_tag,
                        R.raw.mein_tag,
                        getString(R.string.Mein_Tag),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* A1\n" +
                                "📌 *Titel:* Mein Tag\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Mein_Tag) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                "📲 *Download the app now:*\n" +
                                "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        1,
                        i, // cardId
                        "PageA1" // currentPage
                ));
            } else if (i == 2) {
                cardItems.add(new CardItem(
                        "themaA1_2",
                        "Einkaufen im Supermarkt",
                        R.drawable.einkaufen_im_supermarkt,
                        R.raw.einkaufen_im_supermarkt,
                        getString(R.string.Einkaufen_im_Supermarkt),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* A1\n" +
                                "📌 *Titel:* Einkaufen im Supermarkt\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Einkaufen_im_Supermarkt) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                    "📲 *Download the app now:*\n" +
                                    "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        2,
                        i, // cardId
                        "PageA1" // currentPage
                ));
            }  else if (i == 3) {
            cardItems.add(new CardItem(
                    "themaA1_3",
                    "das wetter heute",
                    R.drawable.das_wetter_heute,
                    R.raw.das_wetter_heute,
                    getString(R.string.Das_Wetter_heute),
                    "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                            "🔹 *Niveau:* A1\n" +
                            "📌 *Titel:* Das Wetter heute\n\n" +
                            "📝 *Text:*\n" + getString(R.string.Das_Wetter_heute) + "\n\n" +
                            "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                            "📲 *Download the app now:*\n" +
                            "https://play.google.com/store/apps/details?id=com.devkallouch.german",                    icon,
                    3,
                    i,
                    "PageA1"
            ));
        }
            else if (i == 4) {
                cardItems.add(new CardItem(
                        "themaA1_4",
                        "Mein Lieblingsessen",
                        R.drawable.mein_lieblingsessen,
                        R.raw.mein_lieblingsessen,
                        getString(R.string.Mein_Lieblingsessen),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* A1\n" +
                                "📌 *Titel:* Mein Lieblingsessen\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Mein_Lieblingsessen) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                    "📲 *Download the app now:*\n" +
                                    "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        4,
                        i,
                        "PageA1"
                ));
            }
            else if (i == 5) {
                cardItems.add(new CardItem(
                        "themaA1_5",
                        "Im Park",
                        R.drawable.im_park,
                        R.raw.im_park,
                        getString(R.string.Im_Park),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* A1\n" +
                                "📌 *Titel:* Im Park\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Im_Park) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                    "📲 *Download the app now:*\n" +
                                    "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        5,
                        i,
                        "PageA1"
                ));
            }
            else if (i == 6) {
                cardItems.add(new CardItem(
                        "themaA1_6",
                        "Meine Familie",
                        R.drawable.meine_familie,
                        R.raw.meine_familie,
                        getString(R.string.Meine_Familie),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* A1\n" +
                                "📌 *Titel:* Meine Familie\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Meine_Familie) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                    "📲 *Download the app now:*\n" +
                                    "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        6,
                        i,
                        "PageA1"
                ));
            }
            else if (i == 7) {
                cardItems.add(new CardItem(
                        "themaA1_7",
                        "Mein Haus",
                        R.drawable.mein_haus,
                        R.raw.mein_haus,
                        getString(R.string.Mein_Haus),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* A1\n" +
                                "📌 *Titel:* Mein Haus\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Mein_Haus) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                    "📲 *Download the app now:*\n" +
                                    "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        7,
                        i,
                        "PageA1"
                ));
            }
            else if (i == 8) {
                cardItems.add(new CardItem(
                        "themaA1_8",
                        "Das Haustier",
                        R.drawable.das_haustier,
                        R.raw.das_haustier,
                        getString(R.string.Das_Haustier),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* A1\n" +
                                "📌 *Titel:* Das Haustier\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Das_Haustier) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                    "📲 *Download the app now:*\n" +
                                    "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        8,
                        i,
                        "PageA1"
                ));
            }
            else if (i == 9) {
                cardItems.add(new CardItem(
                        "themaA1_9",
                        "Im Restaurant",
                        R.drawable.im_restaurant,
                        R.raw.im_restaurant,
                        getString(R.string.Im_Restaurant),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* A1\n" +
                                "📌 *Titel:* Im Restaurant\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Im_Restaurant) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                    "📲 *Download the app now:*\n" +
                                    "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        9,
                        i,
                        "PageA1"
                ));
            }
            else if (i == 10) {
                cardItems.add(new CardItem(
                        "themaA1_10",
                        "Der neue Freund",
                        R.drawable.der_neue_freund,
                        R.raw.der_neue_freund,
                        getString(R.string.Der_neue_Freund),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* A1\n" +
                                "📌 *Titel:* Der neue Freund\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Der_neue_Freund) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                    "📲 *Download the app now:*\n" +
                                    "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        10,
                        i,
                        "PageA1"
                ));
            }
        }

        return cardItems;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            boolean isRead = data.getBooleanExtra("isRead", false);

            if (isRead) {
                // تحديث قاعدة البيانات
                dbHelper.updateCardStatus(requestCode, true);

                // تحديث حالة القراءة في CardItem
                for (int i = 0; i < cardItems.size(); i++) {
                    CardItem item = cardItems.get(i);
                    if (item.getCardId() == requestCode) {
                        item.setRead(true); // تحتاج تضيف هذا الميثود في CardItem
                        break;
                    }
                }
                adapter.notifyDataSetChanged();

            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }






}