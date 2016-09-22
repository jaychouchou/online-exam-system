package com.elt.service;

import com.elt.Exception.IdpassException;
import com.elt.entity.ExamInfo;
import com.elt.entity.QuestionInfo;
import com.elt.entity.User;

import java.util.ArrayList;

/**
 * Created by 肖安华 on java
 * 业务接口
 */
public interface ExamService {
    /**
     * 处理登入业务
     * @param id
     * @param pass
     * @return
     * @throws IdpassException
     */
    User login(String id,String pass) throws IdpassException;

    ExamInfo start();
    ArrayList<QuestionInfo> getExamQuestions();
    //获取页面上的考题信息
    QuestionInfo getQuestionInfo(int pageIndex);
    //保存答案
    void saveUserAnswers(int pageIndex,ArrayList<Integer> userAnswers);
    //获取总分数
    int getTotalScore();
}
