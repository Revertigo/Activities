package com.example.activities.data.rtdb.activity;

import java.util.Date;

public class Activity {
    private static int id_counter = 0;//For autoincrement of id's

    private long id;//Very long id number
    private String name;//Activity name
    private String Type;
    private Address addr;//Activity address
    private String difficulty;
    private boolean single_group;//True for Group, False for single
    private Gender gender;
    private String description;
    private Date date;
    private String time;//Format: hh:mm

    private static class Address
    {
        private String city_set;//City or settlement
        private String street;
        private int apartment_number;//zero for none

        public Address(String city_set, String street, int apartment_number) {
            this.city_set = city_set;
            this.street = street;
            this.apartment_number = apartment_number;
        }
    }

    private enum Gender
    {
        FEMALE, MALE, BOTH
    }


}
