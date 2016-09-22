package com.elt.entity;

import java.io.Serializable;

/**
 * Created by 肖安华 on java.
 * 考试信息实体类
 */
public class ExamInfo implements Serializable {
    private User user;//考试的用户信息
    private int timerLimit;//时间限制
    private String title;//考试科目
    private int totalNumbers;//考试题目数量

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTimerLimit() {
        return timerLimit;
    }

    public void setTimerLimit(int timerLimit) {
        this.timerLimit = timerLimit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotalNumbers() {
        return totalNumbers;
    }

    public void setTotalNumbers(int totalNumbers) {
        this.totalNumbers = totalNumbers;
    }

    @Override
    public String toString() {
        return "姓名:" + user.getName() + " 编号:"
                +user.getId() + " 考试时间:"
                + timerLimit + "分钟" + " 科目:"
                +title + " 题量:"
                + totalNumbers;
    }
}
