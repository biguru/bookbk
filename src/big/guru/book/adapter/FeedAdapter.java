package big.guru.book.adapter;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import big.guru.book.R;

public class FeedAdapter extends BaseAdapter {

	private Activity mContext;
	String[]  itemns = new  String[]{"  unix编程指南","  shell超级应用","  家常菜入门与应用","  我操这是啥"};
	public FeedAdapter(Activity context)
	{
		mContext = context;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return 10;
	}

	
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View v =  mContext.getLayoutInflater().inflate(R.layout.feed_item, null);
		LinearLayout list =  (LinearLayout) v.findViewById(R.id.book_list);
		for(int i =0; i<itemns.length;i++)
		{
			String  s = itemns[i];
			TextView t =  new  TextView(this.mContext);
			t.setText(s);
			if(i==0)
			t.setBackgroundResource(R.drawable.background_view_rounded_top);
			else if(i==itemns.length-1)
				t.setBackgroundResource(R.drawable.background_view_rounded_bottom);	
			else 
				t.setBackgroundResource(R.drawable.background_view_rounded_middle);
			t.setLayoutParams(new  LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			list.addView(t);
		}
		v.setPadding(8, 14, 8, 14);
		return v;
	}

	
}
