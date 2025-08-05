package cn.toesbieya.jxc.utils;

import com.google.common.io.ByteStreams;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisUtil {
    //声明lua脚本，redis的模板方法，字符串redis模板方法
    public static String INCREMENT_AND_EXPIRE_SCRIPT = null;
    @Getter
    public static RedisTemplate<String, Object> redisTemplate;
    @Getter
    public static StringRedisTemplate stringRedisTemplate;

    //静态方法实现在类加载时自动读取lua脚本
    static {
        try {
            INCREMENT_AND_EXPIRE_SCRIPT = new String(ByteStreams.toByteArray(
                    new ClassPathResource("/script/increase_and_expire.lua").getInputStream()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //将实例变量赋值给静态变量
    @Autowired
    public RedisUtil(RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
        RedisUtil.stringRedisTemplate = stringRedisTemplate;
    }

    //扫描匹配模式的键，使用SCAN命令避免阻塞
    public static Set<String> keys(String match) {
        return redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> result = new HashSet<>();

            Cursor<byte[]> cursor =
                    connection.scan(
                            new ScanOptions
                                    .ScanOptionsBuilder()
                                    .match(match)
                                    .count(2000)
                                    .build()
                    );

            while (cursor.hasNext()) {
                result.add(new String(cursor.next()));
            }

            return result;
        });
    }

    //检测是否存在
    public static boolean exist(String key) {
        Boolean result = redisTemplate.hasKey(key);
        return result != null && result;
    }

    //删除key
    public static void del(String... keys) {
        redisTemplate.delete(Arrays.asList(keys));
    }

    //原子性递增并设置过期时间
    public static long incrAndExpire(String key, int time) {
        RedisScript<Long> redisScript = new DefaultRedisScript<>(INCREMENT_AND_EXPIRE_SCRIPT, Long.class);
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(key), time);
        return result == null ? 0 : result;
    }

    public static Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public static void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public static void set(String key, Object value, long expire) {
        redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
    }

    /**
     * hash操作
     * */
    public static Object hget(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    public static Map<Object, Object> hgetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public static void hset(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    //fields需要是String类型
    public static void hdel(String key, Object... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    public static List<Object> hmget(String key, String... field) {
        return redisTemplate.opsForHash().multiGet(key, Arrays.asList(field));
    }

    public static void hmset(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * set操作
     * */
    public static long sadd(String key, Object... members) {
        Long result = redisTemplate.opsForSet().add(key, members);
        return result == null ? 0 : result;
    }

    public static void srem(String key, Object... members) {
        redisTemplate.opsForSet().remove(key, members);
    }

    public static long scard(String key) {
        Long result = redisTemplate.opsForSet().size(key);
        return result == null ? 0 : result;
    }

    public static Set<Object> smembers(String key) {
        Set<Object> result = redisTemplate.opsForSet().members(key);
        return result == null ? Collections.emptySet() : result;
    }

    /**
     * Locker内部类
     * */
    public static class Locker implements Closeable {
        //声明键，值，过期时间
        private final String key;
        private final String value;
        private final long expire;

        public Locker(String key){
            this.key = "lock_" + key;//以lock_开头作为键
            this.value = String.valueOf(System.currentTimeMillis());//设置时间戳来确保唯一性
            this.expire = 300;//设置300秒过期时间
        }

        private void unlock() {
            //脚本含义验证锁值是否匹配（防止误删其他线程的锁）
            String UNLOCK_LUA_SCRIPT = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return false end')";//设置lua脚本确保原子性，
            RedisScript<Boolean> redisScript = new DefaultRedisScript<>(UNLOCK_LUA_SCRIPT, Boolean.class);
            Boolean result = redisTemplate.execute(redisScript, Collections.singletonList(key), value);
            if (result == null || !result) {
                log.error("redis锁释放失败，key:{},value:{}", key, value);
            }
        }

        private boolean lock(){
            //
            String LOCK_LUA_SCRIPT = "return redis.call('set',KEYS[1],ARGV[1],'EX',ARGV[2],'NX')";
            RedisScript<Boolean> redisScript = new DefaultRedisScript<>(LOCK_LUA_SCRIPT, Boolean.class);
            Boolean result = redisTemplate.execute(redisScript, Collections.singletonList(key), value, expire);
            return result != null;
        }

        @Override
        public void close() throws IOException {
            this.unlock();
        }
    }
}
