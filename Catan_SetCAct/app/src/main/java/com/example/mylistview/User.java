package com.example.mylistview;

import android.graphics.Bitmap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private Bitmap image;
    // ✅ Generate a stable hash based on name to keep consistency
    public int getStableId() {
        return name.hashCode();
    }
}