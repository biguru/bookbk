package big.guru.book.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import android.graphics.Bitmap;
import android.os.Environment;

public class PicUtils 
{
	public  static  String  savePicAntRetPath(Bitmap bitmap)
	{
		String root = Environment.getExternalStorageDirectory().getPath();
		String path = root + "/book_img/";
		File pic = new  File(path);
		if(!pic.isDirectory())
			pic.mkdirs();
		
		String  picname = path + new Date().getTime()+".jpg";
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(picname);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return  picname;
	}
}
