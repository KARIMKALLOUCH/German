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


    public class PageA2 extends AppCompatActivity {
        DatabaseHelperA2 dbHelperA2; // قاعدة البيانات

        ImageView back;
        TextView textname;
        private RecyclerView recyclerView;
        private List<CardItem> cardItems;

        private CardAdapter adapter;
        private ViewGroup banner2;
        private BannerAdsManager bannerAdsManager;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

            setContentView(R.layout.activity_page_a2);

            banner2 = findViewById(R.id.banner2);
            bannerAdsManager = new BannerAdsManager(this,banner2);
            bannerAdsManager.loadBannerAds();



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
                boolean isRead = dbHelperA2.getCardStatus(i);
                int icon = isRead ? R.drawable.baseline_done_all_24 : R.drawable.baseline_remove_done_24;

                if (i == 1) {
                    cardItems.add(new CardItem(
                            "themaA2_1",
                            "Ein Wochenende auf dem Land",
                            R.drawable.ein_wochenende_auf_dem_land,
                            R.raw.ein_wochenende_auf_dem_land,
                            getString(R.string.Ein_Wochenende_auf_dem_Land),
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* A2\n" +
                                    "📌 *Titel:* Ein Wochenende auf dem Land\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Ein_Wochenende_auf_dem_Land) + "\n\n" +
                                    "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                    "📲 *Download the app now:*\n" +
                                    "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                            icon,
                            1,
                            i, // cardId
                            "PageA2" // currentPage
                    ));
                } else if (i == 2) {
                    cardItems.add(new CardItem(
                            "themaA2_2",
                            "Meine Hobbys",
                            R.drawable.meine_hobbys,
                            R.raw.meine_hobbys,
                            getString(R.string.Meine_Hobbys),
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* A2\n" +
                                    "📌 *Titel:* Meine Hobbys\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Meine_Hobbys) + "\n\n" +
                                   "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                    "📲 *Download the app now:*\n" +
                                    "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                            icon,
                            2,
                            i, // cardId
                            "PageA2" // currentPage
                    ));
                }  else if (i == 3) {
                    cardItems.add(new CardItem(
                            "themaA2_3",
                            "Urlaub am Meere",
                            R.drawable.urlaub_am_meer,
                            R.raw.urlaub_am_meer,
                            getString(R.string.Urlaub_am_Meer),
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* A2\n" +
                                    "📌 *Titel:* Urlaub am Meer\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Urlaub_am_Meer) + "\n\n" +
                                    "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                    "📲 *Download the app now:*\n" +
                                    "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                            icon,
                            3,
                            i,
                            "PageA2"
                    ));
                }
                else if (i == 4) {
                    cardItems.add(new CardItem(
                            "themaA2_4",
                            "Ein Besuch im Zoo",
                            R.drawable.ein_besuch_im_zoo,
                            R.raw.ein_besuch_im_zoo,
                            getString(R.string.Ein_Besuch_im_Zoo),
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* A2\n" +
                                    "📌 *Titel:* Ein Besuch im Zoo\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Ein_Besuch_im_Zoo) + "\n\n" +
                                    "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                    "📲 *Download the app now:*\n" +
                                    "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                            icon,
                            4,
                            i,
                            "PageA2"
                    ));
                }
                else if (i == 5) {
                    cardItems.add(new CardItem(
                            "themaA2_5",
                            "Die neue Wohnung",
                            R.drawable.die_neue_wohnung,
                            R.raw.die_neue_wohnung,
                            getString(R.string.Die_neue_Wohnung),
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* A2\n" +
                                    "📌 *Titel:* Die neue Wohnung\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Die_neue_Wohnung) + "\n\n" +
                                    "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                    "📲 *Download the app now:*\n" +
                                    "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                            icon,
                            5,
                            i,
                            "PageA2"
                    ));
                }
                else if (i == 6) {
                    cardItems.add(new CardItem(
                            "themaA2_6",
                            "Ein Tag in der Schule",
                            R.drawable.ein_tag_in_der_schule,
                            R.raw.ein_tag_in_der_schule,
                            getString(R.string.Ein_Tag_in_der_Schule),
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* A2\n" +
                                    "📌 *Titel:* Ein Tag in der Schule\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Ein_Tag_in_der_Schule) + "\n\n" +
                                    "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                    "📲 *Download the app now:*\n" +
                                    "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                            icon,
                            6,
                            i,
                            "PageA2"
                    ));
                }
                else if (i == 7) {
                    cardItems.add(new CardItem(
                            "themaA2_7",
                            "Das Lieblingsbuch",
                            R.drawable.das_lieblingsbuch,
                            R.raw.das_lieblingsbuch,
                            getString(R.string.Das_Lieblingsbuch),
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* A2\n" +
                                    "📌 *Titel:* Das Lieblingsbuch\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Das_Lieblingsbuch) + "\n\n" +
                                     "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                    "📲 *Download the app now:*\n" +
                                    "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                            icon,
                            7,
                            i,
                            "PageA2"
                    ));
                }
                else if (i == 8) {
                    cardItems.add(new CardItem(
                            "themaA2_8",
                            "Ein Tag ohne Handy",
                            R.drawable.ein_tag_ohne_handy,
                            R.raw.ein_tag_ohne_handy,
                            getString(R.string.Ein_Tag_ohne_Handy),
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* A2\n" +
                                    "📌 *Titel:* Ein Tag ohne Handy\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Ein_Tag_ohne_Handy) + "\n\n" +
                                    "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                    "📲 *Download the app now:*\n" +
                                    "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                            icon,
                            8,
                            i,
                            "PageA2"
                    ));
                }
                else if (i == 9) {
                    cardItems.add(new CardItem(
                            "themaA2_9",
                            "Der Stadtbummel",
                            R.drawable.der_stadtbummel,
                            R.raw.der_stadtbummel,
                            getString(R.string.Der_Stadtbummel),
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* A2\n" +
                                    "📌 *Titel:* Der Stadtbummel\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Der_Stadtbummel) + "\n\n" +
                                    "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                    "📲 *Download the app now:*\n" +
                                    "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                            icon,
                            9,
                            i,
                            "PageA2"
                    ));
                }
                else if (i == 10) {
                    cardItems.add(new CardItem(
                            "themaA2_10",
                            "Der Ausflug in den Wald",
                            R.drawable.der_ausflug_in_den_wald,
                            R.raw.der_ausflug_in_den_wald,
                            getString(R.string.Der_Ausflug_in_den_Wald),
                            "📚 Diese Inhalte stammen aus der App: *Lesen & Hören Deutsch*\n\n" +
                                    "🔹 *Niveau:* A2\n" +
                                    "📌 *Titel:* Der Ausflug in den Wald\n\n" +
                                    "📝 *Text:*\n" + getString(R.string.Der_Ausflug_in_den_Wald) + "\n\n" +
                                    "🎧  Höre und lese, um dein Deutsch zu verbessern! 🚀🇩🇪\n\n" +
                                    "📲 *Download the app now:*\n" +
                                    "https://play.google.com/store/apps/details?id=com.devkallouch.german",
                            icon,
                            10,
                            i,
                            "PageA2"
                    ));
                }
            }

            return cardItems;
        }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK && data != null) {
                boolean isRead = data.getBooleanExtra("isRead1", false);

                if (isRead) {
                    // تحديث قاعدة البيانات
                    dbHelperA2.updateCardStatus(requestCode, true);

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
