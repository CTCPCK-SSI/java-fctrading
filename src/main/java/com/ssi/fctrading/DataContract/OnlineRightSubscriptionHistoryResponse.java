package com.ssi.fctrading.DataContract;

import java.util.ArrayList;

public class OnlineRightSubscriptionHistoryResponse {
    public class OnlineRightSubscriptionHistoryItem
    {
        public String transactionID;
        public String dateTime;
        public String instrumentID;
        public double ratioFrom;
        public double subscriptionPrice;
        public String subscriptionPeriodFrom;
        public String subscriptionPeriodTo;
        public long exercisedReceivedQty;
        public double amount;
        public String status;
        public double ratioTo;
        public String underlyingInstrumentID;
    }

    public ArrayList<OnlineRightSubscriptionHistoryItem> onlineRightSubscriptionHistories = new ArrayList<OnlineRightSubscriptionHistoryItem>();
    public String account;

}
