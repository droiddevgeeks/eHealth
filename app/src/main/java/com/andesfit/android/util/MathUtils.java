package com.andesfit.android.util;

public class MathUtils {
    private static final float EPSILON = 1.0E-4f;
    private static final String TAG = MathUtils.class.getSimpleName();

    public static int compareFloat(float left, float right) {
        float diff = left - right;
        if (Math.abs(diff) < EPSILON) {
            return 0;
        }
        return diff < 0.0f ? -1 : 1;
    }
}
