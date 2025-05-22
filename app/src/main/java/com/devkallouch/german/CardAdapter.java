package com.devkallouch.german;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.devkallouch.german.AdsManager.InterstitialAdManager;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.net.InetAddress;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<CardItem> cardItems;
    private Activity activity;
    private InterstitialAdManager adManager;  // ✅ مدير الإعلان
    private int clickCount = 0 ;
    private Integer maxClicksBeforeAd = null;  // سيتم جلبها من Firebase لاحقاً


    public CardAdapter(List<CardItem> cardItems, Activity activity) {
        this.cardItems = cardItems;
        this.activity = activity;
        this.adManager = new InterstitialAdManager(activity);  // ✅ تحميل الإعلان عند الإنشاء
        fetchMaxClicks();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardItem item = cardItems.get(position);

        holder.cardTitle.setText(item.getTitle());
        holder.cardImage.setImageResource(item.getImageRes());

        // تحديد الأيقونة حسب حالة القراءة
        if (item.isRead()) {
            holder.statusIcon.setImageResource(R.drawable.baseline_done_all_24);
        } else {
            holder.statusIcon.setImageResource(item.getStatusIconRes());
        }

        holder.itemView.setOnClickListener(v -> {
            new Thread(() -> {
                boolean isConnected = isConnectedToRealInternet();

                activity.runOnUiThread(() -> {
                    if (!isConnected) {
                        openDetailActivity(item);  // افتح الشاشة مباشرة إذا مافي إنترنت
                        return;
                    }

                    openDetailActivity(item);  // افتح الشاشة دائمًا

                    if (maxClicksBeforeAd != null) {
                        // ✅ زِد العداد وتحقق إن وصل إلى 2
                        clickCount++;
                        if (clickCount >= maxClicksBeforeAd) {
                            adManager.showInterstitialAd(() -> {
                                // لا شيء بعد الإغلاق
                            });
                            clickCount = 0; // إعادة العداد
                        }
                    }
                });
            }).start();
        });

    }

    // فتح النشاط المفصل
    private void openDetailActivity(CardItem item) {
        Intent intent = new Intent(activity, DetailActvity.class);
        intent.putExtra("currentPage", item.getCurrentPage());
        intent.putExtra("title", item.getTitle());
        intent.putExtra("image", item.getImageRes());
        intent.putExtra("audio", item.getAudioRes());
        intent.putExtra("description", item.getDescription());
        intent.putExtra("share_text", item.getShareText());
        activity.startActivityForResult(intent, item.getRequestCode());
    }

    @Override
    public int getItemCount() {
        return cardItems.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImage, statusIcon;
        TextView cardTitle;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.cardImage);
            statusIcon = itemView.findViewById(R.id.statusIcon);
            cardTitle = itemView.findViewById(R.id.cardTitle);
        }
    }
    private boolean isConnectedToRealInternet() {
        try {
            // نحاول الحصول على عنوان IP الخاص بـ google.com
            InetAddress ipAddr = InetAddress.getByName("google.com");

            // إذا نجحنا في الحصول عليه، فهذا يعني أن هناك إنترنت حقيقي
            return !ipAddr.equals("");
        } catch (Exception e) {
            // إذا حصل خطأ (يعني مافي إنترنت)
            return false;
        }
    }

    private void fetchMaxClicks(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("maxClicksBeforeAd");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Long value  =snapshot.getValue(Long.class);
                    if(value != null){
                        maxClicksBeforeAd  =value.intValue();
                    }
                }
            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {

            }
        });
    }
}
