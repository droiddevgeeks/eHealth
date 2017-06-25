package com.andesfit.android.models;

import java.util.Calendar;

/**
 * Created by Vampire on 2017-06-25.
 */

public class GlucoseRecord
{
    private int type;
    private int status;
    private int timeOffset;
    private int sequenceNumber;
    private int sampleLocation;
    private int concentrationUnit;

    private Calendar time;
    private float glucoseConcentration;

    public int getSequenceNumber()
    {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber)
    {
        this.sequenceNumber = sequenceNumber;
    }

    public Calendar getTime()
    {
        return time;
    }

    public void setTime(Calendar time)
    {
        this.time = time;
    }

    public int getTimeOffset()
    {
        return timeOffset;
    }

    public void setTimeOffset(int timeOffset)
    {
        this.timeOffset = timeOffset;
    }

    public float getGlucoseConcentration()
    {
        return glucoseConcentration;
    }

    public void setGlucoseConcentration(float glucoseConcentration)
    {
        this.glucoseConcentration = glucoseConcentration;
    }

    public int getConcentrationUnit()
    {
        return concentrationUnit;
    }

    public void setConcentrationUnit(int concentrationUnit)
    {
        this.concentrationUnit = concentrationUnit;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public int getSampleLocation()
    {
        return sampleLocation;
    }

    public void setSampleLocation(int sampleLocation)
    {
        this.sampleLocation = sampleLocation;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }
}