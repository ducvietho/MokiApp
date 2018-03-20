package com.example.ducvietho.moki.utils.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.example.ducvietho.moki.utils.Constants;
import com.example.ducvietho.moki.utils.FontCache;


/**
 * Created by ducvietho.
 */

public class FontTextView extends AppCompatTextView {
    private String fontName;
    private Context context;

    public FontTextView(Context context) {
        super(context);
        this.context = context;
        applyCustomFont(context);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        applyCustomFont(context);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        applyCustomFont(context);
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont;
        if(fontName!=null) {
            customFont = FontCache.getTypeface(fontName, context);
        }else {
            customFont = FontCache.getTypeface(Constants.ROBOTO_LIGHT, context);
        }
        setTypeface(customFont);
    }
}
