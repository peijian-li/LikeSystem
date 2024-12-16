package com.example.likesystem.conf;


import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;


    @PostConstruct
    public void createTopics() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        KafkaAdminClient kafkaAdminClient = (KafkaAdminClient) KafkaAdminClient.create(configs);
        NewTopic topic1 = new NewTopic("topic1", 10, (short) 1);
        kafkaAdminClient.createTopics(Arrays.asList(topic1));
    }




}