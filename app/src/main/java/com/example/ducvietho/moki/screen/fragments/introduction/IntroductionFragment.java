package com.example.ducvietho.moki.screen.fragments.introduction;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ducvietho.moki.R;
import com.example.ducvietho.moki.utils.customview.FontTextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroductionFragment extends Fragment {

    @BindView(R.id.tv_name)
    FontTextView mTextView;
    View v;
    public IntroductionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_introduction, container, false);
        ButterKnife.bind(this,v);
        String content = mTextView.getText().toString();
        Pattern moPattern = Pattern.compile("(MO)");
        Pattern kiPattern = Pattern.compile("(KI)");
        StringBuffer sb = new StringBuffer(content.length());
        Matcher o = moPattern.matcher(content);
        while (o.find()) {
            o.appendReplacement(sb, "<font color=\"#d8202a\">" + o.group(1) + "</font>");
        }
        o.appendTail(sb);
        Matcher n = kiPattern.matcher(sb.toString());
        sb = new StringBuffer(sb.length());

        while (n.find()) {
            n.appendReplacement(sb, "<font color=\"#d8202a\">" + n.group(1) + "</font>");
        }
        n.appendTail(sb);
        mTextView.setText(Html.fromHtml(sb.toString()));
        return v;
    }

}
