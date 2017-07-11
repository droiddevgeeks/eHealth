package com.andesfit.android.reading;

import android.os.Parcel;

import java.util.Date;

public class BloodGlucoseMeterReading extends BaseReading {
    public static final Creator<BloodGlucoseMeterReading> CREATOR = new Creator<BloodGlucoseMeterReading>() {
        public BloodGlucoseMeterReading createFromParcel(Parcel in) {
            return new BloodGlucoseMeterReading(in);
        }

        public BloodGlucoseMeterReading[] newArray(int size) {
            return new BloodGlucoseMeterReading[size];
        }
    };
    public static final int UNIT_mg_dL = 0;
    public static final int UNIT_mmol_L = 1;
    public static final float GRAPH_MIN_Y_IN_PERCENTAGE = -1.0f;
    private float cholesterol;
    private Date createdAt;
    private float glucose;
    private int unit;

    public BloodGlucoseMeterReading() {
        this(0, GRAPH_MIN_Y_IN_PERCENTAGE, GRAPH_MIN_Y_IN_PERCENTAGE);
    }

    public BloodGlucoseMeterReading(int unit, float glucose, float cholesterol) {
        this.unit = unit;
        this.glucose = glucose;
        this.cholesterol = cholesterol;
        this.createdAt = new Date();
    }

    public BloodGlucoseMeterReading(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getUnit() {
        return this.unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public float getGlucose() {
        return this.glucose;
    }

    public void setGlucose(float glucose) {
        this.glucose = glucose;
    }

    public float getCholesterol() {
        return this.cholesterol;
    }

    public void setCholesterol(float cholesterol) {
        this.cholesterol = cholesterol;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel p, int flags) {
        super.writeToParcel(p, flags);
        p.writeInt(this.unit);
        p.writeFloat(this.glucose);
        p.writeFloat(this.cholesterol);
        p.writeSerializable(this.createdAt);
    }

    protected void readFromParcel(Parcel p) {
        super.readFromParcel(p);
        this.unit = p.readInt();
        this.glucose = p.readFloat();
        this.cholesterol = p.readFloat();
        this.createdAt = (Date) p.readSerializable();
    }
}
