package http.helper;

import com.demo.utils.FileUtils;
import com.demo.utils.LogUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/16.
 */

public class ParseDataHelper {


    private  static ArrayList mList=null;
    static {
        mList = new ArrayList();
    }

    public  static ArrayList getmList()
    {
        return mList;
    }

    /**
     * 解析出字段guid
     */
    public static void setGuid(byte[] data) {
        String text = null;
        try {
            text = new String(data, "UTF-8");
            String sub = text.substring(text.lastIndexOf("#") + 1, text.length() - 4);
            FileUtils.setKrsdkconfData(sub);
            LogUtil.e("设置guid完成 guid =" + sub);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析出solutions文件，同时调用XmlFileSolute解析出来，添加到NetConfigration
     */
    public static void getSolution(byte[] data)
    {
        try {
            String text = new String(data, "UTF-8");
             solutions(text);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
/**
 * 网络解密使用
 * */
    private static void solutions(String data) {
        if (data != null) {
        if (tag ==1)
        {
            mList.add(data);
            return;
        }
            String[] split = data.split("</root>");
            for (String ret : split) {
                if (ret.indexOf("<root>") == -1)
                {
                    continue;
                }
                String text = ret.substring(ret.indexOf("<root>"));
                String solutions = text + "</root>";
                mList.add(solutions);
            }
        }
        //调用解析函数
    }
private  static int tag =-1;//重启标志位
    public static void setTag(int tag) {
        ParseDataHelper.tag = tag;
    }
}
