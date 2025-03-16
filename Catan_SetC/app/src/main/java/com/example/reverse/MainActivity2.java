package com.example.reverse;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {
    EditText txtName;
    ImageButton btnAdd, btnCancel;
    ImageView imageView;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        // Initialize views
        txtName = findViewById(R.id.editText1); // EditText for name
        btnAdd = findViewById(R.id.imageButton1); // Add button
        btnCancel = findViewById(R.id.imageButton2); // Cancel button
        imageView = findViewById(R.id.imageView); // ImageView for selected image

        // Request focus on the EditText
        txtName.requestFocus();

        // Set click listener for ImageView to pick an image
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, 100); // Request code 100 for image picker
            }
        });

        // Set click listener for Add button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("myname", name);
                intent.putExtra("image", imageUri != null ? imageUri.toString() : ""); // Pass image URI
                setResult(RESULT_OK, intent);
                finish(); // Close the activity
            }
        });

        // Set click listener for Cancel button
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the activity without saving
            }
        });

        // Handle window insets for edge-to-edge design
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            // Handle the selected image
            imageUri = data.getData();
            imageView.setImageURI(imageUri); // Set the selected image to ImageView
        }
    }
}