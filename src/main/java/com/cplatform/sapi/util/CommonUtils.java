package com.cplatform.sapi.util;

import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Title. <br>
 * Description.
 * <p/>
 * Copyright: Copyright (c) 13-6-9 下午3:05
 * <p/>
 * Company: 北京宽连十方数字技术有限公司
 * <p/>
 * Author: nicky
 * <p/>
 * Version: 1.0
 * <p/>
 */
public class CommonUtils {


    /**
     * Contacts the remote URL and returns the response.
     *
     * @param constructedUrl the url to contact.
     * @param encoding       the encoding to use.
     * @return the response.
     */
    public static String getResponseFromServer(final URL constructedUrl, final String encoding) {
        return getResponseFromServer(constructedUrl, HttpsURLConnection.getDefaultHostnameVerifier(), encoding);
    }


    /**
     * Contacts the remote URL and returns the response.
     *
     * @param url      the url to contact.
     * @param encoding the encoding to use.
     * @return the response.
     */
    public static String getResponseFromServer(final String url, String encoding) {
        try {
            return getResponseFromServer(new URL(url), encoding);
        } catch (final MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Contacts the remote URL and returns the response.
     *
     * @param constructedUrl   the url to contact.
     * @param hostnameVerifier Host name verifier to use for HTTPS connections.
     * @param encoding         the encoding to use.
     * @return the response.
     */
    public static String getResponseFromServer(final URL constructedUrl, final HostnameVerifier hostnameVerifier, final String encoding) {
        URLConnection conn = null;
        try {
            conn = constructedUrl.openConnection();
            if (conn instanceof HttpsURLConnection) {
                ((HttpsURLConnection) conn).setHostnameVerifier(hostnameVerifier);
            }
            final BufferedReader in;

            if (StringUtils.isEmpty(encoding)) {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
            }

            String line;
            final StringBuilder stringBuffer = new StringBuilder(255);

            while ((line = in.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append("\n");
            }
            return stringBuffer.toString();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null && conn instanceof HttpURLConnection) {
                ((HttpURLConnection) conn).disconnect();
            }
        }

    }


    public static String randomKey(int sLen) {
        String base = "1234567890";
        String temp = "";
        int i;
        int p;

        for (i = 0; i < sLen; i++) {
            p = (int) (Math.random() * 10);
            temp += base.substring(p, p + 1);
        }
        return temp;
    }
}
