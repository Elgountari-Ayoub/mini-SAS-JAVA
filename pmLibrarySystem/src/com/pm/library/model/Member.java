package com.pm.library.model;

public class Member {
    private int id;
    private String name;
    private String phoneNumber;
    private String address;

    public Member(int id, String name, String phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Member(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;

    }

    public Member(Member member) {
        this.id = member.id;
        this.name = member.name;
        this.phoneNumber = member.phoneNumber;
        this.address = member.address;
    }

    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString(){
        return "Name: "+ this.name+
                "\tPhone number: "+ this.phoneNumber +
                "\tAddress: "+ this.address
                ;
    }
}
