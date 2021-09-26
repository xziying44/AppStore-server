package com.xziying.appstorecloud.utils;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PhoneUtil
 *
 * @author : xziying
 * @create : 2021-04-28 21:10
 */
public class REUtil {
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean isMatch = false;
        //制定验证条件
        String regex1 = "^[1][3,4,5,7,8][0-9]{9}$";
        String regex2 = "^((13[0-9])|(14[579])|(15([0-3,5-9]))|(16[6])|(17[0135678])|(18[0-9]|19[89]))\\d{8}$";
        p = Pattern.compile(regex2);
        m = p.matcher(str);
        isMatch = m.matches();
        return isMatch;

    }

    public static boolean verification(String raw, String regexExpr){
        Pattern pattern=Pattern.compile(regexExpr);
        Matcher matcher = pattern.matcher(raw);
        return matcher.matches();
    }

    @Test
    public void test1(){
        System.out.println(verification("a127420698", "^[a-zA-Z][a-zA-Z0-9_]{5,15}$"));
    }
}
