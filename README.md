# FastConnect Trading Java client

## Build
Run:
```shell
mvn clean package
```
## Usage
Add this to pom.xml:
```xml
<dependency>
  <groupId>com.ssi.fastconnect</groupId>
  <artifactId>fctrading</artifactId>
  <version>2.1.0</version>
</dependency>
```
And using:
```java
FCTradingClient client = new FCTradingClient(consumerId, consumerSecret, privateKey, url);
client.init();
FCTradingStreaming streaming = new FCTradingStreaming(client, streaming_url);
streaming.onReceived(new MessageReceivedHandler() {
    @Override
    public void onMessageReceived(JsonElement json) {
        System.out.println("onMessageReceived " + json.getAsString());
    }
});
streaming.start();
```

More than in `samples/Main.java`
## Samples
Add your `consumerId` and `consumerSecret` (get from [iBoard](https://iboard.ssi.com.vn)) to `fctrading.json`, modify function argument

Place `fctrading.json` to folder to execute and run:
```shell
java -jar fctrading-client-sample-2.1.0.jar
```
