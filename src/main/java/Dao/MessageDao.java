package Dao;


import Bean.Messages;
import DB.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/11/20.
 */
public class MessageDao {
    private DB connection=null;
    private Messages messages=null;

    public  MessageDao(){connection=new DB();}
    public boolean operationMessage(int id) {
        String sql="update messages set M_status='2' where M_ToUserID='"+id+"'";
         /* 执行SQL语句 */
        boolean flag =connection.executeUpdate(sql);
        connection.closed();
        return flag;
    }

    public boolean operationMessage(String type,Messages messages){
        String sql = null;
        if(type.equals("add"))
        {
            sql="insert into messages(M_PostMessages,M_status,M_Time,M_MessagesType,M_FromUserID,M_ToUserID) values('"+messages.getM_PostMessages()+"','"+messages.getM_status()+"','"+messages.getM_Time()+"','"+messages.getM_MessagesType()+"','"+messages.getM_FromUserID()+"','"+messages.getM_ToUserID()+"')";
        }
        else if (type.equals("update"))
        {
            sql="update messages set M_status='"+messages.getM_status()+"' where M_ID='"+messages.getM_ID()+",";
        }
        else if (type.equals("delete"))
        {
            sql="delete from messages where M_FromUserID='"+messages.getM_FromUserID()+"'";
        }
        /* 执行SQL语句 */
        boolean flag =connection.executeUpdate(sql);
        connection.closed();
        return flag;
    }

    public List queryToMessage(int id) {
        List list = new ArrayList();
        ResultSet rs = connection.executeQuery("select * from messages where M_ToUserID='"+id+"' and M_status='1'");
        if (rs != null) {
            try {
                while (rs.next()) {
                    messages=new Messages();
                    messages.setM_ID(rs.getInt("M_ID"));
                    messages.setM_PostMessages(rs.getString("M_PostMessages"));
                    messages.setM_status(rs.getInt("M_status"));
                    messages.setM_MessagesType(rs.getInt("M_MessagesType"));
                    messages.setM_FromUserID(rs.getInt("M_FromUserID"));
                    messages.setM_ToUserID(rs.getInt("M_ToUserID"));
                    list.add(messages);
                }
            } catch (SQLException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            } finally {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                connection.closed();
            }
        }
        return list;
    }
    public List queryFromMessage(String fromid,String toid) {
        List list = new ArrayList();
        ResultSet rs = connection.executeQuery("select * from messages where M_FromUserID='"+fromid+"' and M_ToUserID='"+toid+"'");
        if (rs != null) {
            try {
                while (rs.next()) {
                    messages=new Messages();
                    messages.setM_ID(rs.getInt("M_ID"));
                    messages.setM_PostMessages(rs.getString("M_PostMessages"));
                    messages.setM_status(rs.getInt("M_status"));
                    messages.setM_MessagesType(rs.getInt("M_MessagesType"));
                    messages.setM_FromUserID(rs.getInt("M_FromUserID"));
                    messages.setM_ToUserID(rs.getInt("M_ToUserID"));
                    list.add(messages);
                }
            } catch (SQLException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            } finally {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                connection.closed();
            }
        }
        return list;
    }
}
