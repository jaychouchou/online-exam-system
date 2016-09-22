package com.elt.net;

import com.elt.dao.EntityContext;
import com.elt.entity.Request;
import com.elt.entity.Response;
import com.elt.service.ExamServiceImp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by 肖安华 on java.
 * 服务端
 */
public class MainServer {
  public static void main(String arg[]){
      MainServer mainServer=new MainServer();
      EntityContext entityContext=new EntityContext();
      mainServer.setEntityContext(entityContext);
      mainServer.startServer();
  }

    private   HashMap<String,ExamServiceImp> services=new HashMap<String,ExamServiceImp>();
    private EntityContext entityContext;

    public void setEntityContext(EntityContext entityContext){
        this.entityContext=entityContext;
    }
    public void startServer(){
        try {
            ServerSocket serverSocket=new ServerSocket(9000);
            System.out.println("服务器开启了....");
            //接收访问
            while(true){
                Socket socket=serverSocket.accept();
                new ExamThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private class ExamThread extends Thread {
        private Socket socket;
        ExamThread(Socket socket){
            this.socket=socket;
        }
        @Override
        public void run() {
            //1.读取请求
            Request request=getRequest(socket);
            //2.创建业务对象--->对于同一个用户只要创建一次，其他操作都要认识这个用户
            ExamServiceImp serviceImp=getService(request);
            Response response=new Response();
            response.setSessionId(request.getSessionId());
            //3.调用业务方法
            try {
               Object obj= invokeMethod(serviceImp,request);
                response.setObject(obj);
            } catch (Exception e) {
               response.setException(new RuntimeException(e.getMessage()));
            }finally {
                try {
                    response.setSuccess();
                    ObjectOutputStream objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
                    //4.写回响应
                    objectOutputStream.writeObject(response);
                    objectOutputStream.flush();
                }catch (Exception e2){

                }
            }

        }
        private Request getRequest(Socket socket){
            try {
                ObjectInputStream objectInputStream=new ObjectInputStream(socket.getInputStream());
                Request request=(Request)objectInputStream.readObject();
                return request;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * 获取业务对象
         * @param request
         * @return
         */
        private ExamServiceImp getService(Request request){
            String sessionId=request.getSessionId();
            if (sessionId==null){
                //没有登入过，随机创建一个id
                sessionId= UUID.randomUUID().toString();
                request.setSessionId(sessionId);
                ExamServiceImp serviceImp=new ExamServiceImp();
                serviceImp.setEntityContext(entityContext);
                services.put(sessionId,serviceImp);
            }
            return services.get(sessionId);
        }
        private Object invokeMethod(ExamServiceImp serviceImp,Request request) throws Exception {
            Class c=serviceImp.getClass();
            Object obj=null;
            try {
                //获取业务方法
                Method m=c.getMethod(request.getMethodName(),request.getParamType());
                //调用业务方法
                obj=m.invoke(serviceImp,request.getObjects());
                return obj;
                } catch (InvocationTargetException e) {
                    throw new Exception(e.getTargetException().getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
