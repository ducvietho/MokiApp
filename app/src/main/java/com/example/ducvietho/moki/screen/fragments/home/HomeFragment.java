package com.example.ducvietho.moki.screen.fragments.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.data.model.User;
import com.example.ducvietho.moki.screen.activities.camera.CameraActivity;
import com.example.ducvietho.moki.screen.fragments.category.CategoryFragment;
import com.example.ducvietho.moki.utils.UserSession;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.fab)
    ImageView mImageView;
    private View v;
    private UserSession session;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,v);
        session = new UserSession(v.getContext());
        final User user = session.getUserDetail();
        setupViewPager(mViewPager);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getToken()==null){
                    session.logoutUser(1);

                }
                else{
                    v.getContext().startActivity(new CameraActivity().getIntent(v.getContext(),1));
                }

            }
        });

        return v;
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(CategoryFragment.newInstance(1), "Bé ăn");
        adapter.addFragment(CategoryFragment.newInstance(2), "Bé mặc");
        adapter.addFragment(CategoryFragment.newInstance(3), "Bé ngủ");
        adapter.addFragment(CategoryFragment.newInstance(4), "Bé tắm");
        adapter.addFragment(CategoryFragment.newInstance(5), "Bé vệ sinh");
        viewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(viewPager);
    }


}
