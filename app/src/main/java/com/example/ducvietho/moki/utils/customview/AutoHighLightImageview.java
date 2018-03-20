package com.example.ducvietho.moki.utils.customview;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by ducvietho.
 */

public class AutoHighLightImageview extends AppCompatImageView {
    public AutoHighLightImageview(Context context) {
        super(context);
    }

    public AutoHighLightImageview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoHighLightImageview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setPressed(boolean pressed) {
        if (isEnabled()) {
            setState(pressed);
        }
        super.setPressed(pressed);
    }

    @Override
    public void setEnabled(boolean enabled) {
        setState(!enabled);
        super.setEnabled(enabled);
    }

    private void setState(boolean pressed) {
        if (pressed) {
            setAlpha(0.5f);
        } else {
            setAlpha(1.0f);
        }
    }
}
