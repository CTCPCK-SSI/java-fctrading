package com.ssi.fctrading.DataContract;

import java.util.ArrayList;

public class TransferableStockResponse {
    public class TransferableStock
    {
        public String instrumentID;
        public long quantity;
        public String instrumentType;
    }
    public ArrayList<TransferableStock> transferableStocks  = new ArrayList<TransferableStock>();
    public String account ;
}
