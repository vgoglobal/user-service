package de.hey_car.config;
/*
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisClient {
    private static JedisPool pool;


    private RedisClient() {
    }


    public static void init() {
        if (pool == null) {
            pool = new JedisPool();
        }
    }


    public static Jedis getConnection() {
        if (pool == null) {
            RedisClient.init();
        }
        return pool.getResource();
    }


    public static void pushOnQueue(String queueName, String data) {
        try (Jedis jedis = RedisClient.getConnection()) {
            jedis.lpush(queueName, data);
        }
    }


    public static String get(String key) {
        try (Jedis jedis = RedisClient.getConnection()) {
            return jedis.get(key);
        }
    }


    public static void set(String key, String value) {
        try (Jedis jedis = RedisClient.getConnection()) {
            jedis.set(key, value);
        }
    }


    public static void disconnect() {
        pool.close();
        pool = null;
    }
}
*/