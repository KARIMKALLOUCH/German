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


public class PageB2 extends AppCompatActivity {
    DatabaseHelperB2 dbHelperB2; // قاعدة البيانات

    ImageView back;
    TextView textname;
    private RecyclerView recyclerView;
    private List<CardItem> cardItems;

    private CardAdapter adapter;
    private ViewGroup banner4;
    private BannerAdsManager bannerAdsManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_page_b2);


        banner4 = findViewById(R.id.banner4);
        bannerAdsManger = new BannerAdsManager(this, banner4);
        bannerAdsManger.loadBannerAds();


        textname = findViewById(R.id.toolbar2_text);
        textname.setText("Niveau B2");

        back = findViewById(R.id.left3_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageB2.this, Home.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        // إنشاء كائن قاعدة البيانات
        dbHelperB2 = new DatabaseHelperB2(this);
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
            boolean isRead = dbHelperB2.getCardStatus(i);
            int icon = isRead ? R.drawable.baseline_done_all_24 : R.drawable.baseline_remove_done_24;

            if (i == 1) {
                cardItems.add(new CardItem(
                        "themaB2_1",
                        "Digitale Transformation im Arbeitsmarkt",
                        R.drawable.digitale_transformation_im_arbeitsmarkt,
                        R.raw.digitale_transformation_im_arbeitsmarkt,
                        getString(R.string.Digitale_Transformation_im_Arbeitsmarkt),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* B2\n" +
                                "📌 *Titel:* Digitale Transformation im Arbeitsmarkt\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Digitale_Transformation_im_Arbeitsmarkt) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                "📲 *Download the app now:*\n" +
                                "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        1,
                        i, // cardId
                        "PageB2" // currentPage
                ));
            } else if (i == 2) {
                cardItems.add(new CardItem(
                        "themaB2_2",
                        "Nachhaltigkeit und Umweltbewusstsein",
                        R.drawable.nachhaltigkeit_und_umweltbewusstsein,
                        R.raw.nachhaltigkeit_und_umweltbewusstsein,
                        getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* B2\n" +
                                "📌 *Titel:* Nachhaltigkeit und Umweltbewusstsein\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Nachhaltigkeit_und_Umweltbewusstsein) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                "📲 *Download the app now:*\n" +
                                "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        2,
                        i, // cardId
                        "PageB2" // currentPage
                ));
            }  else if (i == 3) {
                cardItems.add(new CardItem(
                        "themaB2_3",
                        "Technologischer Wandel und seine Folgen",
                        R.drawable.technologische_entwicklungen_und_ihre_auswirkungen,
                        R.raw.technologische_entwicklungen_und_ihre_auswirkungen,
                        getString(R.string.Technologische_Entwicklungen_und_ihre_Auswirkungen),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* B2\n" +
                                "📌 *Titel:* Technologischer Wandel und seine Folgen\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Technologische_Entwicklungen_und_ihre_Auswirkungen) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                "📲 *Download the app now:*\n" +
                                "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        3,
                        i,
                        "PageB2"
                ));
            }
            else if (i == 4) {
                cardItems.add(new CardItem(
                        "themaB2_4",
                        "Gleichberechtigung und Chancen",
                        R.drawable.chancengleichheit_und_soziale_gerechtigkeit,
                        R.raw.chancengleichheit_und_soziale_gerechtigkeit,
                        getString(R.string.Chancengleichheit_und_soziale_Gerechtigkeit),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* B2\n" +
                                "📌 *Titel:* Gleichberechtigung und Chancen\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Chancengleichheit_und_soziale_Gerechtigkeit) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                "📲 *Download the app now:*\n" +
                                "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        4,
                        i,
                        "PageB2"
                ));
            }
            else if (i == 5) {
                cardItems.add(new CardItem(
                        "themaB2_5",
                        "KI: Chancen und Herausforderungen",
                        R.drawable.kunstliche_intelligenz_und_ihre_herausforderungen,
                        R.raw.kunstliche_intelligenz_und_ihre_herausforderungen,
                        getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* B2\n" +
                                "📌 *Titel:* KI: Chancen und Herausforderungen\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Künstliche_Intelligenz_und_ihre_Herausforderungen) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                "📲 *Download the app now:*\n" +
                                "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        5,
                        i,
                        "PageB2"
                ));
            }
            else if (i == 6) {
                cardItems.add(new CardItem(
                        "themaB2_6",
                        "Ernährungsgewohnheiten und Gesundheit",
                        R.drawable.ernahrungsgewohnheiten_und_gesundheit,
                        R.raw.ernahrungsgewohnheiten_und_gesundheit,
                        getString(R.string.Ernährungsgewohnheiten_und_Gesundheit),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* B2\n" +
                                "📌 *Titel:* Ernährungsgewohnheiten und Gesundheit\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Ernährungsgewohnheiten_und_Gesundheit) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                "📲 *Download the app now:*\n" +
                                "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        6,
                        i,
                        "PageB2"
                ));
            }
            else if (i == 7) {
                cardItems.add(new CardItem(
                        "themaB2_7",
                        "Bildung als Schlüssel zum Erfolg",
                        R.drawable.bildung_als_schlussel_zum_erfolg,
                        R.raw.bildung_als_schlussel_zum_erfolg,
                        getString(R.string.Bildung_als_Schlüssel_zum_Erfolg),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* B2\n" +
                                "📌 *Titel:* Bildung als Schlüssel zum Erfolg\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Bildung_als_Schlüssel_zum_Erfolg) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                "📲 *Download the app now:*\n" +
                                "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        7,
                        i,
                        "PageB2"
                ));
            }
            else if (i == 8) {
                cardItems.add(new CardItem(
                        "themaB2_8",
                        "Mobilität und Zukunft der Städte",
                        R.drawable.mobilitat_und_zukunft_der_stadte,
                        R.raw.mobilitat_und_zukunft_der_stadte,
                        getString(R.string.Mobilität_und_Zukunft_der_Städte),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* B2\n" +
                                "📌 *Titel:* Mobilität und Zukunft der Städte\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Mobilität_und_Zukunft_der_Städte) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                "📲 *Download the app now:*\n" +
                                "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        8,
                        i,
                        "PageB2"
                ));
            }
            else if (i == 9) {
                cardItems.add(new CardItem(
                        "themaB2_9",
                        "Soziale Verantwortung von Unternehmen",
                        R.drawable.soziale_verantwortung_von_unternehmen,
                        R.raw.soziale_verantwortung_von_unternehmen,
                        getString(R.string.Soziale_Verantwortung_von_Unternehmen),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* B2\n" +
                                "📌 *Titel:* Soziale Verantwortung von Unternehmen\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Soziale_Verantwortung_von_Unternehmen) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                "📲 *Download the app now:*\n" +
                                "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        9,
                        i,
                        "PageB2"
                ));
            }
            else if (i == 10) {
                cardItems.add(new CardItem(
                        "themaB2_10",
                        "Herausforderungen der Integration",
                        R.drawable.integration_von_migranten_in_die_gesellschaft,
                        R.raw.integration_von_migranten_in_die_gesellschaft,
                        getString(R.string.Integration_von_Migranten_in_die_Gesellschaft),
                        "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                "🔹 *Niveau:* B2\n" +
                                "📌 *Titel:* Herausforderungen der Integration\n\n" +
                                "📝 *Text:*\n" + getString(R.string.Integration_von_Migranten_in_die_Gesellschaft) + "\n\n" +
                                "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                "📲 *Download the app now:*\n" +
                                "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                        icon,
                        10,
                        i,
                        "PageB2"
                ));
            }
        }

        return cardItems;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            boolean isRead = data.getBooleanExtra("isRead3", false);

            if (isRead) {
                // تحديث قاعدة البيانات
                dbHelperB2.updateCardStatus(requestCode, true);

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