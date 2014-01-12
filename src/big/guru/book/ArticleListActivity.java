package big.guru.book;


import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import big.guru.book.adapter.ExamplePagerAdapter;
import big.guru.book.utils.HttpUtils;
import big.guru.book.utils.HttpUtils.ContentType;
import big.guru.book.widget.ActionBar;
import big.guru.book.widget.ActionBar.Action;
import big.guru.book.widget.PullToRefreshListView;

@SuppressLint({ "ParserError", "HandlerLeak" })
public class ArticleListActivity extends Activity {
	
	private ViewPager mPager;
	
	PullToRefreshListView  lv;
	
	private PagerAdapter mPagerAdapter;
	private Button  homeBtn;
	private  Handler handler;  
	public static final String EXTRA_EXAMPLE_TYPE = "EXTRA_EXAMPLE_TYPE";
	
	public enum ExampleType {
		SwipeyTabs, IndicatorLine, FixedTabs, FixedIconTabs, ScrollingTabs
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_list);
		initViewPager(3, 0xFFFFFFFF, 0xFF000000);
		final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
	    actionBar.setTitle("Home");
	    homeBtn =  (Button) this.findViewById(R.id.home_btn);
	    homeBtn.setOnClickListener(new  View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ArticleListActivity.this.finish();
			}
		});
	    actionBar.addAction(new  Action(){

			
			public int getDrawable() {
				
				return R.drawable.ic_refresh;
			}

			
			public void performAction(View view) {
				
					
						// TODO Auto-generated method stub
						
						new  Thread(new  GetRun(handler)).start();
						actionBar.setProgressBarVisibility(View.VISIBLE);
				
			}});
	    	
	    	handler =  new  Handler(){

			@Override
			public void handleMessage(Message msg) {
				
				super.handleMessage(msg);
				Bundle bun = msg.getData();
				CharSequence cs = bun.getCharSequence("data");
				String[]  strs = cs.toString().split("####");
				String[]  aas = strs[1].split("@@");
				JSONArray  jsa=  new  JSONArray();
				for(String ss:aas)
				{
					String[] tts =  ss.split("##");
					JSONObject  jso  =  new  JSONObject();
					
					try {
						jso.putOpt("title", tts[0]);
						jso.putOpt("url", tts[1]);
						jso.putOpt("summary", tts[2]);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					jsa.put(jso);
				}
				((ExamplePagerAdapter)mPagerAdapter).setMadapterData(jsa);
			
				
				actionBar.setProgressBarVisibility(View.INVISIBLE);
				
			}
	    	
	    };
	    
	}
	
	private void initViewPager(int pageCount, int backgroundColor, int textColor) 
	{
		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new ExamplePagerAdapter(this, 1, backgroundColor, textColor,1);
		mPager.setAdapter(mPagerAdapter);
		mPager.setCurrentItem(1);
		mPager.setPageMargin(1);
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
				CharSequence cs = HttpUtils.downloadViaHttp("http://blog.sina.com.cn/s/blog_6a6f813101017fac.html", ContentType.HTML);
				//Log.d("ret", cs.toString());
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
