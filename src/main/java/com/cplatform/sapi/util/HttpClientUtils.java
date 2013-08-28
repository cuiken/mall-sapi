package com.cplatform.sapi.util;

import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 搜索相关控制方法 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2013-6-9 上午10:05:00
 * <p>
 * Company: 北京宽连十方数字技术有限公司
 * <p>
 * 
 * @author yangxm@c-platform.com
 */
public class HttpClientUtils {

	private static final String ckey = "abcdefghijklmnopqrstuvwx";
	
	/**
	 * 发送请求报文
	 * 
	 * @param url
	 * @param queryString
	 * @return
	 * @throws Exception
	 */
	public static String httpGet(String url, String param, Integer manager_timeout, Integer so_timeout) throws Exception {
		String responseData = null;
		HttpMethod method = null;
		HttpClient httpClient = null;
		try {
			method = new GetMethod(url + "?" + param);
			httpClient = new HttpClient();
			httpClient.getParams().setContentCharset("GBK");
			httpClient.getParams().setConnectionManagerTimeout(manager_timeout);
			httpClient.getParams().setSoTimeout(so_timeout);
			// 限制重复发起请求
			HttpMethodRetryHandler retryHandler = new DefaultHttpMethodRetryHandler(
					0, false);
			httpClient.getParams().setParameter(HttpClientParams.RETRY_HANDLER,
					retryHandler);

			int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				System.out.println("HttpPost Method failed: "
						+ method.getStatusLine());
			}
			responseData = method.getResponseBodyAsString();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			method.releaseConnection();
			httpClient = null;
		}
		return responseData;
	}

	/**
	 * 发送请求报文
	 * 
	 * @param url
	 * @param queryString
	 * @return
	 * @throws Exception
	 */
	public static String httpPost(String url, String queryString, Integer manager_timeout, Integer so_timeout)
			throws Exception {
		String responseData = null;
		HttpClient httpClient = new HttpClient(
				new MultiThreadedHttpConnectionManager());
		httpClient.getParams().setContentCharset("UTF-8");

		httpClient.getParams().setConnectionManagerTimeout(
				manager_timeout);
		httpClient.getParams().setSoTimeout(so_timeout);
		// 限制重复发起请求
		HttpMethodRetryHandler retryHandler = new DefaultHttpMethodRetryHandler(
				0, false);
		httpClient.getParams().setParameter(HttpClientParams.RETRY_HANDLER,
				retryHandler);

		PostMethod httpPost = new PostMethod(url);
		httpPost.addParameter("Content-Type",
				"application/x-www-form-urlencoded");
		if (queryString != null && !queryString.equals("")) {
			httpPost.setRequestEntity(new ByteArrayRequestEntity(queryString
					.getBytes()));
		}

		try {
			int statusCode = httpClient.executeMethod(httpPost);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("HttpPost Method failed: "
						+ httpPost.getStatusLine());
			}
			InputStream resStream = httpPost.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					resStream));
			StringBuffer resBuffer = new StringBuffer();
			String resTemp = "";
			while ((resTemp = br.readLine()) != null) {
				resBuffer.append(resTemp);
			}
			responseData = resBuffer.toString();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			httpPost.releaseConnection();
			httpClient = null;
		}

		return responseData;
	}

	/**
	 * 获取签名串
	 * 
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static String doSign(String baseStr) throws Exception {
		SecretKeySpec key = new SecretKeySpec(ckey.getBytes("UTF-8"),
				"HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(key);
		byte[] bytes = mac.doFinal(baseStr.substring(1).getBytes("UTF-8"));
		return new String(Base64.encodeBase64(bytes));
	}

	/**
	 * 获取 nonce
	 * 
	 * @return nonce
	 */
	public static String getNonce() {
		return RandomStringUtils.random(6, false, true);
	}

	/**
	 * 获取 timestamp
	 * 
	 * @return timestamp
	 */
	public static String getTimestamp() {
		return String.valueOf(System.currentTimeMillis());
	}

	/**
	 * 组建post方法的body
	 * 
	 * @param keyword
	 * @param store_id
	 * @param type_id
	 * @param brand
	 * @param sort
	 * @param params
	 * @param curpage
	 * @param pagesize
	 * @return
	 * @throws Exception
	 */
	public static JSONObject makeJsonBody(String keyword, String region_code,
			String type_id, String sort,
			String curpage, int pagesize) throws Exception {
		String jsonStr = "{\"query\":\"goodsList\"}";
		JSONObject json = JSONObject.fromObject(jsonStr);
		String nonce_ = getNonce();
		json.put("nonce", nonce_);
		json.put("ckey", "uniongoods");
		json.put("timestamp", getTimestamp());
		if (type_id.equals("0")) {
			type_id = "";
		}
		json.put("type_id", type_id);
		json.put("keyword", keyword);
//		json.put("store_id", store_id);
		json.put("region_code",region_code);
//		json.put("brand", brand);
//		json.put("params", params);// intel;AMD
		json.put("sort", sort);
		json.put("page", curpage);
		json.put("pagesize", pagesize + "");

		String baseStr = json.toString() + nonce_;
		System.out.println(baseStr);
		try {
			json.put("signature", doSign(baseStr));
		} catch (Exception e) {
			throw new Exception(e);
		}
		return json;
	}

//	public static void main(String[] args) {
//		try {
//			JSONObject json = makeJsonBody("图片", "729", "320500", "1689", "戴尔", "1G,AMD",
//					"0", "1", 20);
//
//			String url = "http://192.168.1.204:8080/mall-back/if_item/info?saleId=200070";
//
//			System.out.println(new String(Base64.decodeBase64(httpPost(
//                    url,
//                    new String(Base64.encodeBase64(json.toString().getBytes(
//                            "UTF-8"))), 1000, 1000).getBytes("UTF-8")), "UTF-8"));
//			String s = httpGet(
//					"http://192.168.1.204:8080/mall-back/if_item/info",
//					"saleId=200070",1000,1000);
//			JSONObject ss = JSONObject.fromObject(s);
//			System.out.println(ss.getJSONObject("item").getString("id"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * 判断是否是信任ip
	 * 
	 * @param trustedIp
	 *            ：以逗号分隔的ip地址
	 * @param remoteIp
	 *            ：需验证的ip地址
	 * @return
	 * 
	 */
	public static boolean isTrustIp(String remoteIp, String trustedIp) {

		boolean flag = false;
		if (StringUtils.isEmpty(trustedIp)) {
			flag = true;
		} else {

			String[] ips = trustedIp.split(",");

			for (String string : ips) {

				if (StringUtils.equals(remoteIp, string)) {
					flag = true;
					break;
				}

			}
		}

		return flag;
	}

}
