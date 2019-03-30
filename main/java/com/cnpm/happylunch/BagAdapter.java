package com.cnpm.happylunch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BagAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Bag> bagList;

    BagAdapter(Context context, int layout, List<Bag> bagList) {
        this.context = context;
        this.layout = layout;
        this.bagList = bagList;
    }

    @Override
    public int getCount() {
        return bagList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        ImageView imgImg, imgStatus;
        TextView txtName, txtTime, txtCount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.imgImg    = convertView.findViewById(R.id.imageView_bagImg);
            holder.txtName   = convertView.findViewById(R.id.textView_bagName);
            holder.txtTime   = convertView.findViewById(R.id.textView_bagTime);
            holder.txtCount  = convertView.findViewById(R.id.textView_bagCount);
            holder.imgStatus = convertView.findViewById(R.id.imageView_bagStatus);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        Bag bag = bagList.get(position);

        holder.imgImg.setImageResource(bag.getImg());
        holder.txtName.setText(bag.getName());
        holder.txtTime.setText(bag.getTime());
        holder.txtCount.setText(String.valueOf(bag.getCount()));
        holder.imgStatus.setImageResource(bag.getStatus());

        return convertView;
    }
}
