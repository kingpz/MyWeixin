package FunctionRealization;

import Dao.MessageDao;
import MyServers.MyServer;
import com.google.protobuf.Message;
import io.grpc.examples.helloworld.MessageReply;
import io.grpc.examples.helloworld.MessageRequest;
import io.grpc.examples.helloworld.Messages;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by hasee on 2017/11/20.
 */
public class MessageFuncction {
    private static MessageDao messageDao=new MessageDao();

    //查找用户在数据库中的未读信息
    public  MessageReply queryUnread(int id){
        List list=messageDao.queryToMessage(id);
        if(list.size()!=0)
        {
            MessageReply.Builder builder=MessageReply.newBuilder();
            builder.setType("ok");
            for (int i=0;i<list.size();i++)
            {
                Bean.Messages messages=(Bean.Messages) list.get(i);
                Messages.Builder mess = Messages.newBuilder();
                mess.setMPostMessages(messages.getM_PostMessages());
                mess.setMMessagesType(messages.getM_MessagesType());
                mess.setMFromUserID(messages.getM_FromUserID());
                messages.setM_status(2);
                builder.addMes(mess);
            }
            messageDao.operationMessage(id);
            return builder.build();
        }
        else
            return MessageReply.newBuilder().setType("no").build();
    }
    //查找用户在线时需接受的信息
    public  MessageReply queryOnlineUnread(List list,int id){
        if(list!=null&&list.size()!=0)
        {
            MessageReply.Builder builder=MessageReply.newBuilder();
            builder.setType("ok");
            for (int i=0;i<list.size();i++)
            {
                Bean.Messages messages=(Bean.Messages) list.get(i);
                Messages.Builder mess = Messages.newBuilder();
                mess.setMPostMessages(messages.getM_PostMessages());
                mess.setMMessagesType(messages.getM_MessagesType());
                mess.setMFromUserID(messages.getM_FromUserID());
                messages.setM_status(2);
                builder.addMes(mess);
            }
            messageDao.operationMessage(id);
            MyServer.onlineMap.put(id+"",new ArrayList<Bean.Messages>());
            return builder.build();
        }
        else
            return MessageReply.newBuilder().setType("no").build();
    }
    //用户发送信息
    public Bean.Messages SendMessage(MessageRequest request){
        Bean.Messages messages = new Bean.Messages();
        messages.setM_status(1);
        messages.setM_MessagesType(request.getMMessagesType());
        messages.setM_PostMessages(request.getMPostMessages());
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        messages.setM_Time(dateString);
        messages.setM_FromUserID(request.getMFromUserID());
        messages.setM_ToUserID(request.getMToUserID());
        messageDao.operationMessage("add",messages);
        return messages;
    }
}
