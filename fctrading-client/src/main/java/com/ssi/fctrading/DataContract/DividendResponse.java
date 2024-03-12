package com.ssi.fctrading.DataContract;

import java.util.ArrayList;

public class DividendResponse {
    public class DividendItem
    {
        public String stockDividend ;
        public String instrumentID ;
        public long quantity ;
        public String executedRate ;
        public String closeDate ;
        public String paidDate ;
        public double amount ;
        public String status ;
        public long receivedQuantity ;
        public String issueInstrument ;
        public String distributedFlag ;
        public String payableDate ;
        public double subscriptionPrice ;
        public double subscriptionAmount ;
        public double subscriptionQuantity ;
        public String subscriptionPeriodFrom ;
        public String subscriptionPeriodTo ;
        public String entitlementID ;
    }


    public ArrayList<DividendItem> dividends  = new ArrayList<DividendItem>();
    public String account ;
}
