package com.example.reverse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class PersonAdapter extends ArrayAdapter<Person> {
    private Context context;
    private List<Person> personList;

    public PersonAdapter(@NonNull Context context, List<Person> personList) {
        super(context, 0, personList);
        this.context = context;
        this.personList = personList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(
                    position % 2 == 0 ? R.layout.item_layout_left : R.layout.item_layout_right, parent, false);
        }

        Person currentPerson = personList.get(position);

        ImageView imageView = listItem.findViewById(R.id.imageView);
        TextView textView = listItem.findViewById(R.id.textView);

        // Set image and text
        // Assuming you have a method to load images from a URL or resource
        // imageView.setImageResource(currentPerson.getImage());
        textView.setText(currentPerson.getName());

        return listItem;
    }
}