package com.example.catan_horoscopes;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.catan_horoscopes.ItemAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ItemAdapter adapter;
    ArrayList<Horoscope> list = new ArrayList<Horoscope>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.listView1);
        list.add(new Horoscope(R.drawable.acquarius,"Acquarius","January 20 - February 18"));
        list.add(new Horoscope(R.drawable.aries,"Aries","March 21 - April 19"));
        list.add(new Horoscope(R.drawable.cancer,"Cancer","June 21 - July 22"));
        list.add(new Horoscope(R.drawable.capricorn,"Capricorn","December 22 - January 19"));
        list.add(new Horoscope(R.drawable.gemini,"Gemini","May 21 - June 20"));
        list.add(new Horoscope(R.drawable.leo,"Leo","July 23 - August 22"));
        list.add(new Horoscope(R.drawable.libra,"Libra","September 23 - October 22"));
        list.add(new Horoscope(R.drawable.pisces,"Pisces","February 19 - March 20"));
        list.add(new Horoscope(R.drawable.sagittarius,"Sagittarius","November 22 - December 21"));
        list.add(new Horoscope(R.drawable.scorpio,"Scorpio","October 23 - November 21"));
        list.add(new Horoscope(R.drawable.taurus,"Taurus","April 20 - May 20"));
        list.add(new Horoscope(R.drawable.virgo,"Virgo","August 23 - September 22"));

        adapter = new ItemAdapter(this,list);
        lv.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}