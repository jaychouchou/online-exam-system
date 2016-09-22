package com.elt.net;

import com.elt.Exception.IdpassException;
import com.elt.entity.*;
import com.elt.service.ExamService;

import java.util.ArrayList;

/**
 * Created by 肖安华 on java.
 * 网络版代理服务类
 */
public class ExamServiceProxy implements ExamService{
    private String sessionId;
    @Override
    public User login(String id, String pass) throws IdpassException {
        //发送请求，读取响应
        Request request=new Request(new Object[]{id,pass},new Class[]{String.class,String.class},"login");
        Response response=SocketUtil.remotaAll(request);
        if (!response.isSuccess())
            throw new IdpassException(response.getException().getMessage());
        this.sessionId=response.getSessionId();
        Object obj=response.getObject();
        return (User)obj;
    }

    @Override
    public ExamInfo start() {
        Request request=new Request(new Object[]{},new Class[]{},"start");
        request.setSessionId(sessionId);
        Response response=SocketUtil.remotaAll(request);
        if (!response.isSuccess())
            throw new RuntimeException(response.getException().getMessage());
        Object obj=response.getObject();
        return (ExamInfo) obj;
    }

    @Override
    public ArrayList<QuestionInfo> getExamQuestions() {
        Request request=new Request(new Object[]{},new Class[]{},"getExamQuestions");
        request.setSessionId(sessionId);
        Response response=SocketUtil.remotaAll(request);
        if (!response.isSuccess())
            throw new RuntimeException(response.getException().getMessage());
        Object obj=response.getObject();
        return (ArrayList<QuestionInfo>) obj;
    }

    @Override
    public QuestionInfo getQuestionInfo(int pageIndex) {
        Request request=new Request(new Object[]{pageIndex},new Class[]{int.class},"getQuestionInfo");
        request.setSessionId(sessionId);
        Response response=SocketUtil.remotaAll(request);
        if (!response.isSuccess())
            throw new RuntimeException(response.getException().getMessage());
        Object obj=response.getObject();
        return (QuestionInfo) obj;
    }

    @Override
    public void saveUserAnswers(int pageIndex, ArrayList<Integer> userAnswers) {
        Request request=new Request(new Object[]{pageIndex,userAnswers},new Class[]{int.class,ArrayList.class},"saveUserAnswers");
        request.setSessionId(sessionId);
        Response response=SocketUtil.remotaAll(request);
        if (!response.isSuccess())
            throw new RuntimeException(response.getException().getMessage());
    }

    @Override
    public int getTotalScore() {
        Request request=new Request(new Object[]{},new Class[]{},"getTotalScore");
        request.setSessionId(sessionId);
        Response response=SocketUtil.remotaAll(request);
        if (!response.isSuccess())
            throw new RuntimeException(response.getException().getMessage());
        Object obj=response.getObject();
        return (int) obj;
    }
}
