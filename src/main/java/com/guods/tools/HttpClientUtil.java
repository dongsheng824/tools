package com.guods.tools;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	
	public static final String charset = "UTF-8";

	private static CloseableHttpClient httpClient = HttpClients.createDefault();
	
	public static void post(String url, String json, String token) throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(url);
		//添加头
		httpPost.addHeader("token", token);
		//添加body
		StringEntity entity = new StringEntity(json, charset);
		entity.setContentEncoding(charset);    
		entity.setContentType("application/json");    
		httpPost.setEntity(entity);
		//执行请求
		HttpResponse response = httpClient.execute(httpPost);
		System.out.println("请求返回：" + response.getStatusLine().getStatusCode());
		HttpEntity responseEntity = response.getEntity();
		if (responseEntity != null) {
			System.out.println(EntityUtils.toString(responseEntity, charset));
		}
	}
}
