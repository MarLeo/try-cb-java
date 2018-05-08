/**
 * Copyright (C) 2015 Couchbase, Inc.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALING
 * IN THE SOFTWARE.
 */
package trycb.config;


import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
@EnableConfigurationProperties({CouchbaseProperties.class})
public class Database {

    @Value("${storage.host}")
    private String host;

    @Value("${storage.bucket}")
    private String bucket;

    @Value("${storage.username}")
    private String username;

    @Value("${storage.password}")
    private String password;

    private CouchbaseProperties couchbaseProperties;

    public Database() {
        Yaml yaml = new Yaml();

        try(InputStream in = Files.newInputStream(Paths.get("config/couchbase.yml"))) {
            couchbaseProperties = yaml.loadAs(in, CouchbaseProperties.class);
            System.out.println("Properties: " + couchbaseProperties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*@Autowired
    public Database(CouchbaseProperties couchbaseProperties) {
        this.couchbaseProperties = couchbaseProperties;
    }*/

    @Bean
    public Cluster couchbaseCluster() {
        CouchbaseCluster cluster = CouchbaseCluster.create(/*host*/couchbaseProperties.getStorage().getHost());
        // PasswordAuthenticator authenticator = new PasswordAuthenticator(username, password);
        cluster.authenticate(couchbaseProperties.getStorage().getUsername(), couchbaseProperties.getStorage().getPassword()/*authenticator*/);
        return cluster;
    }

    @Bean
    public Bucket loginBucket() {
        return couchbaseCluster().openBucket(couchbaseProperties.getStorage().getBucket());
    }

}
