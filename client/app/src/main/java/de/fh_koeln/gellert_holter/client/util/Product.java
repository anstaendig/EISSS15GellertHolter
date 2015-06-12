package de.fh_koeln.gellert_holter.client.util;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    public String _id;
    public String brand;
    public String type;
    public String description;
    public String be;
    public String ke;

    public Boolean selected;

    public Product() {
        this.selected = false;
    }

    public Product(Parcel in) {
        this._id = in.readString();
        this.brand = in.readString();
        this.type = in.readString();
        this.description = in.readString();
        this.be = in.readString();
        this.ke = in.readString();
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBe() {
        return this.be;
    }

    public void setBe(String be) {
        this.be = be;
    }

    public String getKe() {
        return this.ke;
    }

    public void setKe(String ke) {
        this.ke = ke;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(brand);
        dest.writeString(type);
        dest.writeString(description);
        dest.writeString(be);
        dest.writeString(ke);
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

}
