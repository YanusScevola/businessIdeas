package com.example.myapplication.libraries.cardview;

import android.view.animation.Interpolator;


public interface AnimationSetting {
    Direction getDirection();
    int getDuration();
    Interpolator getInterpolator();
}
