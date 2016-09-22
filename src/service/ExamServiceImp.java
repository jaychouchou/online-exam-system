package com.elt.service;

import com.elt.Exception.IdpassException;
import com.elt.dao.EntityContext;
import com.elt.entity.ExamInfo;
import com.elt.entity.Question;
import com.elt.entity.QuestionInfo;
import com.elt.entity.User;
import com.elt.util.ReadUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by 肖安华 on java.
 * 业务实现类
 */
public class ExamServiceImp implements ExamService{

    private EntityContext entityContext;
    ArrayList<QuestionInfo> examQuestions;
    //总分数
    int totalScore;

    /**
     * 为业务设置所需数据
     * @param entityContext
     */
    public void setEntityContext(EntityContext entityContext){
        this.entityContext=entityContext;
    }

    @Override
    public User login(String id,String pass) throws IdpassException{
        HashMap<String,User> users=entityContext.getUsers();
        User user=users.get(id);
        if (user==null){
            throw new IdpassException("用户名不存在");
        }
        if(!user.getPass().equals(pass)){
            throw new IdpassException("密码不正确");
        }
        return user;
    }
    @Override
    public ExamInfo start(){
        // 获取考试题目
        getExamQuestions();
        ExamInfo examInfo=new ExamInfo();
        //examInfo.setUser(user);
        examInfo.setTitle(ReadUtil.getMass("examtitle"));
        examInfo.setTimerLimit(Integer.parseInt(ReadUtil.getMass("timerLimit")));
        examInfo.setTotalNumbers(Integer.parseInt(ReadUtil.getMass("totalNumbers")));
        return examInfo;
    }
    @Override
    public ArrayList<QuestionInfo> getExamQuestions(){
        int index=0;
        Random random=new Random();
        //一次考试的所有试题
         examQuestions=new ArrayList<QuestionInfo>();
        //获得题库信息
        HashMap<Integer,ArrayList<Question>> questions=entityContext.getQuestions();
        for (int key = 0; key <2; key++) {
            // 这个等级所有的题目
            ArrayList<Question> levelQuestions = new ArrayList<Question>(questions.get(key));
            Question question1 = levelQuestions.remove(random
                    .nextInt(levelQuestions.size()));
            examQuestions.add(new QuestionInfo(question1, index++));
            Question question2 = levelQuestions.remove(random
                    .nextInt(levelQuestions.size()));
            examQuestions.add(new QuestionInfo(question2, index++));
        }
        return examQuestions;
    }
    @Override
    public QuestionInfo getQuestionInfo(int pageIndex){
        QuestionInfo questionInfo=this.examQuestions.get(pageIndex);
        return questionInfo;
    }
    @Override
    public void saveUserAnswers(int pageIndex, ArrayList<Integer> userAnswers) {
        QuestionInfo questionInfo = this.examQuestions.get(pageIndex);
        questionInfo.getUserAnswers().clear();
        questionInfo.getUserAnswers().addAll(userAnswers);

    }

    @Override
    public int getTotalScore() {
        totalScore=0;
        //遍历考试的所有试题
        for (QuestionInfo questionInfo:examQuestions) {
            //获取标准答案
          ArrayList<Integer>  answers= questionInfo.getQuestion().getAnswers();
            //获取选择的答案
            ArrayList<Integer> userAnswers=questionInfo.getUserAnswers();
            if(answers.equals(userAnswers))
                totalScore+=questionInfo.getQuestion().getScore();
        }
        return totalScore;
    }
}
