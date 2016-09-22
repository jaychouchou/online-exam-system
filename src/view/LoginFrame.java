package com.elt.view;

import com.elt.Exception.IdpassException;
import com.elt.control.ClientContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by 肖安华 on java.
 * 登入界面
 */
public class LoginFrame extends JFrame {
    //用户名
    JTextField text;
    //密码
    JPasswordField passwordField;
    //控制器
    ClientContext context;
    //给视图设置控制器，并在控制器中设置视图
    public void setContext(ClientContext context) {
        this.context = context;
        this.context.setLoginFrame(this);
    }

    public LoginFrame() {
        this.setTitle("我赢职场在线考试系统");
        //this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //监听事件
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
               context.exit();
            }
        });
        this.setLocationRelativeTo(null);
        this.setSize(280,200);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setContentPane(createContentPane());
    }

    private JPanel createContentPane() {
        JPanel jpanel=new JPanel(new BorderLayout());
        jpanel.add(BorderLayout.NORTH,createNpanel());
        jpanel.add(BorderLayout.CENTER,createCenterPane());
        jpanel.add(BorderLayout.SOUTH,createSpanel());
        return jpanel;
    }

    private JPanel createSpanel() {
        JPanel jpanel=new JPanel();
        JButton jbutton=new JButton("确定");
        jbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    context.login();
                } catch (IdpassException e1) {
                    JOptionPane.showMessageDialog(LoginFrame.this,e1.getMessage());
                }
            }
        });
        JButton jbutton1=new JButton("取消");
        jpanel.add(jbutton);
        jpanel.add(jbutton1);
        jbutton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                context.exit();
            }
        });
        return jpanel;
    }

    private JPanel createCenterPane() {
        JPanel jpanel=new JPanel(new BorderLayout());
        jpanel.add(BorderLayout.NORTH,createNamePassPanel());

        return jpanel;
    }

    private JPanel createNamePassPanel() {
        JPanel jpanel=new JPanel(new GridLayout(2,1));
        jpanel.add(createNamePanel());
        jpanel.add(createPassPanel());
        return jpanel;
    }

    private JPanel createNamePanel(){
        JPanel jpanel=new JPanel(new BorderLayout());
        JLabel jlabel=new JLabel("编号：");
        text=new JTextField(10);
        jpanel.add(BorderLayout.WEST,jlabel);
        jpanel.add(BorderLayout.CENTER,text);
        return jpanel;
    }
    private JPanel createPassPanel(){
        JPanel jpanel=new JPanel(new BorderLayout());
        JLabel jlabel=new JLabel("密码：");
        passwordField=new JPasswordField(10);
        jpanel.add(BorderLayout.WEST,jlabel);
        jpanel.add(BorderLayout.CENTER,passwordField);
        return jpanel;
    }

    private JPanel createNpanel() {
        JPanel jpanel=new JPanel();
        jpanel.add(new JLabel("登入系统"),JLabel.CENTER);
        return jpanel;
    }

    /**
     * 获取id
     * @return
     */
    public String getId(){
        return text.getText();
    }

    /**
     * 获取密码
     * @return
     */
    public String getPassWord(){
        return new String(passwordField.getPassword());
    }

}
