package com.elt.view;

import com.elt.control.ClientContext;
import com.elt.entity.ExamInfo;
import com.elt.entity.QuestionInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by 肖安华 on java.
 * 答题界面
 */
public class ExamFrame extends JFrame{
   JLabel jlabel;
    JTextArea text;
    JLabel label;
    JLabel timerLabel;
    JButton pre;
    JButton next;
    ArrayList<Option> options=new ArrayList<>();
    ClientContext context;
    //给视图设置控制器，并在控制器中设置视图
    public void setContext(ClientContext context) {
        this.context = context;
        this.context.setExamFrame(this);
    }
    //答题界面的构造方法
    public ExamFrame(){
        this.setTitle("我赢职场在线考试系统");
        this.setSize(680,380);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setContentPane(creatContentPanel());
    }
    //答题界面JFrame中的的ContentPanel面板
    private JPanel creatContentPanel() {
        JPanel jpanel=new JPanel(new BorderLayout());
        URL url=this.getClass().getResource("exam_title.png");
        ImageIcon icon=new ImageIcon(url);
        //borderlayout，分为上中下三块
        jpanel.add(BorderLayout.NORTH,new JLabel(icon,JLabel.CENTER));
        jpanel.add(BorderLayout.CENTER,createCenterPanel());
        jpanel.add(BorderLayout.SOUTH,createSPanel());
        return jpanel;
    }

    //面板的中心部分，也分为上中下三部分
    private JPanel createCenterPanel() {
    JPanel jpanel=new JPanel(new BorderLayout());
        jlabel=new JLabel("姓名:xxx 编号:0001 考试时间:1分钟 题量:20",JLabel.CENTER);
        jpanel.add(BorderLayout.NORTH,jlabel);
        jpanel.add(BorderLayout.CENTER,createQuestionPanel());
        jpanel.add(BorderLayout.SOUTH,createOptionsPanel());
        return jpanel;
    }
    //面板的下部分
    private JPanel createSPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        label = new JLabel("共20题当前是第一个", JLabel.LEFT);
        panel.add(BorderLayout.WEST, label);
        panel.add(BorderLayout.CENTER, createBtnPanel());
        timerLabel =  new JLabel("00:00:59", JLabel.RIGHT);
        panel.add(BorderLayout.EAST,timerLabel);
        return panel;
    }
    //面板下部分的按钮区
    private JPanel createBtnPanel() {
        JPanel panel=new JPanel();
        pre=new JButton("上一题");
        pre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                context.pre();
            }
        });
        next=new JButton("下一题");
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                context.next();
            }
        });
        JButton submit=new JButton("交卷");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                context.submit();
            }
        });
        panel.add(pre);
        panel.add(next);
        panel.add(submit);
        return panel;
    }

    //面板中心的问题部分
    private JScrollPane createQuestionPanel(){
        JScrollPane panel=new JScrollPane();
        text=new JTextArea();
        text.setText("A\n\nB\n\n");
        text.setLineWrap(true);
        text.setFocusable(false);
        panel.getViewport().add(text);
        return panel;
    }
    //面板中心的答案部分
    private JPanel createOptionsPanel(){
        JPanel jpanel=new JPanel();
        Option box1 = new Option("A",1);
        Option box2 = new Option("B",2);
        Option box3 = new Option("C",3);
        Option box4 = new Option("D",4);
        jpanel.add(box1);
        jpanel.add(box2);
        jpanel.add(box3);
        jpanel.add(box4);
        options.add(box1);
        options.add(box2);
        options.add(box3);
        options.add(box4);
        return jpanel;
    }
    private class Option extends JCheckBox{
        private int value;
        public Option(String name,int value){
            super(name);
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }
    //更新视图
    public void updateView(ExamInfo examInfo, QuestionInfo questionInfo){
        jlabel.setText(examInfo.toString());
        int pageIndex = questionInfo.getPageIndex();
        updateBtn(pageIndex);
        updateOptions(questionInfo);
        text.setText(questionInfo.getQuestion().toString());
        label.setText("共20题，当前是第"+(questionInfo.getPageIndex()+1)+"题");
    }
    //更新按钮
    public void updateBtn(int pageIndex){
        if(pageIndex==0){
            pre.setEnabled(false);
        }else pre.setEnabled(true);
        if(pageIndex==3){
            next.setEnabled(false);
        }else next.setEnabled(true);
    }
    //更新选项
    public void updateOptions(QuestionInfo questionInfo) {
        ArrayList<Integer> answers = questionInfo.getUserAnswers();
        for (Option option : options) {
            option.setSelected(answers.contains(option.getValue()));
        }
    }
    //保存用户选择的答案
    public ArrayList<Integer> getUserAnswers(){
        ArrayList<Integer> userAnswers = new ArrayList<Integer>();
        for(Option option:options){
            if(option.isSelected())
                userAnswers.add(option.getValue());
        }
        return userAnswers;
    }
    public void updataTime(long sub){
        int hours=(int)sub/1000/60/60;
        int minute=(int)sub/1000/60%60;
        int second=(int)sub/1000%60;
        String shours=getTime(hours);
        String sminute=getTime(minute);
        String ssecond=getTime(second);

    timerLabel.setText(shours+":"+sminute+":"+ssecond);

    }
    public String getTime(int time){
        String stime="00";
        if(time<10){
            stime="0"+time;
        }else
            stime=time+"";
        return stime;
    }


}
