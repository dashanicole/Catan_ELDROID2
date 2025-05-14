package com.example.smslocation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    EditText phoneInput;
    Button sendButton, mapsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.READ_SMS,
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 1);
        }

        // Ask user to make this app the default SMS app (Android 4.4+)
//        if (!Telephony.Sms.getDefaultSmsPackage(this).equals(getPackageName())) {
//            Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
//            intent.putExtra(Telephony.Sms.Intents. EXTRA_PACKAGE_NAME, getPackageName());
//            startActivity(intent);
//        } else {
//            Toast.makeText(this, "This app is already the default SMS app.", Toast.LENGTH_SHORT).show();
//        }

        phoneInput = findViewById(R.id.phone_input);
        sendButton = findViewById(R.id.send_button);
        mapsButton = findViewById(R.id.maps_button);

        sendButton.setOnClickListener(v -> {
            String number = phoneInput.getText().toString().trim();
            if (!number.isEmpty()) {
                // Check if SEND_SMS permission is granted
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
                } else {
                    // Send "SEND_LOCATION" command to Phone B
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, "SEND_LOCATION", null, null);
                    Toast.makeText(this, "SMS Sent", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mapsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            intent.putExtra("latitude", 10.2970976);
            intent.putExtra("longitude", 123.896712);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
}
