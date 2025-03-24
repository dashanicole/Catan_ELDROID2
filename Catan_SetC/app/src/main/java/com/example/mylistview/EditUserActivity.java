package com.example.mylistview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditUserActivity extends AppCompatActivity {
    private ImageView profileView;
    private EditText nameFld;
    private Bitmap selectedImageBitmap;
    private String originalName;
    private int userPosition;

    private final ActivityResultLauncher<Intent> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    try {
                        selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                                getContentResolver(), selectedImageUri);
                        profileView.setImageBitmap(selectedImageBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user);

        profileView = findViewById(R.id.profileView);
        nameFld = findViewById(R.id.nameFld);
        Button saveBtn = findViewById(R.id.saveBtn);
        Button cancelBtn = findViewById(R.id.cancelBtn);

        // Get user data from intent
        Intent intent = getIntent();
        if (intent != null) {
            originalName = intent.getStringExtra("name");
            byte[] byteArray = intent.getByteArrayExtra("image");
            userPosition = intent.getIntExtra("position", -1);

            if (originalName != null) {
                nameFld.setText(originalName);
            }
            if (byteArray != null) {
                selectedImageBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                profileView.setImageBitmap(selectedImageBitmap);
            }
        }

        profileView.setOnClickListener(v -> openImagePicker());
        saveBtn.setOnClickListener(v -> saveChanges());
        cancelBtn.setOnClickListener(v -> finish());
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(intent);
    }

    private void saveChanges() {
        String newName = nameFld.getText().toString().trim();

        if (newName.isEmpty() || selectedImageBitmap == null) {
            Toast.makeText(this, "Please enter a name and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        selectedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Intent intent = new Intent();
        intent.putExtra("name", newName);
        intent.putExtra("image", byteArray);
        intent.putExtra("position", userPosition);
        setResult(RESULT_OK, intent);
        finish();
    }
}
