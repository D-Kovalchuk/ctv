package com.ctv.test;

import org.junit.rules.ExternalResource;
import redis.embedded.RedisServer;

import java.util.Objects;

/**
 * @author Dmitry Kovalchuk
 */
public class EmbeddedRedis extends ExternalResource {

    private RedisServer redisServer;

    private Integer port;

    public EmbeddedRedis(Integer port) {
        this.port = port;
    }

    @Override
    protected void before() throws Throwable {
        redisServer = new RedisServer(port);
        redisServer.start();
    }

    @Override
    protected void after() {
        if (Objects.nonNull(redisServer)) {
            try {
                redisServer.stop();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
