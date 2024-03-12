package com.ssi.fctrading.DataContract;

import java.util.ArrayList;

public class ExercisableQuantityResponse
{
    public class ExercisableQuantity
    {
        public String EntitlementID ;
        public String InstrumentID ;
        public long SubscriptionPrice ;
        public double ExecutedRateFrom ;
        public String SubscriptionPeriodFrom ;
        public String SubscriptionPeriodTo ;
        public long ExerciseableQuantity ;
        public long ExerciseableReceiveQuantity ;
        public long ExercisedReceiveQuantity ;
        public double ExecutedRateTo ;
        public long ExercisedQuantity ;
        public String PayableDate ;
    }
    public String account ;
    public ArrayList<ExercisableQuantity> exercisableQuantities = new ArrayList<ExercisableQuantity>();

}
