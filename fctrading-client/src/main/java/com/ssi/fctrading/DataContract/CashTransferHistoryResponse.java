package com.ssi.fctrading.DataContract;

import java.util.ArrayList;

public class CashTransferHistoryResponse {
    public class CashTransferHistory
    {
        public String transactionID ;
        public String date ;
        public String account ;
        public String beneficiaryAccount ;
        public long amount ;
        public String bankName ;
        public String bankBranchName ;
        public String beneficiary ;
        public String remark ;
        public String type ;
        public String status ;
    }

    public ArrayList<CashTransferHistory> transferHistories = new ArrayList<CashTransferHistory>();
}
