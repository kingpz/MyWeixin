package FunctionRealization;

import Bean.UserBean;
import Dao.UserDao;
import io.grpc.examples.helloworld.AddReply;
import io.grpc.examples.helloworld.LoginReply;
import io.grpc.examples.helloworld.QuerryReply;
import io.grpc.examples.helloworld.RegisterReply;

import java.util.List;

/**
 * Created by hasee on 2017/11/6.
 * 对用户表进行操作的类
 */
public class UserFunction {
    UserDao userDao;

    public LoginReply Login(String name, String password){
        userDao=new UserDao();
        List list=userDao.queryUser("U_LoginID",name);
        if(list.size()==0)
        {
            return LoginReply.newBuilder().setMessage("no").build();
        }
        else
        {
            UserBean userBean=(UserBean) list.get(0);
            if (userBean.getU_PassWord().equals(password))
            {
                LoginReply.Builder builder = LoginReply.newBuilder();
                builder.setMessage("ok");
                builder.setUID(userBean.getU_ID());
                builder.setULoginID(userBean.getU_LoginID());
                builder.setUNickName(userBean.getU_NickName());
                builder.setUHeadPortrait("1");

                UserBean userBean1 = new UserBean();
                userBean1.setU_ID(userBean.getU_ID());
                userBean1.setU_Sex(-1);//防止修改性别
                userBean1.setU_UserState(1);//更改用户状态

                if (userDao.operationUser("update", userBean1))
                    return builder.build();
                else
                    return LoginReply.newBuilder().setMessage("no").build();
            }
            else
                return LoginReply.newBuilder().setMessage("no").build();
        }
    }

    public AddReply addFriend(int u_id,int f_id){
        userDao=new UserDao();
        String name = String.valueOf(f_id);
        boolean isAdd = userDao.addFriend(u_id,f_id);
        System.out.println(isAdd);
        if(isAdd == false)
        {
            return AddReply.newBuilder().setMessage("no").build();
        }
        else
        {
            userDao.queryUser("U_ID",name);
            List users = (List) userDao.queryUser("U_ID",name);
            UserBean user = (UserBean) users.get(0);
            AddReply.Builder builder = AddReply.newBuilder();
            builder.setMessage("ok");
            builder.setUID(user.getU_ID());
            builder.setULoginID(user.getU_LoginID());
            builder.setUNickName(user.getU_NickName());
            builder.setUHeadPortrait("1");
            return builder.build();
        }
        //return AddReply.newBuilder().setMessage("no").build();
    }
    public QuerryReply querryFriend(int u_id,int f_id){
        userDao=new UserDao();
        UserBean friend=userDao.queryFriend(u_id,f_id);
        //System.out.println(list.size());
        System.out.println(friend.toString());
        QuerryReply.Builder builder = QuerryReply.newBuilder();
        builder.setMessage("ok");
        builder.setUID(friend.getU_ID());
        builder.setULoginID(friend.getU_LoginID());
        builder.setUNickName(friend.getU_NickName());
        builder.setUHeadPortrait("1");
        return builder.build();

            //return QuerryReply.newBuilder().setMessage("no").build();
            /*if(userDao.operationUser("add",userBean))
                return RegisterReply.newBuilder().setMessage("ok").build();
            else
                return RegisterReply.newBuilder().setMessage("no").build();
                */
    }
    public RegisterReply Register(String name, String password){
        userDao=new UserDao();
        List list=userDao.queryUser("U_LoginID",name);
        if(list.size()!=0)
        {
            return RegisterReply.newBuilder().setMessage("no").build();
        }
        else
        {
            UserBean userBean=new UserBean();
            userBean.setU_LoginID(name);
            userBean.setU_PassWord(password);
            userBean.setU_UserState(1);
            userBean.setU_NickName(name);
            userBean.setU_ID(Integer.parseInt(name));
            userBean.setU_HeadPortrait("oxox");
            userBean.setU_SignaTure("you are great!");
            if(userDao.operationUser("add",userBean))
                return RegisterReply.newBuilder().setMessage("ok").build();
            else
                return RegisterReply.newBuilder().setMessage("no").build();
        }
    }
}
