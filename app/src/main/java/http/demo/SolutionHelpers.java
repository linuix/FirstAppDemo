package http.demo;

public final class SolutionHelpers {


    /**
     *
     *  <root>
     <root_zip>
     <name>00000000000000000001477537294934.jar</name>
     <sid>00000000000000000000000000000800</sid>
     <version>387043</version>
     <type>1</type>
     <size>2157083</size>
     <verified>1</verified>
     <md5>1a31128b1383cd0f91eaae6eff6c6c5b</md5>
     <encrypt>1</encrypt>
     <url>http://king.myapp.com/myapp/Kingroot/webapp_kingroot/solution_test/00000000000000000001477537294934.jar</url>
     <backup_url>http://king.myapp.com/myapp/Kingroot/webapp_kingroot/solution_test/00000000000000000001477537294934.jar</backup_url>
     <exploit_type>0</exploit_type>
     <interface_type>3</interface_type>
     </root_zip>
     <id>1879</id>
     <weight>9100</weight>
     <verify>1</verify>
     </root>
     *
     *
     * **/


    public String jarName;//jarname
    public String sindex;//sindex
    public String version;//version
    public int type;//type
    public long fileSize;//filesize
    public int veriFied;//verified
    public String md5;//md5
    public String encrypt;//encrypt
    public String url;//url
    public String j;//backup_url
    public int k;//exploit_type
    public int l;//interface_type
    public String m;//所有的数据段字符串，从 ,<root></root>
    public String filePath;//文件的存储路径 /data/data/pkgName/app_krsdk/jars/sindex
    public SolutionHelpers() {
        super();
    }

    @Override
    public String toString() {
        return "SolutionHelpers{" +
                "sdk_gt18='" + jarName + '\'' +
                ", sindex='" + sindex + '\'' +
                ", chart='" + version + '\'' +
                ", type=" + type +
                ", fileSize=" + fileSize +
                ", getHead_Content_Type=" + veriFied +
                ", md5='" + md5 + '\'' +
                ", encrypt='" + encrypt + '\'' +
                ", url='" + url + '\'' +
                ", j='" + j + '\'' +
                ", k=" + k +
                ", l=" + l +
                ", m='" + m + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}

