package com.coder4.sbmvt.redis.configuration;

import com.coder4.sbmvt.redis.utils.RedissonUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author coder4
 */
@Configuration
@ConfigurationProperties(prefix = "redis")
public class RedissonAutoConfiguration {

    // server list
    private String server;

    // redis password
    private String password;

    // connection pool size, default 128
    private int connPoolSize = 128;

    // retry interval in ms
    private int retryInterval = 100;

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient createRedissonClient() throws IOException {
        if (getServer() == null || getServer().isEmpty()) {
            throw new IllegalArgumentException("server is empty");
        }

        Config config = new Config();

        config.useSingleServer()
                .setAddress(RedissonUtils.wrapSchema(server))
                .setPassword(password)
                .setRetryInterval(retryInterval)
                .setConnectionPoolSize(connPoolSize);

        return Redisson.create(config);
    }


    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getConnPoolSize() {
        return connPoolSize;
    }

    public void setConnPoolSize(int connPoolSize) {
        this.connPoolSize = connPoolSize;
    }

    public int getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(int retryInterval) {
        this.retryInterval = retryInterval;
    }
}