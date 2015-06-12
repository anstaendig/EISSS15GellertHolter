package util;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {
    public String author;
    public String body;
    public String date;

    public Comment(Parcel in) {
        this.author = in.readString();
        this.body = in.readString();
        this.date = in.readString();
    }

    public String getAuthor() {
        return this.author;
    }

    public String getBody() {
        return this.body;
    }

    public String getDate() {
        return this.date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(body);
        dest.writeString(date);
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
