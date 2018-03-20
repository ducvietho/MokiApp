package com.example.ducvietho.moki.screen.activities.home;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.Menu;
import com.example.ducvietho.moki.utils.customview.FontTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ducvietho.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private OnItemMenuClick onItemMenuClick;
    private ArrayList<Menu> menus;

    public MenuAdapter(OnItemMenuClick onItemMenuClick) {
        this.onItemMenuClick = onItemMenuClick;
    }

    public void initMenu(ArrayList<Menu> menus) {
        this.menus = menus;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        holder.bindData(menus.get(position));
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }


    public class MenuViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        FontTextView tvTitle;
        @BindView(R.id.imgIconMenu)
        ImageView imgMenuIcon;
        @BindView(R.id.imgNextArrow)
        ImageView imgNext;

        public MenuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemMenuClick != null) {
                        if (getAdapterPosition() != -1) {
                            onItemMenuClick.onClickPosition(getAdapterPosition());
                        }
                    }
                }
            });
        }

        public void bindData(Menu menu) {
            if (menu.isSelect()) {
                imgNext.setImageResource(R.drawable.ic_next_red);
                tvTitle.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.red_dark));
            } else {
                imgNext.setImageResource(R.drawable.ic_next);
                tvTitle.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.black));
            }
            tvTitle.setText(menu.getTitle());
            imgMenuIcon.setImageResource(menu.getIcon());
        }
    }

    public interface OnItemMenuClick {
        void onClickPosition(int position);
    }


}
