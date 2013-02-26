package big.guru.book;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import big.guru.book.utils.HttpUtils;

public class AddBookActivity extends Activity 
{
	
	 private Button scanIsbn;
	 private Button saveButton;
	 private EditText isbn; 
	 private Button  back;
	 
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.add_book);
	        initView();
	        addListener();
	 }
	 
	 private void  initView()
	 {
		 scanIsbn 	= (Button) findViewById(R.id.scan_isbn);
		 saveButton = (Button) findViewById(R.id.save);
		 isbn 	= (EditText) findViewById(R.id.isbn);
		 back = (Button)findViewById(R.id.back);
	 }
	 
	 private void  addListener()
	 {
		 View.OnClickListener lis = new View.OnClickListener() {
			
			public void onClick(View v) {
				Log.e("custom", v.getId()+"");
				switch(v.getId()){
				
					case R.id.scan_isbn: 
						Intent intent = new  Intent();
						intent.setClass(AddBookActivity.this, CaptureActivity.class);
						AddBookActivity.this.startActivityForResult(intent, 1);
						break;
					case R.id.save: AddBookActivity.this.save();break;
					case R.id.back: AddBookActivity.this.finish();
				}
			}
		};
		
		scanIsbn.setOnClickListener(lis);
		saveButton.setOnClickListener(lis);
		back.setOnClickListener(lis);
	 }
	 
	 public void  save()
	 {
		 //Toast.makeText(this, "hahhaha", Toast.LENGTH_SHORT).show();
		 String  bn = isbn.getEditableText().toString();
		 bn = "9787115147318";
		 Log.e("custom", bn+123);
		 if(null==bn||"".equals(bn))
		 {
			 Toast.makeText(this, "hahhaha", Toast.LENGTH_SHORT).show();
		 }
		 String hh = HttpUtils.get("http://192.168.1.100:8080/book_ee/route/add/book?isbn="+bn+"&uid="+BookApp.uid+"&token="+BookApp.token, null);
		 Log.d("custom", hh);
		 Toast.makeText(this, new  String(hh.getBytes()), Toast.LENGTH_LONG+200).show();
		
		 
	 }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==Activity.RESULT_OK)
		{
			String isbn = data.getExtras().getString("isbn");
			this.isbn.setText(isbn.subSequence(0, isbn.length()));
			HashMap<String, String> para =  new  HashMap<String, String>();
			para.put("isbn", isbn);
			para.put("uid", BookApp.uid);
			String ret = HttpUtils.get("http://192.168.1.102:8080/book_ee/route/add/book", para);
			try {
				JSONObject obj =  new  JSONObject(ret);
				Toast.makeText(this, ret, Toast.LENGTH_LONG+200).show();
				ImageView image = (ImageView) this.findViewById(R.id.cover);
				
				image.setImageDrawable(HttpUtils.getImg(obj.getJSONObject("data").getString("pic")));
				image.invalidate();
				TextView name = (TextView) this.findViewById(R.id.book_name);
				name.setText(obj.getJSONObject("data").getString("name"));
				TextView pub =   (TextView) this.findViewById(R.id.publisher);
				pub.setText(obj.getJSONObject("data").getString("publisher"));
				TextView pd =   (TextView) this.findViewById(R.id.pubdate);
				pd.setText(obj.getJSONObject("data").getString("pubdate"));
				TextView price =   (TextView) this.findViewById(R.id.price);
				price.setText(obj.getJSONObject("data").getString("price"));
				TextView author =   (TextView) this.findViewById(R.id.author);
				author.setText(obj.getJSONObject("data").getString("author"));
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	 
	 
}
