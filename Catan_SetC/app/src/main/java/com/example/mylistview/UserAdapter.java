package com.example.mylistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends BaseAdapter implements Filterable {

    private final Context context;
    private List<User> originalList;
    private List<User> filteredList;

    public UserAdapter(Context context, List<User> users) {
        this.context = context;
        this.originalList = users;
        this.filteredList = new ArrayList<>(users);

    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        User user = filteredList.get(position);

        int layoutId = (position % 2 == 0)
                ? R.layout.user_item_left
                : R.layout.user_item_right;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.name);
            holder.image = convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(user.getName());
        holder.image.setImageBitmap(user.getImage());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString().toLowerCase();

                List<User> filtered = new ArrayList<>();
                if (query.isEmpty()) {
                    filtered.addAll(originalList);
                } else {
                    for (User user : originalList) {
                        if (user.getName().toLowerCase().contains(query)) {
                            filtered.add(user);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filtered;
                results.count = filtered.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList.clear();
                filteredList.addAll((List<User>) results.values);
                notifyDataSetChanged();
            }
        };
    }



    static class ViewHolder {
        TextView name;
        ImageView image;
    }
}
