package com.ssi.fctrading.DataContract;

import java.util.ArrayList;

public class UnsettledSoldTransactionResponse {
    public class UnsettledSoldTransactionItem
    {
        public String TradeDate;
        public String InstrumentID;
        public long Quantity;
        public double Price;
        public double NetSellValue;
    }
    public ArrayList<UnsettledSoldTransactionItem> orders = new ArrayList<UnsettledSoldTransactionItem>();
    public String account;
}
