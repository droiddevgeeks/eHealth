package com.andesfit.android.satandard;


public class BloodGlucoseStandard {
    public static float mgPerDLToMmolPerL(float valueInMgPerDl) {
        return valueInMgPerDl / HealthStandard.THRESHOLD_BMI_AGE_YOUTH;
    }

    public static float mmolPerLToMgPerDL(float valueInMmolPerL) {
        return HealthStandard.THRESHOLD_BMI_AGE_YOUTH * valueInMmolPerL;
    }
}
