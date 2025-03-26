package com.example.mylistview;

import android.graphics.Bitmap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String name;
    private Bitmap image;
    public int getStableId() {
        return name.hashCode();
    }
}