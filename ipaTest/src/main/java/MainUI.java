/**
 * Created by Benjamin on 2018/12/2.
 */

import utils.FileUtils;
import utils.IpaUtil;
import vo.ipaInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainUI extends JFrame implements ActionListener  {

    // 定义组件
    private static FileDialog openDia; // 文件打开窗口
    JButton EnterBtn,EmptyBtn; // 定义确认按钮
    JMenuItem MenuAbout;
    JLabel ipainfo,noneline,fileinfo,appName,packgeName,versionCode,versionName,minSdk,provisionName,AppIDName,UUID,TeamName,ExpirationDate,filePaths,fileMD5,fileSize;

    public static void main(String[] args) {
        MainUI mUI=new MainUI();
    }

    public MainUI()
    {

        // 创建组件并设置监听
        EnterBtn = new JButton("获取IPA信息");
        EmptyBtn = new JButton("清空");
        EnterBtn.addActionListener(this);
        EmptyBtn.addActionListener(this);

        //初始化一个菜单栏
        JMenuBar menuBar = new JMenuBar();
        MenuAbout = new JMenuItem("About");
        menuBar.add(MenuAbout);
        myEvent();  // 加载菜单栏监听事件处理

        getContentPane().add(new JLabel("IPA 信息", SwingConstants.CENTER ));
        ipainfo = new JLabel("");
        getContentPane().add(ipainfo);

        getContentPane().add(new JLabel("appName：", SwingConstants.CENTER ));
        appName = new JLabel("CFBundleName");
        getContentPane().add(appName);

        getContentPane().add(new JLabel("packgeName：", SwingConstants.CENTER ));
        packgeName = new JLabel("CFBundleIdentifier");
        getContentPane().add(packgeName);

        getContentPane().add(new JLabel("versionName：", SwingConstants.CENTER ));
        versionName = new JLabel("CFBundleShortVersionString");
        getContentPane().add(versionName);

        getContentPane().add(new JLabel("versionCode：", SwingConstants.CENTER ));
        versionCode = new JLabel("CFBundleVersion");
        getContentPane().add(versionCode);

        getContentPane().add(new JLabel("minSdk：", SwingConstants.CENTER ));
        minSdk = new JLabel("MinimumOSVersion");
        getContentPane().add(minSdk);

        getContentPane().add(new JLabel("provisionName：", SwingConstants.CENTER ));
        provisionName = new JLabel("provisionName");
        getContentPane().add(provisionName);

        getContentPane().add(new JLabel("AppIDName：", SwingConstants.CENTER ));
        AppIDName = new JLabel("provisionAppIDName", SwingConstants.LEFT );
        getContentPane().add(AppIDName);

        getContentPane().add(new JLabel("UUID：", SwingConstants.CENTER ));
        UUID = new JLabel("provisionUUID", SwingConstants.LEFT );
        getContentPane().add(UUID);

        getContentPane().add(new JLabel("TeamName：", SwingConstants.CENTER ));
        TeamName = new JLabel("TeamName", SwingConstants.LEFT );
        getContentPane().add(TeamName);

        getContentPane().add(new JLabel("ExpirationDate：", SwingConstants.CENTER ));
        ExpirationDate = new JLabel("ExpirationDate", SwingConstants.LEFT );
        getContentPane().add(ExpirationDate);

        getContentPane().add(new JLabel("   ", SwingConstants.CENTER ));
        noneline = new JLabel(" ");
        getContentPane().add(noneline);

        getContentPane().add(new JLabel("文件信息", SwingConstants.CENTER ));
        fileinfo = new JLabel("");
        getContentPane().add(fileinfo);

        getContentPane().add(new JLabel("filePath：", SwingConstants.CENTER ));
        filePaths = new JLabel("filePath");
        getContentPane().add(filePaths);

        getContentPane().add(new JLabel("fileMD5：", SwingConstants.CENTER ));
        fileMD5 = new JLabel("fileMD5");
        getContentPane().add(fileMD5);

        getContentPane().add(new JLabel("fileSize：", SwingConstants.CENTER ));
        fileSize = new JLabel("fileSize");
        getContentPane().add(fileSize);

        getContentPane().add(new JLabel("   ", SwingConstants.CENTER ));
        noneline = new JLabel(" ");
        getContentPane().add(noneline);

        getContentPane().add(EnterBtn);
        getContentPane().add(EmptyBtn);

        openDia = new FileDialog(this, "打开", FileDialog.LOAD);

        this.setJMenuBar(menuBar);	//设置菜单栏
        this.setLayout(new GridLayout(0,2));    //选择GridLayout布局管理器
        this.setTitle("iOS-checkIPA");
        this.setSize(700,500);
        this.setLocation(0, 0);
        this.setLocationRelativeTo(null);//窗体居中显示
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //设置当关闭窗口时，保证JVM也退出
        this.setVisible(true);
        this.setResizable(true);

    }


    // 菜单栏监听
    private void myEvent()
    {
        // About 菜单栏监听
        MenuAbout.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(null, "Author: Benjamin\nWeChat: WeChat_Benjamin\nEmail: Benjamin_v@qq.com", "AboutInfo",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        });
    }


    // 事件判断
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand()=="获取IPA信息") {

            getFilePath();  // 获取文件路径
            String filePath = ipaInfo.getFilePath();


            if ( filePath == null ){    //判断路径和文件是否为空，或非ipa后缀文件
                JOptionPane.showMessageDialog(null,"读取ipa文件异常，请切换文件或替换文件目录后重试。","提示消息",JOptionPane.WARNING_MESSAGE);
                return;
            } else {
                try {
                    FileUtils.getFileSize(filePath);    // 获取文件信息
                    File files = new File(filePath);    // 获取ipa文件路径
                    IpaUtil.getIpaMobileProvisio(files);    // 获取并分析文件信息
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null,"读取ipa文件异常，请切换文件或替换文件目录后重试。","提示消息",JOptionPane.WARNING_MESSAGE);
                    e1.printStackTrace();
                    return;
                }

                appName.setText(ipaInfo.getAppName());
                packgeName.setText(ipaInfo.getPackgeName());
                versionCode.setText(ipaInfo.getVersionCode());
                versionName.setText(ipaInfo.getVersionName());
                minSdk.setText(ipaInfo.getMinSdk());
                provisionName.setText(ipaInfo.getProvisionName());
                AppIDName.setText(ipaInfo.getAppIDName());
                UUID.setText(ipaInfo.getUUID());
                TeamName.setText(ipaInfo.getTeamName());
                ExpirationDate.setText(ipaInfo.getExpirationDate());

                filePaths.setText(ipaInfo.getFilePath());
                fileMD5.setText(ipaInfo.getFileMD5());
                fileSize.setText( ipaInfo.getFileSizeByte() + " 字节（" + ipaInfo.getFileSizeMB() + " MB）"  );

                return;
            }

        }

        else if(e.getActionCommand() == "清空" ){

            Object ipaInfo=new ipaInfo();
            ipaInfo = null; // 清空对象,对象被赋值为null将被视为垃圾

            appName.setText("NaN");
            packgeName.setText("NaN");
            versionCode.setText("NaN");
            versionName.setText("NaN");
            minSdk.setText("NaN");
            provisionName.setText("NaN");
            AppIDName.setText("NaN");
            UUID.setText("NaN");
            TeamName.setText("NaN");
            ExpirationDate.setText("NaN");

            filePaths.setText("NaN");
            fileMD5.setText("NaN");
            fileSize.setText("NaN");

            return;

        }

    }

    // 弹出窗口选择获取文件路径
    public static void getFilePath(){
        openDia.setVisible(true);    // 显示打开文件对话框
        String dirpath = openDia.getDirectory();    //获取打开文件路径并保存到字符串中。
        String fileName = openDia.getFile();    //获取打开文件名称并保存到字符串中
        // System.out.println(dirpath+fileName);    // 打印获取到的文件路径
        FileUtils.fileName(dirpath,fileName);  // 判断文件后缀
    }


}
