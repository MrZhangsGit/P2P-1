package com.atguigu.p2pinvest.common;

/**
 * Created by 颜银 on 2016/11/12.
 * QQ:443098360
 * 微信：y443098360
 * 作用：编写AppNetConfig常量类，可以非常方便直观的查看以及管理App中所有发起的服务器请求信息
 */
public class AppNetConfig {

    //本地ip
    private static final String HOST = "192.168.1.144";
//    private static final String HOST = "192.168.191.1";
//    private static final String HOST = "192.168.31.209";

    //访问服务器的base_url
    public static final String BASE_URL = "http://" + HOST + ":8080/P2PInvest/";

    //登陆页面
    public static final String LOGIN = BASE_URL + "login";

    //
    public static final String INDEX = BASE_URL + "index";

    //首页面理财
    public static final String PRODUCT = BASE_URL + "product";

    //测试
    public static final String TEST = BASE_URL + "test";

    //访问服务器端当前应用的版本信息
    public static final String UPDATE = BASE_URL + "update.json";//访问服务器端当前应用的版本信息

}
