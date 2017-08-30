package http.utils;

import android.os.Build;
import android.os.Environment;
import android.os.SystemProperties;
import android.text.TextUtils;

import com.demo.process.RootProcess;
import com.demo.utils.LogUtil;
import com.demo.utils.Utils;
import com.kingroot.sdk.root.CommonLog;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import http.demo.SolutionHelpers;

/**
 * Created by Administrator on 2016/11/1.
 */

public class XmlFileSolute {
    /**
     * 手机信息拼接，请求网络
     * deviceInfoXml
     */
    static String devciInfoxml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "\n" +
            "<root>\n" +
            "  <prop>\n" +
            "    <ro.build.id>KTU84P</ro.build.id>\n" +
            "    <ro.build.display.id>KTU84P.A5009KEU1AOE1</ro.build.display.id>\n" +
            "    <ro.product.model>SM-A5009</ro.product.model>\n" +
            "    <ro.build.version.release>4.4.4</ro.build.version.release>\n" +
            "    <ro.build.version.sdk>19</ro.build.version.sdk>\n" +
            "    <ro.product.manufacturer>samsung</ro.product.manufacturer>\n" +
            "    <ro.product.brand>samsung</ro.product.brand>\n" +
            "    <ro.product.name>a5ltectc</ro.product.name>\n" +
            "    <ro.product.cpu.abi>armeabi-v7a</ro.product.cpu.abi>\n" +
            "    <ro.product.cpu.abi2>armeabi</ro.product.cpu.abi2>\n" +
            "    <ro.product.device>a5ltectc</ro.product.device>\n" +
            "    <ro.product.board>MSM8916</ro.product.board>\n" +
            "    <ro.build.version.codename>REL</ro.build.version.codename>\n" +
            "    <ro.build.fingerprint>samsung/a5ltectc/a5ltectc:4.4.4/KTU84P/A5009KEU1AOE1:user/release-keys</ro.build.fingerprint>\n" +
            "    <linux.version>Linux version 3.10.28-4246999 (dpi@SWDD5520) (gcc version 4.7 (GCC) ) #1 SMP PREEMPT Fri May 15 12:00:39 KST 2015</linux.version>\n" +
            "    <ro.build.hidden_ver>A5009KEU1AOE1</ro.build.hidden_ver>\n" +
            "    <gsm.version.baseband>A5009KEU1AOE1</gsm.version.baseband>\n" +
            "    <ro.serialno>b976da81</ro.serialno>\n" +
            "    <ro.mediatek.platform/>\n" +
            "    <ro.product.real_model>SM-A5009</ro.product.real_model>\n" +
            "    <ro.board.platform>msm8916</ro.board.platform>\n" +
            "    <ro.hardware>qcom</ro.hardware>\n" +
            "    <ro.product.brand.replace/>\n" +
            "    <ro.product.model.replace/>\n" +
            "    <linux.version.date>20150515</linux.version.date>\n" +
            "  </prop>\n" +
            "  <device/>\n" +
            "</root>\n";

