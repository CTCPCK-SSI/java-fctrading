
package com.ssi.fctrading;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import com.ssi.fctrading.DataContract.*;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

/**
 * The main class.
 *
 * @author ducdv@ssi.com.vn
 */
public class FCTradingClient {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    // PRIVATE
    private final String _consumerId;
    private final String _consumerSecret;
    private final PrivateKey _privateKey;
    private final String _url;
    private final OkHttpClient _client = new OkHttpClient();
    private AccessTokenResponse _accessToken = null;
    private DecodedJWT _decodedJwt = null;
    // PUBLIC
    private boolean _isVerify2FA = false;
    /**
     * Main class.
     */
    public FCTradingClient(String consumerId, String consumerSecret, String privateKey, String url)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        _consumerId = consumerId;
        _consumerSecret = consumerSecret;
        _privateKey = Helper.importPrivateKey(privateKey);
        if (url.endsWith("/"))
            _url = url.substring(0, url.length() - 1);
        else
            _url = url;
    }
    boolean isJWTExpired() {
        if(_decodedJwt == null)
            return true;
        Date expiresAt = _decodedJwt.getExpiresAt();
        return expiresAt.before((new Date()));
    }
    public Response<AccessTokenResponse> GetAccessToken(String code, int twoFactorType, boolean isSave) throws Exception {
        AccessTokenRequest req = new AccessTokenRequest();
        req.consumerID = _consumerId;
        req.consumerSecret = _consumerSecret;
        req.twoFactorType = twoFactorType;
        req.code = code;
        req.isSave = isSave;
        ObjectMapper objectMapper = new ObjectMapper();
        String json = new ObjectMapper().writeValueAsString(req);
        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder()
                .url(_url + API.ACCESS_TOKEN)
                .post(body)
                .build();
        try (okhttp3.Response response = _client.newCall(request).execute()) {
            String rString = response.body().string();
            Response<AccessTokenResponse> access = objectMapper.readValue(rString,
                    new TypeReference<Response<AccessTokenResponse>>() {
                    });
            if (access.status == 200) {
                _accessToken = access.data;
                _decodedJwt = JWT.decode(_accessToken.accessToken);
                _isVerify2FA = isSave;
            } 
            return access;
        } catch (Exception e) {
            throw e;
        }
    }
    public String GetAccessToken() throws Exception {
        if(isJWTExpired())
            GetAccessToken("", 0, false);
        return _accessToken.accessToken;
    }
    public boolean IsVerify2FA(){
        return _isVerify2FA;
    }
    public void init() throws Exception {
        GetAccessToken();
    }

    public <TRequest, TResponse> Response<TResponse> post(TRequest reqObj, String urlPath,
            Class<TResponse> classOfResponse) throws Exception {
        assert _accessToken != null;
        ObjectMapper objectMapper = new ObjectMapper();
        String payLoad = objectMapper.writeValueAsString(reqObj);
        String signature = Helper.sign(_privateKey, payLoad.getBytes(StandardCharsets.UTF_8));

        RequestBody body = RequestBody.create(payLoad, JSON);
        Request request = new Request.Builder()
                .url(_url + urlPath)
                .addHeader("Authorization", "Bearer " + GetAccessToken())
                .addHeader("X-Signature", signature)
                .post(body)
                .build();
        try (okhttp3.Response response = _client.newCall(request).execute()) {
            String rString = response.body().string();
            return objectMapper.readValue(rString, new TypeReference<Response<TResponse>>() {
            });
        } catch (Exception e) {
            throw e;
        }
    }

    public <TRequest, TResponse> Response<TResponse> get(TRequest reqObj, String urlPath,
            Class<TResponse> classOfResponse) throws Exception {
        assert _accessToken != null;
        ObjectMapper objectMapper = new ObjectMapper();
        String param = "";
        if(reqObj != null)
            param = "?" + objectMapper.convertValue(reqObj, UriFormat.class).toString();
        Request request = new Request.Builder()
                .url(_url + urlPath + param)
                .addHeader("Authorization", "Bearer " + GetAccessToken())
                .get()
                .build();
        try (okhttp3.Response response = _client.newCall(request).execute()) {
            String rString = response.body().string();
            return objectMapper.readValue(rString, new TypeReference<Response<TResponse>>() {
            });
        }
    }

    public Response<NewOrderResponse> NewOrder(NewOrderRequest req) throws Exception {
        return post(req, API.NEW_ORDER, NewOrderResponse.class);
    }

    public Response<ModifyOrderResponse> ModifyOrder(ModifyOrderRequest req) throws Exception {
        return post(req, API.MODIFY_ORDER, ModifyOrderResponse.class);
    }

    public Response<CancelOrderResponse> CancelOrder(CancelOrderRequest req) throws Exception {
        return post(req, API.CANCEL_ORDER, CancelOrderResponse.class);
    }

    public Response<NewOrderResponse> DerNewOrder(NewOrderRequest req) throws Exception {
        return post(req, API.NEW_ORDER, NewOrderResponse.class);
    }

    public Response<ModifyOrderResponse> DerModifyOrder(ModifyOrderRequest req) throws Exception {
        return post(req, API.MODIFY_ORDER, ModifyOrderResponse.class);
    }

    public Response<CancelOrderResponse> DerCancelOrder(CancelOrderRequest req) throws Exception {
        return post(req, API.CANCEL_ORDER, CancelOrderResponse.class);
    }

    public Response<DerivativeBalanceResponse> GetDerivativeBalance(AccountBalanceRequest req) throws Exception {
        return get(req, API.GET_DER_ACCOUNT_BALANCE, DerivativeBalanceResponse.class);
    }

    public Response<CashBalanceResponse> GetCashBalance(AccountBalanceRequest req) throws Exception {
        return get(req, API.GET_ACCOUNT_BALANCE, CashBalanceResponse.class);
    }

    public Response<PPMMRAccountResponse> GetPPMMRAccount(PPMMRAccountRequest req) throws Exception {
        return get(req, API.GET_PPMMRACCOUNT, PPMMRAccountResponse.class);
    }

    public Response<StockPositionResponse> GetStockPosition(AccountPositionRequest req) throws Exception {
        return get(req, API.GET_STOCK_POSITION, StockPositionResponse.class);
    }

    public Response<DerivativePositionResponse> GetDerivativePosition(AccountPositionRequest req) throws Exception {
        return get(req, API.GET_DER_POSITION, DerivativePositionResponse.class);

    }

    public Response<MaxBuyQtyResponse> GetMaxBuyQty(MaxQtyRequest req) throws Exception {
        return get(req, API.GET_MAX_BUY_QUANTITY, MaxBuyQtyResponse.class);
    }

    public Response<MaxSellQtyResponse> GetMaxSellQty(MaxQtyRequest req) throws Exception {
        return get(req, API.GET_MAX_SELL_QUANTITY, MaxSellQtyResponse.class);
    }

    public Response<OrderHistoryResponse> GetOrderHistory(OrderHistoryRequest req) throws Exception {
        return get(req, API.GET_ORDER_HISTORY, OrderHistoryResponse.class);
    }

    public Response<OrderBookResponse> GetOrderBook(OrderBookRequest req) throws Exception {
        return get(req, API.GET_ORDER_BOOK, OrderBookResponse.class);
    }

    public Response<AuditOrderBookResponse> GetAuditOrderBook(AuditOrderBookRequest req) throws Exception {
        return get(req, API.GET_AUDIT_ORDER_BOOK, AuditOrderBookResponse.class);
    }

    public Response<CIAAmountResponse> GetCashInAdvanceAmount(CIAAmountRequest req) throws Exception {
        return get(req, API.GET_CIA_AMOUNT, CIAAmountResponse.class);
    }

    public Response<UnsettledSoldTransactionResponse> GetUnsettleSoldTransaction(UnsettledSoldTransactionRequest req)
            throws Exception {
        return get(req, API.GET_UNSETTLE_SOLD_TRANSACTION, UnsettledSoldTransactionResponse.class);
    }

    public Response<CashTransferHistoryResponse> GetCashTransferHistory(CashTransferHistoryRequest req)
            throws Exception {
        return get(req, API.GET_TRANSFER_HISTORIES, CashTransferHistoryResponse.class);
    }

    public Response<CIAHistoryResponse> GetCIAHistory(CIAHistoryRequest req) throws Exception {
        return get(req, API.GET_CIA_HISTORIES, CIAHistoryResponse.class);
    }

    public Response<EstimateCashInAdvanceFeeResponse> GetEstimateCashInAdvanceFee(EstimateCashInAdvanceFeeRequest req)
            throws Exception {
        return get(req, API.GET_EST_CIA_FEE, EstimateCashInAdvanceFeeResponse.class);
    }

    public Response<CashTransferVSDResponse> CashTransferVSD(CashTransferVSDRequest req) throws Exception {
        return post(req, API.VSD_CASH_DW, CashTransferVSDResponse.class);
    }

    public Response<CashTransferResponse> CashTransferInternal(CashTransferRequest req) throws Exception {
        return post(req, API.TRANSFER_INTERNAL, CashTransferResponse.class);
    }

    public Response<CreateCashInAdvanceResponse> CreateCashInAdvance(CreateCashInAdvanceRequest req) throws Exception {
        return post(req, API.CREATE_CIA, CreateCashInAdvanceResponse.class);
    }

    public Response<TransferableStockResponse> GetStockTransferable(TransferableStockRequest req) throws Exception {
        return get(req, API.GET_STOCK_TRANSFERABLE, TransferableStockResponse.class);
    }

    public Response<StockTransferHistoryResponse> GetStockTransferHistories(StockTransferHistoryRequest req)
            throws Exception {
        return get(req, API.GET_STOCK_TRANSFER_HISTORIES, StockTransferHistoryResponse.class);
    }

    public Response<StockTransferResponse> StockTransfer(StockTransferRequest req) throws Exception {
        return post(req, API.STOCK_TRANSFER, StockTransferResponse.class);
    }

    //
    public Response<DividendResponse> GetOrsDividend(DividendRequest req) throws Exception {
        return get(req, API.GET_ORS_DIVIDEND, DividendResponse.class);
    }

    public Response<ExercisableQuantityResponse> GetOrsExercisableQuantity(ExercisableQuantityRequest req)
            throws Exception {
        return get(req, API.GET_ORS_EXERCISABLE_QUANTITY, ExercisableQuantityResponse.class);
    }

    public Response<OnlineRightSubscriptionHistoryResponse> GetOrsHistory(OnlineRightSubscriptionHistoryRequest req)
            throws Exception {
        return get(req, API.GET_ORS_HISTORIES, OnlineRightSubscriptionHistoryResponse.class);
    }

    public Response<CreateOnlineRightSubscriptionResponse> OrsCreate(CreateOnlineRightSubscriptionRequest req)
            throws Exception {
        return post(req, API.ORS_CREATE, CreateOnlineRightSubscriptionResponse.class);
    }
    public Response<RatelimitResponse> GetRatelimit()
            throws Exception {
        return get(null, API.GET_RATELIMIT, RatelimitResponse.class);
    }

    public Response<GetOTPResponse> GetOTP(GetOTPRequest req) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String payLoad = objectMapper.writeValueAsString(req);

        RequestBody body = RequestBody.create(payLoad, JSON);
        Request request = new Request.Builder()
                .url(_url + API.GET_OTP)
                .post(body)
                .build();
        try (okhttp3.Response response = _client.newCall(request).execute()) {
            String rString = response.body().string();
            return objectMapper.readValue(rString, new TypeReference<Response<GetOTPResponse>>() {
            });
        } catch (Exception e) {
            throw e;
        }
    }

    
        
}
