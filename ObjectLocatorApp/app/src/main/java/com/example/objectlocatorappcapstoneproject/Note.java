package com.example.objectlocatorappcapstoneproject;

import io.realm.RealmObject;

public class Note extends RealmObject {
    String text_name,text_location;
    public String getText_name() {
        return text_name;
    }

    public void setText_name(String text_name) {
        this.text_name = text_name;
    }

    public String getText_location() {
        return text_location;
    }

    public void setText_location(String text_location) {
        this.text_location = text_location;
    }
}
