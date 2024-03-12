package com.ssi.fctrading.DataContract;

import java.util.ArrayList;

public class CIAHistoryResponse {
    public class CIAHistoryItem
    {
        public String TransactionID;
        public String DateTime;
        public double TotalAmount;
        public ArrayList<CIAHistoryItemDetail> Details = new ArrayList<CIAHistoryItemDetail>();
        public String Status;
    }
    public class CIAHistoryItemDetail
    {
        public String Type;
        public double Value;
        public String SettleDate;
    }
    public ArrayList<CIAHistoryItem> CIAHistories = new ArrayList<CIAHistoryItem>();
    public String account;

}
