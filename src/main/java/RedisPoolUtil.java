import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis连接池工具类
 */
public class RedisPoolUtil {
    private static JedisPool jedisPool = null;
    //把redis连接对象放到本地线程中
    private static ThreadLocal<Jedis> local=new ThreadLocal<Jedis>();

    //不允许通过new创建该类的实例
    private RedisPoolUtil() {
    }

    /**
     * 初始化Redis连接池
     */
    public static void initialPool() {
        try {
            // 创建jedis池配置实例
            JedisPoolConfig config = new JedisPoolConfig();
            // 设置池配置项值
            config.setMaxTotal(100);//jedis的最大活跃连接数
            config.setMaxIdle(50);//jedis最大空闲连接数
            config.setMaxWaitMillis(10*1000);//#jedis池没有连接对象返回时，等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
            config.setTestOnBorrow(true);//从池中获取连接的时候，是否进行有效检查
            config.setTestOnReturn(true);//归还连接的时候，是否进行有效检查
            // 根据配置实例化jedis池
            jedisPool = new JedisPool(config, "192.168.199.123",6379);
            System.out.println("线程池被成功初始化");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得连接
     */
    public static Jedis getConn() {
        //Redis对象
        Jedis jedis =local.get();
        if(jedis==null){
            if (jedisPool == null) {
                initialPool();
            }
            jedis = jedisPool.getResource();
            local.set(jedis);
        }
        return jedis;
    }

    /**
     * 关闭连接
     */
    //新版本用close归还连接
    public static void closeConn(){
        //从本地线程中获取
        Jedis jedis =local.get();
        if(jedis!=null){
            jedis.close();
        }
        local.set(null);
    }

    /**
     * 关闭连接池
     */
    public static void closePool(){
        if(jedisPool!=null){
            jedisPool.close();
        }
    }


    /**
     * 测试
     */
    public static void main(String[] args) {
        RedisPoolUtil.initialPool();
        try {
            Jedis jedis = RedisPoolUtil.getConn();//获取连接
            jedis.set("k11","v11");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            RedisPoolUtil.closeConn();//关闭连接
        }


    }

}
