package com.classwork;

import java.time.LocalDate;
import java.time.LocalTime;

public class DayTemp {
    LocalDate date;
    LocalTime time;
    Integer humidity;
    Double windSpeed, temp;

    public DayTemp(LocalDate date, LocalTime time, Double temp, Integer humidity, Double windSpeed) {
        this.date = date;
        this.time = time;
        this.temp = temp;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }
}
