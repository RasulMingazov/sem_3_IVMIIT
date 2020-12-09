package com.classwork;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class User {
    public StringProperty login;
    public StringProperty password;
    public StringProperty name;
    public StringProperty birthday;
    public StringProperty sex;
    public StringProperty city;
    public StringProperty address;
    public StringProperty color;
    public BooleanProperty flyChecker;
    public ListProperty<String> phones;
    public StringProperty image;


    public User() {
    }

    public User(String login, String password, String name, String birthday, String sex, String city, String address, String color, Boolean flyChecker, ObservableList<String> phones, String image) {
        this.login = new SimpleStringProperty(login);
        this.password = new SimpleStringProperty(password);
        this.name = new SimpleStringProperty(name);
        this.birthday = new SimpleStringProperty(birthday);
        this.sex = new SimpleStringProperty(sex);
        this.city = new SimpleStringProperty(city);
        this.address = new SimpleStringProperty(address);
        this.color = new SimpleStringProperty(color);
        this.flyChecker = new SimpleBooleanProperty(flyChecker);
        this.phones = new SimpleListProperty(phones);
        this.image = new SimpleStringProperty(image);
    }
    public User(String login, String password, String name, String birthday, String sex, String city, String address, String color, boolean flyChecker, ArrayList<String> phones, String image) {
        this.login = new SimpleStringProperty(login);
        this.password = new SimpleStringProperty(password);
        this.name = new SimpleStringProperty(name);
        this.birthday = new SimpleStringProperty(birthday);
        this.sex = new SimpleStringProperty(sex);
        this.city = new SimpleStringProperty(city);
        this.address = new SimpleStringProperty(address);
        this.color = new SimpleStringProperty(color);
        this.flyChecker = new SimpleBooleanProperty(flyChecker);
        ObservableList<String> phonesObsList = FXCollections.observableArrayList(phones);
        this.phones = new SimpleListProperty(phonesObsList);
        this.image = new SimpleStringProperty(image);
    }



    public StringProperty imageProperty() {
        return image;
    }

    public void setImage(String image) {
        this.image.set(image);
    }

    public String getColor() {
        return color.get();
    }

    public StringProperty colorProperty() {
        return color;
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public String getLogin() {
        return (String)this.login.get();
    }
    public String getImage() {
        return (String)this.image.get();
    }

    public StringProperty loginProperty() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getPassword() {
        return (String)this.password.get();
    }

    public StringProperty passwordProperty() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getName() {
        return (String)this.name.get();
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getBirthday() {
        return (String)this.birthday.get();
    }

    public StringProperty birthdayProperty() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday.set(birthday);
    }

    public String getSex() {
        return (String)this.sex.get();
    }

    public StringProperty sexProperty() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex.set(sex);
    }

    public String getCity() {
        return (String)this.city.get();
    }

    public StringProperty cityProperty() {
        return this.city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getAddress() {
        return (String)this.address.get();
    }

    public StringProperty addressProperty() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }


    public boolean isFlyChecker() {
        return this.flyChecker.get();
    }

    public BooleanProperty flyCheckerProperty() {
        return this.flyChecker;
    }

    public void setFlyChecker(boolean flyChecker) {
        this.flyChecker.set(flyChecker);
    }

    public ObservableList<String> getPhones() {
        return (ObservableList)this.phones.get();
    }

    public ListProperty<String> phonesProperty() {
        return this.phones;
    }

    public void setPhones(ObservableList<String> phones) {
        this.phones.set(phones);
    }

    @Override
    public String toString() {
        return "User{" +
                "login=" + getLogin() +
                ", password=" + getPassword() +
                ", name=" + getName() +
                ", birthday=" + getBirthday() +
                ", sex=" + getSex() +
                ", city=" + getCity() +
                ", address=" + getAddress() +
                ", color=" + getColor()+
                ", flyChecker=" + isFlyChecker() +
                ", phones=" + getPhones() +
                ", image=" + getImage() +
                '}';
    }
}

