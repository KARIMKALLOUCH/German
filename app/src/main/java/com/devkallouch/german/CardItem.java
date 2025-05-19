package com.devkallouch.german;

public class CardItem {
    private String id;
    private String title;
    private int imageRes;
    private int audioRes;
    private String description;
    private String shareText;
    private int statusIconRes;
    private int requestCode;
    private int cardId; // هذا ضروري

    private boolean isRead; // إذا محتاج تتبع حالة القراءة
    private String currentPage; // أضف هذا


    public CardItem(String id, String title, int imageRes, int audioRes,
                    String description, String shareText, int statusIconRes, int requestCode , int cardId ,String currentPage) {
        this.id = id;
        this.title = title;
        this.imageRes = imageRes;
        this.audioRes = audioRes;
        this.description = description;
        this.shareText = shareText;
        this.statusIconRes = statusIconRes;
        this.requestCode = requestCode;
        this.cardId = cardId;
        this.isRead = false;
        this.currentPage = currentPage; // تهيئة currentPage

    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public int getImageRes() { return imageRes; }
    public int getAudioRes() { return audioRes; }
    public String getDescription() { return description; }
    public String getShareText() { return shareText; }
    public int getStatusIconRes() { return statusIconRes; }
    public int getRequestCode(){return  requestCode;}
    // Getter and Setter for read
    public int getCardId() {
        return cardId;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isRead() {
        return isRead;
    }
    public String getCurrentPage() {
        return currentPage;
    }
}
