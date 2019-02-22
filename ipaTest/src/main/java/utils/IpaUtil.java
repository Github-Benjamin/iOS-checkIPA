
package utils;

import com.dd.plist.NSDictionary;
import com.dd.plist.NSString;
import com.dd.plist.PropertyListParser;
import vo.ipaInfo;
import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by Benjamin on 2019/1/8.
 */



public class IpaUtil {

    private static String reBody = "<plist version=\"1.0\">(.*?)</plist>";
    private static String reprovisionName = "<key>Name</key>.*?<string>(.*?)</string>";
    private static String reAppIDName = "<key>AppIDName</key>.*?<string>(.*?)</string>";
    private static String reUUID = "<key>UUID</key>.*?<string>(.*?)</string>";
    private static String reTeamName = "<key>TeamName</key>.*?<string>(.*?)</string>";
    private static String reExpirationDate = "<key>ExpirationDate</key>.*?<date>(.*?)</date>";
    private static String notFoundFile = "ipa no embedded.mobileprovision file";

    /**
     * 解压IPA文件，只获取IPA文件中的Info.plist、embedded.mobileprovision文件存储指定位置
     * @param file   zip文件
     * @param unzipDirectory     解压到的目录
     * @throws Exception
     */
    private static File getZipInfo(File file, String unzipDirectory) throws Exception{
        // 定义输入输出流对象
        InputStream input = null;
        OutputStream output = null;
        File result = null;
        File unzipFile = null;
        ZipFile zipFile = null;

        try{
            // 创建zip文件对象
            zipFile = new ZipFile(file);
            // 创建本zip文件解压目录
            String name = file.getName().substring(0, file.getName().lastIndexOf("."));
            unzipFile = new File(unzipDirectory + "/" + name);
            if(unzipFile.exists()){
                unzipFile.delete();
            }
            unzipFile.mkdir();
            // 得到zip文件条目枚举对象
            Enumeration<? extends ZipEntry> zipEnum = zipFile.entries();
            // 定义对象
            ZipEntry entry = null;
            String entryName = null;
            String names[] = null;
            int length;

            // 循环读取条目
            while(zipEnum.hasMoreElements()){
                // 得到当前条目
                entry = zipEnum.nextElement();
                entryName = new String(entry.getName());
                // 用/分隔条目名称
                names = entryName.split("\\/");
                length = names.length;
                for(int v = 0; v < length; v++){


                    if(entryName.endsWith(".app/embedded.mobileprovision")){ // 为embedded.mobileprovision文件,则输出到文件
                        input = zipFile.getInputStream(entry);
                        result = new File(unzipFile.getAbsolutePath() + "/embedded.mobileprovision");
                        ipaInfo.setMobileprovision(result); // 将embedded.mobileprovision存到模块实体类中
                        output = new FileOutputStream(result);
                        byte[] buffer = new byte[1024 * 8];
                        int readLen = 0;
                        while((readLen = input.read(buffer, 0, 1024 * 8)) != -1){
                            output.write(buffer, 0, readLen);
                        }
                        break;
                    }

                    if(entryName.endsWith(".app/Info.plist")){ // 为Info.plist文件,则输出到文件
                        input = zipFile.getInputStream(entry);
                        result = new File(unzipFile.getAbsolutePath() + "/Info.plist");
                        output = new FileOutputStream(result);
                        byte[] buffer = new byte[1024 * 8];
                        int readLen = 0;
                        while((readLen = input.read(buffer, 0, 1024 * 8)) != -1){
                            output.write(buffer, 0, readLen);
                        }
                        break;
                    }

                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally{
            if(input != null){
                input.close();
            }
            if(output != null){
                output.flush();
                output.close();
            }
            // 必须关流，否则文件无法删除
            if(zipFile != null){
                zipFile.close();
            }
        }

        // 如果有必要删除多余的文件
        if(file.exists()){
            file.delete();
        }

        return result;
    }



    /**
     * IPA文件的拷贝，把一个IPA文件复制为Zip文件,同时返回Info.plist文件 参数 oldfile 为 IPA文件
     */
    private static File getIpaInfo(File oldfile) throws IOException {
        try{
            int byteread = 0;
            String filename = oldfile.getAbsolutePath().replaceAll(".ipa", ".zip");
            File newfile = new File(filename);
            if(oldfile.exists()){
                // 创建一个Zip文件
                InputStream inStream = new FileInputStream(oldfile);
                FileOutputStream fs = new FileOutputStream(newfile);
                byte[] buffer = new byte[1444];
                while((byteread = inStream.read(buffer)) != -1){
                    fs.write(buffer, 0, byteread);
                }
                if(inStream != null){
                    inStream.close();
                }
                if(fs != null){
                    fs.close();
                }
                // 解析Zip文件
                return getZipInfo(newfile, newfile.getParent());
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 通过IPA文件获取Info.plist信息
     */
    public static void getIpaInfoMap(File ipa) throws Exception{

        File file = getIpaInfo(ipa);

//        Map<String,String> map = new HashMap<String,String>();

        // 需要第三方jar包dd-plist
        NSDictionary rootDict = (NSDictionary)PropertyListParser.parse(file);

        // 应用包名
        NSString parameters = (NSString)rootDict.objectForKey("CFBundleIdentifier");
//        map.put("CFBundleIdentifier", parameters.toString());
        ipaInfo.setPackgeName(parameters.toString());

        // 应用名称
        parameters = (NSString) rootDict.objectForKey("CFBundleName");
//        map.put("CFBundleName", parameters.toString());
        ipaInfo.setAppName(parameters.toString());

        // 应用外部版本
        parameters = (NSString)rootDict.objectForKey("CFBundleShortVersionString");
//        map.put("CFBundleShortVersionString", parameters.toString());
        ipaInfo.setVersionName(parameters.toString());

        // 应用内部版本
        parameters = (NSString)rootDict.objectForKey("CFBundleVersion");
//        map.put("CFBundleVersion", parameters.toString());
        ipaInfo.setVersionCode(parameters.toString());

        // 应用所需IOS最低版本
        parameters = (NSString)rootDict.objectForKey("MinimumOSVersion");
//        map.put("MinimumOSVersion", parameters.toString());
        ipaInfo.setMinSdk(parameters.toString());

        // 如果有必要，应该删除解压的结果文件
        FileUtils.fileDelet(file);

    }

    /**
     * 通过IPA文件获取embedded.mobileprovision信息
     */
    public static void getIpaMobileProvisio(File ipa) throws Exception {

        // 解压文件 Info.plist、embedded.mobileprovision，解析Info.plist文件并获取embedded.mobileprovision文件中的信息
        getIpaInfoMap(ipa);

        try {

            // 获取文件并以字符串的形式返回
            File filename = ipaInfo.getMobileprovision();
            String filedata = FileUtils.fileRead(filename);

            // 正则匹配Body字段
            String dictData = ReUtils.findString(filedata,reBody);

            // 匹配 provisionName
            ipaInfo.setProvisionName(ReUtils.findString(dictData,reprovisionName));

            // 匹配 reAppIDName
            ipaInfo.setAppIDName(ReUtils.findString(dictData,reAppIDName));

            // 匹配 UUID
            ipaInfo.setUUID(ReUtils.findString(dictData,reUUID).replaceAll("-",""));

            // 匹配 reTeamName
            ipaInfo.setTeamName(ReUtils.findString(dictData,reTeamName));

            // 匹配 reExpirationDate
            ipaInfo.setExpirationDate(ReUtils.findString(dictData,reExpirationDate));

            // 如果有必要，应该删除解压的结果文件
            FileUtils.fileDelet(filename);
        }catch(Exception e){

            ipaInfo.setProvisionName(notFoundFile);
            ipaInfo.setAppIDName(notFoundFile);
            ipaInfo.setUUID(notFoundFile);
            ipaInfo.setTeamName(notFoundFile);
            ipaInfo.setExpirationDate(notFoundFile);

            e.printStackTrace();
        }

    }



}

