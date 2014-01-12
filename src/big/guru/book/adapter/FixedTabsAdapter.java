package big.guru.book.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import big.guru.book.R;
import big.guru.book.widget.ViewPagerTabButton;





public class FixedTabsAdapter implements TabsAdapter {
	
	private Activity mContext;
	
	private String[] mTitles = {
	    "交换", "出售", "我的"
	};
	
	public FixedTabsAdapter(Activity ctx) {
		this.mContext = ctx;
	}
	
	public View getView(int position) {
		ViewPagerTabButton tab;
		
		LayoutInflater inflater = mContext.getLayoutInflater();
		tab = (ViewPagerTabButton) inflater.inflate(R.layout.tab_fixed, null);
		
		if (position < mTitles.length) tab.setText(mTitles[position]);
		
		return tab;
	}
	
}
