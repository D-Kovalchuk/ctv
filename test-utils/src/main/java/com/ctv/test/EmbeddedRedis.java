package com.ctv.test;

import org.junit.After;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.embedded.RedisServer;

import java.io.IOException;

import static java.util.Objects.nonNull;

/**
 * @author Dmitry Kovalchuk
 */
public class EmbeddedRedis extends ExternalResource {

    private static final Logger log = LoggerFactory.getLogger(EmbeddedRedis.class);

    private RedisServer redisServer;

    private Jedis jedis;

    public EmbeddedRedis(Integer port) {
        jedis = new Jedis("localhost", port);
        try {
            redisServer = new RedisServer(port);
        } catch (IOException e) {
            log.warn("Can't create RedisServer instance", e);
        }
    }

    @Override
    protected void before() throws Throwable {
        redisServer.start();
    }

    @Override
    protected void after() {
        if (nonNull(redisServer)) {
            try {
                redisServer.stop();
            } catch (InterruptedException e) {
                log.warn("Can't stop redis", e);
            }
        }
    }

    @After
    public void clean() {
        log.debug("delete all keys");
        jedis.flushDB();
    }

}
