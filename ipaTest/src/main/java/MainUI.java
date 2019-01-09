/**
 * Created by Benjamin on 2018/12/2.
 */

import utils.IpaUtil;
import vo.ipaInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainUI extends JFrame implements ActionListener  {

    // 定义组件
    JButton EnterBtn,EmptyBtn; // 定义确认按钮
    JMenuItem MenuAbout;
    JLabel appName,packgeName,versionCode,versionName,minSdk,provisionName,AppIDName,UUID,TeamName,ExpirationDate;

    public static void main(String[] args) {
        MainUI mUI=new MainUI();
    }

    public MainUI()
    {

        // 创建组件并设置监听
        EnterBtn = new JButton("获取设备信息");
        EmptyBtn = new JButton("清空");
        EnterBtn.addActionListener(this);
        EmptyBtn.addActionListener(this);

        //初始化一个菜单栏
        JMenuBar menuBar = new JMenuBar();
        MenuAbout = new JMenuItem("About");
        menuBar.add(MenuAbout);
        myEvent();  // 加载菜单栏监听事件处理

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

        getContentPane().add(EnterBtn);
        getContentPane().add(EmptyBtn);

        this.setJMenuBar(menuBar);	//设置菜单栏
        this.setLayout(new GridLayout(0,2));    //选择GridLayout布局管理器
        this.setTitle("iOS-checkIPA");
        this.setSize(700,400);
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

        if(e.getActionCommand()=="获取设备信息") {
            File files = new File("C:\\Users\\xielianshi\\Desktop\\ipone.ipa");
            try {
                IpaUtil.getIpaMobileProvisio(files);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null,"读取ipa文件异常，请联系作者：benjamin_v@qq.com","提示消息",JOptionPane.WARNING_MESSAGE);
                e1.printStackTrace();
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
            return;

        }

        else if(e.getActionCommand() == "清空" ){

            appName.setText("NaN");
            packgeName.setText("NaN");
            versionCode.setText("NaN");
            versionName.setText("NaN");
            minSdk.setText("NaN");
            provisionName.setText("NaN");
            AppIDName.setText("NaN");
            UUID.setText("Nan");
            TeamName.setText("Nan");
            ExpirationDate.setText("Nan");
            return;

        }

    }

}
