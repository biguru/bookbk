package big.guru.book;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import big.guru.book.adapter.ExamplePagerAdapter;
import big.guru.book.adapter.FixedTabsAdapter;
import big.guru.book.adapter.TabsAdapter;
import big.guru.book.widget.ActionBar;
import big.guru.book.widget.ActionBar.Action;
import big.guru.book.widget.FixedTabsView;

public class PagerActivity extends Activity {

	private ViewPager mPager;
	private FixedTabsView mFixedTabs;

	private PagerAdapter mPagerAdapter;
	private TabsAdapter mFixedTabsAdapter;

	public static final String EXTRA_EXAMPLE_TYPE = "EXTRA_EXAMPLE_TYPE";

	private Button readBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fixed_tabs);
		initViewPager(3, 0xFFFFFFFF, 0xFF000000);
		mFixedTabs = (FixedTabsView) findViewById(R.id.fixed_tabs);
		mFixedTabsAdapter = new FixedTabsAdapter(this);
		mFixedTabs.setAdapter(mFixedTabsAdapter);
		mFixedTabs.setViewPager(mPager);
		readBtn = (Button) this.findViewById(R.id.read_btn);
		readBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(PagerActivity.this, ArticleListActivity.class);
				PagerActivity.this.startActivity(intent);
			}
		});
		final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		Intent intent = this.getIntent();
		actionBar.setTitle("首页");
		actionBar.addAction(new Action() {

			public int getDrawable() {
				return R.drawable.ic_input_add;
			}

			public void performAction(View view) {

				PagerActivity.this.startActivity(new Intent().setClass(
						PagerActivity.this, AddActionActivity.class));

			}
		});

	}

	private void initViewPager(int pageCount, int backgroundColor, int textColor) {
		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new ExamplePagerAdapter(this, pageCount,
				backgroundColor, textColor, 0);
		mPager.setAdapter(mPagerAdapter);
		mPager.setCurrentItem(1);
		mPager.setPageMargin(1);
	}

}
