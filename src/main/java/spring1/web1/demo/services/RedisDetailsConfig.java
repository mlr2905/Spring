package spring.web.api.service;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

//@ConstructorBinding
@ConfigurationProperties("redis")
public class RedisDetailsConfig {
    @Getter
    protected String host;
    @Getter
    protected int port;
    @Getter
    protected long ttl;

    public RedisDetailsConfig(String host, int port, long ttl) {
        this.host = host;
        this.port = port;
        this.ttl = ttl;
    }
}