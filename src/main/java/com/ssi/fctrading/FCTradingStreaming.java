package com.ssi.fctrading;
import com.google.gson.JsonElement;
import  microsoft.aspnet.signalr.client.hubs.*;
import  microsoft.aspnet.signalr.client.*;
import  microsoft.aspnet.signalr.client.http.*;
import microsoft.aspnet.signalr.client.transport.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FCTradingStreaming {
    private FCTradingClient _client;
    private  String _url;
    HubConnection _hubConnection;
    HubProxy _hubProxy;
    Subscription _subscription;
    private  MessageReceivedHandler mOnReceived;
    public FCTradingStreaming(FCTradingClient client, String url) throws Exception {
        _client = client;
        if(url.endsWith("/"))
            _url = url.substring(0,url.length() -1) + "/v2.0";
        else
            _url = url + "/v2.0";
        final String access_token = _client.GetAccessToken();
        String queryString = "access_token="+access_token;
        //_hubConnection = new HubConnection(_url, queryString, true, new NullLogger());
        _hubConnection = new HubConnection(_url, queryString, true, new Logger(){
            @Override
            public void log(java.lang.String arg0, microsoft.aspnet.signalr.client.LogLevel arg1){
                //System.out.println(arg0);
            }
        }){
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> headers = super.getHeaders();
                headers.put("Authorization", "Bearer " + access_token);
                return headers;
            }
        };

        Credentials credentials = new Credentials() {
            @Override
            public void prepareRequest(Request request) {
                request.addHeader("Authorization", "Bearer " + access_token);
            }
        };
        _hubConnection.setCredentials(credentials);
        _hubProxy =_hubConnection.createHubProxy(API.HUB_NAME);
        _subscription =  _hubProxy.subscribe(API.HUB_EVENT);
        _subscription.addReceivedHandler(new Action<JsonElement[]>() {
            @Override
            public void run(JsonElement[] obj) throws Exception {
                if(mOnReceived != null)
                    for (JsonElement jsonElement: obj
                    ) {
                        mOnReceived.onMessageReceived(jsonElement);
                    }
            }
        });
    }
    public void onError(ErrorCallback callback){
        _hubConnection.error(callback);
    }
    public void onConnected(Runnable callback){
        _hubConnection.connected(callback);
    }
    public void onClosed(Runnable callback){
        _hubConnection.closed(callback);
    }
    public void onStateChanged(StateChangedCallback handler){
        _hubConnection.stateChanged(handler);
    }
    public void onReconnecting(Runnable handler) {
        _hubConnection.reconnecting(handler);
    }

    public void onReconnected(Runnable handler) {
        _hubConnection.reconnected( handler);
    }
    public void onReceived(MessageReceivedHandler handler) {
       mOnReceived = handler;
    }
    public  void start() throws Exception {
       _hubConnection.start(new WebsocketTransport(new NullLogger()));
    }
    public static void main(String[] args) throws Exception {

                FCTradingClient client = new FCTradingClient("", ""
                , ""
                , ""
                , "http://192.168.213.98:1150"
                , true
                , 0
        );
        FCTradingStreaming streaming = new FCTradingStreaming(client, "https://fc-tradehub-uat.ssi.com.vn");
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
