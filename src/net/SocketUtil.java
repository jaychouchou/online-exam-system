package com.elt.net;

import com.elt.entity.Request;
import com.elt.entity.Response;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by 肖安华 on java.
 *
 */
public class SocketUtil {
    public static Response remotaAll(Request request){
        try {
            //连接服务器
            Socket socket=new Socket("127.0.0.1",9000);
            //发送数据
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            //接收数据
            ObjectInputStream objectInputStream=new ObjectInputStream(socket.getInputStream());
           return (Response)objectInputStream.readObject();


        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
