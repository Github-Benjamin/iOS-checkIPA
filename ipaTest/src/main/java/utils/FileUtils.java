package utils;

import java.io.*;

/**
 * Created by Benjamin on 2019/1/9.
 */
public class FileUtils {

    // 回收资源,删除文件
    public static void fileDelet(File filename){
        // 如果有必要，应该删除解压的结果文件
        boolean result = filename.delete();
        if (!result) {
            System.gc();    // 强制解除对文件的占用,回收资源
            filename.delete();
        }
        filename.getParentFile().delete();
    }

    // 读文件并以字符串形式返回
    public static String fileRead(File filename) throws IOException {
        /* 读入文件 */
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
        BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

        String filedata = "";
        String line = "";
        line = br.readLine();
        while (line != null) {
            line = br.readLine(); // 一次读入一行数据
            filedata += line;
        }
        br.close(); // 关闭文件流

        return filedata;
    }

}
