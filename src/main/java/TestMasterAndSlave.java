import redis.clients.jedis.Jedis;

/**
 * Created by 陆英杰
 * 2018/11/24 20:58
 */

/**
 * 主从复制
 */
public class TestMasterAndSlave {

    public static void main(String[] args) {

        Jedis jedis_M=new Jedis("192.168.199.123",6379);//主机---负责写
        Jedis jedis_S=new Jedis("192.168.199.123",6380);//从机---负责读

        jedis_S.slaveof("192.168.199.123",6379);//设置主从关系

        jedis_M.set("k1","v1");

        String str = jedis_S.get("k1");
        System.out.println(str);

    }
}
