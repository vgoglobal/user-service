package de.hey_car.config;

import java.io.IOException;

import org.apache.catalina.util.StandardSessionIdGenerator;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
/*
@Component
public class SessionManager {

    private static final String SESSION_TIMEOUT_KEY = "session.timeout";
    private static final String SESSION_TIMEOUT_DEFAULT = "1800"; // 30 minutes
    private static StandardSessionIdGenerator gen = new StandardSessionIdGenerator();


    public static String create(String sessionData) {
        String sessionId = gen.generateSessionId();
        SessionManager.update(sessionId, sessionData);
        return sessionId;
    }



    public static void update(String sessionId, String sessionData) {

        try (Jedis jedis = RedisClient.getConnection()) {
            jedis.set(sessionId, sessionData);
            jedis.expire(sessionId, getSessionTimeout());
        }
    }



    public static String get(String sessionId) throws IOException {

        try (Jedis jedis = RedisClient.getConnection()) {
            String sessionData = jedis.get(sessionId);
            if (sessionData != null) {
                jedis.expire(sessionId, getSessionTimeout());
            }
            return sessionData;
        }
    }


    public static void refresh(String sessionId) throws IOException {

        try (Jedis jedis = RedisClient.getConnection()) {
            jedis.expire(sessionId, getSessionTimeout());
        }
    }


    public static void remove(String sessionId) {

        try (Jedis jedis = RedisClient.getConnection()) {
            jedis.del(sessionId);
        }
    }


    public static void setSessionTimeout(Integer timeout) {

        try (Jedis jedis = RedisClient.getConnection()) {
            jedis.set(SESSION_TIMEOUT_KEY, timeout == null ? SESSION_TIMEOUT_DEFAULT : timeout.toString());
        }
    }


    private static Integer getSessionTimeout() {

        try (Jedis jedis = RedisClient.getConnection()) {
            String timeout = jedis.get(SESSION_TIMEOUT_KEY);
            if (timeout == null) {
                timeout = SESSION_TIMEOUT_DEFAULT;
                jedis.set(SESSION_TIMEOUT_KEY, timeout);
            }
            return Integer.valueOf(timeout);
        }
    }
}
*/