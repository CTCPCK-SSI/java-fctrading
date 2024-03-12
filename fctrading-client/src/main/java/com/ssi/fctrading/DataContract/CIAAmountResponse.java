package com.ssi.fctrading.DataContract;

import java.util.ArrayList;

public class CIAAmountResponse {
    public class CIAAmountItem
    {
        public String DueDate;
        public Long SellValue;
        public Long NetSellValue;
        public Long Advance;
        public Long CashAdvance;
    }


    public ArrayList<CIAAmountItem> CIAAmounts = new ArrayList<CIAAmountItem>();
    public String account;

}
