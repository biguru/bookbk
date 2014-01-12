package big.guru.book.common;

import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import big.guru.book.utils.HttpUtils;

public class NetRequest implements Runnable {

	private Handler mHandler;
	private String  url;
	public NetRequest(Handler han, String u)
	{
		mHandler =  han;
		url = u;
	}
	
	
	public void run() {
		
		JSONObject  jso =  HttpUtils.postJson(url, null);
		Log.d("ret", jso.toString());
		Message  msg = mHandler.obtainMessage();
		msg.obj =  jso;
		msg.sendToTarget();
	}

}
