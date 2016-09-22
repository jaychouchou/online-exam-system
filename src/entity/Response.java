package com.elt.entity;

import java.io.Serializable;

/**
 * Created by 肖安华 on java.
 * 封装响应数据
 */
public class Response implements Serializable {
    private Exception exception;//异常最终在客服端显示
    private Object object;//服务端返回的数据
    private boolean isSuccess;
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setSuccess() {
        this.isSuccess=this.exception==null;
    }
    public boolean isSuccess(){
        return isSuccess;
    }

}
