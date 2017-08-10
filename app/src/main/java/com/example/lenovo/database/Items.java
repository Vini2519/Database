package com.example.lenovo.database;

/**
 * Created by lenovo on 21-07-2017.
 */

public class Items {

    String id;
    String name;
    String phone;
    String cusines;
    String stallno;
    String rate;

    public String getPhone() {
        return phone;
    }

    public String getCusines() {
        return cusines;
    }

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getStallno() {
        return stallno;
    }

    public String getRate() {
        return rate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCusines(String cusines) {
        this.cusines = cusines;
    }

    public void setStallno(String stallno) {
        this.stallno = stallno;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Items() {

        this.name = name;
        this.stallno = stallno;
        this.phone = phone;
        this.cusines = cusines;
        this.id = id;
        this.rate = rate;

    }
}
