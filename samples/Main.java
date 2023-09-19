package com.ssi.fctrading;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import microsoft.aspnet.signalr.client.MessageReceivedHandler;
import com.ssi.fctrading.DataContract.*;
import com.ssi.fctrading.FCTradingClient;
import com.ssi.fctrading.FCTradingStreaming;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.Scanner;

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
        InputStream jsonStream = Main.class.getResourceAsStream("/fctrading.json");
        Gson gson = new Gson();
        JsonObject config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
        FCTradingClient client = new FCTradingClient(config.get("consumerId").getAsString()
                , config.get("consumerSecret").getAsString()
                , config.get("privateKey").getAsString()
                , config.get("code").getAsString()
                , config.get("url").getAsString());
        client.init();
        String data;
        while (true) {
            System.out.println("1.  NewOrder");
            System.out.println("2.  ModifyOrder");
            System.out.println("3.  CancelOrder");
            System.out.println("4.  GetDerivativeBalance");
            System.out.println("5.  GetCashBalance");
            System.out.println("6.  GetPPMMRAccount");
            System.out.println("7.  GetStockPosition");
            System.out.println("8.  GetDerivativePosition");
            System.out.println("9.  GetMaxBuyQty");
            System.out.println("10. GetMaxSellQty");
            System.out.println("11. GetOrderHistory");
            System.out.println("12. Back");
            Scanner inputReader = new Scanner(System.in);
            String line = inputReader.nextLine();
            switch (line) {
                case "1":
                    jsonStream = Main.class.getResourceAsStream("/fctrading.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("NewOrderRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.NewOrder(gson.fromJson(data, NewOrderRequest.class))));
                    break;
                case "2":
                    jsonStream = Main.class.getResourceAsStream("/fctrading.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("ModifyOrderRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.ModifyOrder(gson.fromJson(data, ModifyOrderRequest.class))));
                    break;
                case "3":
                    jsonStream = Main.class.getResourceAsStream("/fctrading.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("CancelOrderRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.CancelOrder(gson.fromJson(data, CancelOrderRequest.class))));
                    break;
                case "4":
                    jsonStream = Main.class.getResourceAsStream("/fctrading.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("CashAccountBalanceRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetCashBalance(gson.fromJson(data, AccountBalanceRequest.class))));
                    break;
                case "5":
                    jsonStream = Main.class.getResourceAsStream("/fctrading.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("DerCashAccountBalanceRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetDerivativeBalance(gson.fromJson(data, AccountBalanceRequest.class))));
                    break;
                case "6":
                    jsonStream = Main.class.getResourceAsStream("/fctrading.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("PPMMRAccountRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetPPMMRAccount(gson.fromJson(data, PPMMRAccountRequest.class))));
                    break;
                case "7":
                    jsonStream = Main.class.getResourceAsStream("/fctrading.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("StockAccountPositionRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetStockPosition(gson.fromJson(data, AccountPositionRequest.class))));
                    break;
                case "8":
                    jsonStream = Main.class.getResourceAsStream("/fctrading.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("DerAccountPositionRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetDerivativePosition(gson.fromJson(data, AccountPositionRequest.class))));
                    break;
                case "9":
                    jsonStream = Main.class.getResourceAsStream("/fctrading.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("MaxBuyQtyRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetMaxBuyQty(gson.fromJson(data, MaxQtyRequest.class))));
                    break;
                case "10":
                    jsonStream = Main.class.getResourceAsStream("/fctrading.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("MaxSellQtyRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetMaxSellQty(gson.fromJson(data, MaxQtyRequest.class))));
                    break;
                case "11":
                    jsonStream = Main.class.getResourceAsStream("/fctrading.json");
                    config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
                    data = config.get("OrderHistoryRequest").toString();
                    System.out.println("Request: " + data);
                    System.out.println("Response: " + new ObjectMapper().writeValueAsString(client.GetOrderHistory(gson.fromJson(data, OrderHistoryRequest.class))));
                    break;
                case "12":
                    return;
                default:
                    System.out.println("Input again!");
            }

        }
    }

    public static void FCTradingStreaming() throws Exception {
        InputStream jsonStream = Main.class.getResourceAsStream("/fctrading.json");
        Gson gson = new Gson();
        JsonObject config = new JsonParser().parse(read(jsonStream)).getAsJsonObject();
        FCTradingClient client = new FCTradingClient(config.get("consumerId").getAsString()
                , config.get("consumerSecret").getAsString()
                , config.get("privateKey").getAsString()
                , config.get("code").getAsString()
                , config.get("url").getAsString());
        client.init();
        FCTradingStreaming streaming = new FCTradingStreaming(client, config.get("streaming_url").getAsString());
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