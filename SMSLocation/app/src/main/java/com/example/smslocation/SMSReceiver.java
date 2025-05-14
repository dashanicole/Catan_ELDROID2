package com.example.smslocation;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.SmsManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();

        Toast.makeText(context, "Receieved", Toast.LENGTH_SHORT).show();
        if (extras != null) {
            Object[] pdus = (Object[]) extras.get("pdus");
            for (Object pdu : pdus) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu);
                String message = sms.getMessageBody();
                String phoneNumber = sms.getOriginatingAddress(); // Get the sender's phone number

                Log.d("SMSReceiver", "Received SMS: " + message);

                // Auto-reply when "SEND_LOCATION" is received
                if (message.equalsIgnoreCase("SEND_LOCATION")) {
                    // ✅ Correct: if you receive "SEND_LOCATION", send your current location back
                    requestLocation(context, phoneNumber);
                } else {
                    // ✅ Try to parse location and open map
                    try {
                        String[] lines = message.split("\n");
                        if (lines.length >= 3 && lines[0].toLowerCase().contains("location")) {
                            double lat = Double.parseDouble(lines[1].split(":")[1].trim());
                            double lon = Double.parseDouble(lines[2].split(":")[1].trim());

                            Intent mapIntent = new Intent(context, MapActivity.class);
                            mapIntent.putExtra("latitude", lat);
                            mapIntent.putExtra("longitude", lon);
                            mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(mapIntent);
                        }
                    } catch (Exception e) {
                        Log.e("SMSReceiver", "Error parsing formatted location", e);
                    }
                }

            }
        }
    }

    private void requestLocation(Context context, String phoneNumber) {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("SMSReceiver", "No location permission");
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        double lat = location.getLatitude();
                        double lon = location.getLongitude();
                        sendLocation(context, phoneNumber, lat, lon);
                    } else {
                        Log.e("SMSReceiver", "Location is null");
                    }
                })
                .addOnFailureListener(e -> Log.e("SMSReceiver", "Failed to get location", e));
    }

    private void sendLocation(Context context, String phoneNumber, double lat, double lon) {
        // Send location as SMS
        String locationMessage = "Location \n" + "Latitude: " + lat + "\n" + "Longitude: " + lon;
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, locationMessage, null, null);
        Log.d("SMSReceiver", "Location sent to: " + phoneNumber);
    }
}
