package com.example.ducvietho.moki.utils.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.example.ducvietho.moki.utils.Constants;
import com.example.ducvietho.moki.utils.FontCache;

/**
 * Created by ducvietho on 22/03/2018.
 */

public class FontEditText extends AppCompatEditText {
    private Context context;
    private String fontName;
    public FontEditText(Context context) {
        super(context);
        this.context=context;
        applyCustomFont(context);
    }

    public FontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        applyCustomFont(context);
    }

    public FontEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
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
