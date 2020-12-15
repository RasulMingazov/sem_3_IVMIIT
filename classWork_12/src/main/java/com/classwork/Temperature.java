package com.classwork;

import java.time.LocalDate;

public class Temperature {
    LocalDate date;
    Integer temp;

    public Temperature(LocalDate date, Integer temp) {
        this.date = date;
        this.temp = temp;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }
}
