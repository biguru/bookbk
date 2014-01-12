package big.guru.book;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.Toast;
import big.guru.book.adapter.RecentAdapter;

import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;


public class MainActivity extends Activity {

	private Gallery bookBox;

	private Button  myBook;
	private Button  changeInfo;
	private Button  request;
	private Button  history;
	private Button  addBook;
	private Button  weiboAuth;
	private Weibo  mWeibo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        bindClick();
      
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    private void  initView()
    {
    	bookBox = (Gallery) this.findViewById(R.id.books);
    	myBook  = (Button)  this.findViewById(R.id.my_book);
    	changeInfo  = (Button)  this.findViewById(R.id.change_info);
    	request  = (Button)  this.findViewById(R.id.change_request);
    	history  = (Button)  this.findViewById(R.id.history);
    	addBook  = (Button)  this.findViewById(R.id.add_book);
    	weiboAuth = (Button) this.findViewById(R.id.weibo_auth);
    	bookBox.setAdapter(new  RecentAdapter());
    	mWeibo = Weibo.getInstance(BookApp.appKey, BookApp.redirectUrl);
    }
    
    private void bindClick()
    {
    	OnClickListener listener =  new OnClickListener() {
			
			public void onClick(View v) {
				
				switch(v.getId()){
				
					case R.id.books :break;
					case R.id.change_info:break;
					case R.id.change_request:break;
					case R.id.my_book:break;
					case R.id.history:break;
					case R.id.add_book:
						{
							MainActivity.this.startActivity(new Intent().setClass(MainActivity.this,AddBookActivity.class));
							break;
						}
					case R.id.weibo_auth:
						{
							 Toast.makeText(MainActivity.this,"dssdsd", Toast.LENGTH_LONG)
					           .show();
							mWeibo.authorize(MainActivity.this, new  AuthDialogListener());
							break;
						}
				}
			}
		};
		
		myBook.setOnClickListener(listener);
		changeInfo.setOnClickListener(listener);
		request.setOnClickListener(listener);
		history.setOnClickListener(listener);
		myBook.setOnClickListener(listener);
		addBook.setOnClickListener(listener);
		weiboAuth.setOnClickListener(listener);
    }
    class AuthDialogListener implements WeiboAuthListener {

        public void onComplete(Bundle values) {
            String token = values.getString("access_token");
            String  uid = values.getString("uid");
            String expires_in = values.getString("expires_in");
            System.out.println(token+expires_in+values.toString());
           Log.d("gggg", token+expires_in);
           Toast.makeText(MainActivity.this, values.toString(), Toast.LENGTH_LONG)
           .show();
           BookApp.uid = uid;
           BookApp.token = token;
           
        }

    	public void onCancel() {
    		// TODO Auto-generated method stub
    		
    	}

    	public void onError(WeiboDialogError arg0) {
    		// TODO Auto-generated method stub
    		
    	}

    	public void onWeiboException(WeiboException arg0) {
    		// TODO Auto-generated method stub
    		
    	}

    }
}
