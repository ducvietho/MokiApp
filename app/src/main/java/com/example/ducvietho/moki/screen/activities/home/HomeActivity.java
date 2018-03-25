package com.example.ducvietho.moki.screen.activities.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.Menu;
import com.example.ducvietho.moki.data.model.User;
import com.example.ducvietho.moki.screen.activities.product_seller.ProductSellerActivity;
import com.example.ducvietho.moki.screen.activities.search.SearchActivity;
import com.example.ducvietho.moki.screen.fragments.buy.BuyFragment;
import com.example.ducvietho.moki.screen.fragments.favorite.FavoriteFragment;
import com.example.ducvietho.moki.screen.fragments.home.HomeFragment;
import com.example.ducvietho.moki.screen.fragments.news.NewsFragment;
import com.example.ducvietho.moki.screen.fragments.sell.SellFragment;
import com.example.ducvietho.moki.utils.Constants;
import com.example.ducvietho.moki.utils.UserSession;
import com.example.ducvietho.moki.utils.customview.AutoHighLightImageview;
import com.example.ducvietho.moki.utils.customview.FontTextView;
import com.example.ducvietho.moki.utils.dialog.DialogExit;
import com.example.ducvietho.moki.utils.dialog.DialogLoading;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements MenuAdapter.OnItemMenuClick, View.OnClickListener {
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.rcvMenuOption)
    RecyclerView rcvMenuOption;
    @BindView(R.id.layoutMain)
    View layoutMain;
    @BindView(R.id.imgChangeView)
    AutoHighLightImageview imgChange;
    @BindView(R.id.imgNotify)
    AutoHighLightImageview imgNotify;
    @BindView(R.id.imgMessage)
    AutoHighLightImageview imgMessage;
    @BindView(R.id.imgSearch)
    AutoHighLightImageview imgSearch;
    @BindView(R.id.imgMenu)
    AutoHighLightImageview imgMenu;
    @BindView(R.id.imgMore)
    AutoHighLightImageview imgMore;
    @BindView(R.id.layoutHome)
    RelativeLayout layoutHome;
    @BindView(R.id.tvUserName)
    FontTextView mUserName;
    @BindView(R.id.tvTitle)
    FontTextView mTitle;
    @BindView(R.id.imgAvatar)
    CircleImageView mImgAvatar;
    @BindView(R.id.imgAppName)
    ImageView imgApp;
    private ActionBarDrawerToggle drawerToggle;
    private UserSession session;
    private float lastTranslate = 0.0f;
    private int oldPosition = 0;
    private ArrayList<Menu> menus;
    private FrameLayout mainContent;
    private MenuAdapter adapter;
    public static DialogLoading mDialogLoading;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(HomeActivity.this);
        mDialogLoading = new DialogLoading(HomeActivity.this);
        mDialogLoading.showDialog();
        session = new UserSession(HomeActivity.this);
        User user = session.getUserDetail();
        if (user.getName() != null) {
            mUserName.setText(user.getName());
        } else {
            mUserName.setText("Đăng nhập");
        }
        Picasso.with(HomeActivity.this).load(session.getUserDetail().getAvatar()).placeholder(R.drawable.icon_no_avatar).into(mImgAvatar);
        mImgAvatar.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        rcvMenuOption.setHasFixedSize(true);
        rcvMenuOption.setNestedScrollingEnabled(false);
        rcvMenuOption.setLayoutManager(new LinearLayoutManager(this));
        if (adapter == null) {
            adapter = new MenuAdapter(this);
        }
        if (menus == null) {
            menus = new ArrayList<>();
            if (user.getToken() != null) {
                createMenuUser();
            } else {
                createMenuNoUser();
            }

        }
        adapter.initMenu(menus);
        rcvMenuOption.setAdapter(adapter);
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerToggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, 0, 0) {

                        @Override
                        public void onDrawerSlide(View drawerView, float slideOffset) {
                            super.onDrawerSlide(drawerView, slideOffset);
                            float moveFactor = (rcvMenuOption.getWidth() * slideOffset);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                layoutMain.setTranslationX(moveFactor);
                            } else {
                                TranslateAnimation anim = new TranslateAnimation(lastTranslate, moveFactor, 0.0f, 0.0f);
                                anim.setDuration(0);
                                anim.setFillAfter(true);
                                layoutMain.startAnimation(anim);
                                lastTranslate = moveFactor;
                            }
                        }
                    };
                } else {
                    openDrawer();
                }
            }
        });
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float moveFactor = (rcvMenuOption.getWidth() * slideOffset);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    layoutMain.setTranslationX(moveFactor);
                } else {
                    TranslateAnimation anim = new TranslateAnimation(lastTranslate, moveFactor, 0.0f, 0.0f);
                    anim.setDuration(0);
                    anim.setFillAfter(true);
                    layoutMain.startAnimation(anim);
                    lastTranslate = moveFactor;
                }
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        startFragment(new HomeFragment());

    }

    @Override
    public void onBackPressed() {
        DialogExit dialogExit = new DialogExit(HomeActivity.this);
        dialogExit.showdialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgAvatar:
                if (session.getUserDetail().getToken() == null) {
                    session.logoutUser(1);
                } else {
                    startActivity(new ProductSellerActivity().getIntent(HomeActivity.this, session.getUserDetail().getId()));
                }
                break;
            case R.id.imgSearch:
                startActivity(new SearchActivity().getIntent(HomeActivity.this));
                break;
        }
    }

    @Override
    public void onClickPosition(int position) {
        if (oldPosition == position) {
            return;
        }
        switch (position) {
            case Constants.ITEM_HOME:
                imgChange.setVisibility(View.VISIBLE);
                imgMessage.setVisibility(View.VISIBLE);
                imgNotify.setVisibility(View.VISIBLE);
                imgSearch.setVisibility(View.VISIBLE);
                imgApp.setVisibility(View.VISIBLE);
                mTitle.setVisibility(View.GONE);
                startFragment(new HomeFragment());
                break;
            case Constants.ITEM_NEWS:
                imgChange.setVisibility(View.GONE);
                imgMessage.setVisibility(View.GONE);
                imgNotify.setVisibility(View.GONE);
                imgSearch.setVisibility(View.GONE);
                imgApp.setVisibility(View.GONE);
                mTitle.setVisibility(View.VISIBLE);
                mTitle.setText(getResources().getString(R.string.news));
                startFragment(new NewsFragment());
                break;
            case Constants.ITEM_SELL:
                if (session.getUserDetail().getToken() == null) {
                    session.logoutUser(1);
                } else {
                    imgChange.setVisibility(View.GONE);
                    imgMessage.setVisibility(View.GONE);
                    imgNotify.setVisibility(View.GONE);
                    imgSearch.setVisibility(View.GONE);
                    imgApp.setVisibility(View.GONE);
                    mTitle.setVisibility(View.VISIBLE);
                    mTitle.setText(getResources().getString(R.string.list_sell));
                    startFragment(new SellFragment());
                }

                break;
            case Constants.ITEM_FAVORITE:
                if (session.getUserDetail().getToken() == null) {
                    session.logoutUser(1);
                } else {
                    imgChange.setVisibility(View.GONE);
                    imgMessage.setVisibility(View.GONE);
                    imgNotify.setVisibility(View.GONE);
                    imgSearch.setVisibility(View.GONE);
                    imgApp.setVisibility(View.GONE);
                    mTitle.setVisibility(View.VISIBLE);
                    mTitle.setText(getResources().getString(R.string.favorites));
                    startFragment(new FavoriteFragment());
                }

                break;
            case Constants.ITEM_BUY:
                if (session.getUserDetail().getToken() == null) {
                    session.logoutUser(1);
                } else {
                    imgChange.setVisibility(View.GONE);
                    imgMessage.setVisibility(View.GONE);
                    imgNotify.setVisibility(View.GONE);
                    imgSearch.setVisibility(View.GONE);
                    imgApp.setVisibility(View.GONE);
                    mTitle.setVisibility(View.VISIBLE);
                    mTitle.setText(getResources().getString(R.string.list_buy));
                    startFragment(new BuyFragment());
                }
                break;
            case Constants.ITEM_LOGOUT:
                session.logoutUser(2);
                return;
            default:
                return;
        }
        setSelectedItemMenu(position);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                closeDrawer();
            }
        }, 200);

    }

    private void setSelectedItemMenu(int position) {
        Menu newmenu = menus.get(position);
        newmenu.setSelect(true);
        menus.set(position, newmenu);
        adapter.notifyItemChanged(position);

        Menu oldmenu = menus.get(oldPosition);
        oldmenu.setSelect(false);
        menus.set(oldPosition, oldmenu);
        adapter.notifyItemChanged(oldPosition);

        oldPosition = position;
    }

    private void createMenuUser() {
        Menu menu;

        menu = new Menu("Trang chủ", R.drawable.sidemenu_icon_store_normal);
        menu.setSelect(true);
        menus.add(menu);

        menu = new Menu("Tin tức", R.drawable.sidemenu_icon_news_normal);
        menus.add(menu);

        menu = new Menu("Danh sách yêu thích", R.drawable.sidemenu_icon_like_normal);
        menus.add(menu);

        menu = new Menu("Danh sách bán", R.drawable.sidemenu_icon_exhibit_normal);
        menus.add(menu);

        menu = new Menu("Danh sách mua", R.drawable.sidemenu_icon_buy_normal);
        menus.add(menu);

        menu = new Menu("Thiết lập", R.drawable.sidemenu_icon_setting_normal);
        menus.add(menu);

        menu = new Menu("Giới thiệu MOKI", R.drawable.sidemenu_icon_invite_normal);
        menus.add(menu);

        menu = new Menu("Đăng xuất", R.drawable.sidemenu_icon_logout_normal);
        menus.add(menu);

    }

    private void createMenuNoUser() {
        Menu menu;

        menu = new Menu("Trang chủ", R.drawable.sidemenu_icon_store_normal);
        menu.setSelect(true);
        menus.add(menu);

        menu = new Menu("Tin tức", R.drawable.sidemenu_icon_news_normal);
        menus.add(menu);

        menu = new Menu("Danh sách yêu thích", R.drawable.sidemenu_icon_like_normal);
        menus.add(menu);

        menu = new Menu("Danh sách bán", R.drawable.sidemenu_icon_exhibit_normal);
        menus.add(menu);

        menu = new Menu("Danh sách mua", R.drawable.sidemenu_icon_buy_normal);
        menus.add(menu);

        menu = new Menu("Thiết lập", R.drawable.sidemenu_icon_setting_normal);
        menus.add(menu);

        menu = new Menu("Giới thiệu MOKI", R.drawable.sidemenu_icon_invite_normal);
        menus.add(menu);

        menu = new Menu("Đăng nhập", R.drawable.sidemenu_icon_logout_normal);
        menus.add(menu);

    }

    public void closeDrawer() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void openDrawer() {
        if (!drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public void startFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.mainContent, fragment);
            ft.commit();
        }
    }


}
