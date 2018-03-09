package com.example.silence.xiyang;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Silence on 2018/3/9.
 */

public class HandShowAdapter extends ArrayAdapter<HandShow> {
    private int resourceId;
    public HandShowAdapter(Context context, int textViewResourceId , List<HandShow> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        HandShow handshow = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.handshowImage = (ImageView) view.findViewById(R.id.handshow_iamge);
            viewHolder.handshowName = (TextView) view.findViewById(R.id.handshow_name);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }// 避免重复加载布局和控件实例
        viewHolder.handshowImage.setImageResource(handshow.getImageid());
        viewHolder.handshowName.setText(handshow.getName());
        return view;
    }
    class ViewHolder{
        ImageView handshowImage;
        TextView handshowName;
    }
}
