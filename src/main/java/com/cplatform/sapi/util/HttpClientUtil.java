package com.cplatform.sapi.util;

import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.rmi.UnknownHostException;

/**
 * 搜索相关控制方法 类详细说明.
 * <p/>
 * Copyright: Copyright (c) 2013-6-9 上午10:05:00
 * <p/>
 * Company: 北京宽连十方数字技术有限公司
 * <p/>
 *
 * @author yangxm@c-platform.com
 * @author cuikai
 */
public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger("http");

    private static PoolingHttpClientConnectionManager connManager = null;
    private static CloseableHttpClient httpClient = null;

    /**
     * 最大连接数
     */
    private final static int MAX_TOTAL_CONNECTIONS = 100;
    /**
     * 每个路由最大连接数
     */
    private final static int MAX_ROUTE_CONNECTIONS = 50;
    private static final int CONNECT_TIMEOUT = 6000;
    private static final int SOCKET_TIMEOUT = 6000;

    static {
        HttpRequestRetryHandler myRetryHandler = customRetryHandler();
        connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        connManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
        HttpHost localhost = new HttpHost("locahost", 80);
        connManager.setMaxPerRoute(new HttpRoute(localhost), 50);
        httpClient = HttpClients.custom().setConnectionManager(connManager).setRetryHandler(myRetryHandler)
                .build();
    }

    public static String httpGet(String url, String param) throws IOException {

        CloseableHttpResponse response = null;

        HttpGet httpGet = new HttpGet(url + "?" + param);

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT).build();
        httpGet.setConfig(requestConfig);
        try {
            response = httpClient.execute(httpGet);
            int respCode = response.getStatusLine().getStatusCode();
            if (respCode != HttpStatus.SC_OK) {
                logger.error("get请求失败:" + "[" + url + "?" + param + "]状态码：" + respCode);
                throw new Exception("错误码:" + respCode);
            }
            HttpEntity entity = response.getEntity();
            String resp = EntityUtils.toString(entity, "utf-8");
            logger.info("get请求:{}, 响应:{}", url + "?" + param, resp);
            return resp;
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        } finally {
            HttpClientUtils.closeQuietly(response);
            httpGet.releaseConnection();
        }
    }

    public static String httpPost(String url, String queryString, String... headerValue) throws IOException {

        CloseableHttpResponse response = null;

        HttpPost httpPost = new HttpPost(url);

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT).build();
        httpPost.setConfig(requestConfig);

        if (headerValue.length > 0) {
            httpPost.setHeader("KW", headerValue[0]);
        }
        StringEntity myEntity = new StringEntity(queryString, ContentType.APPLICATION_JSON);

        httpPost.setEntity(myEntity);
        try {
            response = httpClient.execute(httpPost);
            int respCode = response.getStatusLine().getStatusCode();
            if (respCode != HttpStatus.SC_OK) {
                logger.error("post请求失败:" + "[" + url + "?" + queryString + "]状态码:" + respCode);
                throw new Exception("错误码:" + respCode);
            }
            HttpEntity entity = response.getEntity();
            String resp = EntityUtils.toString(entity, "utf-8");
            logger.info("post请求:{}, 响应:{}", url + "," + queryString, resp);
            return resp;
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        } finally {
            HttpClientUtils.closeQuietly(response);
            httpPost.releaseConnection();
        }
    }

    private static HttpRequestRetryHandler customRetryHandler() {
        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {

            public boolean retryRequest(
                    IOException exception,
                    int executionCount,
                    HttpContext context) {
                if (executionCount >= 3) {
                    // Do not retry if over max retry count
                    return false;
                }
                if (exception instanceof InterruptedIOException) {
                    // Timeout
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {
                    return true;
                }
                if (exception instanceof UnknownHostException) {
                    // Unknown host
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {
                    // Connection refused
                    return false;
                }
                if (exception instanceof SSLException) {
                    // SSL handshake exception
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    // Retry if the request is considered idempotent
                    return true;
                }
                return false;
            }

        };
        return myRetryHandler;
    }
}
