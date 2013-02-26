package big.guru.book.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.drawable.BitmapDrawable;

public class HttpUtils {

	public  static JSONObject  getJson(String  url,Map<String, String> param)
	{
		String result = get(url, param);
		try 
		{
			return  new JSONObject().getJSONObject(result);
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
		return  null;
	}
	
	public static  String    get(String url, Map<String, String> map)
	{
		List<BasicNameValuePair> params =  new  LinkedList<BasicNameValuePair>();
		if(null!=map)
		{
			for(Entry<String, String> en:map.entrySet())
			{
				params.add(new BasicNameValuePair(en.getKey(), en.getValue()));
			}
		}
		String param =  URLEncodedUtils.format(params, "utf-8");
		HttpGet method =  new  HttpGet(url + "?" + param);
		HttpClient client =  new  DefaultHttpClient();
		try {
			HttpResponse response  = client.execute(method);
			int  code = response.getStatusLine().getStatusCode();
			if(code==200)
				return EntityUtils.toString(response.getEntity(), "utf-8");
			
		} catch (ClientProtocolException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return  "";
	}
	
	public  static  JSONObject postJson(String url, Map<String, String> map)
	{
		String result = post(url, map);
		try 
		{
			return  new JSONObject().getJSONObject(result);
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
		return  null; 
	}
	
	public static  String  post(String url, Map<String, String> map)
	{
		List<BasicNameValuePair> params =  new  LinkedList<BasicNameValuePair>();
		if(null!=map)
		{
			for(Entry<String, String> en:map.entrySet())
			{
				params.add(new BasicNameValuePair(en.getKey(), en.getValue()));
			}
		}
		HttpPost method =  new  HttpPost(url);
		
		HttpClient client =  new  DefaultHttpClient();
		try {
			method.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			HttpResponse response  = client.execute(method);
			int  code = response.getStatusLine().getStatusCode();
			if(code==200)
				return EntityUtils.toString(response.getEntity(), "utf-8");
			
		} catch (ClientProtocolException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return  "";
	}
	
	public static BitmapDrawable  getImg(String  url)
	{
		BitmapDrawable img = null;
		HttpURLConnection  con = null;
		try {
			URL src = new URL(url);
			con  = (HttpURLConnection) src.openConnection();
			img = new  BitmapDrawable(con.getInputStream());
			
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(con!=null)
			con.disconnect();
		}
		return  img;
	}
}
