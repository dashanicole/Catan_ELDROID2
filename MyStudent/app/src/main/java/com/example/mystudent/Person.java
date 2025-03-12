package com.example.mystudent;

import android.net.Uri;

public class Person {
    private int id;
    private Uri imageUri;
    private String name;

    public Person(int id, Uri imageUri, String name) {
        this.id = id;
        this.imageUri = imageUri;
        this.name = name;
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(Uri imageUri, String name) {
        this.imageUri = imageUri;
        this.name = name;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
