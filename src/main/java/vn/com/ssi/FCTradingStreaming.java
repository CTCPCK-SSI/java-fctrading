package vn.com.ssi;
import com.google.gson.JsonElement;
import  microsoft.aspnet.signalr.client.hubs.*;
import  microsoft.aspnet.signalr.client.*;
import  microsoft.aspnet.signalr.client.http.*;
import microsoft.aspnet.signalr.client.transport.*;
import java.util.Scanner;

public class FCTradingStreaming {
    private FCTradingClient _client;
    private  String _url;
    HubConnection _hubConnection;
    HubProxy _hubProxy;
    Subscription _subscription;
    public FCTradingStreaming(FCTradingClient client, String url){
        _client = client;
        _url = url;
    }
    public  void start(){
        String queryString = "access_token="+_client.GetAccessToken();
        _hubConnection = new HubConnection(_url, queryString, true, new Logger(){
            @Override
            public void log(String message, LogLevel level){
                System.out.println("Log: " + message);
            }
        });
        Credentials credentials = new Credentials() {

            @Override
            public void prepareRequest(Request request) {
                request.addHeader("Authorization", "Bearer " + _client.GetAccessToken());
            }
        };
       _hubConnection.setCredentials(credentials);
        _hubProxy =_hubConnection.createHubProxy("BroadcastHubV2");
        _subscription =  _hubProxy.subscribe("Broadcast");
        _subscription.addReceivedHandler(new Action<JsonElement[]>(){
            @Override
            public void run(JsonElement[] obj) throws Exception {
                System.out.println(obj[0].toString());
            }
        });
        // Subscribe to the error event
       _hubConnection.error(new ErrorCallback() {

            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
            }
        });
        // Subscribe to the connected event
       _hubConnection.connected(new Runnable() {
            @Override
            public void run() {
                System.out.println("CONNECTED");
            }
        });

        // Subscribe to the closed event
       _hubConnection.closed(new Runnable() {
            @Override
            public void run() {
                System.out.println("DISCONNECTED");
            }
        });

        // Start the connection
       _hubConnection.start(new WebsocketTransport(new Logger(){
                   @Override
                   public void log(String message, LogLevel level){
                       System.out.println("Log: " + message);
                   }
               }))
                .done(new Action<Void>() {
                    @Override
                    public void run(Void obj) throws Exception {
                        System.out.println("Done Connecting!");
                    }
                });
        // Subscribe to the received event

    }
    public static void main(String[] args) throws Exception {

                FCTradingClient client = new FCTradingClient("", ""
                , ""
                , ""
                , "http://192.168.213.98:1150"
                , false
        );
        client.init();
        FCTradingStreaming streaming = new FCTradingStreaming(client, "http://192.168.213.98:1151/v2.0");
        streaming.start();
        // Read lines and send them as messages.
        Scanner inputReader = new Scanner(System.in);
        String line = inputReader.nextLine();
        while (!"exit".equals(line)) {

            line = inputReader.next();
        }
    }

}
