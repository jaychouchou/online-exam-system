package com.elt.dao;

import com.elt.entity.Question;
import com.elt.entity.User;
import com.elt.util.IOUtil;
import com.elt.util.ReadUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 肖安华 on java.
 * DAO层，操作数据
 */
public class EntityContext {
    //用HashMap来存储用户信息
    HashMap<String,User> users=new HashMap<String, User>();
    public  HashMap<String,User> getUsers(){
        return users;
    }
    /*
    * 存放题库的集合--->所有的题以等级为key，ArrayList为value，ArrayList中存放的是同一个等级的题目
    * */
    private HashMap<Integer,ArrayList<Question>> questions=new HashMap<Integer,ArrayList<Question>>();
    public HashMap<Integer,ArrayList<Question>> getQuestions(){
        return questions;
    }
    //在构造方法中加载数据
    public EntityContext(){
        try {
            LoadUsers();
            loadQuestion();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //加载用户信息，采用文本形式存储
    private void LoadUsers() throws IOException{
        BufferedReader read= IOUtil.getRead(ReadUtil.getMass("userpath"));
        String s=null;
        while ((s=read.readLine())!=null){
            if(s.startsWith("#"))continue;
            String[] mass=s.split(":");
            User user=new User(mass[0],mass[1],mass[2],mass[3]);
            users.put(user.getId(),user);
        }
        IOUtil.close();
    }
    //加载题库的信息，采用文本形式存储
    private void loadQuestion()throws IOException{
        BufferedReader reader=IOUtil.getRead(ReadUtil.getMass("questionpath"));
        String row=null;
       while((row=reader.readLine())!=null){
           Question question=parseQuestion(reader,row);
           putQuestion(question);
       }

    }
    /**
     * 把题库中的题目等级做为key   题目的集合做为value放入Map
     */
    private void putQuestion(Question question){
        int leave=question.getLevel();
        if(questions.containsKey(leave)){
            questions.get(leave).add(question);
        }else {
            ArrayList<Question> leavequestions=new ArrayList<>();
            leavequestions.add(question);
            questions.put(leave,leavequestions);
        }
    }
    //解析一个题目，并封装成question对象
    private Question parseQuestion(BufferedReader reader,String row)throws IOException{
        Question question=new Question();
        String regex="[@,][a-z]+=";
        String[] ss=row.split(regex);
        question.setScore(Integer.parseInt(ss[2]));
        question.setLevel(Integer.parseInt(ss[3]));
        question.setAnswers(getAnswers(ss[1]));
        question.setTitle(reader.readLine());//设置题目标题
        question.setOptions(getOptions(reader));//设置题目选项内容
        return question;
    }
     /*
     *解析题目的答案
     * */
    private ArrayList<Integer> getAnswers(String answers){
        ArrayList<Integer> arrayList=new ArrayList<Integer>();
        String[] ss=answers.split("/");
        for (String s :ss){
            arrayList.add(Integer.parseInt(s));
        }
        return arrayList;
    }
    /**
     * 解析题目的选项
     * @param reader
     * @return
     * @throws IOException
     */
    private ArrayList<String> getOptions(BufferedReader reader) throws IOException {
        ArrayList<String> arrayList=new ArrayList<String>();
        arrayList.add(reader.readLine());
        arrayList.add(reader.readLine());
        arrayList.add(reader.readLine());
        arrayList.add(reader.readLine());
        return arrayList;
    }
}
