import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * Created by 陆英杰
 * 2018/11/24 19:17
 */

/**
 * 事务
 */
public class TestTransaction {

    public static void main(String[] args) throws InterruptedException {

//        TestTransaction.test1();
        TestTransaction.test2();


    }


    //简单的事务操作
    public static void test1(){
        //连接redis
        Jedis jedis=new Jedis("192.168.199.123",6379);

        Transaction transaction = jedis.multi();//开启事务

        transaction.set("k111","v11");

//        transaction.exec();//提交事务
        transaction.discard();//撤销事务
    }


    //使用watch 上锁
    public static void test2() throws InterruptedException {
        //连接redis
        Jedis jedis=new Jedis("192.168.199.123",6379);

        jedis.set("balance","100");//先设置一个数值


        jedis.watch("balance");//开启监控,如果监控的值被修改,那么下面的事务就不会不会被执行
        jedis.set("balance","60");//模拟在过程中这个值被修改了
        Thread.sleep(1000);//模拟可能因为高并发或者网络拥堵造成的延时,在这个过程中变量就可能会被修改

        Transaction transaction = jedis.multi();

        transaction.decrBy("balance",20);

        List<Object> exec = transaction.exec();//提交事务
        System.out.println(exec);

    }

}
