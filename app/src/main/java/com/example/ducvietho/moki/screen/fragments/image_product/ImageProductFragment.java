package com.example.ducvietho.moki.screen.fragments.image_product;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.screen.activities.productimage.DetailZoomActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageProductFragment extends Fragment {
    public static final String EXTRA_URL = "url";
    public static final String EXTRA_ID ="id";
    @BindView(R.id.image_product)
    ImageView imageView ;
    public static ImageProductFragment newInstance(String url,int id) {
        ImageProductFragment fragment = new ImageProductFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_URL,url);
        args.putInt(EXTRA_ID,id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_product, container, false);
        ButterKnife.bind(this,v);
        String url = getArguments().getString(EXTRA_URL);
        final int id = getArguments().getInt(EXTRA_ID,0);
        Picasso.with(v.getContext()).load(url).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)(v.getContext())).startActivityForResult(new DetailZoomActivity().
                        getIntent(v.getContext(),id),0);
                getActivity().overridePendingTransition(R.anim.slide_up_info,R.anim.no_change);
            }
        });
        return v;
    }

}
