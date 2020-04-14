package com.raids.gallery.model.database.datamodel;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Hit implements Parcelable {

    @PrimaryKey
    private int id;
    private String type;
    private String previewURL;
    private String largeImageURL;
    private int previewHeight;
    private int previewWidth;
    private int imageWidth;
    private int imageHeight;

    public Hit(int id, String type, String previewURL, String largeImageURL, int imageWidth, int imageHeight) {
        this.id = id;
        this.type = type;
        this.previewURL = previewURL;
        this.largeImageURL = largeImageURL;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    protected Hit(Parcel in) {
        id = in.readInt();
        type = in.readString();
        previewURL = in.readString();
        largeImageURL = in.readString();
        imageWidth = in.readInt();
        imageHeight = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(type);
        dest.writeString(previewURL);
        dest.writeString(largeImageURL);
        dest.writeInt(imageWidth);
        dest.writeInt(imageHeight);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Hit> CREATOR = new Creator<Hit>() {
        @Override
        public Hit createFromParcel(Parcel in) {
            return new Hit(in);
        }

        @Override
        public Hit[] newArray(int size) {
            return new Hit[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public void setLargeImageURL(String largeImageURL) {
        this.largeImageURL = largeImageURL;
    }

    public int getPreviewHeight() {
        return previewHeight;
    }

    public void setPreviewHeight(int previewHeight) {
        this.previewHeight = previewHeight;
    }

    public int getPreviewWidth() {
        return previewWidth;
    }

    public void setPreviewWidth(int previewWidth) {
        this.previewWidth = previewWidth;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hit hit = (Hit) o;
        return id == hit.id &&
                imageWidth == hit.imageWidth &&
                imageHeight == hit.imageHeight &&
                Objects.equals(type, hit.type) &&
                previewURL.equals(hit.previewURL) &&
                largeImageURL.equals(hit.largeImageURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, previewURL, largeImageURL, imageWidth, imageHeight);
    }

    @NonNull
    @Override
    public String toString() {
        return "Hit{" +
                "id=" + id +
                '}';
    }

    /*
      "id":3063284,
            "pageURL":"https://pixabay.com/photos/rose-flower-petal-floral-noble-3063284/",
            "type":"photo",
            "tags":"rose, flower, petal",
            "previewURL":"https://cdn.pixabay.com/photo/2018/01/05/16/24/rose-3063284_150.jpg",
            "previewWidth":150,
            "previewHeight":99,
            "webformatURL":"https://pixabay.com/get/55e0d340485aa814f6da8c7dda79367b123fdeec5a536c4870277fdd944fcd58bf_640.jpg",
            "webformatWidth":640,
            "webformatHeight":426,
            "largeImageURL":"https://pixabay.com/get/55e0d340485aa814f6da8c7dda79367b123fdeec5a536c4870277fdd944fcd58bf_1280.jpg",
            "imageWidth":6000,
            "imageHeight":4000,
            "imageSize":3574625,
            "views":712760,
            "downloads":447368,
            "favorites":957,
            "likes":1106,
            "comments":246,
            "user_id":1564471,
            "user":"annca",
            "userImageURL":"https://cdn.pixabay.com/user/2015/11/27/06-58-54-609_250x250.jpg"
     */
}
