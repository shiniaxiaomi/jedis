import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * Created by 陆英杰
 * 2018/11/24 18:52
 */

/**
 * 测试五大数据类型
 */
public class TestAPI {

    public static void main(String[] args) {

        //连接redis
        Jedis jedis=new Jedis("192.168.199.123",6379);

        //1.字符串
        jedis.set("k1","v1");
        System.out.println(jedis.get("k1"));

        //2.hash 对于某些不定项操作可以利用哈希扩展
        jedis.hset("hash","name","jack");
        jedis.hset("hash","age","12");
        jedis.hincrBy("hash","age",2);
        System.out.println(jedis.hget("hash","name"));
        System.out.println(jedis.hget("hash","age"));

        //3.list
        jedis.lpush("list","k1");
        jedis.lpush("list","k2");
        jedis.lpush("list","k3");
        jedis.lpush("list","k4");
        jedis.lpush("list","k5");
        List<String> list = jedis.lrange("list", 0, -1);
        System.out.println(list);

        //4.set 不能添加重复元素
        jedis.sadd("set","苹果","香蕉","dog","cat","cat");
        Set<String> set = jedis.smembers("set");
        System.out.println(set);

        //5.有序集合zset 带有分值 默认排序分值从低到高
        jedis.zadd("zset",3,"k3");
        jedis.zadd("zset",2,"k2");
        jedis.zadd("zset",4,"k4");
        jedis.zadd("zset",1,"k1");
        Set<String> zset = jedis.zrange("zset", 0, -1);
        System.out.println(zset);


    }

}
