package com.athlete.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDbConfig {
//@Value injects the value from yml file to the attribute/field..
    @Value("${amazon.access.key}")
    private String awsAccessKey;
    @Value("${amazon.access.secret-key}")
    private String awsSecretKey;
    @Value("${amazon.region}")
    private String awsRegion;
    @Value("${amazon.end-point.url}")
    private String awsDynamoDBEndPoint;


    @Bean
    //DynamoDBMapper is a built in class which helps to access tables
    public DynamoDBMapper mapper(){
        return new DynamoDBMapper(amazonDynamoDBConfig());
    }
    //AmazonDynamoDB is a service interface which helps to make requests to AWS.This service has methods for
    //each action in the service API

    public AmazonDynamoDB amazonDynamoDBConfig() {

        //Each service interface has a corresponding client builder which we can use to implement the
        //interface
        //So here AmazonDynamoDB interface has a corresponding client builder, AmazonDynamoDBClientBuilder

        //To obtain an instance of the client builder, we use the static factory method .standard()

    return AmazonDynamoDBClientBuilder.standard()
            //the .withxxx methods are helpful in configuring the properties.
            //AwsClientBuilder is a container for configuration required to submit,
            //requests to a service(service endpoint and signing region)
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(awsDynamoDBEndPoint,awsRegion))
            //AWSStaticCredentialsProvider implements AWSCredentialsProvider with static credentials.
            //BasicAWSCredentials implements AWSCredentialsInterface which allows us to pass
            //aws access key and secret key in the container.
            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey,awsSecretKey)))
            //used to create client
            .build();
    }

}
