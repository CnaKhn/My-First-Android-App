package com.cnakhn.faradarscompletion.Weather;

import android.os.Parcel;
import android.os.Parcelable;

public class WeatherInfo implements Parcelable {
    private String cityName;
    private String cityDetail;
    private double cityTemp;
    private int weatherID;
    private long sunrise;
    private long sunset;

    public WeatherInfo() {}

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        cityName = cityName.substring(0, 1).toUpperCase() + cityName.substring(1);
        this.cityName = cityName;
    }

    public String getCityDetail() {
        return cityDetail;
    }

    public void setCityDetail(String cityDetail) {
        cityDetail = cityDetail.substring(0, 1).toUpperCase() + cityDetail.substring(1);
        this.cityDetail = cityDetail;
    }

    public double getCityTemp() {
        return cityTemp;
    }

    public void setCityTemp(double cityTemp) {
        this.cityTemp = cityTemp;
    }

    public int getWeatherID() {
        return weatherID;
    }

    public void setWeatherID(int weatherID) {
        this.weatherID = weatherID;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    protected WeatherInfo(Parcel in) {
        cityName = in.readString();
        cityDetail = in.readString();
        cityTemp = in.readDouble();
        weatherID = in.readInt();
        sunrise = in.readLong();
        sunset = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cityName);
        dest.writeString(cityDetail);
        dest.writeDouble(cityTemp);
        dest.writeInt(weatherID);
        dest.writeLong(sunrise);
        dest.writeLong(sunset);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<WeatherInfo> CREATOR = new Parcelable.Creator<WeatherInfo>() {
        @Override
        public WeatherInfo createFromParcel(Parcel in) {
            return new WeatherInfo(in);
        }

        @Override
        public WeatherInfo[] newArray(int size) {
            return new WeatherInfo[size];
        }
    };
}