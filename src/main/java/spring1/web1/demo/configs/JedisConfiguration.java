package spring.web.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPooled;
import spring.web.api.service.RedisDetailsConfig;

@Configuration
public class JedisConfiguration {
    @Autowired
    protected RedisDetailsConfig redisDetailsConfig;

    @Bean
    public JedisPooled initializeJedis() {
        return new JedisPooled(redisDetailsConfig.getHost(), redisDetailsConfig.getPort());
    }
}
