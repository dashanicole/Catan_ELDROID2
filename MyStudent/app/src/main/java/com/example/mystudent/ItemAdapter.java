package com.example.mystudent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {
    Context context;
    ArrayList<Person> list;
    LayoutInflater inflater;

    public ItemAdapter(Context context, ArrayList<Person> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PersonHandler handler = null;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.itemlayout,null);
            handler = new PersonHandler();
            handler.iv = convertView.findViewById(R.id.imageView2);
            handler.tv = convertView.findViewById(R.id.textView);
            convertView.setTag(handler);
        } handler = (PersonHandler) convertView.getTag();
        //populate the layout
        handler.iv.setImageURI(list.get(position).getImageUri());
        handler.tv.setText(list.get(position).getName());

        return convertView;
    }

    static class PersonHandler{
        ImageView iv;
        TextView tv;
    }

}
