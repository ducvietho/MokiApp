package com.example.ducvietho.moki.screen.activities.village;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.District;
import com.example.ducvietho.moki.data.model.Village;
import com.example.ducvietho.moki.screen.activities.district.DistrictAdapter;
import com.example.ducvietho.moki.utils.OnItemtClick;
import com.example.ducvietho.moki.utils.customview.FontTextView;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by ducvietho on 22/03/2018.
 */

public class VillageAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private OnItemtClick<Village> mOnItemtClick;
    private List<Village> mList;
    private LayoutInflater inflater;

    public VillageAdapter(Context context,OnItemtClick<Village> onItemtClick, List<Village> list) {
        mOnItemtClick = onItemtClick;
        mList = list;
        inflater = LayoutInflater.from(context);
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
        return mList.get(position).getName().subSequence(0, 1).charAt(0);
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
            convertView = inflater.inflate(R.layout.item_village, parent, false);
            holder.village = (FontTextView) convertView.findViewById(R.id.tv_village);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.village.setText(mList.get(position).getName());
        holder.village.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemtClick.onClick(mList.get(position));
            }
        });
        return convertView;
    }


    @Override
    public boolean isEmpty() {
        return false;
    }
    class HeaderViewHolder {
        FontTextView header ;
    }

    class ViewHolder {
        FontTextView village;
    }
}
