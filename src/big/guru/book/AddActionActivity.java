package big.guru.book;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import big.guru.book.widget.ActionBar;
import big.guru.book.widget.ActionBar.Action;
import big.guru.book.widget.UIButton;
import big.guru.book.widget.UIButton.ClickListener;

public class AddActionActivity extends Activity {

	private UIButton scan, change, sale;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_actions);

		scan = (UIButton) findViewById(R.id.scan);
		change = (UIButton) findViewById(R.id.change);
		sale = (UIButton) findViewById(R.id.sale);

		CustomClickListener listener = new CustomClickListener();
		
		scan.addClickListener(listener);
		change.addClickListener(listener);
		sale.addClickListener(listener);
		
		final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		actionBar.setTitle("添加与发布");
		actionBar.addAction(new  Action(){

			public int getDrawable() {
				// TODO Auto-generated method stub
				return R.drawable.btn_close_normal;
			}

			public void performAction(View view) {
				// TODO Auto-generated method stub
				AddActionActivity.this.finish();
			}});
	}
	
	 private class CustomClickListener implements ClickListener {

			public void onClick(View view) {
				//Toast.makeText(AddActionActivity.this, "button clicked", Toast.LENGTH_SHORT).show();
				switch(view.getId()){
				case R.id.scan:
				{
					Intent intent = new  Intent();
					intent.setClass(AddActionActivity.this, CaptureActivity.class);
					AddActionActivity.this.startActivityForResult(intent, 1);
					break;
				}
				case R.id.change:
				{
					Intent intent = new  Intent();
					intent.setClass(AddActionActivity.this, ChooseBookActivity.class);
					AddActionActivity.this.startActivity(intent);
					break;
				}
			}
	    	
	    }
	 }
			
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			if(resultCode==Activity.RESULT_OK)
			{
				String isbn = data.getExtras().getString("isbn");
				Toast.makeText(this, isbn, Toast.LENGTH_LONG).show();
			}
	 }
}