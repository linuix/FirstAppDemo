package com.demo.utils;

/**
 * Created by Administrator on 2016/10/20.
 */

public class Const {
    public static final String CHANNELID = "105006";
    public static final String EXPORT_PATH = "export PATH=/sbin:/vendor/bin:/system/sbin:/system/bin:/system/xbin:$PATH";

    /***********************************
     * handler send msg code
     ****************************************************/
    public static final int INT_CODE = 10000;//发送初始化消息
    public static final int INIT_SOURCE=00001;//初始化资源
    public static final int FIND_FILE = 10001;//测试使用，查看data目录下的文件信息
    public static final int HTTP_TEST = 10002;//测试网络；
    public static final int ROOT_TEST = 10003;//测试执行root，获取tmpRoot
    public static final int GET_GUID = 20000;//设置guid属性到kr-stock-conf文件，为了后续的读取属性文件字段w.g做准备
    public static final int GET_SOLUTIONS = 20001;//获取solution解决方案，添加到ArrayList内。通过XmlFileSolute解析出对应的数据字段
    public static final int INIT_OK = 00002; //初始化成功
    public static final int INIT_FAILED = 00000; //初始化失败
    /**************************************************************/
    public static final int GET_TMP_SHELL = 30000;//获取到root的状态标识

    /***************华丽的分割线**** 广播的信息**********************************************************/
    public static final String UP_DATE_UI = "upui";
    public static final String INIT_SUC = "init_sucess";
    public static final String INIT_FAL = "init_failed";


    /************************华丽的分割线，静默安装的参数*************************************/
    public static  final  String APK_PATH="";//开始的时候，可以把需要安装的apk文件放置在对应的额位置
    public static  final  String CMD="pm install -r -type "+APK_PATH;//执行安装的时候，执行命令



    /************************************华丽的分割线******************************/
    public static int kingroot_tag=1;//表示kingroot身份
    public  static int mars_tag =2;//表示自己的身份
    public  static final String SU_NAME="mysu";//执行su文件的tag


    //***************************华丽的分割线********************************
    //3个位置更改 1.Utils.java 出开始做判断是否或者root成功修改为true 2.RootMgr对象在root成功修改true 3.Replace.java在接管root成功后修改true
    //1个位置使用判断 1.RebootUtils.java作判断出使用
    public static final  String ROOT_SUCESS="root_sucess";//判断root成功的标识变量 spf 文件key true 标识root成功 ，false 标识失败







}