    public static String getDeviceXmlInfo() {
        StringBuilder v1 = new StringBuilder();
        v1.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        v1.append("<root>");
        v1.append("<prop>");
        v1.append("<ro.build.id>");
        v1.append(Build.ID);
        v1.append("</ro.build.id>");
        v1.append("<ro.build.display.id>");
        v1.append(Build.DISPLAY);
        v1.append("</ro.build.display.id>");
        v1.append("<ro.product.model>");
        v1.append(Build.MODEL);
        v1.append("</ro.product.model>");
        v1.append("<ro.build.version.release>");
        v1.append(Build.VERSION.RELEASE);
        v1.append("</ro.build.version.release>");
        v1.append("<ro.build.version.sdk>");
        v1.append(Build.VERSION.SDK);
        v1.append("</ro.build.version.sdk>");
        v1.append("<ro.product.manufacturer>");
        v1.append(Build.MANUFACTURER);
        v1.append("</ro.product.manufacturer>");
        v1.append("<ro.product.brand>");
        v1.append(Build.BRAND);
        v1.append("</ro.product.brand>");
        v1.append("<ro.product.name>");
        v1.append(Build.PRODUCT);
        v1.append("</ro.product.name>");
        v1.append("<ro.product.cpu.abi>");
        LogUtil.loge("Build.CPU_ABI = " + Build.CPU_ABI);
        //<ro.product.cpu.abi>armeabi-v7a</ro.product.cpu.abi>
        // <ro.product.cpu.abi2>armeabi</ro.product.cpu.abi2>
        v1.append(/*"armeabi-v7a"*/getProp("ro.product.cpu.abi"));//这里读取的是系统内的数据，使用processUtils读取---读取设备的cpu信息
        v1.append("</ro.product.cpu.abi>");
        v1.append("<ro.product.cpu.abi2>");
        LogUtil.loge("Build.CPU_ABI2 = " + Build.CPU_ABI2);
        v1.append(/*"armeabi"*/getProp("ro.product.cpu.abi2"));//系统内的数据，暂时用固定值代替，后续改进process的流写回来
        v1.append("</ro.product.cpu.abi2>");
        v1.append("<ro.product.device>");
        v1.append(Build.DEVICE);
        v1.append("</ro.product.device>");
        v1.append("<ro.product.board>");
        v1.append(Build.BOARD);
        v1.append("</ro.product.board>");
        v1.append("<ro.build.version.codename>");
        v1.append(Build.VERSION.CODENAME);
        v1.append("</ro.build.version.codename>");
        v1.append("<ro.build.fingerprint>");
        v1.append(Build.FINGERPRINT);
        v1.append("</ro.build.fingerprint>");
        v1.append("<linux.version>");
        String linxeVersion = CommonLog.getLinxeVersion();
        v1.append(linxeVersion);
        v1.append("</linux.version>");
        v1.append("<ro.build.hidden_ver>");
        v1.append(SystemProperties.get("ro.build.hidden_ver"));
        v1.append("</ro.build.hidden_ver>");
        v1.append("<gsm.version.baseband>");
        v1.append(SystemProperties.get("gsm.version.baseband"));
        v1.append("</gsm.version.baseband>");
        v1.append("<ro.serialno>");
        v1.append(SystemProperties.get("ro.serialno"));
        v1.append("</ro.serialno>");
        v1.append("<ro.mediatek.platform>");
        v1.append(SystemProperties.get("ro.mediatek.platform"));
        LogUtil.loge("ro.mediatek.platform : " + SystemProperties.get("ro.mediatek.platform"));
        v1.append("</ro.mediatek.platform>");
        v1.append("<ro.product.real_model>");
        String v0 = SystemProperties.get("ro.product.real_model");
        if (v0 == null || ("".equals(v0.trim()))) {
            v0 = Build.MODEL;//SystemProperties.get("ro.product.model");//由于这里是使用系统的方式去读取，还是没有读取到，同时和ro.product.model一样，那就使用Build来读取
            LogUtil.d("read_model is null, model = " + v0);
        }
        v1.append(v0);
        v1.append("</ro.product.real_model>");
        v1.append("<ro.board.platform>");
        v1.append(SystemProperties.get("ro.board.platform"));
        v1.append("</ro.board.platform>");
        v1.append("<ro.hardware>");
        v1.append(SystemProperties.get("ro.hardware"));
        v1.append("</ro.hardware>");
        v1.append("<ro.product.brand.replace>");
        v1.append(SystemProperties.get("ro.product.brand.replace"));
        v1.append("</ro.product.brand.replace>");
        v1.append("<ro.product.model.replace>");
        v1.append(SystemProperties.get("ro.product.model.replace"));
        v1.append("</ro.product.model.replace>");
        v1.append("<linux.version.date>");
        v1.append(XmlFileSolute.parseFingerData(linxeVersion)); //日期不正确，找到linuxVersion的时候，出现的日期 把对应的月份和日 年分别写出来就可以了
        v1.append("</linux.version.date>");
        v1.append("</prop>");
        v1.append("<device>");
        v1.append("</device>");
        v1.append("</root>\r\n");
        String deviceXmlInfo = v1.toString();
        return deviceXmlInfo;
    }

