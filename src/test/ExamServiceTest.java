package com.elt.test;

import com.elt.Exception.IdpassException;
import com.elt.dao.EntityContext;
import com.elt.entity.User;
import com.elt.service.ExamServiceImp;

/**
 * Created by 肖安华 on java.
 * 测试service层
 */
public class ExamServiceTest {
    public static void main(String arg[]){
        ExamServiceImp examService = new ExamServiceImp();
        EntityContext entityContext = new EntityContext();
        examService.setEntityContext(entityContext);
        try {
            User user = examService.login("00010", "123456");
            System.out.println(user.getName()+","+user.getEmail());
        } catch (IdpassException e) {
            //System.out.println(e.getMessage());
        }
    }
}
