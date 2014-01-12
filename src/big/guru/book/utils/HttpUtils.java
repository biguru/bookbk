package big.guru.book.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
import android.util.Log;

public class HttpUtils {

	 private static final String TAG = HttpUtils.class.getSimpleName();
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
			
			return  new JSONObject(result);
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
	
	 public enum ContentType {
		    /** HTML-like content type, including HTML, XHTML, etc. */
		    HTML,
		    /** JSON content */
		    JSON,
		    /** Plain text content */
		    TEXT,
		  }

		  /**
		   * Downloads the entire resource instead of part.
		   *
		   * @see #downloadViaHttp(String, HttpHelper.ContentType, int)
		   */
		  public static CharSequence downloadViaHttp(String uri, ContentType type) throws IOException {
		    return downloadViaHttp(uri, type, Integer.MAX_VALUE);
		  }

		  /**
		   * @param uri URI to retrieve
		   * @param type expected text-like MIME type of that content
		   * @param maxChars approximate maximum characters to read from the source
		   * @return content as a {@code String}
		   * @throws IOException if the content can't be retrieved because of a bad URI, network problem, etc.
		   */
		  public static CharSequence downloadViaHttp(String uri, ContentType type, int maxChars) throws IOException {
		    String contentTypes;
		    switch (type) {
		      case HTML:
		        contentTypes = "application/xhtml+xml,text/html,text/*,*/*";
		        break;
		      case JSON:
		        contentTypes = "application/json,text/*,*/*";
		        break;
		      case TEXT:
		      default:
		        contentTypes = "text/*,*/*";
		    }
		    return downloadViaHttp(uri, contentTypes, maxChars);
		  }

		  private static CharSequence downloadViaHttp(String uri, String contentTypes, int maxChars) throws IOException {
		    Log.i(TAG, "Downloading " + uri);
		    URL url = new URL(uri);
		    URLConnection conn = url.openConnection();
		    if (!(conn instanceof HttpURLConnection)) {
		      throw new IOException();
		    }
		    HttpURLConnection connection = (HttpURLConnection) conn;
		    connection.setRequestProperty("Accept", contentTypes);
		    connection.setRequestProperty("Accept-Charset", "utf-8,*");
		    connection.setRequestProperty("User-Agent", "ZXing (Android)");
		    try {
		      try {
		        connection.connect();
		      } catch (NullPointerException npe) {
		        // this is an Android bug: http://code.google.com/p/android/issues/detail?id=16895
		        Log.w(TAG, "Bad URI? " + uri);
		        
		      } catch (IllegalArgumentException iae) {
		        // Also seen this in the wild, not sure what to make of it. Probably a bad URL
		        Log.w(TAG, "Bad URI? " + uri);
		        
		      }
		      int responseCode = 0;
		      try {
		        responseCode = connection.getResponseCode();
		      } catch (NullPointerException npe) {
		        // this is maybe this Android bug: http://code.google.com/p/android/issues/detail?id=15554
		        Log.w(TAG, "Bad URI? " + uri);
		        
		      }
		      if (responseCode != HttpURLConnection.HTTP_OK) {
		        throw new IOException("Bad HTTP response: " + responseCode);
		      }
		      Log.i(TAG, "Consuming " + uri);
		      return consume(connection, maxChars);
		    } finally {
		      connection.disconnect();
		    }
		  }

		  private static String getEncoding(URLConnection connection) {
		    String contentTypeHeader = connection.getHeaderField("Content-Type");
		    if (contentTypeHeader != null) {
		      int charsetStart = contentTypeHeader.indexOf("charset=");
		      if (charsetStart >= 0) {
		        return contentTypeHeader.substring(charsetStart + "charset=".length());
		      }
		    }
		    return "UTF-8";
		  }

		  private static CharSequence consume(URLConnection connection, int maxChars) throws IOException {
		    String encoding = getEncoding(connection);
		    StringBuilder out = new StringBuilder();
		    Reader in = null;
		    try {
		      in = new InputStreamReader(connection.getInputStream(), encoding);
		      char[] buffer = new char[1024];
		      int charsRead;
		      while (out.length() < maxChars && (charsRead = in.read(buffer)) > 0) {
		        out.append(buffer, 0, charsRead);
		      }
		    } finally {
		      if (in != null) {
		        try {
		          in.close();
		        } catch (IOException ioe) {
		          // continue
		        } catch (NullPointerException npe) {
		          // another apparent Android / Harmony bug; continue
		        }
		      }
		    }
		    return out;
		  }
}
