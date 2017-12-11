package MyServers;


import Bean.Messages;
import FunctionRealization.MessageFuncction;
import FunctionRealization.UserFunction;
import io.grpc.examples.helloworld.*;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import static MyServers.MyServer.judgeMap;
import static MyServers.MyServer.onlineMap;
import static MyServers.MyServer.onlinedoubleMap;

/**
 * Created by hasee on 2017/11/6.
 */
public class GreeterImpl extends GreeterGrpc.GreeterImplBase{

    /*
    *登陆验证方法入口
    * */
    public void loginVerification(LoginRequest request, StreamObserver<LoginReply> responseObserver) {
        System.out.println("dsfsdfsdfsdf");
        String name=request.getName();
        String password=request.getPassword();
        UserFunction login=new UserFunction();
        LoginReply reply=login.Login(name,password);
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
    public void registerVerification(LoginRequest request,StreamObserver<RegisterReply> responseObserver)
    {
        String name=request.getName();
        String password=request.getPassword();
        UserFunction login=new UserFunction();
        RegisterReply reply=login.Register(name,password);
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
    public void addFriends(AddRequest request, StreamObserver<AddReply> responseObserver)
    {
        int u_id=request.getUId();
        int f_id=request.getFId();
        UserFunction login=new UserFunction();
        AddReply reply=login.addFriend(u_id,f_id);
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    public void querryFriend(QuerryRequest request, StreamObserver<QuerryReply> responseObserver){
        int u_id=request.getUId();
        int f_id=request.getFId();
        UserFunction login=new UserFunction();
        QuerryReply reply=login.querryFriend(u_id,f_id);
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
    /*
    *消息处理功能方法接口
     */
    public void messageVerification(MessageRequest request, StreamObserver<MessageReply> responseObserver){
        String type=request.getType();
        MessageFuncction messageFuncction=new MessageFuncction();
        if (type.equals("1"))//用户刚刚登陆
        {
            int id=request.getMFromUserID();
            MyServer.onlineMap.put(id+"",new ArrayList<Messages>());
            judgeMap.put(id+"",1);
            MessageReply messageReply= messageFuncction.queryUnread(id);
            System.out.println("sssssssssssssssssss1");
            responseObserver.onNext(messageReply);
            responseObserver.onCompleted();
        }
        else if (type.equals("2"))//用户在线连接服务端查询是否有其的消息
        {
            int id=request.getMFromUserID();
            ArrayList list=MyServer.onlineMap.get(id+"");

            if (list==null) {
                MyServer.onlineMap.put(id+"",new ArrayList<Messages>());
                judgeMap.put(id+"",1);
                MessageReply messageReply= messageFuncction.queryUnread(id);
                System.out.println("sssssssssssssssssss2");
                responseObserver.onNext(messageReply);
                responseObserver.onCompleted();
            }

            else {
                int a = judgeMap.get(id + "");
                judgeMap.put(id + "", a + 1);
                MessageReply messageReply = messageFuncction.queryOnlineUnread(list, id);
                System.out.println("sssssssssssssssssss3");
                responseObserver.onNext(messageReply);
                responseObserver.onCompleted();
            }
        }
        else if(type.equals("3"))//用户发送信息
        {
            int fromid=request.getMFromUserID();
            int toid=request.getMToUserID();
            int a = judgeMap.get(fromid + "");
            judgeMap.put(fromid + "", a + 1);
            ArrayList list=onlineMap.get(toid+"");
            System.out.println("sssssssssssssssssss4");
            if(list!=null) {
                Messages messages = messageFuncction.SendMessage(request);
                list.add(messages);
                MyServer.onlineMap.put(toid + "", list);
                responseObserver.onNext(MessageReply.newBuilder().setType("ok").build());
                responseObserver.onCompleted();
            }
            else
            {
                Messages messages = messageFuncction.SendMessage(request);
                if (messages!=null) {
                    responseObserver.onNext(MessageReply.newBuilder().setType("ok").build());
                    responseObserver.onCompleted();
                }
                else
                {
                    responseObserver.onNext(MessageReply.newBuilder().setType("no").build());
                    responseObserver.onCompleted();
                }

            }
        }
    }
    public StreamObserver<MessageRequest> chatMessage(final StreamObserver<MessageReply> responseObserver){
        final MessageFuncction messageFuncction=new MessageFuncction();
        return new StreamObserver<MessageRequest>() {
            public void onNext(MessageRequest request) {
                String type=request.getType();
                if(type.equals("1"))
                {
                    int id=request.getMFromUserID();
                    MyServer.onlinedoubleMap.put(id+"",responseObserver);
                    MessageReply messageReply= messageFuncction.queryUnread(id);
                    responseObserver.onNext(messageReply);
                }
                else if(type.equals("3"))
                {
                    int toid=request.getMToUserID();
                    StreamObserver<MessageReply> response=MyServer.onlinedoubleMap.get(toid+"");
                    if (response==null)
                    {
                        System.out.println("对方不在线线！！！");
                    }
                    else
                    {
                        MessageReply.Builder builder=MessageReply.newBuilder();
                        io.grpc.examples.helloworld.Messages.Builder mess = io.grpc.examples.helloworld.Messages.newBuilder();
                        mess.setMPostMessages(request.getMPostMessages());
                        mess.setMMessagesType(request.getMMessagesType());
                        mess.setMFromUserID(request.getMFromUserID());
                        builder.addMes(mess);
                        response.onNext(builder.build());
                    }
                }
                System.out.println("客户端说："+request.getType());
            }

            public void onError(Throwable t) {
                Iterator<Map.Entry<String, StreamObserver<MessageReply>>> it = onlinedoubleMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, StreamObserver<MessageReply>> entry = it.next();
                    if(responseObserver.equals(entry.getValue()))
                    {
                        onlinedoubleMap.remove(entry.getKey());
                    }
                }
                System.out.println("客户端程序崩了");
            }

            public void onCompleted() {
                Iterator<Map.Entry<String, StreamObserver<MessageReply>>> it = onlinedoubleMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, StreamObserver<MessageReply>> entry = it.next();
                    if(responseObserver.equals(entry.getValue()))
                    {
                        onlinedoubleMap.remove(entry.getKey());
                    }
                }
                System.out.println("对方已下线！！！");
            }
        };
    }

}