    /**
     * 执行getprop命令
     * @param arg
     * @return
     */
    private static String getProp(String arg) {
        LogUtil.loge("getprop == arg  = " + arg);
        String stdout = "";
        RootProcess process = new RootProcess("sh");
        process.execut("export PATH=/sbin:/vendor/bin:/system/sbin:/system/bin:/system/xbin:$PATH");
        stdout = process.execute("getprop " + arg, 10000).stdout;
        if (stdout != null) {
            process.closeAll();
            stdout = stdout.trim();
            return stdout;
        }
        return "";
    }

    private static String[] monthArr;

    static {
        XmlFileSolute.monthArr = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    }

    /**
     * 拼接时间日期
     * 包括 找到月份，日，年
     * 最后返回时间日期
     */
    private static String parseFingerData(String arg6) {
        String v0_1 = null;
        StringBuffer v2 = new StringBuffer();
      try {
          if (arg6 != null && !"".equals(arg6))
          {
              StringTokenizer v3 = new StringTokenizer(arg6.substring(arg6.indexOf("#") + 1), " ");
              int v0 = 0;
              while (v3.hasMoreTokens())
              {
                  String v4 = v3.nextToken();//开始的为1，需要遍历到日期的位置
                  while (true)
                  {
                      if (v0 >= monthArr.length) {
                          v0 = 0;
                          break;
                      }
                      /**
                       * 在这里取到了月份
                       * */
                      if (XmlFileSolute.monthArr[v0].equalsIgnoreCase(v4)) {//取某一个月
                          ++v0;
                          if (v0 < 10) {
                              v2.append("0");
                          }
                          v2.append(v0);//这里添加的是位置
                          if (!v3.hasMoreTokens()) {
                              break;
                          }
                          v0_1 = v3.nextToken();//去某一天
                          if (v0_1.length() == 1) {
                              v2.append("0");
                          }
                          v2.append(v0_1);
                          v0_1 = null;
                          break;
                      } else {
                          ++v0;
                          continue;
                      }
                  }
              }
              //这里是取年份
              while (v3.hasMoreTokens())
              {//这里已经取到了最后一个数据，导致循环直接退出了。
                  v0_1 = v3.nextToken();
                  if (v0_1 == null) {
                      continue;
                  }
                  if (v0_1.length() != 4) {
                      continue;
                  }
                  if (v0_1.length() == 4)
                  {
                      break;
                  }
              }

              v2.insert(0, v0_1);//这里是插入到第1个位置
          }
          if (v2.length() != 8)//总的长度是8 年月日 = 20150515
          {
              LogUtil.loge("Get Fingerprint Date wrong, fingerprint = " + arg6 + ", date = " + v2.toString());
          }
      }catch ( Throwable v)
      {
          LogUtil.exception("parse deviceInfo exception ",v);
      }
        return v2.toString();
    }

    /**
     * 解析网络xml
     * <p>
     * 第一阶段，找到根目录<root></root>
     */

