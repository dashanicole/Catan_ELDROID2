package com.example.smslocation;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MapActivity extends AppCompatActivity {

    WebView mapWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapWebView = findViewById(R.id.mapWebView);
        mapWebView.getSettings().setJavaScriptEnabled(true);
        mapWebView.setWebViewClient(new WebViewClient());

        double lat = getIntent().getDoubleExtra("latitude", 0);
        double lon = getIntent().getDoubleExtra("longitude", 0);

        String htmlData = "<html><head>" +
                "<link rel='stylesheet' href='https://unpkg.com/leaflet/dist/leaflet.css'/>" +
                "<script src='https://unpkg.com/leaflet/dist/leaflet.js'></script>" +
                "<style>html, body, #map { height: 100%; margin: 0; }</style></head>" +
                "<body><div id='map'></div><script>" +
                "var map = L.map('map').setView([" + lat + ", " + lon + "], 15);" +
                "L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(map);" +
                "L.marker([" + lat + ", " + lon + "]).addTo(map)" +
                ".bindPopup('Phone B Location<br>Lat: " + lat + "<br>Lon: " + lon + "').openPopup();" +
                "</script></body></html>";


        mapWebView.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);
    }
}
