package com.example.baithimau;

import java.io.Serializable;

public class Contract implements Serializable {

    private Integer Id;
    private String Name;
    private String PhoneNumber;

    public Contract() {
    }

    public Contract(Integer id, String name, String phoneNumber) {
        Id = id;
        Name = name;
        PhoneNumber = phoneNumber;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return + Id +
                " - " + Name +
                " - " + PhoneNumber;
    }
}
