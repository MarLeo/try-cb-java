package trycb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "couchbase", ignoreUnknownFields = false)
public class CouchbaseProperties {

    public final Jwt jwt = new Jwt();

    public final Storage storage = new Storage();

    @Data
    public static class Jwt {
        private String secret;
        private Boolean enabled;
    }

    @Data
    public static class Storage {
        private String host;
        private String bucket;
        private String username;
        private String password;
        private int expiry;
    }

}
