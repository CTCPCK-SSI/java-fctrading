package com.ssi.fctrading.DataContract;

import java.util.ArrayList;

public class StockTransferHistoryResponse {
    public class StockTransferHistoryItem
    {
        public String transactionID;
        public String beneficiaryAccount;
        public String instrumentID;
        public long quantity;
        public String dateTime;
        public String status;
        public String remark;
        public String auditRemark;
    }


    public ArrayList<StockTransferHistoryItem> stockTransferHistories  = new ArrayList<StockTransferHistoryItem>();
    public String account;

}
