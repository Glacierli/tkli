package com.bcli.backend.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.bcli.backend.constant.Constants;


/**
 * HTTP请求工具类
 * @author bcli
 *
 */
public class HttpUtils {

	/**
	 * HTTP POST 请求
	 * @param requestUrl 请求url地址
	 * @param requestData 请求需要发送的数据
	 * @return String
	 */
	public static String do_post(String requestUrl, String requestData) {
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Content-Length", String.valueOf(requestData.length()));
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setConnectTimeout(3000); // 设置发起连接的等待时间，3s
			conn.setReadTimeout(30000); // 设置数据读取超时的时间，30s
			OutputStream out = conn.getOutputStream();
			out.write(requestData.getBytes("utf-8"));
			out.flush();
			out.close();

			// 请求返回的状态
			if (conn.getResponseCode() == 200) {
				// 请求返回的数据
				InputStream in = conn.getInputStream();
				try {
					byte[] responseData = new byte[in.available()];
					in.read(responseData);
					return new String(responseData, "utf-8");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				return "NO_MESSAGE";
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	
	public static String postFile(String url,Map<String, Object> param, File file) throws ClientProtocolException, IOException {
        String res = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(getMutipartEntry(param,file));
        CloseableHttpResponse response = httpClient.execute(httppost);
        HttpEntity entity = response.getEntity();
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            res = EntityUtils.toString(entity, "UTF-8");
            response.close();
        } else {
            res = EntityUtils.toString(entity, "UTF-8");
            response.close();
            throw new IllegalArgumentException(res);
        }
        return res;
    }
 
 private static HttpEntity getMutipartEntry(Map<String, Object> param, File file) throws UnsupportedEncodingException {
        if (file == null) {
            throw new IllegalArgumentException("文件不能为空");
        }
        
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);
        
        multipartEntityBuilder.addBinaryBody("file", file, ContentType.DEFAULT_BINARY, file.getName());
        

        Iterator<String> iterator = param.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            multipartEntityBuilder.addTextBody(key, String.valueOf( param.get(key)));
           // FormBodyPart field = new FormBodyPart(, new StringBody(String.valueOf( param.get(key))));
           // multipartEntity.addPart(field);

        }
        
        HttpEntity httpEntity = multipartEntityBuilder.build();
        return httpEntity;
    }
 
 //https请求------------------------------------------------------------------------------------------------
 
 public static String httpsDoPost(String url, String map) {
    CloseableHttpClient httpClient = null;
     HttpPost httpPost = null;
     String result = null;
     try {
         httpClient =  SSLClient.getSingleSSLConnection();
         httpPost = new HttpPost(url);
         //设置参数
         httpPost.addHeader("Accept", "application/json");
         httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        /* StringEntity stringEntity = new StringEntity(map);
         stringEntity.setContentEncoding("UTF-8");
         stringEntity.setContentType("application/json");
         httpPost.setEntity(stringEntity);*/
         httpPost.setEntity(new StringEntity(map.toString(), Charset.forName("UTF-8")));
         HttpResponse response = httpClient.execute(httpPost);
         if (response != null) {
             HttpEntity resEntity = response.getEntity();
             if (resEntity != null) {
                 result = EntityUtils.toString(resEntity, "UTF-8");
             }
         }
     } catch (Exception ex) {
         ex.printStackTrace();
     }
     return result;
 }

 
 //https文件请求------------------------------------------------------------------------------------------------
	public static String httpsPostFile(String url,Map<String, Object> param, File file) throws ClientProtocolException, IOException {
        String res = null;
        CloseableHttpClient httpClient = null;
		try {
			httpClient = SSLClient.getSingleSSLConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(getMutipartEntry(param,file));
        CloseableHttpResponse response = httpClient.execute(httppost);
        HttpEntity entity = response.getEntity();
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            res = EntityUtils.toString(entity, "UTF-8");
            response.close();
        } else {
            res = EntityUtils.toString(entity, "UTF-8");
            response.close();
            throw new IllegalArgumentException(res);
        }
        return res;
    }
	
	
/*	public static String checkPostMethod(String requestUrl, String requestData){
		String postWay = Constants.POSTWAY;
		if(postWay.equals("https")){
		 return	httpsDoPost(requestUrl,requestData);
		}else{
		return do_post(requestUrl,requestData);
		}
	}*/
	/*
	public static String checkFilePostMethod(String url,Map<String, Object> param, File file){
		String postWay = Constants.POSTWAY;
		String data = "";
 		try {
			if(postWay.equals("https")){
				
				data =  httpsPostFile(url,param,file);
				
			}else{
				data = postFile(url,param,file);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}*/



}
