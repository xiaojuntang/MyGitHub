package com.funi.news.common;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.funi.news.modle.Parameter;

public class Communicate {
	/*
	 * 从服务端获取数据
	 */
	public JSONObject GetDataByWebService() {
		return null;
	}

	/**--------------------------------------------Http-------------------------------------
	 * 通过GET方式发送请求
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String HttpGet(String url, String params) throws Exception {
		String response = null;
		if (null != params && !params.equals(""))
			url += "?" + params;
		int timeoutconn = 3000;
		int timeoutsocket = 5000;
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutconn);
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutsocket);
		// 构造HttpClient的实例
		HttpClient httpClient = new DefaultHttpClient(httpParameters);
		// 创建GET方法的实例
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) // SC_OK = 200
			{
				// 获得返回结果
				response = EntityUtils.toString(httpResponse.getEntity());
			} else {
				response = "返回码：" + statusCode;
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return response;
	}

	/**
	 * 通过POST方式发送请求
	 * @param url URL地址
	 * @param params 参数
	 * @return
	 * @throws Exception
	 */
	public String HttpPost(String url, List<Parameter> params) throws Exception
	{
		Log.i("url", url);
		String response = null;
		int timeoutConnection = 3000;  
		int timeoutSocket = 5000;  
		HttpParams httpParameters = new BasicHttpParams();
	    HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
	    HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);  
		// 构造HttpClient的实例
		HttpClient httpClient = new DefaultHttpClient(httpParameters);  
		HttpPost httpPost = new HttpPost(url);		
		httpPost.addHeader("Accept", "application/json");
		httpPost.addHeader("Content-Type", "application/json; charset=utf-8");// 必须要添加该Http头才能调用WebMethod时返回JSON数据
		
		if (params.size()>=0)
		{
			//设置httpPost请求参数
			//httpPost.setEntity(new UrlEncodedFormEntity(BuildNameValuePair(params),HTTP.UTF_8));
			httpPost.setEntity(BuildNameValuePair2(params));
		}
		//使用execute方法发送HTTP Post请求，并返回HttpResponse对象
		HttpResponse httpResponse = httpClient.execute(httpPost);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if(statusCode==HttpStatus.SC_OK)
		{
			//获得返回结果
			response = EntityUtils.toString(httpResponse.getEntity());
		}
		else
		{
			response = "返回码："+statusCode;
		}
		return response;
	}
	
	/**
	 * 把Parameter类型集合转换成NameValuePair类型集合
	 * @param params 参数集合
	 * @return
	 */
	private List<BasicNameValuePair> BuildNameValuePair(List<Parameter> params)
	{
		List<BasicNameValuePair> result = new ArrayList<BasicNameValuePair>();
		for (Parameter param : params)
		{
			BasicNameValuePair pair = new BasicNameValuePair(param.getName(), param.getValue());
			result.add(pair);
		}
		return result;
	}

	/**--------------------------------------------Http-------------------------------------
	 * 请求数据
	 * @param params
	 * @return
	 * @throws JSONException
	 * @throws UnsupportedEncodingException
	 */
	public HttpEntity BuildNameValuePair2(List<Parameter> params) throws JSONException, UnsupportedEncodingException{
		JSONObject jsonParams = new JSONObject();
		for (Parameter param : params)
		{
			jsonParams.put(param.getName(), param.getValue());			
		}
		return new StringEntity(jsonParams.toString(), "utf8");
	}
}
