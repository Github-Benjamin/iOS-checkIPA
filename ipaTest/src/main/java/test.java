
import utils.IpaUtil;
import vo.ipaInfo;
import java.io.*;


/**
 * Created by Benjamin on 2019/1/8.
 */
public class test {


    public static void main(String[] args) throws Exception {

        File files = new File("C:\\Users\\xielianshi\\Desktop\\test\\aibench.ipa");
        IpaUtil.getIpaMobileProvisio(files);

        // 获取一条信息
        System.out.println(ipaInfo.getAppName());
        System.out.println(ipaInfo.getPackgeName());
        System.out.println(ipaInfo.getVersionCode());
        System.out.println(ipaInfo.getVersionName());
        System.out.println(ipaInfo.getMinSdk());

        System.out.println(ipaInfo.getProvisionName());
        System.out.println(ipaInfo.getAppIDName());
        System.out.println(ipaInfo.getUUID());
        System.out.println(ipaInfo.getTeamName());
        System.out.println(ipaInfo.getExpirationDate());

    }

}
