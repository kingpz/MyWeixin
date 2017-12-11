package Dao;

/**
 * Created by hasee on 2017/7/22.
 */


import Bean.UserBean;
import DB.DB;
import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class UserDao
{

    private DB connection = null;
    private UserBean userone = null;

    public UserDao() {
        connection = new DB();
    }

    /**
     * 功能：对用户进行增 删 改的操作，
     * 参数：type为String类型变量，用来表示要进行的操作；user为User类型的实体类变量，用来储存用户信息。
     * 返回一个boolean值类型。
     */
    public boolean operationUser(String type, UserBean userBean) {
		/* 生成SQL语句 */
        String sql = null;
        if (type.equals("add"))					//增加用户
            sql = "insert into user(U_LoginID,U_NickName,U_PassWord,U_HeadPortrait,U_SignaTure,U_UserState,U_ID) values ('"+userBean.getU_LoginID() + "','"+ userBean.getU_NickName() + "','" + userBean.getU_PassWord()+ "','"+userBean.getU_HeadPortrait()+"','"+ userBean.getU_SignaTure()+ "','"+userBean.getU_UserState()+ "','"+userBean.getU_ID()+"')";

        if (type.equals("update"))//id号修改用户
        {
            StringBuffer s=new StringBuffer();
            s.append("update user set ");
            if (userBean.getU_NickName()!=null&&!userBean.getU_NickName().equals(""))
                s.append("U_NickName='"+userBean.getU_NickName() + "',");
            if (userBean.getU_PassWord()!=null&&!userBean.getU_PassWord().equals(""))
                s.append("U_PassWord='"+userBean.getU_PassWord() + "',");
            if (userBean.getU_SignaTure()!=null&&!userBean.getU_SignaTure().equals(""))
                s.append("U_SignaTure='" + userBean.getU_SignaTure() + "',");
            if (userBean.getU_Sex()!=-1)
                s.append("U_Sex='" + userBean.getU_Sex() + "',");
            if (userBean.getU_Email()!=null&&!userBean.getU_Email().equals(""))
                s.append("U_Email='"+userBean.getU_Email()+"',");
            if (userBean.getU_Telephone()!=null&&!userBean.getU_Telephone().equals(""))
                s.append("U_Telephone='"+userBean.getU_Telephone()+"',");
            if (userBean.getU_HeadPortrait()!=null&&!userBean.getU_HeadPortrait().equals(""))
                s.append("U_HeadPortrait='"+userBean.getU_HeadPortrait()+"',");
            if (userBean.getU_City()!=null&&!userBean.getU_City().equals(""))
                s.append("U_City='"+userBean.getU_City()+"',");
            if (userBean.getU_UserState()!=0)
                s.append("U_UserState='"+userBean.getU_UserState()+"',");
            if (userBean.getU_ID()!=0)
                s.append("U_ID='"+userBean.getU_ID()+"'");
            s.append(" where U_ID='"+userBean.getU_ID()+"'");
            sql = s.toString();
        }

        if (type.equals("delete"))				//删除用户
            sql = "delete from user where U_ID='" + userBean.getU_ID()+"'";


		/* 执行SQL语句 */
        boolean flag =connection.executeUpdate(sql);
        connection.closed();
        return flag;
    }
    public boolean addFriend (int user_id,int friend_id) {
        // int u_id = user.getU_ID();
        //int f_id = friend.getU_ID();
        List userlist = new ArrayList();
        ResultSet rs = connection.executeQuery("select * from user where U_ID = "+friend_id);

        if(rs == null){
            return false;
        }
        try {
            java.sql.Connection conn = DB.createCon();
            String sqlInset1 = "insert into friend (f_UserID,f_FirendID）values(?, ?)";
            String sqlInset2 = "insert into friend (f_UserID,f_FirendID）values(?, ?)";
            String sql1 = "insert into friend(f_UserID,f_FriendID) values ('"+user_id + "','"+ friend_id + "')";
            String sql2 = "insert into friend(f_UserID,f_FriendID) values ('"+friend_id + "','"+ user_id + "')";

            PreparedStatement stmt1 = (PreparedStatement) conn.prepareStatement(sql1);
            PreparedStatement stmt2 = (PreparedStatement) conn.prepareStatement(sql2);
            /*stmt1.setInt(1,user_id);
            stmt1.setInt(2,friend_id);
            stmt2.setInt(1,friend_id);
            stmt2.setInt(2,user_id);
            */
            int i = stmt1.executeUpdate();
            int j = stmt2.executeUpdate();
            System.out.println(i);
            System.out.println(j);
            System.out.println(i > 0&&j > 0);
            if (i > 0&&j > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //boolean rs = connection.executeUpdate("insert into friends (f_UserID,f_FriendID）values" );
        //boolean rs1 = connection.executeUpdate("insert into friends(f_UserID,f_FriendID) values (friend_id,user_id)");
        return false;
    }
    /**
     * 功能: 查询指定用户
     * 参数 :value表示查询用户类别的值，type，为查询类别；
     * 返回值: List集合
     */
    public List queryUser(String type,String value){
        List userlist = new ArrayList();
        ResultSet rs = connection.executeQuery("select * from user where "+type+"='"+value+"'");
        if(rs!=null)
        {
            try
            {
                while(rs.next())
                {
                    userone=new UserBean();
                    userone.setU_ID(rs.getInt("U_ID"));
                    userone.setU_LoginID(rs.getString("U_LoginID"));
                    userone.setU_NickName(rs.getString("U_NickName"));
                    userone.setU_PassWord(rs.getString("U_PassWord"));
                    userone.setU_SignaTure(rs.getString("U_SignaTure"));
                    userone.setU_Sex(rs.getInt("U_Sex"));
                    userone.setU_Email(rs.getString("U_Email"));
                    userone.setU_Telephone(rs.getString("U_Telephone"));
                    userone.setU_HeadPortrait(rs.getString("U_HeadPortrait"));
                    userone.setU_City(rs.getString("U_City"));
                    userone.setU_UserState(rs.getInt("U_UserState"));

                    userlist.add(userone);
                }
            } catch (SQLException e)
            {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
            finally{
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                connection.closed();
            }
        }
        return userlist;

    }
    public UserBean queryFriend(int u_id,int f_id) {
        UserBean friend = new UserBean();
        List userlist1 = new ArrayList();
        List userlist2 = new ArrayList();
        ResultSet rs1 = connection.executeQuery("select * from friend where f_UserID = '" + u_id+"'and f_FriendID = '"+f_id+"'");
        //select * from user where "+type+"='"+value+"'"
        ResultSet rs2 = connection.executeQuery("select * from friend where f_UserID = '" + f_id+"'and f_FriendID = '"+u_id+"'");
        System.out.println(rs1 != null && rs2 != null);
        if (rs1 != null || rs2 != null){
            friend = (UserBean) queryUser(f_id).get(0);
            return friend;
        }
        return null;
    }

    /**
     * 功能: 查询所有用户
     * 返回值: List集合
     */
    public List queryUser(int id){
        List userlist = new ArrayList();
        ResultSet rs = connection.executeQuery("select * from user where U_ID = "+id);
        if(rs!=null)
        {
            try
            {
                while(rs.next())
                {
                    userone=new UserBean();
                    userone.setU_ID(rs.getInt("U_ID"));
                    userone.setU_LoginID(rs.getString("U_LoginID"));
                    userone.setU_NickName(rs.getString("U_NickName"));
                    userone.setU_PassWord(rs.getString("U_PassWord"));
                    userone.setU_SignaTure(rs.getString("U_SignaTure"));
                    userone.setU_Sex(rs.getInt("U_Sex"));
                    userone.setU_Email(rs.getString("U_Email"));
                    userone.setU_Telephone(rs.getString("U_Telephone"));
                    userone.setU_HeadPortrait(rs.getString("U_HeadPortrait"));
                    userone.setU_City(rs.getString("U_City"));
                    userone.setU_UserState(rs.getInt("U_UserState"));
                    userlist.add(userone);
                }
                return userlist;
            } catch (SQLException e)
            {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
            finally{
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                connection.closed();
            }
        }
        return userlist;

    }
}