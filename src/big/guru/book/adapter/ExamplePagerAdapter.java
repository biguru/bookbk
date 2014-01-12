package big.guru.book.adapter;

import java.util.HashMap;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import big.guru.book.ArticleActivity;
import big.guru.book.R;
import big.guru.book.widget.PullToRefreshListView;
import big.guru.book.widget.PullToRefreshListView.OnRefreshListener;


public class ExamplePagerAdapter extends PagerAdapter {
	
	protected transient Activity mContext;
	public PullToRefreshListView refs = null;
	private int mLength = 0;
	private int mBackgroundColor = 0xFFFFFFFF;
	private int mTextColor = 0xFF000000;
	private ListAdapter mAdapter;
	private int  type;
	private String[] mData = {
	    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"
	};
	
	public ExamplePagerAdapter(Activity context, int length, int backgroundColor, int textColor) {
		mContext = context;
		mLength = length;
		mBackgroundColor = backgroundColor;
		mTextColor = textColor;
	}
	
	public void  setMadapterData(JSONArray jsa)
	{
		((ArticleAdapter)mAdapter).setItems(jsa);
		refs.setSelection(1);
		
	}
	
	public ExamplePagerAdapter(Activity context, int length, int backgroundColor, int textColor,int type) 
	{
		this(context, length, backgroundColor, textColor);
		this.type = type;
		if(type==0)
		{
			this.mAdapter =  new  FeedAdapter(context);
		}
		else if(1==type)
		{
			this.mAdapter = new ArticleAdapter(context);
		}
	}
	
	@Override
	public int getCount() {
		return mLength;
	}
	
	@Override
	public Object instantiateItem(View container, int position) {
		
		Log.d("pos", position+"");
		RelativeLayout v = new RelativeLayout(mContext);
		PullToRefreshListView  lv = new  PullToRefreshListView(mContext);
		
		//ListView lv = new  ListView(mContext);
		lv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		lv.setBackgroundColor(mBackgroundColor);
		lv.setFadingEdgeLength(0);
		
		lv.setAdapter(mAdapter);
		
		if(this.type==1)
		{
			
			refs = lv;
			//lv.setLastUpdated(null);
			lv.invalidate();
		}
		lv.setOnItemClickListener(new  OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent =  new  Intent();
				intent.setClass(mContext, ArticleActivity.class);
				
				Bundle bun =  new  Bundle();
				bun.putString("url", arg1.getTag().toString());
				bun.putString("title", ((TextView)arg1.findViewById(R.id.art_title)).getText().toString());
				intent.putExtras(bun);
				mContext.startActivity(intent);
			}});
		v.addView(lv);
		
		((ViewPager) container).addView(v, 0);
		lv.addFooterView(new  TextView(mContext));
		return v;
	}
	
	
	@Override
	public void destroyItem(View container, int position, Object view) {
		((ViewPager) container).removeView((View) view);
	}
	
	
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((View) object);
	}
	
	
	@Override
	public void finishUpdate(View container) {}
	
	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {}
	
	@Override
	public Parcelable saveState() {
		return null;
	}
	
	@Override
	public void startUpdate(View container) {}
	
}