    public static SolutionHelpers[] getSolutionHelpers(ArrayList arg7) {
        XmlPullParser v3 =null;
        try {
            int v1 = 0;
            ArrayList v2 = new ArrayList();
             v3 = XmlPullParserFactory.newInstance().newPullParser();
            int v5 = 0;
            while (true) {
                if (v1 >= arg7.size()) {
                    return (SolutionHelpers[]) v2.toArray(new SolutionHelpers[v2.size()]);
                }
                Object v0_1 = arg7.get(v1);
                v3.setInput(new StringReader(((String) v0_1)));
                if (v3.next() == 1) {
                    v1++;
                    continue;
                }
                String name = v3.getName();
                if (name == null) {
                    v1++;
                    continue;
                }
                v5 = v3.getEventType();
                if (!name.equals("root")) {
                    v1++;
                    continue;
                }
                SolutionHelpers v4_1 = parseXml(v3);
                if (v4_1 != null) {
                    v4_1.m = (String) v0_1;
                    v2.add(v4_1);
                    v1++;
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//----------------------------------------华丽的分割线-----------------------------------------


    public static void test() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                SolutionHelpers[] solutionHelpers = getSolutionHelpers(getData());
                if (solutionHelpers != null) {
                    LogUtil.e("读取到数据 ！！！");
                    for (SolutionHelpers so : solutionHelpers) {
                        LogUtil.e("data : " + so.toString() + "\n");
                    }
                }
            }
        }).start();
    }


    /**
     * 测试使用
     * 主要是测试解析函数的功能，返回的是arralist
     */
    private static ArrayList getData() {
        ArrayList list = new ArrayList();
        if (Utils.isSdcardMounted()) {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            String name = "so.txt";
            File file = new File(path, name);
            if (!file.exists() || !file.isFile()) {
                return null;
            }
            list =test(file);
        }
        return list;

    }

