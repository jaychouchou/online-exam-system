package com.elt.test;

import com.elt.dao.EntityContext;
import com.elt.entity.Question;
import com.elt.entity.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 肖安华 on java.
 * 测试数据
 */
public class EntityContextTest {

    public static void main(String arg[]) {
        EntityContext context=new EntityContext();
        HashMap<String,User> users=context.getUsers();
        for(String id:users.keySet()){
            System.out.println(users.get(id));
        }
        HashMap<Integer,ArrayList<Question>> question=context.getQuestions();
        for (Integer qus:question.keySet()){
            System.out.println(question.get(qus));
        }
    }
}
