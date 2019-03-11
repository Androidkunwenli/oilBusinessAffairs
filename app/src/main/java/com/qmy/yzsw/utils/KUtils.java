package com.qmy.yzsw.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BaiduNaviManagerFactory;
import com.baidu.navisdk.adapter.IBNRoutePlanManager;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;
import com.qmy.yzsw.R;
import com.qmy.yzsw.YawsApp;
import com.qmy.yzsw.activity.NavigationActivity;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.config.Urls;

import java.io.File;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.qmy.yzsw.activity.NavigationActivity.ROUTE_PLAN_NODE;
import static java.util.regex.Pattern.matches;


public class KUtils {
    public static void sendMail(Activity activity, File file) {
        Intent email = new Intent(android.content.Intent.ACTION_SEND);
        email.setType("application/octet-stream");
        email.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        activity.startActivity(Intent.createChooser(email, "请选择邮件发送软件"));
    }

    /**
     * 启动百度地图导航(Native)
     */
    public static void startNavi(final Activity activity, LatLng mLat1, String startName, LatLng mLat2, String endName) {
        // 构建 导航参数
//        NaviParaOption para = new NaviParaOption()
//                .startPoint(mLat1).endPoint(mLat2)
//                .startName(startName).endName(endName);
//        try {
//            BaiduMapNavigation.openBaiduMapNavi(para, activity);
//        } catch (BaiduMapAppNotSupportNaviException e) {
//            e.printStackTrace();
//            showDialog(activity);
//        }
//        String addressStr = "http://api.map.baidu.com/direction?origin=latlng:" + mLat1.latitude + "," + mLat1.longitude + "|name:" + startName +
//                "&destination=latlng:" + mLat2.latitude + "," + mLat2.longitude + "|name:" + endName + "&mode=driving&region=石家庄&output=html&src=油中商务";
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(addressStr));
//        activity.startActivity(intent);
        if (!BaiduNaviManagerFactory.getBaiduNaviManager().isInited()) {
            return;
        }
        final BNRoutePlanNode sNode = new BNRoutePlanNode(mLat1.longitude, mLat1.latitude, startName, startName, CoordinateType.BD09LL);
        BNRoutePlanNode eNode = new BNRoutePlanNode(mLat2.longitude, mLat2.latitude, endName, endName, CoordinateType.BD09LL);
        List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
        list.add(sNode);
        list.add(eNode);
        BaiduNaviManagerFactory.getRoutePlanManager().routeplanToNavi(
                list,
                IBNRoutePlanManager.RoutePlanPreference.ROUTE_PLAN_PREFERENCE_DEFAULT,
                null,
                new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_START:
                                Toast.makeText(activity, "算路开始", Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_SUCCESS:
                                Toast.makeText(activity, "算路成功", Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_FAILED:
                                Toast.makeText(activity, "算路失败", Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_TO_NAVI:
                                Toast.makeText(activity, "算路成功准备进入导航", Toast.LENGTH_SHORT)
                                        .show();
                                Intent intent = new Intent(activity, NavigationActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(ROUTE_PLAN_NODE, sNode);
                                intent.putExtras(bundle);
                                activity.startActivity(intent);
                                break;
                            default:
                                // nothing
                                break;
                        }
                    }
                });


    }

