package com.example.ducvietho.moki.utils.customview;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by ducvietho
 */

public class AutoHighLightTextview extends FontTextView {
    public AutoHighLightTextview(Context context) {
        super(context);
    }

    public AutoHighLightTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoHighLightTextview(Context context, AttributeSet attrs, int defStyleAttr) {
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
