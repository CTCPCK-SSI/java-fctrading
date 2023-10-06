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

                FCTradingClient client = new FCTradingClient("dd11c30e98494385a180f6ebae321272", "7526cf28e8024d249615ec57b940bec0"
                , "PFJTQUtleVZhbHVlPjxNb2R1bHVzPmxnM3Y5WitscUxDVVZZdU8xMkM4QVhRQ0Ruems4SVh4bWwxWnM0WC92Zyt2WEZTZkl0ekRxSEhOT2R1ekozZ0RETVE0aGJrRmhJaHlHMXFGcE84UmtaTlVJMTJjbmdReGQvczAxK0Yyb0RQWm5ua2hCQkF5TkVZTVR5Z21aMGtlZEh3UDh3RDdpRm96c3NKK1lvRERTWXFpb1BPUFFoUEg2bHdmR2xZQ0ZiMD08L01vZHVsdXM+PEV4cG9uZW50PkFRQUI8L0V4cG9uZW50PjxQPnYwL2RrWk5XVzQvcjBub1YvSHZPc29Ta0lsbkYzbGNlcDhtTmNYTS9pWVFUdXJUZE1zZ3YySFZVd3ZxM3hwM0RpUk9ZRmJQYTRvUk9YdFdmQTdjNWl3PT08L1A+PFE+eU1ySWdWb1hZbE9welg2S2wrSU5UaXlxNU04SVJINmNsYkRMRVRjZ3lMOHcreklDYTVzN21YVm53QUI3dWJyc0s0a0tqNE1uOXZ4OGVNb3NHVWFHMXc9PTwvUT48RFA+Ym13bnBEWFptNEhIaUxYazZOV0JLWEw3MWNQaFBJTW1jNHdYTXN0bGVmY2MrQ0FhcUl2NWFjUFhSNjgwMGRRUG41Z0txVVFsYTZjWUlRN0YrSHdTSHc9PTwvRFA+PERRPmF1UElxVk9VYnp1cWdVS0Q5U2JaZm13NDdXVnBPVTFOZmtLSFY3ckpNUlRXdG1LQTkxZkQrb1dObmFDa1BOTk5Oai9WNTJBaXZsRGEwbEtOOTBhY01RPT08L0RRPjxJbnZlcnNlUT5mbS9KVEl1aldJRmkyWFYwWkNMZkZCOTJUWXdnRGUrSjdMOSthV1dZRk5vSlRIWmZzT3JmREhHZHFKQVUwc2pTRGJJbkk5MWlrTVZJUmdqV2E5dko5QT09PC9JbnZlcnNlUT48RD5GMWFKbU1sQXliRFZFVTBJWldVdE9JaWpmVDlkbUM4cytaWkZTayt3REE2Q2Ryak9tMng4bmVrOVF4OXhpTlQ0bkhhMFY5QUVGSzNvR1dzRjJEWTZwQTJxM2xyTVRwVFFya0VFWjVGOUpaN1RTUy92cSt1RDgyc3EwSkxDYkk3ZVhsclNycW9xQUd6OFZCaGl2ZCt4dE1WOTN5U3VRdjg4YXFGLzc0RnR0QjA9PC9EPjwvUlNBS2V5VmFsdWU+"
                , "12345678"
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
