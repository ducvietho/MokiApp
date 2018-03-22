package com.example.ducvietho.moki.screen.activities.district;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.District;
import com.example.ducvietho.moki.utils.OnItemtClick;
import com.example.ducvietho.moki.utils.customview.FontTextView;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by ducvietho on 21/03/2018.
 */

public class DistrictAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private OnItemtClick<District> mOnItemtClick;
    private List<District> mList;
    private LayoutInflater inflater;

    public DistrictAdapter(Context context,List<District> list,OnItemtClick<District> onItemtClick) {
        inflater = LayoutInflater.from(context);
        mList = list;
        mOnItemtClick = onItemtClick;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_district, parent, false);
            holder.district = (FontTextView) convertView.findViewById(R.id.tv_district);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.district.setText(mList.get(position).getName());
        holder.district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemtClick.onClick(mList.get(position));
            }
        });
        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.item_header, parent, false);
            holder.header = (FontTextView) convertView.findViewById(R.id.tv_header);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //set header text as first char in name
        String headerText = "" + mList.get(position).getName().subSequence(0, 1).charAt(0);
        holder.header.setText(headerText);

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
        return mList.get(position).getName().subSequence(0, 1).charAt(0);
    }

    class HeaderViewHolder {
        FontTextView header ;
    }

    class ViewHolder {
        FontTextView district;
    }

}
