package com.dym.alarm.common;

import android.util.Log;

import com.dym.alarm.BuildConfig;
import com.dym.alarm.Form;


public class NLog {

	public static boolean release = !BuildConfig.DEBUG;
	static String tag = "NLog";
	
	public static void o(String str)
	{
		if( release )
			   return;
		Log.w(tag, str);
		
	}
	public static void log(String tag,String format,Object ...args){

		if( release )
			return;
		if( args.length == 0 )
			Log.w(tag,format);
		else {
			String str = String.format(format, args);
			Log.w(tag, str);
			str = null;
		}

	}
	
	public static void i(String format,Object ...args)
	{


		log(tag,format,args);

	}
	public static void e(String format,Object ...args)
	{
		if( release )
			   return;	
	    Log.e(tag,String.format(format, args));
	}
	
	public static void z(String format,Object ...args)
	{
		if( release)
		return;
			Log.i(tag,String.format(format, args));
		
	}
	public static void s(String s)
	{
		if( release )
			return;
		Log.w(tag,s);
	}

	public static void e(Exception e) {


		if( release ) return;
		else{
			e.printStackTrace();
		}

	}
}
