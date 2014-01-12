package big.guru.book;


import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import big.guru.book.utils.HttpUtils;
import big.guru.book.utils.HttpUtils.ContentType;
import big.guru.book.widget.ActionBar;
import big.guru.book.widget.ActionBar.Action;

@SuppressLint({ "ParserError", "HandlerLeak" })
public class ArticleActivity extends Activity {
	
	TextView content;
	
	

	
	private  Handler handler;  
	public static final String EXTRA_EXAMPLE_TYPE = "EXTRA_EXAMPLE_TYPE";
	String  url  ;
	public enum ExampleType {
		SwipeyTabs, IndicatorLine, FixedTabs, FixedIconTabs, ScrollingTabs
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article);
		Intent intent = this.getIntent();
		url = intent.getExtras().getString("url");
		final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
	     content = (TextView) this.findViewById(R.id.content);  
	     content.setMovementMethod(ScrollingMovementMethod.getInstance());
	    actionBar.setTitle(intent.getExtras().getString("title"));
	    actionBar.addAction(new  Action(){

			
			public int getDrawable() {
				
				return R.drawable.ic_refresh;
			}

			
			public void performAction(View view) {
				new  Thread(new  GetRun(handler)).start();
				actionBar.setProgressBarVisibility(View.VISIBLE);
			}});
	    actionBar.addAction(new  Action(){
	    	
	    	
	    	public int getDrawable() {
	    		
	    		return R.drawable.btn_close_normal;
	    	}
	    	
	    	
	    	public void performAction(View view) {
	    		ArticleActivity.this.finish();
	    	}});
	    	
	    	handler =  new  Handler(){

			@Override
			public void handleMessage(Message msg) {
				
				super.handleMessage(msg);
				Bundle bun = msg.getData();
				CharSequence cs = bun.getCharSequence("data");
				String[]  strs = cs.toString().split("####");
				//String[]  aas = strs[1].split("@@");
				if(strs.length>1)
				{
					content.setText(strs[1].replaceAll("&nbsp;", " ").replaceAll("<wbr>"," ").replaceAll("<br>", "\n").replaceAll("<br />","\n"));
				}
				else
				{
					content.setText(strs[1].replaceAll("&nbsp;", " ").replaceAll("<wbr>"," ").replaceAll("<br>", "\n").replaceAll("<br />","\n"));
				}
			
				actionBar.setProgressBarVisibility(View.INVISIBLE);
				
			}
	    	
	    };
	    new  Thread(new  GetRun(handler)).start();
		actionBar.setProgressBarVisibility(View.VISIBLE);
	}
	
	
	 
	class  GetRun implements Runnable{
		private Handler han ;
		public  GetRun(Handler h)
		{
			han  =h;
		}
		
		public void run() {
			try {
				Log.d("ret", "start   jsjjsdhjsd");
				CharSequence cs = HttpUtils.downloadViaHttp(url, ContentType.HTML);
				Log.d("ret", cs.toString());
				Message  msg =  han.obtainMessage();
				Bundle data =  new  Bundle();
				data.putCharSequence("data", cs);
				msg.setData(data);
				msg.sendToTarget();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	
}
