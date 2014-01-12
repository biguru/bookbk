package big.guru.book.adapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import big.guru.book.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("ParserError")
public class ArticleAdapter extends BaseAdapter {

	private Activity mContext;
	private JSONArray  items =  new  JSONArray();
	public ArticleAdapter(Activity context)
	{
		mContext = context;
		
	}
	
	public  void  setItems(JSONArray  jsa)
	{
		
		items = jsa;
		this.notifyDataSetChanged();
		
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return items.length();
	}

	
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		try {
			return items.getJSONObject(arg0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  null;
	}

	
	
	
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View  vv = mContext.getLayoutInflater().inflate(R.layout.article_item, null);
		JSONObject jso =  (JSONObject) this.getItem(arg0);
		((TextView)vv.findViewById(R.id.art_title)).setText(jso.optString("title"));
		
		vv.setTag(jso.opt("url"));
		((TextView)vv.findViewById(R.id.summary)).setText(jso.optString("summary"));
		vv.setPadding(8, 14, 8, 14);
		return vv;
	}

	
}
