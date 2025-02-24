package com.example.catan_horoscopes;

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
    ArrayList<Horoscope> list;
    LayoutInflater inflater;

    public ItemAdapter(Context context, ArrayList<Horoscope> list) {
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
        HoroscopeHandler handler = null;
        if (convertView == null) {
            handler = new HoroscopeHandler();
            convertView = inflater.inflate(R.layout.itemlayout,null);
            handler.iv = convertView.findViewById(R.id.imageView);
            handler.tv = convertView.findViewById(R.id.textView);
            handler.tv1 = convertView.findViewById(R.id.textView1);
            convertView.setTag(handler);
        }
        else handler = (HoroscopeHandler) convertView.getTag();

        handler.iv.setImageResource(list.get(position).getImage());
        handler.tv.setText(list.get(position).getName());
        handler.tv1.setText(list.get(position).getDate());

        return convertView;
    }

    static class HoroscopeHandler {
        ImageView iv;
        TextView tv, tv1;
    }
}
