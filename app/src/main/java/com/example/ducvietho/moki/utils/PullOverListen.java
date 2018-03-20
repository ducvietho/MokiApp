package com.example.ducvietho.moki.utils;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ducviet .
 */

public abstract class PullOverListen {

    View view;

    public PullOverListen(View view){
        this.view = view;
        setup();
    }

    protected abstract void onStart();
    protected abstract void onPull(int d);
    protected  abstract void onFinish(int d);

    protected abstract boolean isStart();

    private void setup(){

        view.setOnTouchListener(new View.OnTouchListener() {

            boolean isDraging = false;
            float beginY = 0;
            float d = 0;

            private void init(){
                isDraging = false;
                beginY = 0;
                d = 0;
            }

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_MOVE) {
                    if (!isDraging && isStart()) {
//                        BEGIN PROGRESS
                        onStart();
                        isDraging = true;
                        beginY = motionEvent.getRawY();
                    }
                    if (isDraging) {
                        d = motionEvent.getRawY() - beginY;
//                        Log.d("Touch", d + "");

                        if (d > 0) {
                            onPull((int)d);
                            return true;
                        }else{
                            return false;
                        }
                    }
                } else if (action == MotionEvent.ACTION_UP) {
                    if (!isDraging) {
                        return false;
                    }
                    onFinish((int)d);
                    init();
                }
                return false;
            }
        });
    }

}
