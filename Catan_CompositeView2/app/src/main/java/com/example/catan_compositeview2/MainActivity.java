package com.example.catan_compositeview2;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ItemAdapter adapter;
    ArrayList<Person> list = new ArrayList<Person>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.listView1);
        list.add(new Person(R.drawable.image1,"My Husband"));
        list.add(new Person(R.drawable.image2,"My Ex-Husband"));
        list.add(new Person(R.drawable.image3,"My Ex-Boyfriend"));
        list.add(new Person(R.drawable.image4,"My Bestfriend"));
        list.add(new Person(R.drawable.image5,"My Father"));
        list.add(new Person(R.drawable.image6,"My Brother"));

        adapter = new ItemAdapter(this,list);
        lv.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}