package com.dym.alarm.common;

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


}
