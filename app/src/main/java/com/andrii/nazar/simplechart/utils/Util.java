package com.andrii.nazar.simplechart.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Environment;
import android.os.IBinder;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.andrii.nazar.simplechart.constants.AppConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.andrii.nazar.simplechart.R;

public class Util {
	public static void hideKeyboard(Activity activity,
                                    IBinder binder) {
		InputMethodManager imm;
		
		imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(binder, 0);
	}

	public static void showNotification(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public static void showLongNotification(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
	
	public static String getCurrLanguage(Context context) {
		return context.getResources().getConfiguration().locale.getLanguage();
	}
	
	public static Bitmap getBitmapFromURL(String src) {
	    try {
	        Log.e("src",src);
	        URL url = new URL(src);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        Log.e("Bitmap","returned");
	        return myBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	        Log.e("Exception",e.getMessage());
	        return null;
	    }
	}
	
	public static float convertDpToPixel(float dp, Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float px = dp * (metrics.densityDpi / 160f);
	    return px;
	}

	public static float convertPixelsToDp(float px, Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float dp = px / (metrics.densityDpi / 160f);
	    return dp;
	}
	
	public final static boolean isValidEmail(CharSequence target) {
	  return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	}
	
	public static void showMessage(Context context, String title, String message){
		try {
			AlertDialog alertDialog = new AlertDialog.Builder(context).create();
			alertDialog.setTitle(title);
			alertDialog.setMessage(message);
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			alertDialog.show();
		}catch (Exception e){
			e.printStackTrace();
		}
	}


    public static Point getScaledDimension(Point imgSize, Point boundary) {

        int original_width = imgSize.x;
        int original_height = imgSize.y;
        int bound_width = boundary.x;
        int bound_height = boundary.y;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Point(new_width, new_height);
    }

	public static void SaveImage(Bitmap finalBitmap, String url) {

		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + "/advertisements");
		myDir.mkdirs();

		String fileName = url.substring( url.lastIndexOf('/')+1, url.length() );
		String fileNameWithoutExtn = fileName.substring(0, fileName.lastIndexOf('.'));
		String fname = fileNameWithoutExtn + ".jpg";

		File file = new File(myDir, fname);
		if (file.exists ()) file.delete ();
		try {
			FileOutputStream out = new FileOutputStream(file);
			finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean imageExist(String fileName){
		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + "/advertisements");
		myDir.mkdirs();
		String fname = fileName + ".jpg";
		File file = new File(myDir, fname);
		return file.exists();
	}

	public static String getExternalStorageFilePath(String fileName ){
		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + "/advertisements");
		myDir.mkdirs();
		String fname = fileName + ".jpg";
		File file = new File(myDir, fname);
		return "file://" + file.getPath();
	}

//	public static String getLiteralCode(Context context, int numericCode){
//		String mLiteralCode = "";
//		Context mContext = context;
//		switch (numericCode){
//			case AppConstants.UAH_CODE :
//				mLiteralCode = mContext.getResources().getString(R.string.add_account_uah_literal_code);
//				break;
//			case AppConstants.USD_CODE :
//				mLiteralCode = mContext.getResources().getString(R.string.add_account_usd_literal_code);
//				break;
//			case AppConstants.EUR_CODE :
//				mLiteralCode = mContext.getResources().getString(R.string.add_account_eur_literal_code);
//				break;
//			case AppConstants.GBR_CODE :
//				mLiteralCode = mContext.getResources().getString(R.string.add_account_gbr_literal_code);
//				break;
//		}
//		return mLiteralCode;
//	}

	public static String getDayFromTimestamp(long timestampInMilliSeconds) {
		Date date = new Date();
		date.setTime(timestampInMilliSeconds);
		String mDate = new SimpleDateFormat("dd.MM").format(date);
		String mDay = new SimpleDateFormat("dd").format(date);
		String mYear = new SimpleDateFormat("yyyy").format(date);
		String formattedDate = mDate;
		return formattedDate;

	}

}
