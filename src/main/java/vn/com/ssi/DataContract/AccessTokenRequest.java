package vn.com.ssi.DataContract;

public class AccessTokenRequest {
    public String consumerID;
    public String consumerSecret;
    public int twoFactorType;
    public String code;
    public boolean isSave;
}
