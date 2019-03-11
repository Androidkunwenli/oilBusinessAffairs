package com.qmy.yzsw;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        getName("赵亚坤 130428199505210010 130182199505210010");
    }

    /**
     * 获取TF卡姓名
     * 作者：赵亚坤
     * 时间：2018/5/5-9:59
     */
    public  String getName(String valCode) {
        Pattern pattern = Pattern.compile("^\\d*[[\u4e00-\u9fa5]\\s]*");
        Matcher matcher = pattern.matcher(valCode);
        while (matcher.find()) {
            return matcher.group();
        }
        return "";
    }
}