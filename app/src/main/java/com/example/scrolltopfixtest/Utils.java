package com.example.scrolltopfixtest;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Utils {

    private static float sDensityRatio = 0f;

    public static int pxFromDp(Context context, float dp) {
        return Math.round(dp * getDensityRatio(context));
    }

    public static float getDensityRatio(Context context) {
        if (sDensityRatio > 0f) {
            return sDensityRatio;
        }
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        sDensityRatio = (float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT;
        return sDensityRatio;
    }

}
