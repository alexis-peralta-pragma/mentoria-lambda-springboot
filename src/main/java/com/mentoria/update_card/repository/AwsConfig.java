package com.mentoria.update_card.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsConfig {

    @Bean
    public DynamoDbClient getDynamoDbClient() {
        return DynamoDbClient.builder()
                .credentialsProvider(getProviderChain())
                .httpClientBuilder(ApacheHttpClient.builder())
                .overrideConfiguration(ClientOverrideConfiguration.builder().build())
                .region(Region.US_EAST_1)
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient getDynamoDbEnhancedClient() {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(getDynamoDbClient())
                .build();
    }

    @Bean
    public S3Client getS3Client() {
        return S3Client.builder()
                .region(Region.US_EAST_1) // Cambia esto a tu región
                .credentialsProvider(getProviderChain())
                .build();
    }

    private AwsCredentialsProvider getProviderChain(){
        return AwsCredentialsProviderChain.builder()
                .addCredentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .addCredentialsProvider(SystemPropertyCredentialsProvider.create())
                .addCredentialsProvider(ProfileCredentialsProvider.create())
                .addCredentialsProvider(ContainerCredentialsProvider.builder().build())
                .addCredentialsProvider(InstanceProfileCredentialsProvider.create())
                .build();
    }
}