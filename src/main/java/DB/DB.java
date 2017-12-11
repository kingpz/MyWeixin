package DB;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by hasee on 2017/22.     eatrestaurant
 */
public class DB {
    private final static String url = "jdbc:mysql://localhost:3306/wechat?useUnicode=true&useSSL=true&characterEncoding=UTF-8";
    private final static String userName = "root";
    private final static String password = "123";
    private static Connection con = null;
    private Statement stm=null;

    /* 通过构造方法加载数据库驱动 */
    public DB(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("加载数据库驱动成功！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("加载数据库驱动失败！");
        }
    }
    /* 创建数据库连接 */
    public static Connection createCon() {
        try {
            con = DriverManager.getConnection(url, userName, password);
            System.out.println("获取数据库连接成功！");
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取数据库连接失败！");
        }
        return null;
    }
    /* 获取Statement对象 */
    public void getStm(){
         Connection con = createCon();
        try {
            stm=con.createStatement();
            System.out.println("创建Statement对象成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("创建Statement对象失败！");
        }
    }
    /**
     * @功能 对数据库的增加、修改和删除的操作
     * @参数 sql为要执行的SQL语句
     * @返回值 boolean型值
     */
    public boolean executeUpdate(String sql) {
        System.out.println(sql);
        boolean mark=false;
        try {
            getStm();
            int iCount = stm.executeUpdate(sql);
            if(iCount>0)
                mark=true;
            else
                mark=false;
        } catch (Exception e) {
            e.printStackTrace();
            mark=false;
        }
        return mark;
    }
    /* 查询数据库 */
    public ResultSet executeQuery(String sql) {
        ResultSet rs=null;
        try {
            getStm();
            try {
                rs = stm.executeQuery(sql);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("查询数据库失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    /* 关闭数据库的操作 */
    public void closed() {
        if(stm!=null)
            try {
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("关闭stm对象失败！");
            }
        if(con!=null)
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("关闭con对象失败！");
            }
    }
}