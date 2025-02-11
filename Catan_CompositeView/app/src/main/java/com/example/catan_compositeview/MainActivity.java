package com.example.catan_compositeview;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv;
    AlertDialog.Builder builder;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.listView1);

        builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.csps);
        builder.setTitle("Selected Item");
        builder.setNeutralButton("Okay", null);

        lv.setOnItemClickListener(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = (String) lv.getItemAtPosition(position);
        builder.setMessage(item);
        dialog = builder.create();
        dialog.show();
    }
}