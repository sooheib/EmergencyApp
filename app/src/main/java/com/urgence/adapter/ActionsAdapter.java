package com.urgence.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.urgence.R;
/**
 * Created by SooheibSelmi
 */

public class ActionsAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;

    private final String[] mTitles;
    private final int[] mIcons;
    Context ctx;

    public ActionsAdapter(Context context, int type) {
        mInflater = LayoutInflater.from(context);
        ctx = context;

        TypedArray iconsArray;
        final Resources res = context.getResources();

        if (type == 0)

        {
            mTitles = res.getStringArray(R.array.insricption_function);
            iconsArray = res
                    .obtainTypedArray(R.array.insricption_function_icon);
        } else {
            mTitles = res.getStringArray(R.array.iao_function);
            iconsArray = res
                    .obtainTypedArray(R.array.iao_function_icon);
        }


        final int count = iconsArray.length();
        mIcons = new int[count];
        for (int i = 0; i < count; ++i) {
            mIcons[i] = iconsArray.getResourceId(i, 0);
        }
        iconsArray.recycle();

    }



    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public Uri getItem(int position) {
        return Uri.parse(mTitles[position]);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        convertView = mInflater.inflate(R.layout.action_list_item, parent,
                false);

        TextView text = (TextView) convertView.findViewById(R.id.text_sliding_menu);
        ImageView icone = (ImageView) convertView.findViewById(R.id.icone);

        final Drawable icon = convertView.getContext().getResources()
                .getDrawable(mIcons[position]);
        icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());


        icone.setImageDrawable(icon);
        text.setText(mTitles[position]);

        convertView.setTag(mTitles[position]);



        convertView.setEnabled(false);


        return convertView;

    }

}
