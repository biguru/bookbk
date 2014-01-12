package big.guru.book;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import big.guru.book.adapter.MyBooksAdapter;
import big.guru.book.common.NetRequest;
import big.guru.book.widget.AnimationLayout;

public class ChooseBookActivity extends Activity implements AnimationLayout.Listener {
    public final static String TAG = "Demo";
    private LinkedList<String> mItems ;
    protected ListView        mList;
    protected ListView  selectedList;
    protected AnimationLayout mLayout;
    protected MyBooksAdapter selectAdapter;
    protected String[] mStrings = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};
    protected Button  sideBar;
    private  Handler mHandler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchange_edit);
        mItems =  new  LinkedList<String>();
        mLayout = (AnimationLayout) findViewById(R.id.animation_layout);
        sideBar = (Button)this.findViewById(R.id.select_btn);
        mLayout.setListener(this);
        sideBar.setOnClickListener(new  View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onClickContentButton(v);
				new  Thread(new NetRequest(mHandler,"http://192.168.1.101:8080/book_ee/route/user/book?uid=2463642345&page=0")).start();
			}
		});
        mHandler =  new  Handler(){

			@Override
			public void handleMessage(Message msg) {
				
				JSONObject jso  =  (JSONObject) msg.obj;
				JSONArray  jsa =  (JSONArray) jso.opt("data");
				selectAdapter.addItems(jsa);
				mList.invalidate();
				
			}
        	
        };
        selectedList = (ListView) this.findViewById(R.id.selected_list);
        selectedList.setFadingEdgeLength(1);
        selectedList.setAdapter(new  ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked, mItems));
        mList   = (ListView) findViewById(R.id.sidebar_list);
        selectAdapter  = new MyBooksAdapter(this);
        mList.setAdapter(selectAdapter);
        mList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mList.setOnItemClickListener(new  OnItemClickListener() {
        
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			TextView  bookName = (TextView) arg1.findViewById(R.id.book_name);
			mItems.addFirst( bookName.getText().toString());
			selectedList.invalidateViews();
		}
	});
    }

    public void onClickContentButton(View v) {
        mLayout.toggleSidebar();
    }

    @Override
    public void onBackPressed() {
        if (mLayout.isOpening()) {
            mLayout.closeSidebar();
        } else {
            finish();
        }
    }

    /* Callback of AnimationLayout.Listener to monitor status of Sidebar */
    
    public void onSidebarOpened() {
        Log.d(TAG, "opened");
    }

    /* Callback of AnimationLayout.Listener to monitor status of Sidebar */
    
    public void onSidebarClosed() {
        Log.d(TAG, "opened");
    }

    /* Callback of AnimationLayout.Listener to monitor status of Sidebar */
   
    public boolean onContentTouchedWhenOpening() {
        // the content area is touched when sidebar opening, close sidebar
        Log.d(TAG, "going to close sidebar");
        mLayout.closeSidebar();
        return true;
    }
}

