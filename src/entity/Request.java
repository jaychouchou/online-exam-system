package com.elt.entity;

import java.io.Serializable;

/**
 * Created by 肖安华 on java.
 * 封装请求数据
 */
public class Request implements Serializable{
    //请求发送的数据（其实就是服务端调用业务所需的参数）
    private Object[] objects;
    //服务器获取方法的参数
    private Class[] paramType;
    //服务器调用方法的名称
    private String methodName;
    //请求用户的唯一标识
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Request(){}

    public Request(Object[] objects, Class[] paramType, String methodName) {
        this.objects = objects;
        this.paramType = paramType;
        this.methodName = methodName;
    }

    public Object[] getObjects() {
        return objects;
    }
    public void setObjects(Object[] objects) {
        this.objects = objects;
    }

    public Class[] getParamType() {
        return paramType;
    }

    public void setParamType(Class[] paramType) {
        this.paramType = paramType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
