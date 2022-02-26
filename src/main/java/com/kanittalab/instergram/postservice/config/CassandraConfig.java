package com.kanittalab.instergram.postservice.config;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.io.IOException;
import java.nio.file.Paths;

@Configuration
public class CassandraConfig {
    @Value("${spring.cassandra.secure-connect-bundle}")
    private String secureConnectBundlePath;

    @Value("${spring.cassandra.client-id}")
    private String username;

    @Value("${spring.cassandra.client-secret}")
    private String password;

    @Value("${spring.cassandra.keyspace}")
    private String keyspaceName;

    @Bean
    public CqlSession cqlSession() throws IOException {
        final CqlSession cqlSession = CqlSession.builder()
                .withCloudSecureConnectBundle(Paths.get(new ClassPathResource(secureConnectBundlePath).getURI()))
                .withAuthCredentials(username, password)
                .withKeyspace(keyspaceName)
                .build();
        return cqlSession;
    }

    @Bean
    public CassandraTemplate cassandraTemplate() throws IOException{
        return new CassandraTemplate(cqlSession());
    }
}