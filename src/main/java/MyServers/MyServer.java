package MyServers;




import Bean.Messages;
import Bean.UserBean;
import Dao.UserDao;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.examples.helloworld.MessageReply;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

/**
 * Created by hasee on 2017/11/6.
 */
public class MyServer {
    private static final Logger logger = Logger.getLogger(MyServer.class.getName());


    public static Map<String,ArrayList<Messages>> onlineMap = new HashMap<String, ArrayList<Messages>>();//存储登陆用户和用户在线接受的信息
    public static Map<String,Integer> judgeMap= new HashMap<String, Integer>();

    public static Map<String,StreamObserver<MessageReply>> onlinedoubleMap= new HashMap<String, StreamObserver<MessageReply>>();

    /* The port on which the server should run */
    private int port = 50053;
    private Server server;

    private void start() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new GreeterImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                MyServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        System.err.println("*** server run");
        final MyServer server = new MyServer();
        server.start();
        server.blockUntilShutdown();

        Thread t = new Thread(new Runnable(){
            public void run(){
                Iterator<Map.Entry<String, Integer>> it = judgeMap.entrySet().iterator();
                while(true){
                    while (it.hasNext()) {
                        Map.Entry<String, Integer> entry = it.next();
                        System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
                        judgeMap.put(entry.getKey(),0);
                    }
                    try {
                        Thread.sleep(2000);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    Iterator<Map.Entry<String, Integer>> its = judgeMap.entrySet().iterator();
                    while (its.hasNext()) {
                        Map.Entry<String, Integer> entry = its.next();
                        System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
                        if (entry.getValue()==1)
                        {
                            onlineMap.remove(entry.getKey());
                            judgeMap.remove(entry.getKey());
                        }
                    }
            }
            }});
        t.start();
//        UserDao userDao=new UserDao();
//        List list=userDao.queryUser("U_ID","1");
//        UserBean userBean=(UserBean) list.get(0);
//        System.out.println(userBean.getU_NickName());
    }
}
