import redis.clients.jedis.Jedis;

/**
 * Created by 陆英杰
 * 2018/11/24 16:23
 */
public class HelloWorld {

    public static void main(String[] args) {

        Jedis jedis=new Jedis("192.168.199.123",6379);
        System.out.println(jedis.ping());

    }

}
