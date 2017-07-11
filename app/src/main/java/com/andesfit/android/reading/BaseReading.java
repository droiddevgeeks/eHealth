package com.andesfit.android.reading;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

public abstract class BaseReading implements Parcelable {
    private String deviceAddress;
    private String deviceManufacturer;
    private String deviceName;
    private int googleFitDeviceType;

    public final void setDeviceInfo(@Nullable BluetoothDevice device) {
        if (device != null) {
            this.deviceName = device.getName();
            this.deviceAddress = device.getAddress();
        }
    }

    @Nullable
    public final String getDeviceManufacturer() {
        return this.deviceManufacturer;
    }

    public final void setDeviceManufacturer(@Nullable String deviceManufacturer) {
        this.deviceManufacturer = deviceManufacturer;
    }

    @Nullable
    public final String getDeviceName() {
        return this.deviceName;
    }

    @Nullable
    public final String getDeviceAddress() {
        return this.deviceAddress;
    }

    public final int getGoogleFitDeviceType() {
        return this.googleFitDeviceType;
    }

    public final void setGoogleFitDeviceType(int googleFitDeviceType) {
        this.googleFitDeviceType = googleFitDeviceType;
    }

    public void writeToParcel(Parcel p, int flags) {
        p.writeString(this.deviceManufacturer);
        p.writeString(this.deviceName);
        p.writeString(this.deviceAddress);
        p.writeInt(this.googleFitDeviceType);
    }

    protected void readFromParcel(Parcel p) {
        this.deviceManufacturer = p.readString();
        this.deviceName = p.readString();
        this.deviceAddress = p.readString();
        this.googleFitDeviceType = p.readInt();
    }
}
