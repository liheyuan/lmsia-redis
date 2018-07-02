package com.coder4.sbmvt.redis.configuration;

import com.coder4.sbmvt.redis.utils.RedissonUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;

/**
 * @author coder4
 */
@Configuration
@ConfigurationProperties(prefix = "redis-sentinel")
public class RedissonSentinelAutoConfiguration {

    // server list
    private String sentinelServerList;

    // sentinel master name
    private String masterName;

    // redis password
    private String password;

    // connection pool size, default 128
    private int connPoolSize = 128;

    // retry interval in ms
    private int retryInterval = 100;

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient createRedissonClient() throws IOException {
        List<String> sentinelAddrs = RedissonUtils.splitStr(sentinelServerList);
        if (sentinelAddrs == null || sentinelAddrs.size() == 0) {
            throw new IllegalArgumentException("sentinel address is empty");
        }

        Config config = new Config();

        config.useSentinelServers()
                .setMasterName(masterName)
                .addSentinelAddress(sentinelAddrs.stream().map(RedissonUtils::wrapSchema).toArray(String[]::new))
                .setPassword(password)
                .setMasterConnectionPoolSize(connPoolSize)
                .setSlaveConnectionPoolSize(connPoolSize)
                .setRetryInterval(retryInterval)
                .setReadMode(ReadMode.MASTER);

        return Redisson.create(config);
    }


    public String getSentinelServerList() {
        return sentinelServerList;
    }

    public void setSentinelServerList(String sentinelServerList) {
        this.sentinelServerList = sentinelServerList;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
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