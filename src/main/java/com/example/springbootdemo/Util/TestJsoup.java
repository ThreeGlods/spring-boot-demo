package com.example.springbootdemo.Util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import com.google.common.base.Joiner;



public class TestJsoup {
	
	// 定义申请获得的appKey和appSecret
	String appkey = "76305365";
	String secret = "0cd12f3bd76633946336ade52d62c013";
	String proxyIP = "s6.proxy.mayidaili.com";
	int proxyPort = 8123;
	
	public String getAuthHeader() {

		// 创建参数表
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("app_key", appkey);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("GMT+8"));// 使用中国时间，以免时区不同导致认证错误
		paramMap.put("timestamp", format.format(new Date()));
		
		// 对参数名进行排序
		String[] keyArray = paramMap.keySet().toArray(new String[0]);
		Arrays.sort(keyArray);
		
		// 拼接有序的参数名-值串
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(secret);
		for (String key : keyArray) {
			stringBuilder.append(key).append(paramMap.get(key));
		}
		
		stringBuilder.append(secret);
		String codes = stringBuilder.toString();
		
		// MD5编码并转为大写， 这里使用的是Apache codec
		String sign = org.apache.commons.codec.digest.DigestUtils.md5Hex(codes).toUpperCase();
		
		paramMap.put("sign", sign);
		
		// 拼装请求头Proxy-Authorization的值，这里使用 guava 进行map的拼接
		String authHeader = "MYH-AUTH-MD5 " + Joiner.on('&').withKeyValueSeparator("=").join(paramMap);
		
		//System.out.println(authHeader);
		
		return authHeader;

	}
	
	public String ip(String url) {
		
		try {

			//Proxy-Authorization
			Connection.Response res = Jsoup.connect(url).maxBodySize(Integer.MAX_VALUE).header("Proxy-Authorization",getAuthHeader()).proxy(proxyIP,proxyPort).ignoreContentType(true).userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)").followRedirects(false).validateTLSCertificates(false).timeout(10000).method(Connection.Method.GET).execute();
			String carStr = new String(res.bodyAsBytes());
            return carStr;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	public static void main(String[] args) {
		new TestJsoup().ip("https://huodong.weibo.com/netchina2019/people");
    }
}
