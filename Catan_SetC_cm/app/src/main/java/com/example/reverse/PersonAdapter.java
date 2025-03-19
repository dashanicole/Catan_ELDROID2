package com.example.reverse;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PersonAdapter extends ArrayAdapter<Person> implements Filterable {
    private Context context;
    private List<Person> originalList;
    private List<Person> filteredList;

    public PersonAdapter(@NonNull Context context, List<Person> personList) {
        super(context, 0, personList);
        this.context = context;
        this.originalList = new ArrayList<>(personList);
        this.filteredList = new ArrayList<>(personList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(
                    position % 2 == 0 ? R.layout.item_layout_left : R.layout.item_layout_right, parent, false);
        }

        Person currentPerson = filteredList.get(position);

        ImageView imageView = listItem.findViewById(R.id.imageView);
        TextView textView = listItem.findViewById(R.id.textView);

        // Log the data being bound
        Log.d("PersonAdapter", "Binding Name: " + currentPerson.getName());
        Log.d("PersonAdapter", "Binding Image URI: " + currentPerson.getImage());

        // Set text
        textView.setText(currentPerson.getName());

        // Load image using Glide
        if (currentPerson.getImage() != null && !currentPerson.getImage().isEmpty()) {
            Glide.with(context)
                    .load(Uri.parse(currentPerson.getImage()))
                    .error(R.mipmap.ic_launcher) // Fallback image if loading fails
                    .into(imageView);
        } else {
            imageView.setImageResource(R.mipmap.ic_launcher); // Default image if no image is selected
        }

        return listItem;
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Nullable
    @Override
    public Person getItem(int position) {
        return filteredList.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Person> filteredResults = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    // If the search text is empty, show the original list
                    filteredResults.addAll(originalList);
                } else {
                    // Filter the list based on the search text
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Person person : originalList) {
                        if (person.getName().toLowerCase().contains(filterPattern)) {
                            filteredResults.add(person);
                        }
                    }
                }

                results.values = filteredResults;
                results.count = filteredResults.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList.clear();
                filteredList.addAll((List<Person>) results.values);
                notifyDataSetChanged();
            }
        };
    }
}