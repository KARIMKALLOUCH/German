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


public class PageB1 extends AppCompatActivity {
    DatabaseHelperB1 dbHelperB1; // قاعدة البيانات

    ImageView back;
    TextView textname;
    private RecyclerView recyclerView;
    private List<CardItem> cardItems;

    private CardAdapter adapter;
    private ViewGroup banner3;
    private BannerAdsManager bannerAdsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_page_b1);

        banner3 = findViewById(R.id.banner3);
        bannerAdsManager = new BannerAdsManager(this, banner3);
        bannerAdsManager.loadBannerAds();



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
            boolean isRead = dbHelperB1.getCardStatus(i);
            int icon = isRead ? R.drawable.baseline_done_all_24 : R.drawable.baseline_remove_done_24;

            if (i == 1) {
                cardItems.add(new CardItem(
                        "themaB1_1",
                        "Freundschaft und Vertrauen",
                        R.drawable.freundschaft_und_vertrauen,
                        R.raw.freundschaft_und_vertrauen,
                        getString(R.string.Freundschaft_und_Vertrauen),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* B1\n" +
                                "📌 *Titel:* Freundschaft und Vertrauen\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Freundschaft_und_Vertrauen) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                "📲 *Download the app now:*\n" +
                                "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        1,
                        i, // cardId
                        "PageB1" // currentPage
                ));
            } else if (i == 2) {
                cardItems.add(new CardItem(
                        "themaB1_2",
                        "Meine erste Reise ins Ausland",
                        R.drawable.meine_erste_reise_ins_ausland,
                        R.raw.meine_erste_reise_ins_ausland,
                        getString(R.string.Meine_erste_Reise_ins_Ausland),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* B1\n" +
                                "📌 *Titel:* Meine erste Reise ins Ausland\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Meine_erste_Reise_ins_Ausland) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                "📲 *Download the app now:*\n" +
                                "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        2,
                        i, // cardId
                        "PageB1" // currentPage
                ));
            }  else if (i == 3) {
                cardItems.add(new CardItem(
                        "themaB1_3",
                        "Sport und ein gesunder Lebensstil",
                        R.drawable.sport_und_ein_gesunder_lebensstil,
                        R.raw.sport_und_ein_gesunder_lebensstil,
                        getString(R.string.Sport_und_gesunder_Lebensstil),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* B1\n" +
                                "📌 *Titel:* Sport und ein gesunder Lebensstil\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Sport_und_gesunder_Lebensstil) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                "📲 *Download the app now:*\n" +
                                "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        3,
                        i,
                        "PageB1"
                ));
            }
            else if (i == 4) {
                cardItems.add(new CardItem(
                        "themaB1_4",
                        "Der Umweltschutz und seine Bedeutung",
                        R.drawable.der_umweltschutz_und_seine_bedeutung,
                        R.raw.der_umweltschutz_und_seine_bedeutung,
                        getString(R.string.Der_Umweltschutz_und_seine_Bedeutung),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* B1\n" +
                                "📌 *Titel:* Der Umweltschutz und seine Bedeutung\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Der_Umweltschutz_und_seine_Bedeutung) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                "📲 *Download the app now:*\n" +
                                "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        4,
                        i,
                        "PageB1"
                ));
            }
            else if (i == 5) {
                cardItems.add(new CardItem(
                        "themaB1_5",
                        "Das Leben ohne moderne Technologie",
                        R.drawable.das_leben_ohne_moderne_technologie,
                        R.raw.das_leben_ohne_moderne_technologie,
                        getString(R.string.Das_Leben_ohne_moderne_Technologie),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* B1\n" +
                                "📌 *Titel:* Das Leben ohne moderne Technologie\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Das_Leben_ohne_moderne_Technologie) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                "📲 *Download the app now:*\n" +
                                "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        5,
                        i,
                        "PageB1"
                ));
            }
            else if (i == 6) {
                cardItems.add(new CardItem(
                        "themaB1_6",
                        "Wichtige Entscheidungen im Leben",
                        R.drawable.wichtige_entscheidungen_im_leben,
                        R.raw.wichtige_entscheidungen_im_leben,
                        getString(R.string.Wichtige_Entscheidungen_im_Leben),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* B1\n" +
                                "📌 *Titel:* Wichtige Entscheidungen im Leben\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Wichtige_Entscheidungen_im_Leben) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                "📲 *Download the app now:*\n" +
                                "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        6,
                        i,
                        "PageB1"
                ));
            }
            else if (i == 7) {
                cardItems.add(new CardItem(
                        "themaB1_7",
                        "Die ideale Schule",
                        R.drawable.die_ideale_schule,
                        R.raw.die_ideale_schule,
                        getString(R.string.Die_ideale_Schule),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* B1\n" +
                                "📌 *Titel:* Die ideale Schule\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Die_ideale_Schule) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                "📲 *Download the app now:*\n" +
                                "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        7,
                        i,
                        "PageB1"
                ));
            }
            else if (i == 8) {
                cardItems.add(new CardItem(
                        "themaB1_8",
                        "Stadt oder Land – Wo lebt es sich besser?",
                        R.drawable.leben_in_der_stadt_vs_leben_auf_dem_land,
                        R.raw.leben_in_der_stadt_vs_leben_auf_dem_land,
                        getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* B1\n" +
                                "📌 *Titel:* Stadt oder Land – Wo lebt es sich besser?\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Leben_in_der_Stadt_vs_Leben_auf_dem_Land) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                "📲 *Download the app now:*\n" +
                                "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        8,
                        i,
                        "PageB1"
                ));
            }
            else if (i == 9) {
                cardItems.add(new CardItem(
                        "themaB1_9",
                        "Erfolg und Motivation im Berufsleben",
                        R.drawable.erfolg_und_motivation_im_berufsleben,
                        R.raw.erfolg_und_motivation_im_berufsleben,
                        getString(R.string.Erfolg_und_Motivation_im_Berufsleben),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* B1\n" +
                                "📌 *Titel:* Erfolg und Motivation im Berufsleben\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Erfolg_und_Motivation_im_Berufsleben) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                "📲 *Download the app now:*\n" +
                                "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        9,
                        i,
                        "PageB1"
                ));
            }
            else if (i == 10) {
                cardItems.add(new CardItem(
                        "themaB1_10",
                        "Familie und ihre Rolle im Alltag",
                        R.drawable.familie_und_ihre_rolle_im_alltag,
                        R.raw.familie_und_ihre_rolle_im_alltag,
                        getString(R.string.Familie_und_Rolle_im_Alltag),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* B1\n" +
                                "📌 *Titel:* Familie und ihre Rolle im Alltag\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Familie_und_Rolle_im_Alltag) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                "📲 *Download the app now:*\n" +
                                "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        10,
                        i,
                        "PageB1"
                ));
            }
        }

        return cardItems;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            boolean isRead = data.getBooleanExtra("isRead2", false);

            if (isRead) {
                // تحديث قاعدة البيانات
                dbHelperB1.updateCardStatus(requestCode, true);

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