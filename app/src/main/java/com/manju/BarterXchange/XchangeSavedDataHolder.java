package com.manju.BarterXchange;

/**
 * Created by Manjunath on 10/29/2015.
 * Data holder or POJO for saved data from shared preferences
 */
public class XchangeSavedDataHolder {

    private String mFrom_Cur = null;
    private String mTo_Cur = null;

    private float mMax = 0;
    private float mMin = 0;
    private boolean misActive = false;

    /**
     * default ctor
     */
    public XchangeSavedDataHolder() {

    }

    /**
     * parametrized ctor
     *
     * @param aFrom
     * @param aTo
     * @param aMax
     * @param aMin
     * @param aisActive
     */

    public XchangeSavedDataHolder(final String aFrom, final String aTo, final float aMax, final float aMin, final boolean aisActive)
    {
        mFrom_Cur = aFrom;
        mTo_Cur = aTo;
        mMax = aMax;
        mMin = aMin;
        misActive = aisActive;
    }

    public void setFrom(final String aFrom)
    {
        mFrom_Cur = aFrom;
    }

    public void setTo(final String aTo)
    {
        mTo_Cur = aTo;
    }

    public void setMax(final float aMax)
    {
        mMax = aMax;
    }

    public void setMin(final float aMin)
    {
        mMin = aMin;
    }

    public void setActive(final boolean aisActive)
    {
        misActive = aisActive;
    }


    public String getFrom()
    {
        return mFrom_Cur;
    }

    public String getTo()
    {
        return mTo_Cur;
    }

    public float getMax()
    {
        return mMax;
    }

    public float getMin()
    {
        return mMin;
    }

    public boolean getActive()
    {
        return misActive;
    }


}
