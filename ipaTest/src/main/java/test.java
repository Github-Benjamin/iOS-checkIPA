
import utils.IpaUtil;
import vo.ipaInfo;
import java.io.*;


/**
 * Created by Benjamin on 2019/1/8.
 */
public class test {

    private static String filename = "C:\\Users\\xielianshi\\Desktop\\iOS测试整理\\test\\享拍乐iphone版下载.ipa";

    public static void main(String[] args) throws Exception {


        String[] endName = filename.split(".");
        System.out.println(filename.substring(filename.length() - 3));

//        File files = new File(filename);
//        IpaUtil.getIpaMobileProvisio(files);
//
//        // 获取一条信息
//        System.out.println(ipaInfo.getAppName());
//        System.out.println(ipaInfo.getPackgeName());
//        System.out.println(ipaInfo.getVersionCode());
//        System.out.println(ipaInfo.getVersionName());
//        System.out.println(ipaInfo.getMinSdk());
//
//        System.out.println(ipaInfo.getProvisionName());
//        System.out.println(ipaInfo.getAppIDName());
//        System.out.println(ipaInfo.getUUID());
//        System.out.println(ipaInfo.getTeamName());
//        System.out.println(ipaInfo.getExpirationDate());

    }

}
