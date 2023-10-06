package com.ssi.fctrading;


public class API {

    final static String ACCESS_TOKEN = "/api/v2/Trading/AccessToken";
    final static String NEW_ORDER = "/api/v2/Trading/NewOrder";
    final static String MODIFY_ORDER = "/api/v2/Trading/ModifyOrder";
    final static String CANCEL_ORDER = "/api/v2/Trading/CancelOrder";
    final static String DER_NEW_ORDER = "/api/v2/Trading/derNewOrder";
    final static String DER_MODIFY_ORDER = "/api/v2/Trading/derModifyOrder";
    final static String DER_CANCEL_ORDER = "/api/v2/Trading/derCancelOrder";
    final static String GET_ORDER_HISTORY = "/api/v2/Trading/orderHistory";
    final static String GET_DER_POSITION = "/api/v2/Trading/derivPosition";
    final static String GET_STOCK_POSITION = "/api/v2/Trading/stockPosition";
    final static String GET_MAX_BUY_QUANTITY = "/api/v2/Trading/maxBuyQty";
    final static String GET_MAX_SELL_QUANTITY = "/api/v2/Trading/maxSellQty";
    final static String GET_ACCOUNT_BALANCE = "/api/v2/Trading/cashAcctBal";
    final static String GET_DER_ACCOUNT_BALANCE = "/api/v2/Trading/derivAcctBal";
    final static String GET_PPMMRACCOUNT = "/api/v2/Trading/ppmmraccount";
    final static String GET_ORDER_BOOK = "/api/v2/Trading/orderBook";
    final static String GET_AUDIT_ORDER_BOOK = "/api/v2/Trading/auditOrderBook";
    final static String GET_CIA_AMOUNT = "/api/v2/cash/cashInAdvanceAmount";
    final static String GET_UNSETTLE_SOLD_TRANSACTION = "/api/v2/cash/unsettleSoldTransaction";
    final static String GET_TRANSFER_HISTORIES = "/api/v2/cash/transferHistories";
    final static String GET_CIA_HISTORIES = "/api/v2/cash/cashInAdvanceHistories";
    final static String GET_EST_CIA_FEE = "/api/v2/cash/estCashInAdvanceFee";
    final static String VSD_CASH_DW = "/api/v2/cash/vsdCashDW";
    final static String TRANSFER_INTERNAL = "/api/v2/cash/transferInternal";
    final static String CREATE_CIA = "/api/v2/cash/createCashInAdvance";
    final static String GET_STOCK_TRANSFERABLE = "/api/v2/stock/transferable";
    final static String GET_STOCK_TRANSFER_HISTORIES = "/api/v2/stock/transferHistories";
    final static String STOCK_TRANSFER = "/api/v2/stock/transfer";
    final static String GET_ORS_DIVIDEND = "/api/v2/ors/dividend";
    final static String GET_ORS_EXERCISABLE_QUANTITY = "/api/v2/ors/exercisableQuantity";
    final static String GET_ORS_HISTORIES = "/api/v2/ors/histories";
    final static String ORS_CREATE = "/api/v2/ors/create";
    final static String GET_OTP = "/api/v2/Trading/GetOTP";

    final static String HUB_NAME = "BroadcastHubV2";
    final static String HUB_EVENT="Broadcast";
}