    /**
     * 提示未安装百度地图app或app版本过低
     *
     * @param activity
     */
    private static void showDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OpenClientUtil.getLatestBaiduMapApp(activity);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();

    }

    /*** 验证电话号码
     * @return 如果是符合格式的字符串, 返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean IsTelephone(String str) {
        String regex = "^0(10|2[0-5789]-|\\d{3})-?\\d{7,8}$";
        return matches(regex, str);
    }

    /**
     * 获取验证码倒计时
     */
    public static void setRegisterTimeCount(final TextView view, long fen, long miao) {
        CountDownTimer timer = new CountDownTimer(fen, miao) {
            ColorStateList originalColor = view.getTextColors();

            @Override
            public void onTick(long millisUntilFinished) {
                view.setEnabled(false);
                view.setAllCaps(false);
                view.setTextColor(YawsApp.getInstance().getResources().getColor(R.color.white));
                view.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                view.setText("重新发送");
                view.setTextColor(originalColor);
                view.setEnabled(true);
            }
        };
        timer.start();
    }

    /**
     * webview 设置
     */
    public static void startJavascript(WebView webView) {
        WebSettings mWebSettings = webView.getSettings();
        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        mWebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebSettings.setBlockNetworkImage(false); // 是否阻止网络图像
        mWebSettings.setBlockNetworkLoads(false); // 是否阻止网络请求
        mWebSettings.setJavaScriptEnabled(true); // 是否加载JS
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebSettings.setAppCacheEnabled(false);
        mWebSettings.setUseWideViewPort(true); // 使用广泛视窗
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setBuiltInZoomControls(false);
        mWebSettings.setSupportZoom(false);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebSettings.setUserAgentString(mWebSettings.getUserAgentString()
                + ";native-android");// 获得浏览器的环境
        //webview在安卓5.0之前默认允许其加载混合网络协议内容
// 在安卓5.0之后，默认不允许加载http与https混合内容，需要设置webview允许其加载混合网络协议内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    public static String markByContainQuestion(String s) {
        return s.contains("?") ? "&" : "?";
    }

    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 设置EditText可输入和不可输入状态
     */
    public static void editTextable(EditText editText, boolean editable) {
        if (!editable) { // disable editing password
            editText.setEnabled(false);
        } else { // enable editing of password
            editText.setEnabled(true);
        }
    }

    /***
     * 将对象转换为map对象
     * @param thisObj 对象
     * @return map
     */
    public static Map<String, String> objectToMap(Object thisObj) {
        Map<String, String> map = new HashMap();
        Class c;
        try {
            c = Class.forName(thisObj.getClass().getName());
            //获取所有的方法
            Method[] m = c.getMethods();
            for (Method aM : m) {   //获取方法名
                String method = aM.getName();
                //获取get开始的方法名
                if (method.startsWith("get") && !method.contains("getClass")) {
                    try {
                        //获取对应对应get方法的value值
                        Object value = aM.invoke(thisObj);
                        if (value != null) {
                            //截取get方法除get意外的字符 如getUserName-->UserName
                            String key = method.substring(3);
                            //将属性的第一个值转为小写
                            key = key.substring(0, 1).toLowerCase() + key.substring(1);
                            //将属性key,value放入对象
                            map.put(key, String.valueOf(value));
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println("error:" + method);
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return map;
    }

    /**
     * @param path      图片文件
     * @param opType    图片类型:1营业执照;2法人身份证正面;3法人身份证反面;4危险化学品经营许可证;5成品油许可证;6法人资格证;7消防证;8规划证;9土地证;10站点全景照;11站点正面照;12站点左面照;13站点右面照;14便利店照;15营业室照;16站点场景其它
     * @param oldPicUrl 替换原来图片时需传参原来的图片地址，新加图片传空串
     */
    public static synchronized void uploadFile(final Context context, String path, int opType, String oldPicUrl, JsonCallback<BaseBean<String>> jsonCallback) {
        if (path != null && !path.isEmpty()) {
            OkGo.<BaseBean<String>>post(Urls.IMGUPLOAD)
                    .tag(context)//
                    .params("token", SPUtils.getInstance().getString("token"))
                    .params("file", new File(path))
                    .params("opType", opType)
                    .params("oldPicUrl", oldPicUrl)
                    .execute(jsonCallback);
        } else {
            ToastUtils.showShort("图片地址不可为空");
        }
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param context  上下文
     * @param phoneNum 电话号码
     */
    public static void callPhone(Context context, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 验证是否是数字
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 两位小数
     */
    public static String setFormatNum(String numStr) {
        if (TextUtils.isEmpty(numStr)) {
            return "";
        }
        if (numStr.contains(".")) {
            return numStr.replaceAll("\\.*0*$", "");
        } else {
            return numStr;
        }
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, 5, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确乘法运算的mul方法
     *
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static double mul(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.multiply(b2).doubleValue();
    }
}
