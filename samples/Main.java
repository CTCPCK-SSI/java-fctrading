package com.ssi.fctrading;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import microsoft.aspnet.signalr.client.MessageReceivedHandler;
import com.ssi.fctrading.DataContract.*;
import com.ssi.fctrading.FCTradingClient;
import com.ssi.fctrading.FCTradingStreaming;
import com.google.gson.JsonElement;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.nio.file.Paths;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Main {
    public static String read(InputStream s) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int length; (length = s.read(buffer)) != -1;) {
            result.write(buffer, 0, length);
        }
        // StandardCharsets.UTF_8.name() > JDK 7
        return result.toString("UTF-8");
    }

    public static void main(String[] args) throws Exception {
        while (true) {
            System.out.println("1. FCTrading API");
            System.out.println("2. FCTrading Streaming");
            System.out.println("3. Exit");
            Scanner inputReader = new Scanner(System.in);
            String line = inputReader.nextLine();
            switch (line) {
                case "1":
                    FCTradingAPI();
                    break;
                case "2":
                    FCTradingStreaming();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Input again!");
            }

        }
    }

    private static void printLn(Object arg) throws Exception {
        System.out.println("Response: " + new ObjectMapper().writeValueAsString(arg));
    }

    public static void FCTradingAPI() throws Exception {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(Paths.get("").toAbsolutePath().toString() + "/fctrading.json"));
        JSONObject jsonObject = (JSONObject) obj;
        long twoFactorType = (long) jsonObject.get("twoFactorType");
        String consumerId = (String) jsonObject.get("consumerId");
        String consumerSecret = (String) jsonObject.get("consumerSecret");
        String privateKey = (String) jsonObject.get("privateKey");
        String code = (String) jsonObject.get("code");
        String url = (String) jsonObject.get("url");
        boolean isSave = false;
        if (twoFactorType != 1) {
            isSave = (boolean) jsonObject.get("isSave");
        }

        FCTradingClient client = new FCTradingClient(consumerId, consumerSecret, privateKey, url);
        client.init();
        while (true) {
            System.out.println("1.  GetOTP");
            System.out.println("2.  VerifyOTP");
            System.out.println("3.  NewOrder");
            System.out.println("4.  ModifyOrder");
            System.out.println("5.  CancelOrder");
            System.out.println("6.  DerNewOrder");
            System.out.println("7.  DerModifyOrder");
            System.out.println("8.  DerCancelOrder");
            System.out.println("9.  GetCashBalance");
            System.out.println("10. GetDerivativeBalance");
            System.out.println("11. GetPPMMRAccount");
            System.out.println("12. GetStockPosition");
            System.out.println("13. GetDerivativePosition");
            System.out.println("14. GetMaxBuyQty");
            System.out.println("15. GetMaxSellQty");
            System.out.println("16. GetOrderHistory");
            System.out.println("17. GetOrderBook");
            System.out.println("18. GetAuditOrderBook");
            System.out.println("19. CashInAdvanceAmount");
            System.out.println("20. GetUnsettleSoldTransaction");
            System.out.println("21. GetCashTransferHistory");
            System.out.println("22. GetCIAHistory");
            System.out.println("23. GetEstimateCashInAdvanceFee");
            System.out.println("24. CashTransferVSD");
            System.out.println("25. CashTransferInternal");
            System.out.println("26. CreateCashInAdvance");
            System.out.println("27. GetStockTransferable");
            System.out.println("28. GetStockTransferHistories");
            System.out.println("29. StockTransfer");
            System.out.println("30. GetOrsDividend");
            System.out.println("31. GetOrsExercisableQuantity");
            System.out.println("32. GetOrsHistory");
            System.out.println("33. OrsCreate");
            System.out.println("34. GetRatelimit");
            System.out.println("35. Back");
            Scanner inputReader = new Scanner(System.in);
            String line = inputReader.nextLine();
            Gson g = new Gson();
            switch (line) {
                case "1":
                    GetOTPRequest req = new GetOTPRequest();
                    req.consumerID = consumerId;
                    req.consumerSecret = consumerSecret;
                    printLn((client.GetOTP(req)));
                    break;
                case "2":
                    printLn((client.GetAccessToken(System.console().readLine("Code: "), (int) twoFactorType, true)));
                    break;
                case "3":
                    NewOrderRequest dataNewOrderRequest = getRequest(jsonObject, "NewOrderRequest",
                            NewOrderRequest.class);
                    dataNewOrderRequest.requestID = Helper.getRandom();
                    dataNewOrderRequest.deviceId = Helper.getDeviceId();
                    if (!client.IsVerify2FA()) {
                        dataNewOrderRequest.code = System.console().readLine("Code: ");
                    }
                    printLn((client.NewOrder(dataNewOrderRequest)));
                    break;
                case "4":
                    ModifyOrderRequest dataModifyOrderRequest = getRequest(jsonObject, "ModifyOrderRequest",
                            ModifyOrderRequest.class);
                    dataModifyOrderRequest.requestID = Helper.getRandom();
                    dataModifyOrderRequest.deviceId = Helper.getDeviceId();
                    if (!client.IsVerify2FA()) {
                        dataModifyOrderRequest.code = System.console().readLine("Code: ");
                    }
                    printLn((client.ModifyOrder(dataModifyOrderRequest)));
                    break;
                case "5":
                    CancelOrderRequest dataCancelOrderRequest = getRequest(jsonObject, "CancelOrderRequest",
                            CancelOrderRequest.class);
                    dataCancelOrderRequest.requestID = Helper.getRandom();
                    dataCancelOrderRequest.deviceId = Helper.getDeviceId();
                    if (!client.IsVerify2FA()) {
                        dataCancelOrderRequest.code = System.console().readLine("Code: ");
                    }
                    printLn((client.CancelOrder(dataCancelOrderRequest)));
                    break;
                case "6":
                    NewOrderRequest dataDerNewOrderRequest = getRequest(jsonObject, "DerNewOrderRequest",
                            NewOrderRequest.class);
                    dataDerNewOrderRequest.requestID = Helper.getRandom();
                    dataDerNewOrderRequest.deviceId = Helper.getDeviceId();
                    if (!client.IsVerify2FA()) {
                        dataDerNewOrderRequest.code = System.console().readLine("Code: ");
                    }
                    printLn((client.DerNewOrder(dataDerNewOrderRequest)));
                    break;
                case "7":
                    ModifyOrderRequest dataDerModifyOrderRequest = getRequest(jsonObject, "DerModifyOrderRequest",
                            ModifyOrderRequest.class);
                    dataDerModifyOrderRequest.requestID = Helper.getRandom();
                    dataDerModifyOrderRequest.deviceId = Helper.getDeviceId();
                    if (!client.IsVerify2FA()) {
                        dataDerModifyOrderRequest.code = System.console().readLine("Code: ");
                    }
                    printLn((client.DerModifyOrder(dataDerModifyOrderRequest)));
                    break;
                case "8":
                    CancelOrderRequest dataDerCancelOrderRequest = getRequest(jsonObject, "DerCancelOrderRequest",
                            CancelOrderRequest.class);
                    dataDerCancelOrderRequest.requestID = Helper.getRandom();
                    dataDerCancelOrderRequest.deviceId = Helper.getDeviceId();
                    if (!client.IsVerify2FA()) {
                        dataDerCancelOrderRequest.code = System.console().readLine("Code: ");
                    }
                    printLn((client.DerCancelOrder(dataDerCancelOrderRequest)));
                    break;
                case "9":
                    printLn((client.GetCashBalance(
                            getRequest(jsonObject, "CashAccountBalanceRequest", AccountBalanceRequest.class))));
                    break;
                case "10":
                    printLn((client.GetDerivativeBalance(
                            getRequest(jsonObject, "DerCashAccountBalanceRequest", AccountBalanceRequest.class))));
                    break;
                case "11":
                    printLn((client.GetPPMMRAccount(
                            getRequest(jsonObject, "PPMMRAccountRequest", PPMMRAccountRequest.class))));
                    break;
                case "12":
                    printLn((client.GetStockPosition(
                            getRequest(jsonObject, "StockAccountPositionRequest", AccountPositionRequest.class))));
                    break;
                case "13":
                    printLn((client.GetDerivativePosition(
                            getRequest(jsonObject, "DerAccountPositionRequest", AccountPositionRequest.class))));
                    break;
                case "14":
                    printLn((client.GetMaxBuyQty(getRequest(jsonObject, "MaxBuyQtyRequest", MaxQtyRequest.class))));
                    break;
                case "15":
                    printLn((client.GetMaxSellQty(getRequest(jsonObject, "MaxSellQtyRequest", MaxQtyRequest.class))));
                    break;
                case "16":
                    printLn((client.GetOrderHistory(
                            getRequest(jsonObject, "OrderHistoryRequest", OrderHistoryRequest.class))));
                    break;
                case "17":
                    printLn((client.GetOrderBook(getRequest(jsonObject, "OrderBookRequest", OrderBookRequest.class))));
                    break;
                case "18":
                    printLn((client.GetAuditOrderBook(
                            getRequest(jsonObject, "AuditOrderBookRequest", AuditOrderBookRequest.class))));
                    break;
                case "19":
                    printLn((client.GetCashInAdvanceAmount(
                            getRequest(jsonObject, "CIAAmountRequest", CIAAmountRequest.class))));
                    break;

                case "20":
                    printLn((client.GetUnsettleSoldTransaction(getRequest(jsonObject, "UnsettledSoldTransactionRequest",
                            UnsettledSoldTransactionRequest.class))));
                    break;

                case "21":
                    printLn((client.GetCashTransferHistory(
                            getRequest(jsonObject, "CashTransferHistoryRequest", CashTransferHistoryRequest.class))));
                    break;

                case "22":
                    printLn((client
                            .GetCIAHistory(getRequest(jsonObject, "CIAHistoryRequest", CIAHistoryRequest.class))));
                    break;

                case "23":
                    printLn((client.GetEstimateCashInAdvanceFee(getRequest(jsonObject,
                            "EstimateCashInAdvanceFeeRequest", EstimateCashInAdvanceFeeRequest.class))));
                    break;

                case "24":
                    printLn((client.CashTransferVSD(
                            getRequest(jsonObject, "CashTransferVSDRequest", CashTransferVSDRequest.class))));
                    break;

                case "25":
                    printLn((client.CashTransferInternal(
                            getRequest(jsonObject, "CashTransferRequest", CashTransferRequest.class))));
                    break;

                case "26":
                    printLn((client.CreateCashInAdvance(
                            getRequest(jsonObject, "CreateCashInAdvanceRequest", CreateCashInAdvanceRequest.class))));
                    break;

                case "27":
                    printLn((client.GetStockTransferable(
                            getRequest(jsonObject, "TransferableStockRequest", TransferableStockRequest.class))));
                    break;

                case "28":
                    printLn((client.GetStockTransferHistories(
                            getRequest(jsonObject, "StockTransferHistoryRequest", StockTransferHistoryRequest.class))));
                    break;

                case "29":
                    printLn((client.StockTransfer(
                            getRequest(jsonObject, "StockTransferRequest", StockTransferRequest.class))));
                    break;

                case "30":
                    printLn((client.GetOrsDividend(getRequest(jsonObject, "DividendRequest", DividendRequest.class))));
                    break;

                case "31":
                    printLn((client.GetOrsExercisableQuantity(
                            getRequest(jsonObject, "ExercisableQuantityRequest", ExercisableQuantityRequest.class))));
                    break;

                case "32":
                    printLn((client.GetOrsHistory(getRequest(jsonObject, "OnlineRightSubscriptionHistoryRequest",
                            OnlineRightSubscriptionHistoryRequest.class))));
                    break;

                case "33":
                    printLn((client.OrsCreate(getRequest(jsonObject, "CreateOnlineRightSubscriptionRequest",
                            CreateOnlineRightSubscriptionRequest.class))));
                    break;

                case "34":

                    printLn(client.GetRatelimit());
                    break;

                case "35":
                    return;
                default:
                    System.out.println("Input again!");
            }

        }
    }

    public static <T> T getRequest(JSONObject jsonObject, String section, java.lang.Class<T> classOfT) {
        JSONObject newOrderRequest = (JSONObject) jsonObject.get(section);
        return (new Gson()).fromJson(newOrderRequest.toJSONString(), classOfT);
    }

    public static void FCTradingStreaming() throws Exception {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(Paths.get("").toAbsolutePath().toString() + "/fctrading.json"));
        JSONObject jsonObject = (JSONObject) obj;

        String consumerId = (String) jsonObject.get("consumerId");
        String consumerSecret = (String) jsonObject.get("consumerSecret");
        long twoFactorType = (long) jsonObject.get("twoFactorType");

        FCTradingClient client = new FCTradingClient(consumerId, consumerSecret, (String) jsonObject.get("privateKey"),
                (String) jsonObject.get("url"));
        client.init();
        FCTradingStreaming streaming = new FCTradingStreaming(client, (String) jsonObject.get("streaming_url"));
        streaming.onReceived(new MessageReceivedHandler() {
            @Override
            public void onMessageReceived(JsonElement json) {
                System.out.println("onMessageReceived " + json.getAsString());
            }
        });
        streaming.start();
        // Read lines and send them as messages.
        Scanner inputReader = new Scanner(System.in);
        String line = inputReader.nextLine();
        while (!"exit".equals(line)) {

            line = inputReader.next();
        }
    }

}
