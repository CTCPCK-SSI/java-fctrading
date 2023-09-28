
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
        for (int length; (length = s.read(buffer)) != -1; ) {
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

    public static void FCTradingAPI() throws Exception {
        JSONParser parser = new JSONParser();
        //Object obj = parser.parse(new FileReader("C:\\Users\\hoaht\\Desktop\\fctrading.json"));
        Object obj = parser.parse(new FileReader(Paths.get("").toAbsolutePath().toString() + "/fctrading.json"));
        JSONObject jsonObject = (JSONObject)obj;
        
        // Gson gson = new Gson();
        // JsonObject config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
        FCTradingClient client = new FCTradingClient((String)jsonObject.get("consumerId")
                , (String)jsonObject.get("consumerSecret")
                , (String)jsonObject.get("privateKey")
                , (String)jsonObject.get("code")
                , (String)jsonObject.get("url"));
        client.init();
        while (true) {
            System.out.println("1.  NewOrder");
            System.out.println("2.  ModifyOrder");
            System.out.println("3.  CancelOrder");
            System.out.println("4.  DerNewOrder");
            System.out.println("5.  DerModifyOrder");
            System.out.println("6.  DerCancelOrder");
            System.out.println("7.  GetCashBalance");
            System.out.println("8.  GetDerivativeBalance");
            System.out.println("9.  GetPPMMRAccount");
            System.out.println("10. GetStockPosition");
            System.out.println("11. GetDerivativePosition");
            System.out.println("12. GetMaxBuyQty");
            System.out.println("13. GetMaxSellQty");
            System.out.println("14. GetOrderHistory");
            System.out.println("15. GetOrderBook");
            System.out.println("16. GetAuditOrderBook");
            System.out.println("17. CashInAdvanceAmount");
            System.out.println("18. GetUnsettleSoldTransaction");
            System.out.println("19. GetCashTransferHistory");
            System.out.println("20. GetCIAHistory");
            System.out.println("21. GetEstimateCashInAdvanceFee");
            System.out.println("22. CashTransferVSD");
            System.out.println("23. CashTransferInternal");
            System.out.println("24. CreateCashInAdvance");
            System.out.println("25. GetStockTransferable");
            System.out.println("26. GetStockTransferHistories");
            System.out.println("27. StockTransfer");
            System.out.println("28. GetOrsDividend");
            System.out.println("29. GetOrsExercisableQuantity");
            System.out.println("30. GetOrsHistory");
            System.out.println("31. OrsCreate");
            System.out.println("35. Back");
            Scanner inputReader = new Scanner(System.in);
            String line = inputReader.nextLine();
            Gson g = new Gson();
            switch (line) {
                case "1":
                    JSONObject newOrderRequest = (JSONObject)jsonObject.get("NewOrderRequest");
                    NewOrderRequest dataNewOrderRequest = g.fromJson(newOrderRequest.toJSONString(),NewOrderRequest.class);
                    dataNewOrderRequest.requestID = Helper.getRandom();
                    dataNewOrderRequest.deviceId = Helper.getDeviceId();
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.NewOrder(dataNewOrderRequest)));
                    break;
                case "2":
                    JSONObject modifyOrderRequest = (JSONObject)jsonObject.get("ModifyOrderRequest");
                    ModifyOrderRequest dataModifyOrderRequest = g.fromJson(modifyOrderRequest.toJSONString(),ModifyOrderRequest.class);
                    dataModifyOrderRequest.requestID = Helper.getRandom();
                    dataModifyOrderRequest.deviceId = Helper.getDeviceId();
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.ModifyOrder(dataModifyOrderRequest)));
                    break;
                case "3":
                    JSONObject cancelOrderRequest = (JSONObject)jsonObject.get("CancelOrderRequest");
                    CancelOrderRequest dataCancelOrderRequest = g.fromJson(cancelOrderRequest.toJSONString(),CancelOrderRequest.class);
                    dataCancelOrderRequest.requestID = Helper.getRandom();
                    dataCancelOrderRequest.deviceId = Helper.getDeviceId();
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.CancelOrder(dataCancelOrderRequest)));
                    break;
                case "4":
                    JSONObject derNewOrderRequest = (JSONObject)jsonObject.get("DerNewOrderRequest");
                    NewOrderRequest dataDerNewOrderRequest = g.fromJson(derNewOrderRequest.toJSONString(),NewOrderRequest.class);
                    dataDerNewOrderRequest.requestID = Helper.getRandom();
                    dataDerNewOrderRequest.deviceId = Helper.getDeviceId();
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.DerNewOrder(dataDerNewOrderRequest)));
                    break;
                case "5":
                    JSONObject derModifyOrderRequest = (JSONObject)jsonObject.get("DerModifyOrderRequest");
                    ModifyOrderRequest dataDerModifyOrderRequest = g.fromJson(derModifyOrderRequest.toJSONString(),ModifyOrderRequest.class);
                    dataDerModifyOrderRequest.requestID = Helper.getRandom();
                    dataDerModifyOrderRequest.deviceId = Helper.getDeviceId();
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.DerModifyOrder(dataDerModifyOrderRequest)));
                    break;
                case "6":
                    JSONObject derCancelOrderRequest = (JSONObject)jsonObject.get("DerCancelOrderRequest");
                    CancelOrderRequest dataDerCancelOrderRequest = g.fromJson(derCancelOrderRequest.toJSONString(),CancelOrderRequest.class);
                    dataDerCancelOrderRequest.requestID = Helper.getRandom();
                    dataDerCancelOrderRequest.deviceId = Helper.getDeviceId();
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.DerCancelOrder(dataDerCancelOrderRequest)));
                    break;
                case "7":
                    JSONObject accountBalanceRequest = (JSONObject)jsonObject.get("CashAccountBalanceRequest");
                    AccountBalanceRequest dataAccountBalanceRequest = g.fromJson(accountBalanceRequest.toJSONString(),AccountBalanceRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetCashBalance(dataAccountBalanceRequest)));
                    break;
                case "8":
                    JSONObject derAccountBalanceRequest = (JSONObject)jsonObject.get("DerCashAccountBalanceRequest");
                    AccountBalanceRequest dataDerAccountBalanceRequest = g.fromJson(derAccountBalanceRequest.toJSONString(),AccountBalanceRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetDerivativeBalance(dataDerAccountBalanceRequest)));
                    break;
                case "9":
                    JSONObject PPMMRAccountRequest = (JSONObject)jsonObject.get("PPMMRAccountRequest");
                    PPMMRAccountRequest dataPPMMRAccountRequest = g.fromJson(PPMMRAccountRequest.toJSONString(),PPMMRAccountRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetPPMMRAccount(dataPPMMRAccountRequest)));
                    break;
                case "10":
                    JSONObject accountPositionRequest = (JSONObject)jsonObject.get("StockAccountPositionRequest");
                    AccountPositionRequest dataAccountPositionRequest = g.fromJson(accountPositionRequest.toJSONString(),AccountPositionRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetStockPosition(dataAccountPositionRequest)));
                    break;
                case "11":
                    JSONObject derAccountPositionRequest = (JSONObject)jsonObject.get("DerAccountPositionRequest");
                    AccountPositionRequest dataDerAccountPositionRequest = g.fromJson(derAccountPositionRequest.toJSONString(),AccountPositionRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetDerivativePosition(dataDerAccountPositionRequest)));
                    break;
                case "12":
                    JSONObject maxBuyQtyRequest = (JSONObject)jsonObject.get("MaxBuyQtyRequest");
                    MaxQtyRequest dataMaxBuyQtyRequest = g.fromJson(maxBuyQtyRequest.toJSONString(),MaxQtyRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetMaxBuyQty(dataMaxBuyQtyRequest)));
                    break;
                case "13":
                    JSONObject maxSellQtyRequest = (JSONObject)jsonObject.get("MaxSellQtyRequest");
                    MaxQtyRequest dataMaxSellQtyRequest = g.fromJson(maxSellQtyRequest.toJSONString(),MaxQtyRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetMaxSellQty(dataMaxSellQtyRequest)));
                    break;
                case "14":
                    JSONObject orderHistoryRequest = (JSONObject)jsonObject.get("OrderHistoryRequest");
                    OrderHistoryRequest dataOrderHistoryRequest = g.fromJson(orderHistoryRequest.toJSONString(),OrderHistoryRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetOrderHistory(dataOrderHistoryRequest)));
                    break;
                case "15":
                    JSONObject orderBookRequest = (JSONObject)jsonObject.get("OrderBookRequest");
                    OrderBookRequest dataOrderBookRequest = g.fromJson(orderBookRequest.toJSONString(),OrderBookRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetOrderBook(dataOrderBookRequest)));
                    break;
                case "16":
                    JSONObject auditOrderBookRequest = (JSONObject)jsonObject.get("AuditOrderBookRequest");
                    AuditOrderBookRequest dataAuditOrderBookRequest = g.fromJson(auditOrderBookRequest.toJSONString(),AuditOrderBookRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetAuditOrderBook(dataAuditOrderBookRequest)));
                    break;
                case "17":
                    JSONObject CIAAmountRequest = (JSONObject)jsonObject.get("CIAAmountRequest");
                    CIAAmountRequest dataCIAAmountRequest = g.fromJson(CIAAmountRequest.toJSONString(),CIAAmountRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetCashInAdvanceAmount(dataCIAAmountRequest)));
                    break;
                
                case "18":
                    JSONObject unsettledSoldTransactionRequest = (JSONObject)jsonObject.get("UnsettledSoldTransactionRequest");
                    UnsettledSoldTransactionRequest dataUnsettledSoldTransactionRequest = g.fromJson(unsettledSoldTransactionRequest.toJSONString(),UnsettledSoldTransactionRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetUnsettleSoldTransaction(dataUnsettledSoldTransactionRequest)));
                    break;

                case "19":
                    JSONObject cashTransferHistoryRequest = (JSONObject)jsonObject.get("CashTransferHistoryRequest");
                    CashTransferHistoryRequest dataCashTransferHistoryRequest = g.fromJson(cashTransferHistoryRequest.toJSONString(),CashTransferHistoryRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetCashTransferHistory(dataCashTransferHistoryRequest)));
                    break;

                case "20":
                    JSONObject CIAHistoryRequest = (JSONObject)jsonObject.get("CIAHistoryRequest");
                    CIAHistoryRequest dataCIAHistoryRequest = g.fromJson(CIAHistoryRequest.toJSONString(),CIAHistoryRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetCIAHistory(dataCIAHistoryRequest)));
                    break;

                case "21":
                    JSONObject estimateCashInAdvanceFeeRequest = (JSONObject)jsonObject.get("EstimateCashInAdvanceFeeRequest");
                    EstimateCashInAdvanceFeeRequest dataEstimateCashInAdvanceFeeRequest = g.fromJson(estimateCashInAdvanceFeeRequest.toJSONString(),EstimateCashInAdvanceFeeRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetEstimateCashInAdvanceFee(dataEstimateCashInAdvanceFeeRequest)));
                    break;

                case "22":
                    JSONObject cashTransferVSDRequest = (JSONObject)jsonObject.get("CashTransferVSDRequest");
                    CashTransferVSDRequest dataCashTransferVSDRequest = g.fromJson(cashTransferVSDRequest.toJSONString(),CashTransferVSDRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.CashTransferVSD(dataCashTransferVSDRequest)));
                    break;

                case "23":
                    JSONObject cashTransferRequest = (JSONObject)jsonObject.get("CashTransferRequest");
                    CashTransferRequest dataCashTransferRequest = g.fromJson(cashTransferRequest.toJSONString(),CashTransferRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.CashTransferInternal(dataCashTransferRequest)));
                    break;

                case "24":
                    JSONObject createCashInAdvanceRequest = (JSONObject)jsonObject.get("CreateCashInAdvanceRequest");
                    CreateCashInAdvanceRequest dataCreateCashInAdvanceRequest = g.fromJson(createCashInAdvanceRequest.toJSONString(),CreateCashInAdvanceRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.CreateCashInAdvance(dataCreateCashInAdvanceRequest)));
                    break;
                
                case "25":
                    JSONObject transferableStockRequest = (JSONObject)jsonObject.get("TransferableStockRequest");
                    TransferableStockRequest dataTransferableStockRequest = g.fromJson(transferableStockRequest.toJSONString(),TransferableStockRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetStockTransferable(dataTransferableStockRequest)));
                    break;

                case "26":
                    JSONObject stockTransferHistoryRequest = (JSONObject)jsonObject.get("StockTransferHistoryRequest");
                    StockTransferHistoryRequest dataStockTransferHistoryRequest = g.fromJson(stockTransferHistoryRequest.toJSONString(),StockTransferHistoryRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetStockTransferHistories(dataStockTransferHistoryRequest)));
                    break;

                case "27":
                    JSONObject stockTransferRequest = (JSONObject)jsonObject.get("StockTransferRequest");
                    StockTransferRequest dataStockTransferRequest = g.fromJson(stockTransferRequest.toJSONString(),StockTransferRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.StockTransfer(dataStockTransferRequest)));
                    break;

                case "28":
                    JSONObject dividendRequest = (JSONObject)jsonObject.get("DividendRequest");
                    DividendRequest dataDividendRequest = g.fromJson(dividendRequest.toJSONString(),DividendRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetOrsDividend(dataDividendRequest)));
                    break;

                case "29":
                    JSONObject exercisableQuantityRequest = (JSONObject)jsonObject.get("ExercisableQuantityRequest");
                    ExercisableQuantityRequest dataExercisableQuantityRequest = g.fromJson(exercisableQuantityRequest.toJSONString(),ExercisableQuantityRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetOrsExercisableQuantity(dataExercisableQuantityRequest)));
                    break;

                case "30":
                    JSONObject onlineRightSubscriptionHistoryRequest = (JSONObject)jsonObject.get("OnlineRightSubscriptionHistoryRequest");
                    OnlineRightSubscriptionHistoryRequest dataOnlineRightSubscriptionHistoryRequest = g.fromJson(onlineRightSubscriptionHistoryRequest.toJSONString(),OnlineRightSubscriptionHistoryRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetOrsHistory(dataOnlineRightSubscriptionHistoryRequest)));
                    break;

                case "31":
                    JSONObject createOnlineRightSubscriptionRequest = (JSONObject)jsonObject.get("CreateOnlineRightSubscriptionRequest");
                    CreateOnlineRightSubscriptionRequest dataCreateOnlineRightSubscriptionRequest = g.fromJson(createOnlineRightSubscriptionRequest.toJSONString(),CreateOnlineRightSubscriptionRequest.class);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.OrsCreate(dataCreateOnlineRightSubscriptionRequest)));
                    break;

                case "35":
                    return;
                default:
                    System.out.println("Input again!");
            }

        }
    }

    public static void FCTradingStreaming() throws Exception {
        JSONParser parser = new JSONParser();
        //Object obj = parser.parse(new FileReader("C:\\Users\\hoaht\\Desktop\\fctrading.json"));
        Object obj = parser.parse(new FileReader(Paths.get("").toAbsolutePath().toString() + "/fctrading.json"));
        JSONObject jsonObject = (JSONObject)obj;
       
        FCTradingClient client = new FCTradingClient((String)jsonObject.get("consumerId")
                , (String)jsonObject.get("consumerSecret")
                , (String)jsonObject.get("privateKey")
                , (String)jsonObject.get("code")
                , (String)jsonObject.get("url"));
        client.init();
        FCTradingStreaming streaming = new FCTradingStreaming(client, (String)jsonObject.get("streaming_url"));
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

    

    