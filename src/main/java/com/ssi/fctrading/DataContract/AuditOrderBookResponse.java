package com.ssi.fctrading.DataContract;

import java.util.ArrayList;

public class AuditOrderBookResponse {
    public class AuditOrderBookItem
    {
        public String uniqueID ;
        public String orderID ;
        public String buySell ;
        public double price ;
        public long quantity ;
        public long filledQty ;
        public String orderStatus ;
        public String marketID ;
        public String inputTime ;
        public String modifiedTime ;
        public String instrumentID;
        public String orderType ;
        public long cancelQty ;
        public double avgPrice ;
        public String isForcesell ;
        public String isShortsell ;
        public String rejectReason = "";
        public AuditOrderBookItem lastErrorEvent = null;
    
    }

    public ArrayList<AuditOrderBookItem> orders = new ArrayList<AuditOrderBookItem>();
    public String account;

}
