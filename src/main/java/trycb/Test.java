package trycb;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;
import trycb.config.CouchbaseProperties;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;


@SpringBootApplication
@Configuration
@ComponentScan
@EnableConfigurationProperties({CouchbaseProperties.class})
public class Test {

    public static void main(String[] args) {

        String s = "man i need a taxi up to ubud";

        String result = Arrays.stream(s.split(" ")).
                max(Comparator.comparingInt(a -> a.chars().map(i -> i - 96)
                        .sum()))
                .get();

        System.out.println("Result: " + result);


        Yaml yaml = new Yaml();
        CouchbaseProperties couchbaseProperties;

        try(InputStream in = Files.newInputStream(Paths.get("config/couchbase.yml"))) {
            couchbaseProperties = yaml.loadAs(in, CouchbaseProperties.class);
            System.out.println("Properties: " + couchbaseProperties);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
