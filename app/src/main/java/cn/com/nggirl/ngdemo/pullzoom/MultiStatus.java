package cn.com.nggirl.ngdemo.pullzoom;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class MultiStatus implements MultiItemEntity {
    private boolean isRetweet;
    private String text;
    private String userName;
    private String userAvatar;
    private String createdAt;

    private int itemType;

    public static final int ITEM_ITEM = 1;
    public static final int ITEM_HEADER = 0;
    public MultiStatus(int itemType) {
        this.itemType = itemType;
    }

    public MultiStatus(boolean isRetweet, String text, String userName, String userAvatar, String createdAt, int itemType) {
        this.isRetweet = isRetweet;
        this.text = text;
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.createdAt = createdAt;
        this.itemType = itemType;
    }

    public boolean isRetweet() {
        return isRetweet;
    }

    public void setRetweet(boolean retweet) {
        isRetweet = retweet;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Status{" +
                "isRetweet=" + isRetweet +
                ", text='" + text + '\'' +
                ", userName='" + userName + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
