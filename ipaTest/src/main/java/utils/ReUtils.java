package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Benjamin on 2019/1/9.
 */
public class ReUtils {

    // 传入正则表达式和字符串匹配指定字符串
    public static String findString(String filedata,String re){
        // 正则匹配dict字段
        Pattern fildDict = Pattern.compile(re);
        Matcher fildDictData = fildDict.matcher(filedata);
        if ( fildDictData.find( )) {
            return fildDictData.group(1);
        }
        return null;
    }

}
