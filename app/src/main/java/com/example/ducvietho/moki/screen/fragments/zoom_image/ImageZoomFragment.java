package com.example.ducvietho.moki.screen.fragments.zoom_image;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ducvietho.moki.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.koushikdutta.ion.Ion;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageZoomFragment extends Fragment {

    PhotoView imageView;
    public static final String EXTRA_URL = "url";
    public static ImageZoomFragment newInstance(String url) {
        ImageZoomFragment fragment = new ImageZoomFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_URL,url);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_zoom, container, false);
        String url = getArguments().getString(EXTRA_URL);
        imageView = (PhotoView)v.findViewById(R.id.im_zoom);
        Ion.with(v.getContext()).load(url).withBitmap().deepZoom().intoImageView(imageView);
        return v;
    }

}
