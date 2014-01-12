package big.guru.book.adapter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import big.guru.book.R;

public class MyBooksAdapter extends BaseAdapter {

	private ArrayList<JSONObject> mItems =  new ArrayList<JSONObject>();
	private Context context;
	
	public MyBooksAdapter(Context  con)
	{
		context = con;
	}
	public int getCount() {
		
		return mItems.size();
	}

	public void  addItems(JSONArray  jsa)
	{
		int  len = jsa.length();
		for(int i = 0; i < len; i++)
			mItems.add(jsa.optJSONObject(i));
		this.notifyDataSetChanged();
	}
	
	public Object getItem(int position) {
		
		return mItems.get(position);
	}

	public long getItemId(int position) {
		
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		JSONObject  item = mItems.get(position);
		if(view != null)
		{
			view = convertView;
			
		}
		else
		{
		
			LayoutInflater  inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			view  = inflater.inflate(R.layout.select_item, null);
			
		}
		TextView bookName = (TextView) view.findViewById(R.id.book_name);
		bookName.setText(item.optString("name"));
		bookName.setTag(item.opt("isbn"));
		return view;
	}

}
