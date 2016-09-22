package com.elt.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 肖安华 on java.
 * 题目信息实体类
 */
public class QuestionInfo implements Serializable {
    private Question question;//当前页的考试题目
    private int pageIndex;//当前页
    private ArrayList<Integer> userAnswers = new ArrayList<Integer>();//用户选择的答案
    public QuestionInfo(){};

    public QuestionInfo(Question question, int pageIndex) {
        super();
        this.question = question;
        this.pageIndex = pageIndex;
    }

    public Question getQuestion() {
        return question;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }
    public int getPageIndex() {
        return pageIndex;
    }
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
    public ArrayList<Integer> getUserAnswers() {
        return userAnswers;
    }
    public void setUserAnswers(ArrayList<Integer> userAnswers) {
        this.userAnswers = userAnswers;
    }
    @Override
    public String toString() {
        return question.toString();
    }
}
