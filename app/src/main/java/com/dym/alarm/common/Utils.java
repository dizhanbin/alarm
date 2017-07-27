package com.dym.alarm.common;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.alibaba.fastjson.JSON;
import com.dym.alarm.ActController;
import com.dym.alarm.RP;
import com.dym.alarm.model.MOpenSource;
import com.dym.alarm.model.MSound;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dizhanbin on 17/2/13.
 */

public class Utils {

    private static final String TAG = "Utils";
    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";


    public static final String REGEX_NUMBER = "\\d+";


    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
//    public static final String REGEX_EMAIL = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$";
    public static final String REGEX_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    public static void openMarket() {

        String marketUrl = "market://details?id=" +  ActController.instance.getPackageName();
        Uri urii = Uri.parse(marketUrl);
        Intent itView = new Intent(Intent.ACTION_VIEW, urii);
        itView.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ActController.instance.startActivity(itView);

    }


    public static class Strings {


        public static boolean isEmail(String email) {

            if (null == email || "".equals(email))
                return false;
            Pattern p = Pattern.compile(REGEX_EMAIL);
            Matcher m = p.matcher(email);

            return m.matches();
        }

        public static boolean isNumber(String str) {
            if (null == str || "".equals(str))
                return false;
            Pattern p = Pattern.compile(REGEX_NUMBER);
            Matcher m = p.matcher(str);

            return m.matches();
        }

        public static boolean isVerifyCode(String str) {
            if (null == str || "".equals(str))
                return false;
            Pattern p = Pattern.compile("\\d{4}");
            Matcher m = p.matcher(str);

            return m.matches();
        }

        public static boolean canCompleteEmail(String string, String email_name) {
            if (string.contains("@")) {
                int pos = string.indexOf("@");
                String content = string.substring(pos);
                if (email_name.startsWith(content)) return true;
            }
            return false;
        }
    }

    public static void openUrl(String url) {


        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        ActController.instance.startActivity(intent);

    }

    public static  List<MOpenSource>  getAssetString(Context context){

       // ByteArrayInputStream bis = new ByteArrayInputStream()


        try {
            InputStream is = context.getResources().getAssets().open("opensource.json");

            byte[] datas = new byte[1024];


            ByteArrayOutputStream bout = new ByteArrayOutputStream();

            int len;

            while( (len=is.read(datas))>0 ){
                bout.write(datas,0,len);
            }


            String str = new String(bout.toByteArray(),"utf-8");


            is.close();
            bout.close();
            List<MOpenSource> list = JSON.parseArray(str, MOpenSource.class);

            return list;



        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    public static void sendTo(Context context){

        Intent data=new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse("mailto:zhanbin.di@gmail.com"));
        data.putExtra(Intent.EXTRA_SUBJECT, "advice");
        data.putExtra(Intent.EXTRA_TEXT, "");
        context.startActivity(data);

    }

    public static void openGoogleDriver(){

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        //intent.addCategory(Intent.CATEGORY_VOICE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        ActController.instance.startActivityForResult(intent, RP.CK.K_GOOGLEDRIVER);



    }


    public static   List<MSound> scanAlarms() {

        //notification
        ContentResolver cr = ActController.instance.getContentResolver();

        List<MSound> list = new ArrayList<>();


        //alarm
        Cursor cursor = cr.query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,
                new String[] { MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.TITLE }, "is_alarm != ?",
                new String[] { "0" }, "_id asc");
        if (cursor == null) {
            return list;
        }

        if( cursor.moveToFirst() )
        do  {
            // NLog.i("autio :%s %s",cursor.getString( 2 ),cursor.getString( 1 ));

            MSound ms = new MSound();
            ms.name = cursor.getString(2);
            ms.path = cursor.getString(1);
            list.add(ms);

        }while (cursor.moveToNext());


        cursor.close();

        return list;
    }

}