    private static ArrayList test(File file) {

        ArrayList list = null;
        LogUtil.d("test -- ");
        if (file.exists()) {
            LogUtil.e("ok");
            byte[] buf = new byte[2048];
            int len = -1;
            try {

                FileInputStream fis = new FileInputStream(file);
                ByteArrayOutputStream boas = new ByteArrayOutputStream();
                while (true) {
                    len = fis.read(buf);
                    if (len != -1) {
                        boas.write(buf, 0, len);
                        boas.flush();
                        boas.size();
                    }
                    if (len == -1) {
                        break;
                    }
                }
                boas.close();
                byte[] ret = boas.toByteArray();
                if (ret != null) {
                    String text = new String(ret, "UTF-8");//UTF-8
                    list = solutions(text);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                LogUtil.e("FileNotFoundException");
            } catch (IOException e) {
                e.printStackTrace();
                LogUtil.e("IOException");
            }

        } else {
            LogUtil.d("error");
        }
        return list;
    }

    /**
     * 解析solution字符串
     * 转成list，在解析xml文件
     */
    private static ArrayList solutions(String data) {
        StringBuilder sb = new StringBuilder("data: ");
        LogUtil.e("orignal data =" + data);
        ArrayList list = new ArrayList();
        int coutn = 0;
        if (data != null) {
            String[] split = data.split("</root>");
            for (String ret : split) {
                if (ret.indexOf("<root>") == -1) {
                    continue;
                }
                String text = ret.substring(ret.indexOf("<root>"));
                String solutions = text + "</root>";
                //记录文件信息，给android 调试
                LogUtil.e("solutions: " + solutions + "\n");
                list.add(solutions);
                coutn++;
            }
        }
        //调用解析函数
        String ret = sb.toString();
        LogUtil.e("resp: " + "\n" + ret + "\n count =" + coutn);
        return list;
    }


    //------------------------------------------华丽的分割线----------------------------------------


    /**
     * 第二阶段
     * 找到<root_zip></root_zip>
     */
    private static SolutionHelpers parseXml(XmlPullParser arg2) {
        SolutionHelpers retObj = null;
        if (arg2 == null) {
            return null;
        }
        try {
            do {
                if (arg2.next() != 1) {
                    String v0 = arg2.getName();
                    if (v0 == null) {
                        continue;
                    }
                    if (!v0.equals("root_zip")) {
                        continue;
                    }
                    break;
                } else {
                    return null;
                }

            } while (true);
            retObj = parseXmlEnd(arg2);
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.exception("解析xml配置文件出错 IOException", e);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            LogUtil.exception("解析xml配置文件出错 XmlPullParserException", e);
        }
        return retObj;
    }

    /**
     * 解析第二层
     * root_zip字段
     * 开始解析
     */

    private static SolutionHelpers parseXmlEnd(XmlPullParser arg5) {
        SolutionHelpers v1 = new SolutionHelpers();

        try {
            arg5.nextTag();
            arg5.require(2, "", "name");
            v1.a = arg5.nextText();
            arg5.require(3, "", "name");
        } catch (Exception v0) {
            v0.printStackTrace();
        }
        try {
            arg5.nextTag();
            arg5.require(2, "", "sid");
            v1.b = String.valueOf(Integer.parseInt(arg5.nextText()));
            arg5.require(3, "", "sid");
        } catch (Exception v0) {
            v0.printStackTrace();
        }

        try {
            arg5.nextTag();
            arg5.require(2, "", "version");
            v1.c = arg5.nextText();
            arg5.require(3, "", "version");
        } catch (Exception v0) {
            v0.printStackTrace();
        }
        try {
            arg5.nextTag();
            arg5.require(2, "", "type");
            v1.d = checkInteger(arg5.nextText(), 1);
            arg5.require(3, "", "type");
        } catch (Exception v0) {
            v0.printStackTrace();
        }

        try {
            arg5.nextTag();
            arg5.require(2, "", "size");
            v1.e = checkLonger(arg5.nextText());
            arg5.require(3, "", "size");
        } catch (Exception v0) {
            v0.printStackTrace();
        }

        try {
            arg5.nextTag();
            arg5.require(2, "", "verified");
            v1.f = checkInteger(arg5.nextText(), 1);
            arg5.require(3, "", "verified");
        } catch (Exception v0) {
            v0.printStackTrace();
        }

        try {
            arg5.nextTag();
            arg5.require(2, "", "md5");
            v1.g = arg5.nextText();
            arg5.require(3, "", "md5");
        } catch (Exception v0) {
            v0.printStackTrace();
        }
        try {
            arg5.nextTag();
            arg5.require(2, "", "encrypt");
            v1.h = arg5.nextText();
            arg5.require(3, "", "encrypt");
        } catch (Exception v0) {
            v0.printStackTrace();
        }

        try {
            arg5.nextTag();
            arg5.require(2, "", "url");
            v1.i = arg5.nextText();
            arg5.require(3, "", "url");
        } catch (Exception v0) {
            v0.printStackTrace();
        }

        try {
            arg5.nextTag();
            arg5.require(2, "", "backup_url");
            v1.j = arg5.nextText();
            arg5.require(3, "", "backup_url");
        } catch (Exception v0) {
            v0.printStackTrace();
        }

        try {
            arg5.nextTag();
            arg5.require(2, "", "exploit_type");
            v1.k = checkInteger(arg5.nextText(), 0);
            arg5.require(3, "", "exploit_type");
        } catch (Exception v0) {
            v0.printStackTrace();
            v1.k = 0;
        }

        try {
            arg5.nextTag();
            arg5.require(2, "", "interface_type");
            v1.l = checkInteger(arg5.nextText(), 0);
            arg5.require(3, "", "interface_type");
        } catch (Exception v0) {
            v0.printStackTrace();
            v1.l = 0;
        }
        return v1;
    }


    private static int checkInteger(String arg1, int arg2) {
        if (!TextUtils.isEmpty(((CharSequence) arg1))) {
            try {
                arg2 = Integer.parseInt(arg1);
            } catch (Exception v0) {
                v0.printStackTrace();
            }
        }

        return arg2;
    }

    private static long checkLonger(String arg3) {
        long v0 = 0;
        if (!TextUtils.isEmpty(((CharSequence) arg3))) {
            try {
                v0 = Long.parseLong(arg3);
            } catch (Exception v2) {
                v2.printStackTrace();
            }
        }

        return v0;
    }


}
