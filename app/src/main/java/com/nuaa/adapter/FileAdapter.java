package com.nuaa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nuaa.zhuandongforonline.R;

import java.util.List;

/**
 * Created by where1993 on 2017/3/13.
 */

public class FileAdapter extends ArrayAdapter<String> {
    private int resourceId;
    private int ImageID;
    public FileAdapter(Context context, int textViewResourceId,
                       String[] objects,int ImageId) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        this.ImageID = ImageId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position); // 获取当前项的Fruit实例
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.Image = (ImageView) view.findViewById
                    (R.id.image);
            viewHolder.Name = (TextView) view.findViewById
                    (R.id.name);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.Image.setImageResource(this.ImageID);
        viewHolder.Name.setText(name);
        return view;
    }

    class ViewHolder {
        public ImageView Image;
        public TextView  Name;
    }


}


