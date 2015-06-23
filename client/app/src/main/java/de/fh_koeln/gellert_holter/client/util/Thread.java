package de.fh_koeln.gellert_holter.client.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Thread implements Parcelable {
    public String _id;
    public String author;
    public String date;
    public String body;
    public List<String> topics;
    public List<Comment> comments;

    public Thread() {
        topics = new ArrayList<>();
        comments = new ArrayList<>();
    }

    public Thread(Parcel in) {
        this();
        this._id = in.readString();
        this.author = in.readString();
        this.date = in.readString();
        this.body = in.readString();
        in.readStringList(topics);
        in.readTypedList(comments, Comment.CREATOR);
    }

    public String getId() {
        return this._id;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getDate() {
        return this.date;
    }

    public String getBody() {
        return this.body;
    }

    public List<String> getTopics() {
        return this.topics;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(author);
        dest.writeString(date);
        dest.writeString(body);
        dest.writeStringList(topics);
        dest.writeTypedList(comments);
    }

    public static final Parcelable.Creator<Thread> CREATOR = new Parcelable.Creator<Thread>() {
        public Thread createFromParcel(Parcel source) {
            return new Thread(source);
        }

        public Thread[] newArray(int size) {
            return new Thread[size];
        }
    };
}
