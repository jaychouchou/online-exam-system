package com.elt.test;

import com.elt.control.ClientContext;
import com.elt.dao.EntityContext;
import com.elt.net.ExamServiceProxy;
import com.elt.view.ExamFrame;
import com.elt.view.LoginFrame;
import com.elt.view.MenuFrame;

/**
 * Created by 肖安华 on java.
 * 进入系统测试
 */
public class ExamTest {

    public static void main(String[] args) {
        //视图对象
        ExamFrame examFrame=new ExamFrame();
        MenuFrame menuFrame=new MenuFrame();
        LoginFrame loginFrame=new LoginFrame();
        //控制器对象
        ClientContext clientContext=new ClientContext();
        //设置视图与控制器之间的依赖
        examFrame.setContext(clientContext);
        menuFrame.setContext(clientContext);
        loginFrame.setContext(clientContext);
        //创建数据层对象
        EntityContext entityContext=new EntityContext();
        //创建业务对象
       // ExamServiceImp examServiceImp=new ExamServiceImp();
        ExamServiceProxy serviceProxy=new ExamServiceProxy();
        //创建业务层和数据层之间的依赖
        //examServiceImp.setEntityContext(entityContext);
        //创建控制器和业务之间的依赖
        clientContext.setExamService(serviceProxy);
        loginFrame.setVisible(true);
    }
}
