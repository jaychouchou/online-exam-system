package com.elt.control;

import com.elt.Exception.IdpassException;
import com.elt.entity.ExamInfo;
import com.elt.entity.QuestionInfo;
import com.elt.entity.User;
import com.elt.service.ExamService;
import com.elt.view.ExamFrame;
import com.elt.view.LoginFrame;
import com.elt.view.MenuFrame;

import javax.swing.*;
import java.util.*;
import java.util.Timer;

/**
 * Created by 肖安华 on java.
 * 登入的控制器
 */
public class ClientContext {
    private LoginFrame loginFrame;
    private MenuFrame menuFrame;
    private ExamFrame examFrame;
    private ExamService examService;
    private User user;
    private QuestionInfo currentInfo;
    private ExamInfo examInfo;
    private int total=0;
    private boolean isOver;
    private Timer timer;

    public void setExamService(ExamService examService) {
        this.examService = examService;
    }
    public void setExamFrame(ExamFrame examFrame) {
        this.examFrame = examFrame;
    }

    public void setLoginFrame(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
    }

    public void setMenuFrame(MenuFrame menuFrame) {
        this.menuFrame = menuFrame;
    }
    public void exit(){
        if(JOptionPane.YES_OPTION==JOptionPane.showConfirmDialog(null,"确定要退出吗？")){
            System.exit(0);
        }

    }
    public void login()throws IdpassException{
        String id=loginFrame.getId();
        if(id==null||"".equals(id))
            throw new IdpassException("编号不能为空");
        String password=loginFrame.getPassWord();
        if (password==null||"".equals(password))
            throw new IdpassException("密码不能为空");
        user=examService.login(id,password);
        loginFrame.setVisible(false);
        menuFrame.updateMenuFrame();
        menuFrame.setVisible(true);
    }
    public User getUser(){
        return user;
    }
    //开始考试的方法
    public void begin(){

        //初始化页面标签上的数据
        examInfo=this.examService.start();
        this.currentInfo=examService.getQuestionInfo(0);
        examInfo.setUser(user);
        examFrame.updateView(examInfo,currentInfo);
        menuFrame.setVisible(false);
        examFrame.setVisible(true);
        startTimer();
    }
    private void startTimer(){
       final long end=System.currentTimeMillis()+examInfo.getTimerLimit()*60*1000;
       timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
               long sub=end-System.currentTimeMillis();
                examFrame.updataTime(sub);
                if (sub<=0)
                    over();
            }
        },0,1000);
    }
    //下一题
    public void next(){
        int pageIndex=this.currentInfo.getPageIndex();
        ArrayList<Integer> userAnswers=examFrame.getUserAnswers();
        examService.saveUserAnswers(pageIndex,userAnswers);
        //获取下一页
        pageIndex=pageIndex+1;
        QuestionInfo questionInfo=this.examService.getQuestionInfo(pageIndex);
        examFrame.updateView(examInfo,questionInfo);
        this.currentInfo=questionInfo;
    }
    //上一题
    public void pre(){
        int pageIndex=this.currentInfo.getPageIndex();
        ArrayList<Integer> userAnswers=examFrame.getUserAnswers();
        examService.saveUserAnswers(pageIndex,userAnswers);
        pageIndex=pageIndex-1;
        QuestionInfo questionInfo=this.examService.getQuestionInfo(pageIndex);
        examFrame.updateView(examInfo,questionInfo);
        this.currentInfo=questionInfo;
    }
    public void submit(){
        if(JOptionPane.YES_OPTION==JOptionPane.showConfirmDialog(examFrame,"确定要交卷吗？")){
            over();
        }else
            return;
    }
    private void over(){
        isOver=true;
        timer.cancel();
        //把交卷的当前页的答案保存
        ArrayList<Integer> userAnswers=examFrame.getUserAnswers();
        examService.saveUserAnswers(this.currentInfo.getPageIndex(),userAnswers);
        total=this.examService.getTotalScore();
        JOptionPane.showMessageDialog(examFrame,total);
        examFrame.setVisible(false);
        menuFrame.setVisible(true);
    }
    public void check(){
        if(isOver==true)
        JOptionPane.showMessageDialog(examFrame,"你的分数是"+total);
        else
            JOptionPane.showMessageDialog(examFrame,"请先参加考试！");
    }

}
